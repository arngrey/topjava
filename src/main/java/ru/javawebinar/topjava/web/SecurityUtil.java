package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    public static final int ADMIN_USER_ID = 1;

    private static int authUserId = ADMIN_USER_ID;

    public static int getAuthUserId() {
        return authUserId;
    }

    public static void setAuthUserId(int userId) { authUserId = userId; }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}