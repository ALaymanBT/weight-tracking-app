package com.cs360_project_alayman.data.converters;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class LocalDateConverter {

    /**
     * Converts a string to a LocalDate object
     * @param dateString the string to convert
     * @return the LocalDate object representation of the string
     */
    @TypeConverter
    public static LocalDate toDate(String dateString) {
        return dateString == null ? null : LocalDate.parse(dateString);
    }

    /**
     * Converts a LocalDate object to a string
     * @param date the LocalDate object to convert
     * @return the string representation of the LocalDate object
     */
    @TypeConverter
    public static String toDateString(LocalDate date) {
        return date == null ? null : date.toString();
    }
}