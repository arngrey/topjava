package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealRepository mealRepository = new MealRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("get meals");

        String action = request.getParameter("action");

        if (action == null) {
            List<MealTo> meals = MealsUtil.filteredByStreams(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
            request.setAttribute("meals", meals);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            String id = request.getParameter("id");
            mealRepository.delete(Integer.parseInt(id));
            response.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("create")) {
            request.getRequestDispatcher("/add_meal.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("update")) {
            String id = request.getParameter("id");
            Optional<Meal> meal = mealRepository.get(Integer.parseInt(id));
            if (!meal.isPresent()) {
                response.sendError(404, "Meal not found");
            }
            MealTo mealTo = MealTo.of(meal.get());
            request.setAttribute("meal", mealTo);
            request.getRequestDispatcher("/edit_meal.jsp").forward(request, response);
        } else {
            response.sendError(400, "Unknown action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("post meal");

        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
        } catch (Exception e) {
            response.sendError(400, "Invalid dateTime parameter");
            return;
        }

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        String id = request.getParameter("id");

        if (id == null) {
            Meal meal = new Meal(dateTime, description, calories);
            mealRepository.create(meal);
        } else {
            Meal meal = new Meal(Integer.parseInt(id), dateTime, description, calories);
            mealRepository.update(meal);
        }

        response.sendRedirect("meals");
    }
}
