package studymatch.domain.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeMaskParserTest {

    @Test
    @DisplayName("null 이나 공백 문자열은 빈 TimeMask 를 생성한다")
    void parse_blank_returnsEmptyMask() {
        TimeMask mask1 = TimeMaskParser.parse(null);
        TimeMask mask2 = TimeMaskParser.parse("   ");

        assertTrue(mask1.isEmpty());
        assertTrue(mask2.isEmpty());
    }

    @Test
    @DisplayName("단일 구간을 올바르게 파싱한다 (예: 금20-22)")
    void parse_singleRange() {
        TimeMask mask = TimeMaskParser.parse("금20-22");

        TimeMask same = TimeMaskParser.parse("금20-22");
        TimeMask disjointDay = TimeMaskParser.parse("토20-22");
        TimeMask disjointHour = TimeMaskParser.parse("금10-12");

        assertFalse(mask.and(same).isEmpty());
        assertTrue(mask.and(disjointDay).isEmpty());
        assertTrue(mask.and(disjointHour).isEmpty());
    }

    @Test
    @DisplayName("대시 없이 한 시간만 주어지면 단일 시각 구간으로 파싱한다 (예: 금20)")
    void parse_singleHour() {
        TimeMask mask = TimeMaskParser.parse("금20");
        TimeMask same = TimeMaskParser.parse("금20-20");
        TimeMask other = TimeMaskParser.parse("금21-21");

        assertFalse(mask.and(same).isEmpty());
        assertTrue(mask.and(other).isEmpty());
    }

    @Test
    @DisplayName("쉼표로 구분된 여러 구간을 모두 파싱한다 (예: 금20-22,토14-16)")
    void parse_multipleRanges() {
        TimeMask mask = TimeMaskParser.parse("금20-22,토14-16");

        TimeMask friday = TimeMaskParser.parse("금21-21");
        TimeMask saturday = TimeMaskParser.parse("토15-15");
        TimeMask sunday = TimeMaskParser.parse("일20-22");

        assertFalse(mask.and(friday).isEmpty());
        assertFalse(mask.and(saturday).isEmpty());
        assertTrue(mask.and(sunday).isEmpty());
    }

    @Test
    @DisplayName("잘못된 시간 구간은 예외를 발생시킨다")
    void parse_invalidRange_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> TimeMaskParser.parse("금25-26"));

        assertThrows(IllegalArgumentException.class,
                () -> TimeMaskParser.parse("금22-20"));
    }
}
