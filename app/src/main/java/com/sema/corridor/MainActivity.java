package com.sema.corridor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.sema.corridor.httpHelper.lm;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE_STATUS = "message_status";
    static Location locationLast;

    private static final int REQUEST_LOCATION = 123;
    Button btnSend;

    boolean isServiceRunning = false;
    final WorkManager mWorkManager = WorkManager.getInstance();

    private boolean isServiceRunning2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = findViewById(R.id.btn);



    }

    public void StartInBackground(View view) {
        startService(new Intent(this,MyLocationService .class));
        RequestPermissions();
        if (isServiceRunning)
            stopService();
        else
            startService();



    }
    public void startService() {

        startService(new Intent(this, Service2.class));
        isServiceRunning=true;
    }
    public void stopService() {
        stopService(new Intent(this, Service2.class));
        isServiceRunning=false;
    }
    public void startService2() {

        startService(new Intent(this, Service1.class));
        isServiceRunning2=true;
    }
    public void stopService2() {
        stopService(new Intent(this, Service1.class));
        isServiceRunning2=false;
    }

    public void StartInBackground2(View view) {
        RequestPermissions();
        if(isServiceRunning2)
            stopService2();
        else
            startService2();
    }
    private void RequestPermissions()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,

                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,

                            Manifest.permission.ACCESS_COARSE_LOCATION,

                            Manifest.permission.BLUETOOTH,

                            Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_LOCATION);

        }
    }
}
