package co.kuntz.oglgamemastershelper;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SingleAttributeBlock extends Fragment {
    @InjectView(R.id.ability_score) TextView abilityScore;
    @InjectView(R.id.ability_name) TextView abilityName;

    public SingleAttributeBlock() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_single_attribute, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName.setText(abilityName);
    }

    public String getAbilityName() {
        return this.abilityName.getText().toString();
    }

    public void setAbilityScore(int abilityScore) {
        this.abilityScore.setText(Integer.toString(abilityScore));
    }

    public int getAbilityScore() {
        return Integer.parseInt(this.abilityScore.getText().toString());
    }
}
