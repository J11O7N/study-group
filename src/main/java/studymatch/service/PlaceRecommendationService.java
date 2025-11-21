package studymatch.service;

import studymatch.domain.person.Applicant;
import studymatch.domain.person.Leader;
import studymatch.domain.place.Cafe;
import studymatch.domain.place.RegionDistanceTable;

import java.util.List;

public class PlaceRecommendationService {

    private final List<Cafe> cafes = List.of(
            new Cafe("루프스터디카페 강남점", "강남"),
            new Cafe("작심스터디 홍대점", "홍대"),
            new Cafe("토즈 신촌점", "신촌"),
            new Cafe("토즈 건대점", "건대")
    );

    public Cafe recommend(Leader leader, List<Applicant> members) {
        List<String> regions = members.stream()
                .map(Applicant::region)
                .toList();

        regions = new java.util.ArrayList<>(regions);
        regions.addAll(leader.regions());

        String center = findCentralRegion(regions);

        return cafes.stream()
                .min((c1, c2) -> Integer.compare(
                        RegionDistanceTable.distance(center, c1.region()),
                        RegionDistanceTable.distance(center, c2.region())
                )).orElse(null);
    }

    private String findCentralRegion(List<String> regions) {
        int bestScore = Integer.MAX_VALUE;
        String bestRegion = regions.get(0);

        for (String candidate : regions) {
            int sum = 0;
            for (String r : regions) sum += RegionDistanceTable.distance(candidate, r);
            if (sum < bestScore) {
                bestScore = sum;
                bestRegion = candidate;
            }
        }
        return bestRegion;
    }
}
