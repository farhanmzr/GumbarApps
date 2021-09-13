package com.android.kumbar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.android.kumbar.R
import com.android.kumbar.databinding.ItemRvListEquipmentBinding
import com.android.kumbar.loadImage
import com.android.kumbar.model.Equipment


class ListEquipmentAdapter : RecyclerView.Adapter<ListEquipmentAdapter.ListViewHolder>() {

    private var mData = ArrayList<Equipment>()

    fun setData(items: ArrayList<Equipment>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvListEquipmentBinding.bind(itemView)
        fun bind(equipment: Equipment) {
            binding.imgEquipment.loadImage(equipment.imgEquipment)
            binding.txtName.text = equipment.name
            binding.txtType.text = equipment.type
            binding.txtDescription.text = equipment.description

            binding.imgExpand.setOnClickListener {
                if (binding.hiddenView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        binding.fixedLayout,
                        AutoTransition()
                    )
                    binding.hiddenView.visibility = View.GONE
                    binding.imgExpand.setImageResource(R.drawable.ic_arrow_down)
                } else {
                    TransitionManager.beginDelayedTransition(
                        binding.fixedLayout,
                        AutoTransition()
                    )
                    binding.hiddenView.visibility = View.VISIBLE
                    binding.imgExpand.setImageResource(R.drawable.ic_arrow_up)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_list_equipment, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }


}
