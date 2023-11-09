package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    private static final Comparator<String> BY_DAYS = new SortByDays(); 
    private static final Comparator<String> BY_ORDER = new SortByMonthOrder();

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

        Month(final int numDays) {
            this.numDays = numDays;
        }

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

    private static class SortByMonthOrder implements Comparator<String> {
        @Override
        public int compare(final String month1, final String month2) {
            return Month.fromString(month1).compareTo(Month.fromString(month2));
        }
    }

    private static class SortByDays implements Comparator<String> {
        @Override
        public int compare(final String month1, final String month2) {
            return Integer.compare(Month.fromString(month1).numDays, Month.fromString(month2).numDays);
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return BY_DAYS;
    }

    @Override
    public Comparator<String> sortByOrder() {
        return BY_ORDER;
    }
}
