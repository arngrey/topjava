package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.model.Meal.BY_DATETIME_DESC;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach((meal) -> save(meal.getUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        lock.writeLock().lock();
        try {
            if (meal.getUserId() != userId) {
                return null;
            }
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                mealsMap.put(meal.getId(), meal);
                return meal;
            }
            // handle case: update, but not present in storage
            return mealsMap.computeIfPresent(
                    meal.getId(),
                    (id, oldMeal) -> oldMeal.getUserId() == userId ? meal : null
            );
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean delete(int userId, int id) {
        lock.writeLock().lock();
        try {
            Meal meal = mealsMap.get(id);
            if (meal.getUserId() != userId) {
                return false;
            }
            return mealsMap.remove(id) != null;
        } catch (NullPointerException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Meal get(int userId, int id) {
        lock.readLock().lock();
        try {
            Meal meal = mealsMap.get(id);
            if (meal.getUserId() != userId) {
                return null;
            }
            return meal;
        } catch (NullPointerException e) {
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getAllFiltered(userId, meal -> true);
    }

    public Collection<Meal> getBetweenHalfOpen(int userId, @Nullable LocalDate dateFrom , @Nullable LocalDate dateTo) {
        return getAllFiltered(userId, meal -> Util.isBetweenHalfOpen(meal.getDate(), dateFrom, dateTo));
    }

    private Collection<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        lock.readLock().lock();
        try {
            return mealsMap.values().stream()
                    .filter(meal -> meal.getUserId() == userId)
                    .filter(filter)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .sorted(BY_DATETIME_DESC)
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
}

