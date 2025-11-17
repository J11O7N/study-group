package studymatch.util;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtils {
    private RandomUtils() {}
    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static <T> T pickOne(List<T> list) {
        return list.get(randInt(0, list.size() - 1));
    }
}
