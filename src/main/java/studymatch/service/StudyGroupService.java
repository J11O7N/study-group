package studymatch.service;

import java.util.List;
import studymatch.domain.person.Applicant;
import studymatch.domain.person.Leader;
import studymatch.support.generator.ApplicantGenerator;
import studymatch.domain.selection.KeywordSelectionPolicy;
import studymatch.domain.selection.SelectionResult;

public class StudyGroupService {
    private final ApplicantGenerator generator = new ApplicantGenerator();

    public List<Applicant> generateApplicants() {
        return generator.createApplicants(100); // 100명 생성
    }

    public SelectionResult matchStudyGroup(Leader leader, List<Applicant> applicants) {
        return KeywordSelectionPolicy.select(leader, applicants);
    }
}
