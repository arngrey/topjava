package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealRepository implements Repository<Meal> {
    public static final List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public void create(Meal meal) {
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
    public void delete(Meal meal) {
        meals.remove(meal);
    }
}
