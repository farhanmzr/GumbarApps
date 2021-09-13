package com.android.kumbar.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.kumbar.adapter.ListEquipmentAdapter
import com.android.kumbar.databinding.FragmentEquipmentBinding
import com.android.kumbar.model.Equipment
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets


class EquipmentFragment : Fragment() {

    private var _binding: FragmentEquipmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListEquipmentAdapter
    val listEquipments = ArrayList<Equipment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEquipmentBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = ListEquipmentAdapter()
        adapter.notifyDataSetChanged()

        binding.rvEquipment.layoutManager = LinearLayoutManager(activity)
        binding.rvEquipment.adapter = adapter
        binding.rvEquipment.setHasFixedSize(true)

        showLoading(true)
        //method to show data mountain
        getListEquipment()

        return view
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getListEquipment() {
        try {
            val stream = requireContext().assets.open("equipment.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val strContent = String(buffer, StandardCharsets.UTF_8)
            try {
                val jsonObject = JSONObject(strContent)
                val jsonArray = jsonObject.getJSONArray("peralatan")
                for (i in 0 until jsonArray.length()) {
                    val jsonObjectData = jsonArray.getJSONObject(i)
                    val dataApi = Equipment()
                    dataApi.name = jsonObjectData.getString("nama")
                    dataApi.imgEquipment = jsonObjectData.getString("image_url")
                    dataApi.type = jsonObjectData.getString("tipe")
                    dataApi.description = jsonObjectData.getString("deskripsi")
                    listEquipments.add(dataApi)
                    adapter.setData(listEquipments)
                    showLoading(false)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } catch (ignored: IOException) {
            Toast.makeText(activity, "Oops, ada yang tidak beres. Coba ulangi beberapa saat lagi.", Toast.LENGTH_SHORT).show()
        }
    }

}