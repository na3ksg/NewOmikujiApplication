package com.example.newomikuji;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by kasagoman on 2016/02/06.
 */
public class OmikujiPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
