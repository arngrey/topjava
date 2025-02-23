package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public void delete(int userId, int mealId) {
        checkNotFound(repository.delete(userId, mealId), mealId);
    }

    public Meal get(int userId, int mealId) {
        return checkNotFound(repository.get(userId, mealId), mealId);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(int userId, Meal meal) {
        checkNotFound(repository.save(userId, meal), meal.getId());
    }
}