package com.TriVe.Apps.Ramono;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * <b>Activity used to manage preferences.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class UserSettingActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        addPreferencesFromResource(R.xml.user_settings);
        // Display the fragment as the main content.
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        UserSettingsFragment mUserSettingsFragment = new UserSettingsFragment();
        mFragmentTransaction.replace(android.R.id.content, mUserSettingsFragment);
        mFragmentTransaction.commit();
//        getFragmentManager().beginTransaction().add(new UserSettingsFragment(), "UserSettingFragment").commit();
    }


    public static class UserSettingsFragment extends PreferenceFragment
    {

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.user_settings);
        }
    }
}
