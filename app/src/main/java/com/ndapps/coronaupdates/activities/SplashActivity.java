package com.ndapps.coronaupdates.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ndapps.coronaupdates.R;

public class SplashActivity extends AppCompatActivity {

    String permissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!hasPermissions(SplashActivity.this, permissions)) {
            ActivityCompat.requestPermissions(SplashActivity.this, permissions, 131);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, 2000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == 131) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /* Create an Intent that will start the Menu-Activity. */
                        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();
                    }
                }, 2000);
            } else {
                Toast.makeText(SplashActivity.this, "Please grant all permissions", Toast.LENGTH_SHORT).show();
                SplashActivity.this.finish();
            }
            return;
        } else {
            Toast.makeText(SplashActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            SplashActivity.this.finish();
            return;
        }
    }

    public boolean hasPermissions(Context context, String[] permissions) {
        boolean hasAll = true;
        for (String permission : permissions) {
            int res = context.checkCallingOrSelfPermission(permission);
            if (res != PackageManager.PERMISSION_GRANTED)
                hasAll = false;
        }
        return hasAll;
    }
}
