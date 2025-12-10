package core.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    // -----------------------------
    // 🎯 Форматы дат, часто используемые в Creatio
    // -----------------------------
    public static final DateTimeFormatter FORMAT_DD_MM_YYYY =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static final DateTimeFormatter FORMAT_YYYY_MM_DD =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // -----------------------------
    // 📌 Сегодня
    // -----------------------------
    public static LocalDate today() {
        return LocalDate.now();
    }

    // -----------------------------
    // 📌 Дата + N дней
    // -----------------------------
    public static LocalDate plusDays(int days) {
        return LocalDate.now().plusDays(days);
    }

    // -----------------------------
    // 📌 Дата - N дней
    // -----------------------------
    public static LocalDate minusDays(int days) {
        return LocalDate.now().minusDays(days);
    }

    // -----------------------------
    // 📌 Дата + N месяцев
    // -----------------------------
    public static LocalDate plusMonths(int months) {
        return LocalDate.now().plusMonths(months);
    }

    // -----------------------------
    // 📌 Форматирование под Creatio (dd.MM.yyyy)
    // -----------------------------
    public static String formatDdMmYyyy(LocalDate date) {
        return date.format(FORMAT_DD_MM_YYYY);
    }

    // -----------------------------
    // 📌 Форматирование SQL/JSON (yyyy-MM-dd)
    // -----------------------------
    public static String formatIso(LocalDate date) {
        return date.format(FORMAT_YYYY_MM_DD);
    }

    // -----------------------------
    // 📌 Парсинг строки dd.MM.yyyy → LocalDate
    // -----------------------------
    public static LocalDate parseDdMmYyyy(String value) {
        return LocalDate.parse(value, FORMAT_DD_MM_YYYY);
    }

    // -----------------------------
    // 📌 Вычисление возраста (полезно для скоринга)
    // -----------------------------
    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    // -----------------------------
    // 📌 Проверка достижение возраста
    // -----------------------------
    public static boolean isOlderThan(LocalDate birthDate, int age) {
        return calculateAge(birthDate) >= age;
    }

    // -----------------------------
    // 📌 Получить дату рождения X лет назад
    // -----------------------------
    public static LocalDate yearsAgo(int years) {
        return LocalDate.now().minusYears(years);
    }
}
