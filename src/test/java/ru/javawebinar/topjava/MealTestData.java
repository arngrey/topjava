package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_FIRST_BREAKFAST_MEAL_ID = START_SEQ + 3;
    public static final LocalDateTime USER_FIRST_BREAKFAST_MEAL_DATETIME = LocalDateTime.of(2025, 1, 1, 10, 23, 54);
    public static final Meal userFirstBreakfast = new Meal(
            USER_FIRST_BREAKFAST_MEAL_ID,
            USER_FIRST_BREAKFAST_MEAL_DATETIME,
            "User First Breakfast",
            300);

    public static final int USER_SECOND_BREAKFAST_MEAL_ID = START_SEQ + 4;
    public static final LocalDateTime USER_SECOND_BREAKFAST_MEAL_DATETIME = LocalDateTime.of(2025, 1, 2, 11, 23, 54);
    public static final Meal userSecondBreakfast = new Meal(
            USER_SECOND_BREAKFAST_MEAL_ID,
            USER_SECOND_BREAKFAST_MEAL_DATETIME,
            "User Second Breakfast",
            300);

    public static final int GUEST_BREAKFAST_MEAL_ID = START_SEQ + 5;
    public static final LocalDateTime GUEST_BREAKFAST_MEAL_DATETIME = LocalDateTime.of(2025, 1, 1, 12, 23, 54);
    public static final Meal guestBreakfast = new Meal(
            GUEST_BREAKFAST_MEAL_ID,
            GUEST_BREAKFAST_MEAL_DATETIME,
            "Guest Breakfast",
            300);

    public static final List<Meal> allMeals = new ArrayList<>();
    public static final List<Meal> userMeals = new ArrayList<>();
    public static final List<Meal> userMealsOfFirstJanuary2025 = new ArrayList<>();
    static {
        allMeals.add(userSecondBreakfast);
        allMeals.add(guestBreakfast);
        allMeals.add(userFirstBreakfast);

        userMeals.add(userSecondBreakfast);
        userMeals.add(userFirstBreakfast);

        userMealsOfFirstJanuary2025.add(userFirstBreakfast);
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2025, 1, 3, 12, 23, 54), "Test Description", 400);
    }

    public static Meal getUserFirstBreakfastUpdated() {
        Meal updated = new Meal(userFirstBreakfast);
        updated.setDateTime(LocalDateTime.of(2025, 1, 4, 12, 23, 54));
        updated.setDescription("Updated Description");
        updated.setCalories(500);
        return updated;
    }

    public static final LocalDate FIRST_OF_JANUARY_2025 = LocalDate.of(2025, 1, 1);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
