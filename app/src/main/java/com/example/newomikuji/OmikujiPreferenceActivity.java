package com.example.newomikuji;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by kasagoman on 2016/02/06.
 */
public class OmikujiPreferenceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, new OmikujiPreferenceFragment());
        fragmentTransaction.commit();
    }
}
