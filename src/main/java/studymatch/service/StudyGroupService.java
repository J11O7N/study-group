package studymatch.service;

import java.util.List;
import studymatch.domain.person.Applicant;
import studymatch.domain.person.Leader;
import studymatch.domain.place.Cafe;
import studymatch.support.generator.ApplicantGenerator;
import studymatch.domain.selection.KeywordSelectionPolicy;
import studymatch.domain.selection.SelectionResult;

public class StudyGroupService {
    private final ApplicantGenerator generator = new ApplicantGenerator();
    private final PlaceRecommendationService placeService = new PlaceRecommendationService();

    public List<Applicant> generateApplicants() {
        return generator.createApplicants(100); // 100명 생성
    }

    public SelectionResult matchStudyGroup(Leader leader, List<Applicant> applicants) {
        SelectionResult base = KeywordSelectionPolicy.select(leader, applicants);

        // 장소 추천 추가
        Cafe cafe = placeService.recommend(leader, base.selectedApplicants());

        return new SelectionResult(
                base.leader(),
                base.selectedApplicants(),
                base.commonTime(),
                cafe
        );
    }
}
