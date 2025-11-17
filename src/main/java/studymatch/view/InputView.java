package studymatch.view;

import studymatch.domain.person.Leader;
import studymatch.domain.time.TimeMask;
import studymatch.domain.time.TimeMaskParser;
import studymatch.error.ParsingException;
import studymatch.error.ValidationException;

import java.util.*;

public class InputView {
    private final Scanner sc = new Scanner(System.in);

    public Leader inputLeader() {
        String name  = askNonBlank("이름");
        String field = askNonBlank("분야");
        Set<String> regions = askRegions();
        TimeMask mask = askTimeMask();
        List<String> keywords = askKeywords(3);
        return new Leader(name, field, regions, mask, keywords);
    }

    public int askApplicantCount() {
        while (true) {
            System.out.print("뽑을 지원자 수: ");
            String s = sc.nextLine().trim();
            try {
                int n = Integer.parseInt(s);
                if (n > 0) {
                    return n;
                }
                System.out.println("[ERROR] 양의 정수를 입력해 주세요.");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] 숫자를 입력해 주세요.");
            }
        }
    }

    private String askNonBlank(String label) {
        while (true) {
            System.out.print(label + ": ");
            String s = sc.nextLine().trim();
            if (!s.isBlank()) {
                return s;
            }
            System.out.println("[ERROR] " + label + "은(는) 비어 있을 수 없습니다.");
        }
    }

    private Set<String> askRegions() {
        while (true) {
            System.out.print("가능 지역(쉼표로 복수): ");
            String line = sc.nextLine();
            Set<String> regions = new LinkedHashSet<>();
            for (String t : line.split(",")) {
                String v = t.trim();
                if (!v.isEmpty()) {
                    regions.add(v);
                }
            }
            if (!regions.isEmpty()) {
                return regions;
            }
            System.out.println("[ERROR] 최소 1개 이상의 지역을 입력해 주세요.");
        }
    }

    private TimeMask askTimeMask() {
        while (true) {
            System.out.print("가능 시간대(예: 금20-22,토14-16): ");
            String expr = sc.nextLine().trim();
            try {
                return TimeMaskParser.parse(expr);
            } catch (ParsingException | ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<String> askKeywords(int required) {
        while (true) {
            System.out.print("선정 키워드 " + required + "개(쉼표): ");
            List<String> ks = Arrays.stream(sc.nextLine().split(","))
                    .map(String::trim).filter(s -> !s.isEmpty()).toList();
            if (ks.size() == required) {
                return ks;
            }
            System.out.println("[ERROR] 정확히 " + required + "개를 입력해 주세요.");
        }
    }
}
