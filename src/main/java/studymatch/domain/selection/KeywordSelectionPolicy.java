package studymatch.domain.selection;

import studymatch.domain.person.Applicant;
import studymatch.domain.person.Leader;
import studymatch.domain.time.TimeMask;

import java.util.List;
import java.util.stream.Collectors;

public class KeywordSelectionPolicy {

    public static SelectionResult select(Leader leader, List<Applicant> applicants) {
        List<Applicant> filtered = applicants;
        filtered = filterByField(leader, filtered);
        filtered = filterByRegion(leader, filtered);
        filtered = filterByTime(leader, filtered);
        filtered = filterByKeyword(leader, filtered);

        List<Applicant> finalList = limitCount(filtered, 4);
        TimeMask common = calculateCommonTime(leader, finalList);

        return new SelectionResult(leader, finalList, common);
    }

    private static List<Applicant> filterByField(Leader leader, List<Applicant> list) {
        return list.stream()
                .filter(a -> a.field().equals(leader.field()))
                .collect(Collectors.toList());
    }

    private static List<Applicant> filterByRegion(Leader leader, List<Applicant> list) {
        return list.stream()
                .filter(a -> leader.regions().contains(a.region()))
                .collect(Collectors.toList());
    }

    private static List<Applicant> filterByTime(Leader leader, List<Applicant> list) {
        return list.stream()
                .filter(a -> !leader.availableTime()
                        .and(a.availableTime())
                        .isEmpty())
                .collect(Collectors.toList());
    }

    private static List<Applicant> filterByKeyword(Leader leader, List<Applicant> list) {
        return list.stream()
                .filter(a -> leader.keywords().stream()
                        .anyMatch(k -> a.motivation().contains(k)))
                .collect(Collectors.toList());
    }

    private static List<Applicant> limitCount(List<Applicant> list, int limit) {
        return list.stream().limit(limit).collect(Collectors.toList());
    }

    private static TimeMask calculateCommonTime(Leader leader, List<Applicant> list) {
        TimeMask mask = leader.availableTime();
        for (Applicant a : list) {
            mask = mask.and(a.availableTime());
        }
        return mask;
    }
}
