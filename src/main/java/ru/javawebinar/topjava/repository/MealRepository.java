package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MealRepository implements Repository<Meal, Integer> {
    private static int nextId = 0;

    private synchronized static int getNextId() {
        return nextId++;
    }

    public static final List<Meal> meals = new ArrayList<>(Arrays.asList(
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    ));

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public Optional<Meal> get(Integer id) {
        return meals.stream()
                .filter(meal -> meal.getId() == id)
                .findFirst();
    }

    @Override
    public void create(Meal meal) {
        meal.setId(getNextId());
        meals.add(meal);
    }

    @Override
    public void update(Meal meal) {
        Meal foundMeal = meals.stream()
                .filter(m -> m.getId() == meal.getId())
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        foundMeal.setDateTime(meal.getDateTime());
        foundMeal.setDescription(meal.getDescription());
        foundMeal.setCalories(meal.getCalories());
    }

    @Override
    public void delete(Integer id) {
        meals.removeIf(meal -> meal.getId() == id);
    }
}
