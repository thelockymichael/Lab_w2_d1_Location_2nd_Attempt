package com.example.lab_w2_d1_location_2nd_attempt

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var request: LocationRequest

    private val REQUEST_CODE: Int = 8990
    private val ACCESS_FINE_LOCATION_CODE: Int = 2341
    private val ACCESS_COARSE_LOCATION_CODE: Int = 3552

    private lateinit var locationCallback: LocationCallback

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        request = LocationRequest()
                .setFastestInterval(3000)
                .setInterval(300)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        var builder = LocationSettingsRequest.Builder().addLocationRequest(request)

        var result: Task<LocationSettingsResponse>? = LocationServices
                .getSettingsClient(this)
                .checkLocationSettings(builder.build())

        result?.addOnFailureListener(this, OnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    var resolvable: ResolvableApiException = e
                    resolvable.startResolutionForResult(this, REQUEST_CODE)
                } catch (ex: IntentSender.SendIntentException) {
                    ex.printStackTrace()
                }
            }
        })

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    longitudeField.text = "Null"
                    latitudeField.text = "Null"
                    return
                }

                for (location in locationResult!!.locations) {
                    latitudeField.text = "${location.latitude}"
                    longitudeField.text = "${location.longitude}"
                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (fusedLocationProviderClient != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                requestLocationPermission()

                return
            }
            fusedLocationProviderClient.requestLocationUpdates(request, locationCallback,
                    Looper.getMainLooper())
        }


    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) and ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {

        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    ACCESS_FINE_LOCATION_CODE)

            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                    ACCESS_COARSE_LOCATION_CODE)
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}