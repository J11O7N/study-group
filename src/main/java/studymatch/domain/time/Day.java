package studymatch.domain.time;

public enum Day {
    MON("월", 0), TUE("화", 1), WED("수", 2), THU("목", 3),
    FRI("금", 4), SAT("토", 5), SUN("일", 6);

    private final String label;
    private final int index;

    Day(String label, int index) {
        this.label = label; this.index = index;
    }

    public String label() {
        return label;
    }
    public int index() {
        return index;
    }

    public static Day fromLabel(String label) {
        for (Day d : values()) {
            if (d.label.equals(label)) {
                return d;
            }
        }
        throw new IllegalArgumentException("[ERROR] 요일 형식 오류: " + label);
    }

    public static Day fromIndex(int idx) {
        for (Day d : values()) {
            if (d.index == idx) {
                return d;
            }
        }
        throw new IllegalArgumentException("[ERROR] 요일 인덱스 오류: " + idx);
    }
}
