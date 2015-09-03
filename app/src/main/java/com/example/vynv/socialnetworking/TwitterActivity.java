package com.example.vynv.socialnetworking;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.vynv.socialnetworking.twitter.Config;
import com.example.vynv.socialnetworking.twitter.LoginFragment;

public class TwitterActivity extends Activity {
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        saveAPItoSharePref();
        Fragment login = new LoginFragment();
        FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame, login);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void saveAPItoSharePref() {
        pref = getPreferences(0);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("CONSUMER_KEY", Config.API_KEY);
        edit.putString("CONSUMER_SECRET", Config.API_SECRET);
        edit.apply();
    }
}
