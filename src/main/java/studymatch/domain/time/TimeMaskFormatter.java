package studymatch.domain.time;

public class TimeMaskFormatter {
    public static String format(TimeMask mask) {
        StringBuilder sb = new StringBuilder();
        boolean[][] a = mask.matrix();

        String[] days = {"월","화","수","목","금","토","일"};

        for (int d = 0; d < 7; d++) {
            boolean found = false;
            for (int h = 0; h < 24; h++) {
                if (a[d][h]) {
                    if (!found) {
                        sb.append(days[d]).append(":");
                        found = true;
                    }
                    sb.append(" ").append(h);
                }
            }
            if (found) sb.append("\n");
        }
        return sb.toString().trim();
    }
}
