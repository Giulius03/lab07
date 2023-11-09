package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    /**
     * A comparator of months that uses the number of the days
     */
    private static final Comparator<String> BY_DAYS = new SortByDays(); 
    /**
     * A comparator of months that uses their order in a year
     */
    private static final Comparator<String> BY_ORDER = new SortByMonthOrder();

    /**
     * This enumeration represents the months of a year
     */
    private enum Month {
        JANUARY(31), 
        FEBRUARY(28), 
        MARCH(31),
        APRIL(30), 
        MAY(31), 
        JUNE(30), 
        JULY(31),
        AUGUST(31), 
        SEPTEMBER(30), 
        OCTOBER(31), 
        NOVEMBER(30), 
        DECEMBER(31);

        private int numDays;

        /**
         * Constructor with one parameter (the number of the days of the month)
         * @param numDays the number of the days of the month
         */
        Month(final int numDays) {
            this.numDays = numDays;
        }

        /**
         * This method associates a month to the string given as parameter
         * @param text the string that has to be associated with the months
         * @throws IllegalArgumentException if there are no matches or there are more than one
         * @return the month associated to the string
         */
        public static Month fromString(final String text) {
            Objects.requireNonNull(text);
            try {
                return Month.valueOf(text);
            } catch (IllegalArgumentException e) {
                Month match = null;
                for (final Month month: values()) {
                    if (month.toString().toLowerCase(Locale.ROOT).startsWith(text.toLowerCase(Locale.ROOT))) {
                        if (match != null) {
                            throw new IllegalArgumentException(
                                text + " is ambiguous: both " + match + " and " + month + " would be valid matches",
                                e
                            );
                        }
                        match = month;
                    }
                }
                if (match == null) {
                    throw new IllegalArgumentException("No matching months for " + text, e);
                }
                return match;
            }
        }
    }

    /**
     * Innested class that implements Comparator<String>, which compare two months
     * by their order in a year
     */
    private static class SortByMonthOrder implements Comparator<String> {
        /**
         * @return a negative integer or a positive integer as the first argument is less 
         * than, or greater than the second.
         */
        @Override
        public int compare(final String month1, final String month2) {
            return Month.fromString(month1).compareTo(Month.fromString(month2));
        }
    }

    /**
     * Innested class that implements Comparator<String>, which compare two months
     * by the number of their days
     */
    private static class SortByDays implements Comparator<String> {
        /**
         * @return a negative integer, zero, or a positive integer as the first argument is less 
         * than, equal to, or greater than the second.
         */
        @Override
        public int compare(final String month1, final String month2) {
            return Integer.compare(Month.fromString(month1).numDays, Month.fromString(month2).numDays);
        }
    }

    /**
     * This method is used to sort two months by the number of their days
     * @return the static field BY_DAYS
     */
    @Override
    public Comparator<String> sortByDays() {
        return BY_DAYS;
    }

    /**
     * This method is used to sort two months by their order in a year
     * @return the static field BY_ORDER
     */
    @Override
    public Comparator<String> sortByOrder() {
        return BY_ORDER;
    }
}
