package com.example.a13058.weather;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by 13058 on 07-03-17.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s){
        addPreferencesFromResource(R.xml.preferences);
    }
}
