package studymatch.support.generator;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import studymatch.domain.person.Applicant;
import studymatch.domain.time.TimeMaskParser;

public class ApplicantGenerator {
    private final Faker faker = new Faker(new Locale("ko"));
    private final List<String> regions = List.of("목동", "구일", "구로", "신도림", "홍대");
    private final List<String> fields = List.of("백엔드", "프론트엔드", "안드로이드");

    public List<Applicant> createApplicants(int count) {
        List<Applicant> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = faker.name().firstName();
            String field = fields.get(faker.random().nextInt(fields.size()));
            String region = regions.get(faker.random().nextInt(regions.size()));
            String motivation = MotivationGenerator.randomMotivation();
            String time = TimeExpressionGenerator.randomExpression();
            list.add(new Applicant(name, field, region, TimeMaskParser.parse(time), motivation));
        }
        return list;
    }
}
