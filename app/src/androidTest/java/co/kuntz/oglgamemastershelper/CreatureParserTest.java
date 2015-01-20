package co.kuntz.oglgamemastershelper;

import android.text.style.AlignmentSpan;
import android.util.Log;
import android.util.Xml;

import com.braintreegateway.util.NodeWrapper;
import com.braintreegateway.util.NodeWrapperFactory;

import junit.framework.TestCase;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import co.kuntz.oglgamemastershelper.models.Creature;
import co.kuntz.oglgamemastershelper.utils.XmlWrapper;

public class CreatureParserTest extends TestCase {
    private static final String TAG = CreatureParserTest.class.getName();

    private static final String AZER_XML =  "<Monster id=\"srd:Azer\">" +
                                            "<Name>Azer</Name>" +
                                            "<StatBlock>" +
                                            "<SizeAndType Size=\"medium-size\" Type=\"outsider\" Subtypes=\"fire,lawful\">Medium-Size Outsider (Fire, Lawful)</SizeAndType>" +
                                            "<HitDice D4=\"0\" D6=\"0\" D8=\"0\" D10=\"0\" D12=\"0\" Modifier=\"0\" HitPoints=\"11\">2d8+2 (11)</HitDice>" +
                                            "<Initiative>+1 (Dex)</Initiative>" +
                                            "<Speed>30 ft.</Speed>" +
                                            "<ArmorClass>19 (+1 Dex, +6 natural, +2 large shield)</ArmorClass>" +
                                            "<Attacks>Warhammer +3 melee; or halfspear +3 ranged</Attacks>" +
                                            "<Damage>Warhammer 1d8+1 and 1 fire; or halfspear 1d6+1 and 1 fire</Damage>" +
                                            "<FaceAndReach>5 ft. by 5 ft./5 ft.</FaceAndReach>" +
                                            "<SpecialAttacks>Heat</SpecialAttacks>" +
                                            "<SpecialQualities>SR 13, fire subtype</SpecialQualities>" +
                                            "<Saves Fort=\"4\" Ref=\"4\" Will=\"4\">Fort +4, Ref +4, Will +4</Saves>" +
                                            "<Abilities Str=\"13\" Dex=\"13\" Con=\"13\" Int=\"12\" Wis=\"12\" Cha=\"9\">Str 13, Dex 13, Con 13, Int 12, Wis 12, Cha 9</Abilities>" +
                                            "<Skills>Climb +2, Craft (any one) +6, Hide -1, Listen +4, Search+4, Spot +5</Skills>" +
                                            "<Feats>Power Attack</Feats>" +
                                            "<ClimateAndTerrain Climate=\"cold,temperate,warm\" Terrain=\"desert,forest,hill,marsh,mountains,plains,underground\">Any land and underground</ClimateAndTerrain>" +
                                            "<Organization>Solitary, pair, team (2-4), squad (11-20 plus 23rd-level sergeants and 1 leader of 3rd-6th level), or clan (30-100 plus 50% noncombatants plus 1 3rd-level sergeant per 20 adults, 5 5th-level lieutenants, and 3 7th-level captains)</Organization>" +
                                            "<ChallengeRating Value=\"2\">2</ChallengeRating>" +
                                            "<Treasure>Standard coins; double goods (nonflammables only);standard items (nonflammables only)</Treasure>" +
                                            "<Alignment>Always lawful neutral</Alignment>" +
                                            "<Advancement>By character class</Advancement>" +
                                            "</StatBlock>" +
                                            "<Description>" +
                                            "<General>" +
                                            "<p>Azers speak Ignan and Common.</p>" +
                                            "</General>" +
                                            "<Combat>" +
                                            "<p>Azers use broad-headed spears or well-crafted hammers in " +
                                            "combat. When unarmed, they attempt to grapple foes. They wear no " +
                                            "armor, for their tough skin provides ample protection.</p>" +
                                            "</Combat>" +
                                            "<SpecialAbilities>" +
                                            "<SpecialAbility Name=\"Heat\" Type=\"Ex\">" +
                                            "<p>Heat (Ex): Azers&apos; bodies are intensely hot, so their " +
                                            "unarmed attacks deal additional fire damage. Their metallic " +
                                            "weapons also conduct this heat.</p>" +
                                            "</SpecialAbility>" +
                                            "</SpecialAbilities>" +
                                            "</Description>" +
                                            "</Monster>";

    public void testXmlParser() throws Exception {
        XmlWrapper wrapper = XmlWrapper.parse(AZER_XML);

        /*
        StringBuilder builder = new StringBuilder();
        int tabLevel = 0;

        builder.append("\n");
        builder.append(wrapper.getName());
        builder.append("\n");
        tabLevel = 1;

        appendNodeChildren(wrapper, tabLevel, builder);

        Log.d(TAG, builder.toString());
        */

        Creature azer = Creature.parse(wrapper);



        assertEquals(azer.getName(), "Azer");

        assertEquals(azer.getStrength(), 13);
        assertEquals(azer.getDexterity(), 13);
        assertEquals(azer.getConstitution(), 13);
        assertEquals(azer.getIntelligence(), 12);
        assertEquals(azer.getWisdom(), 12);
        assertEquals(azer.getCharisma(), 9);
    }

    private void appendNodeChildren(XmlWrapper wrapper, int tabLevel, StringBuilder builder) {
        Log.d(TAG, "calling appendNodeChildren with " + wrapper.getName() + " tabLevel " + tabLevel);

        for (Object o : wrapper.getContent()) {
            if (o instanceof  XmlWrapper) {
                XmlWrapper n = (XmlWrapper) o;

                StringBuilder tabBuilder = new StringBuilder();

                for (int i = 0; i < tabLevel; i++) {
                    tabBuilder.append("..");
                }
                tabBuilder.append(" ");

                String tabs = tabBuilder.toString();
                builder.append(tabs);

                builder.append(n.getName());

                Map<String, String> attrs = n.getAttributes();
                for(String key : attrs.keySet()) {
                    builder.append("\n");
                    builder.append(tabs);
                    builder.append(".. ");
                    builder.append("attr: ");
                    builder.append(key);
                    builder.append(" : ");
                    builder.append(attrs.get(key));
                }

                ArrayList<XmlWrapper> children = new ArrayList<>();
                for (Object c : n.getContent()) {
                    if (c instanceof XmlWrapper) {
                        children.add((XmlWrapper) c);
                    }
                }

                if (!children.isEmpty()) {
                    builder.append(" ->\n");
                    appendNodeChildren(n, tabLevel + 1, builder);
                } else {
                    builder.append("\n");
                }
            }
        }
    }
}
