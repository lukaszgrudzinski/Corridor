package com.sema.corridor;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

public class httpHelper {
    public static String Response = "";
    public static Location lastLocation;
    public static
    LocationManager lm;


    public static void sendPost(final boolean sendType, final Context applicationContext, final Location locatioLast) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(lm == null)
                    lm = (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (applicationContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && applicationContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                          //  return;
                        }
                    }
                   // lm.requestSingleUpdate(LocationManager.GPS_PROVIDER,null);
                  //  loo
                   // lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1 ,new MyLocationListener());


                   // Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Location location = lastLocation;
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                    String UrlToGo = sendType ? "https://costconedatamanager20200107095714.azurewebsites.net/api/Location" : "https://costconedatamanager20200107095714.azurewebsites.net/api/Find";
                    URL url = new URL(UrlToGo);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("lattitude", latitude);
                    jsonParam.put("longtitude", longitude);


                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());


                    os.flush();
                    os.close();

                    String results = conn.getResponseMessage();
                    Log.i("RESULTSS "+results
                            , jsonParam.toString());
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));

                    try(BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        Response=response.toString();
                        Log.i("jsonresponse ",Response);
                    }

                    conn.disconnect();
                   // startMap(applicationContext);
                  //  CompareResults(Response, applicationContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    static public void notifyToast(Context con)
    {
      //  Toast.makeText( con, Response, Toast.LENGTH_LONG).show();
    }
    static private void startMap(Context con)
    {
        Context context = con;
        notifyToast(con);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            contentIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
    static void CompareResults(String result, Context applicationContext)
    {

        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        //jsonObject.get()
        if (result.length()>4)
            startMap(applicationContext);
    }
}