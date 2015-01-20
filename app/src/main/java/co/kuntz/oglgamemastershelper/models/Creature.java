package co.kuntz.oglgamemastershelper.models;

import android.support.annotation.Nullable;

import com.orm.SugarRecord;
import com.orm.query.Select;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import co.kuntz.oglgamemastershelper.utils.MapUtil;
import co.kuntz.oglgamemastershelper.utils.XmlWrapper;

public class Creature extends SugarRecord<Creature> {
    private static final String TAG = Creature.class.getName();

    int strength;
    int dexterity;
    int constitution;
    int intelligence;
    int wisdom;
    int charisma;

    String name;
    int ac;
    int cr;

    String srdId;

    public Creature(){}

    public Creature(String name) {
        this.name = name;
    }

    public void setAbilities(int str, int dex, int con, int intell, int wis, int cha) {
        strength = str;
        dexterity = dex;
        constitution = con;
        intelligence = intell;
        wisdom = wis;
        charisma = cha;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getStrengthMod() {
        return abilityScoreToModifier(strength);
    }

    public int getDexterityMod() {
        return abilityScoreToModifier(dexterity);
    }

    public int getConstitutionMod() {
        return abilityScoreToModifier(constitution);
    }

    public int getIntelligenceMod() {
        return abilityScoreToModifier(intelligence);
    }

    public int getWisdomMod() {
        return abilityScoreToModifier(wisdom);
    }

    public int getCharismaMod() {
        return abilityScoreToModifier(charisma);
    }

    public String getName() {
        return name;
    }

    private static final int abilityScoreToModifier(int score) {
        return (score - 10) / 2;
    }

    public static Creature parse(XmlWrapper node) {
        Creature c = new Creature();
        c.name = node.findString("Name");
        c.srdId = node.getAttributes().get("id");

        XmlWrapper abilitiesNode = node.find("StatBlock/Abilities");
        Map<String, String> abilitiesAttrs = abilitiesNode.getAttributes();

        c.strength = Integer.parseInt(MapUtil.get(abilitiesAttrs, "Str", "0"));
        c.dexterity = Integer.parseInt(MapUtil.get(abilitiesAttrs, "Dex", "0"));
        c.constitution = Integer.parseInt(MapUtil.get(abilitiesAttrs, "Con", "0"));
        c.intelligence = Integer.parseInt(MapUtil.get(abilitiesAttrs, "Int", "0"));
        c.wisdom = Integer.parseInt(MapUtil.get(abilitiesAttrs, "Wis", "0"));
        c.charisma = Integer.parseInt(MapUtil.get(abilitiesAttrs, "Cha", "0"));

        return c;
    }

    public static Iterator<Creature> all() {
        Select<Creature> creatures = Select.from(Creature.class).orderBy("name ASC");
        return creatures.iterator();
    }


    public static Creature random() {
        Select<Creature> rand = Select.from(Creature.class).orderBy("random()").limit("1");
        return rand.first();
    }

    public static Map<String, Long> getNameIdPairs() {
        List<Creature> creatures = new ArrayList<>();
        CollectionUtils.addAll(creatures, all());

        Map<String, Long> nameIdPairs = new HashMap<>();

        for (Creature c : creatures) {
            nameIdPairs.put(c.getName(), c.getId());
        }

        return nameIdPairs;
    }

    public static Creature findByName(String name) {
        Select<Creature> creatureSelect = Select.from(Creature.class).where("name = ?", new String[]{name});
        return creatureSelect.first();
    }
}
