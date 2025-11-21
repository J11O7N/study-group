package studymatch.domain.place;

import java.util.Map;

public class RegionDistanceTable {
    private static final Map<String, Map<String, Integer>> DISTANCES = Map.of(
            "강남", Map.of("홍대", 12, "신촌", 10, "건대", 8),
            "홍대", Map.of("강남", 12, "신촌", 2, "건대", 14),
            "신촌", Map.of("홍대", 2, "강남", 10, "건대", 15),
            "건대", Map.of("강남", 8, "홍대", 14, "신촌", 15)
    );

    public static int distance(String a, String b) {
        if (!DISTANCES.containsKey(a) || !DISTANCES.get(a).containsKey(b)) return 999;
        return DISTANCES.get(a).get(b);
    }
}
