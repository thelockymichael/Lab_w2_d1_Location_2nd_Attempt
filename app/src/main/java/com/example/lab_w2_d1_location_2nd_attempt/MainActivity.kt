package com.example.lab_w2_d1_location_2nd_attempt

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var latituteField: TextView
    private lateinit var longitudeField: TextView
    private lateinit var locationManager: LocationManager
    private var provider: String? = null

    // declare a global variable of FusedLocationProviderClient
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // globally declare LocationRequest
    private lateinit var locationRequest: LocationRequest

    // globally declare LocationCallback
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if ((Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) !=
                    PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0
            )
        }


        // in onCreate() initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLocationUpdates()
    }

    /**
     * call this method for receive location
     * get location and give callback when successfully retrieve
     * function itself check location permission before access related methods
     *
     */

    fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {

                        Log.d("USR", "${location.latitude} ${location.longitude}")
                        // use your location object
                        // get latitude , longitude and other info from this
                    }

                }

            return
        }

    }

    /**
     * call this method in onCreate
     * onLocationResult call when location is changed
     */
    private fun getLocationUpdates() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest()
        locationRequest.interval = 50000
        locationRequest.fastestInterval = 50000
        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
        locationRequest.priority =
            LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {
                    // get latest location
                    val location =
                        locationResult.lastLocation

                    Log.d("USR", "IFHSAIUFHSUAHIGHIUA")
                    // use your location object
                    // get latitude , longitude and other info from this
                }


            }
        }
    }

    //start location updates
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null /* Looper */
            )

            return
        }

    }

    // stop location updates
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // stop receiving location update when activity not visible/foreground
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    // start receiving location update when activity  visible/foreground
    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }
}

/*
package com.example.lab_w2_d1_location_2nd_attempt

import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var latituteField: TextView
    private lateinit var longitudeField: TextView
    private lateinit var locationManager: LocationManager
    private var provider: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if ((Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0
            )
        }

        // Get the location manager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        // Define the criteria how to select the locatioin provider -> use
        // default

        locationBtn.setOnClickListener {
            try {
                // Request location updates
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
            } catch(ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available")
            }
        }

        */
/* BACKUP *//*

*/
/*        val criteria = Criteria()
        provider = locationManager?.getBestProvider(criteria, false)
        val location = locationManager?.getLastKnownLocation(provider!!)*//*


        // Initialize the location fields

        // Initialize the location fields
*/
/*        if (location != null) {
            println("Provider $provider has been selected.")
            onLocationChanged(location)
        } else {
            latituteField!!.text = "Location not available"
            longitudeField!!.text = "Location not available"
        }*//*


*/
/*        getSystemService(Context.LOCATION_SERVICE)

        var enabled: Boolean = service!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!enabled) {
            var intent: Intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }*//*



*/
/*        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
            if (task.isSuccessful && task.result != null) {
                // Got last know location, react...
                Log.d("GEOLOCATION", "latitude: ${task.result.latitude} and longitude: ${task.result.longitude}")
            }
        }*//*

    }

    */
/* Request updates at startup *//*

  */
/*  override fun onResume() {
        super.onResume()
        locationManager.requestLocationUpdates(provider, 400, 1, this)
    }

    *//*
*/
/* Remove the locationlistener updates when Activity is paused *//*
*/
/*
    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        val lat = location.latitude.toInt()
        val lng = location.longitude.toInt()
        latituteField!!.text = lat.toString()
        longitudeField!!.text = lng.toString()
    }

    fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    fun onProviderEnabled(provider: String) {
        Toast.makeText(
            this, "Enabled new provider $provider",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onProviderDisabled(provider: String) {
        Toast.makeText(
            this, "Disabled provider $provider",
            Toast.LENGTH_SHORT
        ).show()
    }*//*


    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            latituteField.text = ("" + location?.longitude + ":" + location?.latitude)    }

    }
}

*/
