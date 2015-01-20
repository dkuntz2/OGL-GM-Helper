package co.kuntz.oglgamemastershelper.database;

import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.orm.Database;
import com.orm.SugarApp;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import co.kuntz.oglgamemastershelper.models.Creature;
import co.kuntz.oglgamemastershelper.utils.CreatureParser;

public class DBApp extends SugarApp {

    private static final String SHARED_PREFERENCES_NAME = DBApp.class.getName() + "SHARED_PREFS";
    private static final String FIRST_RUN_KEY = "first_run";

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        boolean firstRun = preferences.getBoolean(FIRST_RUN_KEY, true);

        if (firstRun) {
            try {
                AssetManager assetManager = getAssets();
                InputStream iStream = assetManager.open("ogl_monsters.xml");

                List<Creature> creatures = CreatureParser.getCreaturesFromXml(IOUtils.toString(iStream));
                for (Creature creature : creatures) {
                    creature.save();
                }
            } catch (IOException ioe) {
                throw new RuntimeException(ioe.getMessage(), ioe);
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FIRST_RUN_KEY, false);
            editor.apply();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    protected Database getDatabase() {
        return super.getDatabase();
    }


}
