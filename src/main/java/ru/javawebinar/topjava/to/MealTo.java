package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public class MealTo {
    private final Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public static MealTo of(Meal meal) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }

    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public Meal toMeal(int userId) {
        return new Meal(id, dateTime, description, calories, userId);
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
