package studymatch.support.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import studymatch.domain.person.Applicant;
import studymatch.domain.time.TimeMask;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ApplicantGeneratorTest {

    @Test
    @DisplayName("요청한 수만큼 지원자를 생성한다")
    void createApplicants_size() {
        ApplicantGenerator generator = new ApplicantGenerator();
        List<Applicant> list = generator.createApplicants(50);

        assertEquals(50, list.size());
    }

    @Test
    @DisplayName("생성된 지원자는 이름/분야/지역/시간/동기가 유효한 값이어야 한다")
    void createApplicants_validFields() {
        ApplicantGenerator generator = new ApplicantGenerator();
        List<Applicant> list = generator.createApplicants(20);

        Set<String> validFields = Set.of("백엔드", "프론트엔드", "안드로이드");
        Set<String> validRegions = Set.of("강남", "홍대", "신촌", "잠실", "건대");

        for (Applicant a : list) {
            assertNotNull(a.name());
            assertFalse(a.name().isBlank());

            assertTrue(validFields.contains(a.field()));
            assertTrue(validRegions.contains(a.region()));

            TimeMask time = a.availableTime();
            assertNotNull(time);
            assertFalse(time.isEmpty());

            assertNotNull(a.motivation());
            assertFalse(a.motivation().isBlank());
        }
    }
}
