package studymatch.domain.time;

public record TimeRange(int start, int end) {

    public TimeRange {
        if (start < 0 || end > 23 || start > end) {
            throw new IllegalArgumentException("[ERROR] 시간 범위 오류: " + start + "-" + end);
        }
    }
}
