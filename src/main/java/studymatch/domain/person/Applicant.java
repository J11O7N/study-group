package studymatch.domain.person;

import studymatch.domain.time.TimeMask;

public record Applicant(String name, String field, String region, TimeMask availableTime, String motivation) {}

