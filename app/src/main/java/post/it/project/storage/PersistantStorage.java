package post.it.project.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Михаил on 11.12.2016.
 */

public class PersistantStorage {
    public static final String STORAGE_NAME = "StorageName";

    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;
    private static boolean[] networks = {
            false,
            false,
            false,
            false
    };

    public static void init(Context cntxt) {
        context = cntxt;
    }

    private static void init() {
        settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public static void addProperty(String name, boolean value) {
        if (settings == null) {
            init();
        }
        switch (name) {
            case "vk":
                networks[0] = value;
                break;
            case "fb":
                networks[1] = value;
                break;
            default:
                return;
        }

//        editor.putString(name, value);
        editor.commit();
    }

    public static boolean getProperty(String name) {
        if (settings == null) {
            init();
        }
        switch (name) {
            case "vk":
                return networks[0];
            case "fb":
                return networks[1];
            default:
                return false;
        }
//        return settings.getString(name, null);
    }
}
