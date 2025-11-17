package studymatch.domain.selection;

import java.util.List;
import studymatch.domain.person.Leader;
import studymatch.domain.person.Applicant;
import studymatch.domain.time.TimeMask;

public record SelectionResult(
        Leader leader,
        List<Applicant> selectedApplicants,
        TimeMask commonTime,
        String recommendedPlace
) {
    public SelectionResult(Leader leader, List<Applicant> applicants, TimeMask commonTime) {
        this(leader, applicants, commonTime, "강남 스터디카페 A");
    }
}
