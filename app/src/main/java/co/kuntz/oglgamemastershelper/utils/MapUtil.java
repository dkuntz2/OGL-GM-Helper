package co.kuntz.oglgamemastershelper.utils;

import java.util.Map;

/**
 * Created by don on 1/19/15.
 */
public class MapUtil {

    public static String get(Map<String, String> map, String key, String defaultValue) {
        String v = map.get(key);
        return v == null ? defaultValue : v;
    }

}
