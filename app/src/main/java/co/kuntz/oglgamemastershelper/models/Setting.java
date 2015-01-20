package co.kuntz.oglgamemastershelper.models;

import com.orm.SugarRecord;

public class Setting extends SugarRecord<Setting> {
    public String key;
    public String value;

    public Setting() {}

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(key);
    }

    public static String get(String key) {
        return find(Setting.class, "key = ?", new String[]{key}).get(0).value;
    }
}
