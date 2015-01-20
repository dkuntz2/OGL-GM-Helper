package co.kuntz.oglgamemastershelper;

import android.content.res.AssetManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.kuntz.oglgamemastershelper.models.Creature;
import co.kuntz.oglgamemastershelper.models.Setting;
import co.kuntz.oglgamemastershelper.utils.CreatureParser;
import co.kuntz.oglgamemastershelper.views.SingleAttributeView;
import co.kuntz.oglgamemastershelper.views.StatBlockView;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getName();

    @InjectView(R.id.monster_selector) protected Spinner monsterSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        boolean isDBInitialized = Setting.getBoolean("initialized");
        if (!isDBInitialized) {
            try {
                AssetManager assetManager = getAssets();
                InputStream iStream = assetManager.open("ogl_monsters.xml");

                List<Creature> creatures = CreatureParser.getCreaturesFromXml(IOUtils.toString(iStream));
                for(Creature creature : creatures) {
                    creature.save();
                }

                Setting dbInitialized = new Setting();
                dbInitialized.key = "initialized";
                dbInitialized.value = "true";
                dbInitialized.save();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        showRandomCreature();

        Collection<String> creatureNames = CollectionUtils.collect(Creature.all(), new Transformer() {
            @Override
            public Object transform(Object input) {
                return ((Creature) input).getName();
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);
        arrayAdapter.addAll(creatureNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        monsterSelector.setAdapter(arrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showRandomCreature();
    }


    private void showRandomCreature() {
        //Creature dummyCreature = Creature.random();
        //((StatBlockView)findViewById(R.id.stat_block)).fillInCreature(dummyCreature);
    }
}
