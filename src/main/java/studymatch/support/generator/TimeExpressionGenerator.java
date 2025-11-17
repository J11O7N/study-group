package studymatch.support.generator;

import studymatch.util.RandomUtils;

import java.util.List;

public final class TimeExpressionGenerator {
    private static final List<String> DAYS =
            List.of("월", "화", "수", "목", "금", "토", "일");

    private TimeExpressionGenerator() {}

    public static String randomExpression() {
        String day = RandomUtils.pickOne(DAYS);

        int start = RandomUtils.randInt(9, 21);
        int end = RandomUtils.randInt(start + 1, 22);

        return day + start + "-" + end;
    }
}
