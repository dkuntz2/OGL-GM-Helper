package co.kuntz.oglgamemastershelper.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.kuntz.oglgamemastershelper.R;
import co.kuntz.oglgamemastershelper.models.Creature;

public class StatBlockView extends LinearLayout {
    View view;

    public StatBlockView(Context context) {
        super(context);
        init(context, null);
    }

    public StatBlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StatBlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_stat_block, this);
    }

    public void fillInCreature(Creature creature) {
        if (view == null) {
            // TODO make this a real exception / not runtime
            throw new RuntimeException("View is null");
        }

        ((TextView)view.findViewById(R.id.creature_name)).setText(creature.getName());

        ((SingleAttributeView)view.findViewById(R.id.ability_strength)).set("str", creature.getStrength());
        ((SingleAttributeView)view.findViewById(R.id.ability_dexterity)).set("dex", creature.getDexterity());
        ((SingleAttributeView)view.findViewById(R.id.ability_constitution)).set("con", creature.getConstitution());
        ((SingleAttributeView)view.findViewById(R.id.ability_intelligence)).set("int", creature.getIntelligence());
        ((SingleAttributeView)view.findViewById(R.id.ability_wisdom)).set("wis", creature.getWisdom());
        ((SingleAttributeView)view.findViewById(R.id.ability_charisma)).set("cha", creature.getCharisma());
    }
}
