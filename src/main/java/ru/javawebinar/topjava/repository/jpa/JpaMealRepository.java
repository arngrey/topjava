package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User currentUser = em.find(User.class, userId);

        if (!meal.isNew()) {
            Meal existsingMeal = em.find(Meal.class, meal.getId());
            boolean isMealBelongsToCurrentUser = existsingMeal.getUser().equals(currentUser);
            if (!isMealBelongsToCurrentUser) {
                return null;
            }
        }

        meal.setUser(currentUser);
        return em.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Meal meal = em.find(Meal.class, id);

        if (meal == null) {
            return false;
        }
        boolean isMealBelongsToAnotherUser = meal.getUser().getId() != userId;
        if (isMealBelongsToAnotherUser) {
            return false;
        }
        em.remove(meal);
        return true;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);

        boolean isMealBelongsToAnotherUser = meal.getUser().getId() != userId;
        if (isMealBelongsToAnotherUser) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id = :userId", Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id = :userId " +
                "AND m.dateTime >= :startDateTime AND m.dateTime < :endDateTime ORDER BY m.dateTime DESC", Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}