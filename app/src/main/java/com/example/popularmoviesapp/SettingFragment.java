package com.example.popularmoviesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import java.util.List;

public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.setting);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen screen = getPreferenceScreen();
        int count = screen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference p = screen.getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setSummary(p, value);
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0)
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PreferenceScreen screen = getPreferenceScreen();
        int count = screen.getPreferenceCount();
        Preference p = getPreference(key);
        if (!(p instanceof CheckBoxPreference)) {
            String value = sharedPreferences.getString(p.getKey(), "");
            setSummary(p, value);
        }

    }

    private Preference getPreference(String key) {
        PreferenceScreen screen = getPreferenceScreen();
        int count = screen.getPreferenceCount();
        for(int i =0 ;i<count;i++)
            if(screen.getPreference(i).getKey().equals(key))
                return screen.getPreference(i);
        return null;
    }
}
