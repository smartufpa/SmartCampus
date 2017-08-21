package com.example.kaeuc.smartufpa.activities;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kaeuc.smartufpa.R;

public class NoGpsActivity extends AppCompatActivity {
    public final static String CATEGORY_NO_GPS = "smartufpa.CATEGORY_NO_GPS";
    public final static String ACTION_NO_GPS = "smartufpa.ACTION_NO_GPS";
    public final static String TAG = NoGpsActivity.class.getSimpleName();

    private Button turnOnGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_gps_activity);
        turnOnGps = (Button) findViewById(R.id.btn_turn_on_gps);
        turnOnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (((LocationManager) getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(MainActivity.ACTION_MAIN);
            intent.addCategory(MainActivity.CATEGORY_MAIN);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy called");
    }
}
