package studymatch.domain.selection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import studymatch.domain.person.Applicant;
import studymatch.domain.person.Leader;
import studymatch.domain.time.TimeMask;
import studymatch.domain.time.TimeMaskParser;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class KeywordSelectionPolicyTest {

    private Leader createLeader() {
        TimeMask time = TimeMaskParser.parse("금20-22");
        return new Leader(
                "정준",
                "백엔드",
                Set.of("강남", "홍대"),
                time,
                List.of("목표", "꾸준함", "성장")
        );
    }

    private Applicant applicant(
            String name,
            String field,
            String region,
            String timeExpr,
            String motivation
    ) {
        return new Applicant(
                name,
                field,
                region,
                TimeMaskParser.parse(timeExpr),
                motivation
        );
    }

    @Test
    @DisplayName("분야, 지역, 시간, 키워드 조건을 모두 통과하는 지원자만 선발된다")
    void select_filtersByAllConditions() {
        Leader leader = createLeader();

        Applicant ok1 = applicant("A", "백엔드", "강남", "금20-22", "꾸준함과 성장에 대한 목표");
        Applicant wrongField = applicant("B", "프론트엔드", "강남", "금20-22", "목표는 있음");
        Applicant wrongRegion = applicant("C", "백엔드", "건대", "금20-22", "성장 목표");
        Applicant wrongTime = applicant("D", "백엔드", "강남", "토20-22", "꾸준함 있어요");
        Applicant noKeyword = applicant("E", "백엔드", "강남", "금20-22", "그냥 재미로");

        List<Applicant> pool = List.of(ok1, wrongField, wrongRegion, wrongTime, noKeyword);

        SelectionResult result = KeywordSelectionPolicy.select(leader, pool);

        assertEquals(1, result.selectedApplicants().size());
        assertEquals("A", result.selectedApplicants().get(0).name());
        assertFalse(result.commonTime().isEmpty()); // 최소 한 시간 이상 공통 시간 존재
    }

    @Test
    @DisplayName("최대 인원 제한을 넘으면 상위 4명까지만 선발한다")
    void select_limitsSizeToFour() {
        Leader leader = createLeader();
        // 모두 조건을 만족하는 6명
        List<Applicant> pool = List.of(
                applicant("a", "백엔드", "강남", "금20-22", "목표"),
                applicant("b", "백엔드", "강남", "금20-22", "목표"),
                applicant("c", "백엔드", "강남", "금20-22", "목표"),
                applicant("d", "백엔드", "강남", "금20-22", "목표"),
                applicant("e", "백엔드", "강남", "금20-22", "목표"),
                applicant("f", "백엔드", "강남", "금20-22", "목표")
        );

        SelectionResult result = KeywordSelectionPolicy.select(leader, pool);

        assertTrue(result.selectedApplicants().size() <= 4);
    }
}
