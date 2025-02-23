package ru.javawebinar.topjava.to;

import java.time.LocalDateTime;

public class MealWithExcessTo extends MealTo {

    private final boolean excess;

    public MealWithExcessTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        super(id, dateTime, description, calories);
        this.excess = excess;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + getId() +
                ", dateTime=" + getDateTime() +
                ", description='" + getDescription() + '\'' +
                ", calories=" + getCalories() +
                ", excess=" + excess +
                '}';
    }
}
