package studymatch.support.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotivationGeneratorTest {

    @Test
    @DisplayName("랜덤 지원 동기는 null 또는 빈 문자열이 아니어야 한다")
    void randomMotivation_notBlank() {
        for (int i = 0; i < 20; i++) {
            String m = MotivationGenerator.randomMotivation();
            assertNotNull(m);
            assertFalse(m.isBlank());
        }
    }
}
