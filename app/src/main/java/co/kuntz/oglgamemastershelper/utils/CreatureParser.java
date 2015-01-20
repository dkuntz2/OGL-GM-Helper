package co.kuntz.oglgamemastershelper.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.kuntz.oglgamemastershelper.models.Creature;

public class CreatureParser {
    private static final String TAG = CreatureParser.class.getName();

    public static List<Creature> getCreaturesFromXml(String xml) {
        List<Creature> creatures = new ArrayList<>();

        XmlWrapper node = XmlWrapper.parse(xml);

        Log.d(TAG, "top level node: " + node.getName());

        List<XmlWrapper> creaturesXml = node.findAll("Monsters/Monster");
        Log.d(TAG, "number of creature nodes: " + creaturesXml.size());

        //StringBuilder builder = new StringBuilder();
        //appendNodeChildren(creaturesXml.get(0), 0, builder);
        //Log.d(TAG, builder.toString());


        for (XmlWrapper n : creaturesXml) {

            creatures.add(Creature.parse(n));
        }

        return creatures;
    }

    public static void logXml(String tag, XmlWrapper xml) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append(xml.getName());
        builder.append("\n");

        appendNodeChildren(xml, 1, builder);
        Log.d(tag, builder.toString());
    }

    public static void appendNodeChildren(XmlWrapper wrapper, int tabLevel, StringBuilder builder) {
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
