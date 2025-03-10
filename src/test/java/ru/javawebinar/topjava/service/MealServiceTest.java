package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.GUEST_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@Qualifier("InMemoryMealRepository")
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService mealService;

    @Test
    public void get_whenGetUserMeal_thenReturnUserMeal() {
        Meal meal = mealService.get(USER_FIRST_BREAKFAST_MEAL_ID, USER_ID);
        MealTestData.assertMatch(meal, userFirstBreakfast);
    }

    @Test
    public void get_whenGetSomeoneElseMeal_thenThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> mealService.get(USER_FIRST_BREAKFAST_MEAL_ID, GUEST_ID));
    }

    @Test
    public void delete_whenDeleteUserMeal_thenThrowNotFoundExceptionOnGet() {
        mealService.delete(USER_FIRST_BREAKFAST_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(USER_FIRST_BREAKFAST_MEAL_ID, USER_ID));
    }

    @Test
    public void delete_whenDeleteSomeoneElseMeal_thenThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> mealService.delete(USER_FIRST_BREAKFAST_MEAL_ID, GUEST_ID));
    }

    @Test
    public void getBetweenInclusive_whenGetUserMealsAtFirstJanuary2025_thenReturnListOfOneMeal() {
        List<Meal> meals = mealService.getBetweenInclusive(FIRST_OF_JANUARY_2025, FIRST_OF_JANUARY_2025, USER_ID);
        assertMatch(meals, userMealsOfFirstJanuary2025);
    }

    @Test
    public void getAll_whenGetAllUserMeals_thenReturnAllUserMeal() {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals, userMeals);
    }

    @Test
    public void update_whenUpdateUserFirstBreakfastMeal_thenReturnUpdated() {
        Meal updated = getUserFirstBreakfastUpdated();
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(USER_FIRST_BREAKFAST_MEAL_ID, USER_ID), getUserFirstBreakfastUpdated());
    }

    @Test
    public void update_whenUpdateSomeoneElseMeal_thenThrowNotFoundException() {
        Meal updated = getUserFirstBreakfastUpdated();
        assertThrows(NotFoundException.class, () -> mealService.update(updated, GUEST_ID));
    }

    @Test
    public void update_whenUpdateWithDuplicatedDateTime_thenThrowDataAccessException() {
        Meal updated = new Meal(userFirstBreakfast);
        updated.setDateTime(USER_SECOND_BREAKFAST_MEAL_DATETIME);
        assertThrows(DataAccessException.class, () -> mealService.update(updated, USER_ID));
    }

    @Test
    public void create_whenCreateNewMeal_thenReturnCreatedMeal() {
        Meal created = mealService.create(getNew(), USER_ID);
        Meal expectedMeal = getNew();
        expectedMeal.setId(created.getId());
        assertMatch(created, expectedMeal);
        assertMatch(mealService.get(created.getId(), USER_ID), expectedMeal);
    }

    @Test
    public void create_whenCreateMealWithDuplicatedDateTime_thenThrowDataAccessException() {
        Meal newMeal = getNew();
        newMeal.setDateTime(USER_FIRST_BREAKFAST_MEAL_DATETIME);
        assertThrows(DataAccessException.class, () -> mealService.create(newMeal, USER_ID));
    }
}