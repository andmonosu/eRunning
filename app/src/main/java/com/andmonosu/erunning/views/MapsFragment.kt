package com.andmonosu.erunning.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.andmonosu.erunning.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import java.lang.Double
import java.lang.Float
import kotlin.math.round

class MapsFragment : Fragment(), OnMyLocationButtonClickListener {
    val ui = MutableLiveData(Ui.EMPTY)
    lateinit var map:GoogleMap
    private lateinit var locationProvider:LocationProvider
    private lateinit var tvDistance:TextView
    private lateinit var tvPace:TextView
    private lateinit var chronometer: Chronometer
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireParentFragment().requireContext(), R.raw.map_style))
        locationProvider.liveLocation.observe(this) { latLng ->
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f))
        }
        ui.observe(this) { ui ->
            updateUi(ui)
        }
        enableMyLocation()
        onMapLoaded()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationProvider = LocationProvider(requireParentFragment().requireActivity() as AppCompatActivity)
        return inflater.inflate(R.layout.fragment_maps2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createMapFragment()
        initComponents()
        locationProvider.liveLocations.observe(requireParentFragment().requireActivity()) { locations ->
            val current = ui.value
            ui.value = current?.copy(userPath = locations)
        }

        locationProvider.liveLocation.observe(requireParentFragment().requireActivity()) { currentLocation ->
            val current = ui.value
            ui.value = current?.copy(currentLocation = currentLocation)
        }

        locationProvider.liveDistance.observe(requireParentFragment().requireActivity()) { distance ->
            val current = ui.value
            var distanceKm = distance/1000.0
            distanceKm = round(distanceKm*100)/100.0
            val formattedDistance = "$distanceKm km"
            val timeElapsed = SystemClock.elapsedRealtime() - chronometer.base
            val timeElapsedInMinutes = timeElapsed / 60000.0
            var pace = (timeElapsedInMinutes/(distance/1000.0))
            pace = round(pace*100)/100.0
            val formattedPace ="$pace min/km"
            ui.value = current?.copy(formattedDistance = formattedDistance?:"", formattedPace = formattedPace?:"")

        }
    }

    private fun initComponents() {
        val parentFragmentView = requireParentFragment().view
        if (parentFragmentView != null) {
            tvDistance = parentFragmentView.findViewById(R.id.tvDistance)
            tvPace = parentFragmentView.findViewById(R.id.tvPace)
            chronometer = parentFragmentView.findViewById(R.id.chronometer)
        }
    }

    private fun onMapLoaded() {
        locationProvider.getUserLocation()
    }

    fun startTracking() {
        locationProvider.trackUser()
    }

    fun stopTracking() {
        locationProvider.stopTracking()
    }

    private fun createMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(requireParentFragment().requireContext()
       ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        requireParentFragment().requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED


    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireParentFragment().requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(requireParentFragment().requireActivity(), "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(requireParentFragment().requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(requireParentFragment().requireActivity(), "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun updateUi(ui: Ui) {
        if (ui.currentLocation != null && ui.currentLocation != map.cameraPosition.target) {
            map.isMyLocationEnabled = true
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(ui.currentLocation, 20f))
        }

        tvDistance.text = ui.formattedDistance
        tvPace.text = ui.formattedPace

        drawRoute(ui.userPath)
    }

    private fun drawRoute(locations: List<LatLng>) {
        val polylineOptions = PolylineOptions()
        map.clear()
        val points = polylineOptions.points
        points.addAll(locations)
        map.addPolyline(polylineOptions)
    }

    override fun onResume(){
        super.onResume()
        if (!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            map.isMyLocationEnabled = false
            Toast.makeText(requireParentFragment().requireActivity(), "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val REQUEST_CODE_LOCATION = 1679
        @JvmStatic
        fun newInstance() =
            MapsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(requireParentFragment().requireActivity(), "Boton Pulsado", Toast.LENGTH_SHORT).show()
        return false
    }




}