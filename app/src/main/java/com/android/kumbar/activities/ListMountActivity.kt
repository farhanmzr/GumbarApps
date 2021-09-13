package com.android.kumbar.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.kumbar.adapter.ListMountAdapter
import com.android.kumbar.databinding.ActivityListMountBinding
import com.android.kumbar.model.Mount
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class ListMountActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LOCATION = "extra_location"

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

    private lateinit var binding: ActivityListMountBinding
    private lateinit var adapter: ListMountAdapter
    var locationMountain: String? = null
    val listMountains = ArrayList<Mount>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set transparent statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }


        initView()
    }

    private fun initView() {

        binding.imgBack.setOnClickListener {
            finish()
        }
        //getDataIntent
        locationMountain = intent.getStringExtra(EXTRA_LOCATION)
        binding.txtTitle.text = locationMountain
        Log.e(locationMountain, "location")

        adapter = ListMountAdapter()
        adapter.notifyDataSetChanged()

        binding.rvMount.layoutManager = LinearLayoutManager(this)
        binding.rvMount.adapter = adapter
        binding.rvMount.setHasFixedSize(true)

        showLoading(true)
        //method to show data mountain
        getListGunung()
        adapter.setOnItemClickCallback(object : ListMountAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Mount) {
                showSelectedMount(data)
            }
        })

    }

    private fun showSelectedMount(data: Mount) {
//        Toast.makeText(this, "${data.name}", Toast.LENGTH_SHORT).show()
        val intentDetail = Intent(this, DetailMountActivity::class.java)
        intentDetail.putExtra(DetailMountActivity.EXTRA_MOUNT, data)
        startActivity(intentDetail)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getListGunung() {
        try {
            val stream = assets.open("data.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val strContent = String(buffer, StandardCharsets.UTF_8)
            try {
                val jsonObject = JSONObject(strContent)
                val jsonArray = jsonObject.getJSONArray("gunung")
                for (i in 0 until jsonArray.length()) {
                    val jsonObjectData = jsonArray.getJSONObject(i)
                    if (jsonObjectData.getString("lokasi") == locationMountain) {
                        val jsonArrayMountain = jsonObjectData.getJSONArray("nama_gunung")
                        for (j in 0 until jsonArrayMountain.length()) {
                            val dataApi = Mount()
                            val objectMountain = jsonArrayMountain.getJSONObject(j)
                            dataApi.imgMount = objectMountain.getString("image_gunung")
                            dataApi.name = objectMountain.getString("nama")
                            dataApi.location = objectMountain.getString("lokasi")
                            dataApi.altitude = objectMountain.getString("altitude")
                            dataApi.description = objectMountain.getString("deskripsi")
                            dataApi.infoMount = objectMountain.getString("info_gunung")
                            dataApi.hikingTrails = objectMountain.getString("jalur_pendakian")
                            dataApi.lat = objectMountain.getDouble("lat")
                            dataApi.lon = objectMountain.getDouble("lon")
                            listMountains.add(dataApi)
                        }
                        adapter.setData(listMountains)
                        showLoading(false)

                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } catch (ignored: IOException) {
            Toast.makeText(this@ListMountActivity, "Oops, ada yang tidak beres. Coba ulangi beberapa saat lagi.",
                Toast.LENGTH_SHORT).show()
        }
    }


}

