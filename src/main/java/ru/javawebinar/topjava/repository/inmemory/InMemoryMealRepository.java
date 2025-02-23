package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

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
        } finally {
            lock.writeLock().lock();
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
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        lock.readLock().lock();
        try {
            return mealsMap.values().stream()
                    .filter(meal -> meal.getUserId() == userId)
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
}

