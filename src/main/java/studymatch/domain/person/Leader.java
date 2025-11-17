package studymatch.domain.person;

import java.util.List;
import java.util.Set;

import studymatch.domain.time.TimeMask;

public record Leader(String name, String field, Set<String> regions, TimeMask availableTime, List<String> keywords) {}
