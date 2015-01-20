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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
    @InjectView(R.id.monster_name) protected TextView monsterName;

    @InjectView(R.id.ability_strength) protected SingleAttributeView abilityStr;
    @InjectView(R.id.ability_dexterity) protected SingleAttributeView abilityDex;
    @InjectView(R.id.ability_constitution) protected SingleAttributeView abilityCon;
    @InjectView(R.id.ability_intelligence) protected SingleAttributeView abilityInt;
    @InjectView(R.id.ability_wisdom) protected SingleAttributeView abilityWis;
    @InjectView(R.id.ability_charisma) protected SingleAttributeView abilityCha;


    private List<String> creatureNames;
    private int currentItemInList = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        creatureNames = new ArrayList<String>(CollectionUtils.collect(Creature.all(), new Transformer() {
            @Override
            public Object transform(Object input) {
                return ((Creature) input).getName();
            }
        }));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);
        arrayAdapter.addAll(creatureNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        monsterSelector.setAdapter(arrayAdapter);

        monsterSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentItemInList = position;
                setMonster();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
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
    }


    private void setMonster() {
        Creature currentCreature = Creature.findByName(creatureNames.get(currentItemInList));

        monsterName.setText(currentCreature.getName());

        abilityStr.set("str", currentCreature.getStrength());
        abilityDex.set("dex", currentCreature.getDexterity());
        abilityCon.set("con", currentCreature.getConstitution());
        abilityInt.set("int", currentCreature.getIntelligence());
        abilityWis.set("wis", currentCreature.getWisdom());
        abilityCha.set("cha", currentCreature.getCharisma());
    }
}
