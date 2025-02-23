package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.MealWithExcessTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

public abstract class AbstractMealController {
    public static final Logger log = LoggerFactory.getLogger(AbstractMealController.class);

    private MealService service;

    public AbstractMealController(MealService service) {
        this.service = service;
    }

    public Collection<MealWithExcessTo> getAll(int userId) {
        log.info("getAll");
        Collection<Meal> meals =  service.getAll(userId);
        return MealsUtil.getTos(meals, SecurityUtil.authUserCaloriesPerDay());
    }

    public MealTo get(int userId, int id) {
        log.info("get {}", id);
        Meal meal = service.get(userId, id);
        return MealTo.of(meal);
    }

    public MealTo create(int userId, MealWithExcessTo mealTo) {
        log.info("create {}", mealTo);
        Meal creatingMeal = mealTo.toMeal(SecurityUtil.authUserId());
        checkIsNew(creatingMeal);
        Meal createdMeal = service.create(userId, creatingMeal);
        return MealTo.of(createdMeal);
    }

    public void delete(int userId, int id) {
        log.info("delete {}", id);
        service.delete(userId, id);
    }

    public void update(int userId, MealTo mealTo, int id) {
        log.info("update {} with id={}", mealTo, id);
        Meal creatingMeal = mealTo.toMeal(SecurityUtil.authUserId());
        assureIdConsistent(creatingMeal, id);
        service.update(userId, creatingMeal);
    }
}
