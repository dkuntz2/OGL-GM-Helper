package co.kuntz.oglgamemastershelper;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class StatBlock extends Fragment {
    public static final String TAG = StatBlock.class.getCanonicalName();

    private SingleAttributeBlock constitutionStat;
    private SingleAttributeBlock dexterityStat;


    public StatBlock() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stat_block, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        constitutionStat = (SingleAttributeBlock) getFragmentManager().findFragmentById(R.id.constitution_stat);

        Log.d(TAG, "constitutionStat.equals(null)? " + new Boolean(constitutionStat == null).toString());

        //constitutionStat.setAbilityName("con");
        //constitutionStat.setAbilityScore(10);

        dexterityStat = (SingleAttributeBlock) getFragmentManager().findFragmentById(R.id.dexterity_stat);
    }
}
