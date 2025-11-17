package studymatch.view;

import studymatch.domain.person.Applicant;
import studymatch.domain.person.Leader;
import studymatch.domain.selection.SelectionResult;
import studymatch.domain.time.TimeMaskFormatter;

public class OutputView {

    public void printResult(SelectionResult result) {

        Leader leader = result.leader();

        System.out.println("\n[선정 결과]");
        System.out.println("- 리더: " + leader.name() + " / " + leader.field());

        System.out.println("- 공통 시간:");
        System.out.println(TimeMaskFormatter.format(result.commonTime()));

        System.out.println("- 인원 (" + result.selectedApplicants().size() + "명):");

        for (Applicant a : result.selectedApplicants()) {
            System.out.println("  • "
                    + a.name() + " / " + a.field()
                    + " / 지역: " + a.region()
                    + " / 가능 시간: " + TimeMaskFormatter.format(a.availableTime())
                    + " / 동기: " + a.motivation());
        }
    }
}
