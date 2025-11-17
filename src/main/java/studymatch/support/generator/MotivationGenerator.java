package studymatch.support.generator;

import java.util.List;
import java.util.Random;

public class MotivationGenerator {
    private static final List<String> TEMPLATES = List.of(
            "꾸준히 공부하는 습관을 만들고 싶어요.",
            "함께 성장하며 목표를 이루고 싶습니다.",
            "백엔드 심화 개념을 팀원들과 정리하고 싶어요.",
            "프로젝트 경험을 쌓고 싶어요.",
            "매주 일정한 루틴으로 개발 공부를 하고 싶습니다.",
            "끝까지 포기하지 않고 완주하고 싶습니다.",
            "저는 도전정신이 강한사람입니다."
    );

    public static String randomMotivation() {
        return TEMPLATES.get(new Random().nextInt(TEMPLATES.size()));
    }
}
