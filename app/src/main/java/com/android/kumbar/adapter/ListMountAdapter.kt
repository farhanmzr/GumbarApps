package com.android.kumbar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.kumbar.R
import com.android.kumbar.databinding.ItemRvListMountBinding
import com.android.kumbar.loadImage
import com.android.kumbar.model.Mount
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListMountAdapter : RecyclerView.Adapter<ListMountAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var mData = ArrayList<Mount>()

    fun setData(items: ArrayList<Mount>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvListMountBinding.bind(itemView)
        fun bind(mount: Mount) {
            binding.imgMountain.loadImage(mount.imgMount)
            binding.txtName.text = mount.name
            binding.txtAltitude.text = mount.altitude
            binding.txtDescription.text = mount.description
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_list_mount, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(mData[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Mount)
    }

}