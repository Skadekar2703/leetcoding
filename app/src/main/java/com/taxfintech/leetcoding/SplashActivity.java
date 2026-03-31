package com.taxfintech.leetcoding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.taxfintech.leetcoding.ui.ThemePreferenceManager;

public class SplashActivity extends AppCompatActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable navigateToMain = () -> {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemePreferenceManager.applySavedTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(navigateToMain, 1600L);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(navigateToMain);
        super.onDestroy();
    }
}
