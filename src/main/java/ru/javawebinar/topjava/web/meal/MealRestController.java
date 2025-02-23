package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealFiltersTo;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.MealWithExcessTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

@Controller
public class MealRestController {
    public static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<MealWithExcessTo> getAll() {
        log.info("getAll");
        return getAll(new MealFiltersTo());
    }

    public Collection<MealWithExcessTo> getAll(MealFiltersTo mealFiltersTo) {
        log.info("getAll with meal filters");
        Collection<Meal> meals =  service.getAll(SecurityUtil.authUserId(), mealFiltersTo.getDateFrom(), mealFiltersTo.getDateTo());
        return MealsUtil.getFilteredTos(meals, SecurityUtil.authUserCaloriesPerDay(), mealFiltersTo.getTimeFrom(), mealFiltersTo.getTimeTo());
    }

    public MealTo get(int id) {
        log.info("get {}", id);
        Meal meal = service.get(SecurityUtil.authUserId(), id);
        return MealTo.of(meal);
    }

    public MealTo create(MealTo mealTo) {
        log.info("create {}", mealTo);
        Meal creatingMeal = mealTo.toMeal(SecurityUtil.authUserId());
        checkIsNew(creatingMeal);
        Meal createdMeal = service.create(SecurityUtil.authUserId(), creatingMeal);
        return MealTo.of(createdMeal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(SecurityUtil.authUserId(), id);
    }

    public void update(MealTo mealTo, int id) {
        log.info("update {} with id={}", mealTo, id);
        Meal creatingMeal = mealTo.toMeal(SecurityUtil.authUserId());
        assureIdConsistent(creatingMeal, id);
        service.update(SecurityUtil.authUserId(), creatingMeal);
    }
}
