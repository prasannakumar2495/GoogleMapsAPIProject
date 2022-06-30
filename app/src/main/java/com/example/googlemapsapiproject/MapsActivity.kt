package com.example.googlemapsapiproject

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.googlemapsapiproject.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        //API Key
        // AIzaSyCHXnHxx_b7CoQCBRFXz7IM3T_wBmCM30k
        mMap = googleMap

        /**
         * adding more setting
         */
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        drawMarker(sydney)
        addCircleAroundMarker(sydney)

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {
            }

            override fun onMarkerDragEnd(p0: Marker) {
                if (currentMarker != null) {
                    currentMarker?.remove()

                    val newLatLang = LatLng(p0.position.latitude, p0.position.longitude)
                    drawMarker(newLatLang)
                }
            }

            override fun onMarkerDragStart(p0: Marker) {
            }
        })
    }

    private fun drawMarker(sydney: LatLng) {
        currentMarker =
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")).also {
                it?.isDraggable = true
            }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        currentMarker?.showInfoWindow()
    }

    private fun addCircleAroundMarker(sydney: LatLng) {
        val circleOption = CircleOptions()
        circleOption.center(sydney)
        circleOption.radius(120.0)
        circleOption.strokeColor(Color.RED)
        circleOption.strokeWidth(2F)
        circleOption.fillColor(Color.RED)
        mMap.addCircle(circleOption)
    }
}