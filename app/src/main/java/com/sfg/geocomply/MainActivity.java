package com.sfg.geocomply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.sfg.geocomply.ui.main.MainFragment;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

        RxJavaPlugins.setErrorHandler(e -> {
            Log.e(TAG, "RxJavaPlugins, error handler ", e);
        });
    }
}