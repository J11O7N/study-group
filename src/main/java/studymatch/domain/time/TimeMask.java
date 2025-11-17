package studymatch.domain.time;

public final class TimeMask {
    public static final int DAYS = 7;
    public static final int HOURS = 24;

    private final boolean[][] availability; // [day][hour]

    private TimeMask(boolean[][] matrix) {
        this.availability = deepCopy(matrix);
    }

    static TimeMask fromMatrix(boolean[][] matrix) {
        return new TimeMask(matrix);
    }

    public TimeMask and(TimeMask other) {
        boolean[][] r = new boolean[DAYS][HOURS];
        for (int d = 0; d < DAYS; d++) {
            for (int h = 0; h < HOURS; h++) {
                r[d][h] = availability[d][h] && other.availability[d][h];
            }
        }
        return new TimeMask(r);
    }

    public boolean isEmpty() {
        for (int d = 0; d < DAYS; d++) {
            for (int h = 0; h < HOURS; h++) {
                if (availability[d][h]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean[][] matrix() {
        return deepCopy(availability);
    }

    private static boolean[][] deepCopy(boolean[][] src) {
        boolean[][] dst = new boolean[DAYS][HOURS];
        for (int d = 0; d < DAYS; d++) {
            System.arraycopy(src[d], 0, dst[d], 0, HOURS);
        }
        return dst;
    }


}
