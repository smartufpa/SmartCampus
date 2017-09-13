package com.example.kaeuc.smartufpa.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.kaeuc.smartufpa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaeuc on 10/5/2016.
 */
public class LoginScreenActivity extends AppCompatActivity {
    public static final String CATEGORY_LOGIN = "smartufpa.CATEGORY_LOGIN";
    public static final String ACTION_LOGIN = "smartufpa.ACTION_LOGIN";
    public static final String TAG = LoginScreenActivity.class.getSimpleName();

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    private Button btnVisitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_activity);

        // Views
        btnVisitante = findViewById(R.id.btn_entrar);
        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == btnVisitante.getId()) {
                    Intent intent = new Intent(AppMenuActivity.ACTION_APP_MENU);
                    intent.addCategory(AppMenuActivity.CATEGORY_APP_MENU);
                    startActivity(intent);
                }
            }
        };

        btnVisitante.setOnClickListener(clickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Requests permissions on versions from Marshmallow and over
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        }
    }

    // Ask for permission to use file storage for cache and location features
    // Methods taken from OSMdroid samples
    // Reference: OpenStreetMapView > Samples > ExtraSamples > SampleFollowMe

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            String[] params = permissions.toArray(new String[permissions.size()]);
            requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } // else: We already have permissions, so handle as normal
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION and WRITE_EXTERNAL_STORAGE
                Boolean location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                Boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if (location && storage) {
                    // All Permissions Granted
                    Toast.makeText(this, R.string.msg_all_permissions_granted,
                            Toast.LENGTH_SHORT).show();
                } else if (location) {
                    Toast.makeText(this, R.string.msg_request_storage_permission,
                            Toast.LENGTH_LONG).show();
                } else if (storage) {
                    Toast.makeText(this, R.string.msg_request_location_permission, Toast.LENGTH_LONG).show();
                } else { // !location && !storage case
                    // Permission Denied
                    Toast.makeText(this, R.string.msg_request_both_permissions, Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}