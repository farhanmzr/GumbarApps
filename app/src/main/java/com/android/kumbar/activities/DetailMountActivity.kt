package com.android.kumbar.activities

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.android.kumbar.R
import com.android.kumbar.databinding.ActivityDetailMountBinding
import com.android.kumbar.loadImage
import com.android.kumbar.model.Mount
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailMountActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val EXTRA_MOUNT = "extra_mount"

        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }

    private lateinit var binding: ActivityDetailMountBinding
    var lat : Double? = null
    var lon : Double? = null
    var mountainName : String? = null
    lateinit var googleMaps: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set transparent statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= 21) {
            ListMountActivity.setWindowFlag(
                this,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                false
            )
            window.statusBarColor = Color.TRANSPARENT
        }



        initView()
    }

    private fun initView() {

        binding.imgBack.setOnClickListener {
            finish()
        }
        //getData
        val mount = intent.getParcelableExtra<Mount>(EXTRA_MOUNT) as Mount
        binding.txtName.text = mount.name
        binding.txtLocation.text = mount.location
        binding.txtDescription.text = mount.description
        binding.txtHikingTrails.text = mount.hikingTrails
        binding.txtInformation.text = mount.infoMount

        Glide.with(binding.imgMountain)
            .load(mount.imgMount)
            .into(binding.imgMountain)
        binding.imgMountain.loadImage(mount.imgMount)

        lat = mount.lat
        lon = mount.lon
        mountainName = mount.name
//        Toast.makeText(this, "$lat" + "$lon", Toast.LENGTH_SHORT).show()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMaps = googleMap
        val latLng = lat?.let { lon?.let { it1 -> LatLng(it, it1) } }
        googleMaps.addMarker(MarkerOptions().position(latLng).title(mountainName))
        googleMaps.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        googleMaps.uiSettings.setAllGesturesEnabled(true)
        googleMaps.uiSettings.isZoomGesturesEnabled = true
        googleMaps.isTrafficEnabled = true
    }
}