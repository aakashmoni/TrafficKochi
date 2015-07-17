package com.example.traffic;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

import java.util.List;
import java.util.Locale;

import com.example.locationmap.MainActivity;
import com.example.locationmap.R;
import com.example.locationmap.MainActivity.MyLocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	boolean gps_enabled = false;
	boolean network_enabled = false;
	LocationListener mlocListener;
	LocationManager lm;
	String address, city, country, loc,address1;
	private GoogleMap map; 
	LatLng currentloc;
	double lat, lon;
	Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //b=(Button) findViewById(R.id.report1);
        
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mlocListener = new MyLocationListener();
		gps_enabled = lm.isProviderEnabled(GPS_PROVIDER);
		network_enabled = lm.isProviderEnabled(NETWORK_PROVIDER);
		Location net_loc = null, gps_loc = null, finalloc = null;
		b=(Button) findViewById(R.id.report1);
        b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent i=new Intent(MainActivity.this,Registration.class);
			startActivity(i);
			
			}
		});
		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {

		
			//Identifying location name from latitude and longitude
			try {
				Geocoder geocoder;
				List<Address> addresses;
				geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
				lat = location.getLatitude();
				lon = location.getLongitude();
				currentloc=new LatLng(lat, lon);

				addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

				address = addresses.get(0).getAddressLine(0);
				city = addresses.get(0).getAddressLine(1);
				country = addresses.get(0).getAddressLine(2);

				map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
						.getMap();
				map.setMyLocationEnabled(true);
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			
			//	Marker current = map.addMarker(new MarkerOptions().position(currentloc)
			//			.title("Current Location is: "+address+","+city+","+country));

				map.moveCamera(CameraUpdateFactory.newLatLng(currentloc));

				// Zoom in, animating the camera.
				map.animateCamera(CameraUpdateFactory.zoomTo(13));
				MarkerOptions marker=new MarkerOptions();
				marker.position(currentloc);
				map.addMarker(marker);
			} catch (Exception e) {

			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    
}
}









