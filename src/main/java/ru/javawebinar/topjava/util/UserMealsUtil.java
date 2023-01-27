package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        meals.sort(new Comparator<UserMeal>() {
            @Override
            public int compare(UserMeal o1, UserMeal o2) {
                if (o1.getDateTime().isEqual(o2.getDateTime()))
                    return 0;
                return o1.getDateTime().isBefore(o2.getDateTime()) ? 1 : -1;
            }
        });

        HashMap<LocalDate, Integer> mapDateAndCalories = new HashMap<>();
        LocalDate localDate = meals.get(0).getDateTime().toLocalDate();
        int calories = 0;
        for (UserMeal um : meals) {
            LocalDate localDateForEach = um.getDateTime().toLocalDate();
            if (localDate.equals(localDateForEach)) {
                calories += um.getCalories();
            } else {
                mapDateAndCalories.put(localDate, calories);
                calories = um.getCalories();
                localDate = localDateForEach;
            }
        }
        mapDateAndCalories.put(localDate, calories);
        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMeal um : meals
        ) {
            LocalDate currentDate = um.getDateTime().toLocalDate();
            if (um.getDateTime().toLocalTime().isAfter(startTime)
                    && um.getDateTime().toLocalTime().isBefore(endTime)
                    && mapDateAndCalories.get(currentDate) >= caloriesPerDay) {
                result.add(new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(), true));


            }
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
