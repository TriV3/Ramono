package com.TriVe.Apps.Ramono;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.TriVe.Apps.Ramono.Location.FallbackLocationTracker;
import com.TriVe.Apps.Ramono.Ramonage.Client;
import com.TriVe.Apps.mycontact.ContactAPI.ContactAPI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>Maps activity used to locate clients on map.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLoadedCallback
{
    public static final String TAG = "MapsActivityTAG";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private List<Marker> markers = new ArrayList<>();
    private Marker MyPosition;

    FallbackLocationTracker flt;

    AsyncTask<String, Void, Void> stringVoidVoidAsyncTask = new AsyncTask<String, Void, Void>()
    {
        BufferedReader in;
        @Override
        protected Void doInBackground(String... strings)
        {
            String url = "";
            if (strings.length > 0)
            {
                url = strings[0];
            }
            else
            {
                return null;
            }

            try
            {
                HttpClient httpClient = new DefaultHttpClient();// Client
                HttpGet getRequest = new HttpGet();

                getRequest.setURI(new URI(url));
                HttpResponse response = httpClient.execute(getRequest);


                in = new BufferedReader
                        (new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
                String page = sb.toString();
                JSONObject jsonObject = new JSONObject(page);
                JSONArray jsonArray = (JSONArray) jsonObject.get("results");
                if (jsonArray.length() > 0) {
                    jsonObject = (JSONObject) jsonArray.get(0);
                    jsonObject = (JSONObject) jsonObject.get("geometry");
                    JSONObject location = (JSONObject) jsonObject.get("location");
                    Double lat = (Double) location.get("lat");
                    Double lng = (Double) location.get("lng");
                    System.out.println("lat - " + lat + " , lon - " + lng);
                    LatLng MarkerPoint = new LatLng(lat, lng);
                    markers.add(mMap.addMarker(new MarkerOptions().position(MarkerPoint).title(strings[1])));
                }
                System.out.println(page);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        flt = new FallbackLocationTracker(this);
        flt.start();

        mMap.setOnMapLoadedCallback(this);


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded()
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        }
    }

    private void AddClientMarker(Client client)
    {
        List<Address> address;
        client.setAddresses(new ContactAPI(this).getContactAddresses(client.getId()));
        if(client.getAddresses().size() == 0)
            return;

         if (Geocoder.isPresent())
        {
            try
            {
                Geocoder geocoder = new Geocoder(getBaseContext());
                Log.d(MapsActivity.TAG, "Trying to locate \"" + client.getDisplayName() + "\" at " + client.getAddresses().get(0).toString() + " using geocoder.");
                address = geocoder.getFromLocationName(client.getAddresses().get(0).toString(), 1);
                if (address.size() == 0)
                    return;
                LatLng MarkerPoint = new LatLng(address.get(0).getLatitude() , address.get(0).getLongitude());
                markers.add(mMap.addMarker(new MarkerOptions().position(MarkerPoint).title(client.getDisplayName())));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {

            // FIXME : Case of geocoder is not implemented on phone, dot it by https request...
            Log.d(MapsActivity.TAG, "geocoder is not present...");
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + client.getAddresses().get(0).toString().replace(" ", "+");
            url += "&key=AIzaSyBI1R3sUU18Ajbhx6WW2J2EyseVSVs6PeU";

            String[] list =  new String[]{url, client.getDisplayName()};
            stringVoidVoidAsyncTask.execute(list);
            ZoomOnPins();
        }
    }

    @Override
    public void onMapLoaded()
    {

        Location loc = flt.getLocation();
        if (loc != null)
        {
            LatLng MarkerPoint = new LatLng(loc.getLatitude(), loc.getLongitude());
            MyPosition = mMap.addMarker(new MarkerOptions().position(MarkerPoint).title("Moi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
        else
        {
            Toast.makeText(this, "Impossible de localiser l'appareil", Toast.LENGTH_LONG).show();
        }


        try
        {
            for(Client cl : Datas.clients)
            {
                AddClientMarker(cl);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ZoomOnPins();
    }

    private void ZoomOnPins()
    {
        if (markers.size() == 0)
            return;

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }

        if (MyPosition != null)
            builder.include(MyPosition.getPosition());

        LatLngBounds bounds = builder.build();
        int padding = 15; // offset from edges of the map in pixels
        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }
}
