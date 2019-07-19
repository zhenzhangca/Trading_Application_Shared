package ca.jrvs.apps.trading.util;

import java.util.Arrays;

public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEmpty(String... strs) {
        return Arrays.stream(strs).anyMatch(str -> str == null | str.isEmpty());
    }


}
