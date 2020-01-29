package com.sema.corridor;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener
{

    public void onLocationChanged(final Location loc)
    {
        Log.i("*******************", "Location changed");
            httpHelper.lastLocation=loc;

    }

    public void onProviderDisabled(String provider)
    {
       // Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
    }


    public void onProviderEnabled(String provider)
    {
      //  Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
    }


    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

}
