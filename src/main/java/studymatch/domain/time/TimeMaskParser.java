package studymatch.domain.time;

import java.util.Arrays;
import java.util.List;

public final class TimeMaskParser {

    private TimeMaskParser() {
    }

    public static TimeMask parse(String expression) {
        if (expression == null || expression.isBlank()) {
            return TimeMask.fromMatrix(emptySlots());
        }

        boolean[][] slots = emptySlots();
        for (String token : tokensOf(expression)) {
            fillSlotsForToken(slots, token);
        }
        return TimeMask.fromMatrix(slots);
    }

    private static boolean[][] emptySlots() {
        return new boolean[TimeMask.DAYS][TimeMask.HOURS];
    }

    private static List<String> tokensOf(String expression) {
        return Arrays.stream(expression.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    private static void fillSlotsForToken(boolean[][] slots, String token) {
        Day day = Day.fromLabel(token.substring(0, 1));
        TimeRange range = parseRange(token.substring(1));

        for (int hour = range.start(); hour <= range.end(); hour++) {
            slots[day.index()][hour] = true;
        }
    }

    private static TimeRange parseRange(String part) {
        int dash = part.indexOf('-');
        if (dash < 0) {
            int hour = Integer.parseInt(part);
            return new TimeRange(hour, hour);
        }
        int start = Integer.parseInt(part.substring(0, dash));
        int end = Integer.parseInt(part.substring(dash + 1));
        return new TimeRange(start, end);
    }
}