package com.android.kumbar.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.kumbar.R
import com.android.kumbar.activities.ListMountActivity
import com.android.kumbar.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnCentralJava.setOnClickListener(this)
        binding.btnEastJava.setOnClickListener(this)
        binding.btnWestJava.setOnClickListener(this)
        binding.btnOutsideJava.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCentralJava -> {
                val moveWithDataIntent = Intent(activity, ListMountActivity::class.java)
                moveWithDataIntent.putExtra(ListMountActivity.EXTRA_LOCATION, "Central Java")
                startActivity(moveWithDataIntent)
            }
            R.id.btnEastJava -> {
                val moveWithDataIntent = Intent(activity, ListMountActivity::class.java)
                moveWithDataIntent.putExtra(ListMountActivity.EXTRA_LOCATION, "East Java")
                startActivity(moveWithDataIntent)
            }
            R.id.btnWestJava -> {
                val moveWithDataIntent = Intent(activity, ListMountActivity::class.java)
                moveWithDataIntent.putExtra(ListMountActivity.EXTRA_LOCATION, "West Java")
                startActivity(moveWithDataIntent)
            }
            R.id.btnOutsideJava -> {
                val moveWithDataIntent = Intent(activity, ListMountActivity::class.java)
                moveWithDataIntent.putExtra(ListMountActivity.EXTRA_LOCATION, "Outside of Java")
                startActivity(moveWithDataIntent)
            }
        }
    }

}