package co.kuntz.oglgamemastershelper.views;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.kuntz.oglgamemastershelper.R;

public class SingleAttributeView extends RelativeLayout {
    public static final String TAG = SingleAttributeView.class.getName();

    private View view;

    public SingleAttributeView(Context context) {
        super(context);
        init(context, null);
    }

    public SingleAttributeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SingleAttributeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_single_attribute, this);

        if (attrs == null) {
            return;
        }

        TextView abilityName = (TextView) view.findViewById(R.id.ability_name);
        TextView abilityScore = (TextView) view.findViewById(R.id.ability_score);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SingleAttributeView);

        String abilityNameStr = arr.getString(R.styleable.SingleAttributeView_ability_name);
        int abilityScoreInt = arr.getInt(R.styleable.SingleAttributeView_ability_value, 0);

        Log.d(TAG, "Ability: " + abilityNameStr);
        Log.d(TAG, "Score: " + abilityScoreInt);

        abilityName.setText(abilityNameStr);
        abilityScore.setText(String.valueOf(abilityScoreInt));

        arr.recycle();
    }

    public void setAbilityName(String abilityName) {
        if (view == null) {
            return;
        }

        TextView tv = (TextView) view.findViewById(R.id.ability_name);
        tv.setText(abilityName);
    }

    public void setAbilityScore(int abilityScore) {
        if (view == null) {
            return;
        }

        TextView tv = (TextView) view.findViewById(R.id.ability_score);
        tv.setText(String.valueOf(abilityScore));
    }

}
