package com.angelodev.helpapp.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angelodev.helpapp.data.local.entity.CircuitEntity
import com.angelodev.helpapp.databinding.RvLayoutBinding

class CircuitListAdapter(
    private val onEditClick: (CircuitEntity) -> Unit
) : ListAdapter<CircuitEntity, CircuitListAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<CircuitEntity>() {
        override fun areItemsTheSame(oldItem: CircuitEntity, newItem: CircuitEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CircuitEntity, newItem: CircuitEntity) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(private val binding: RvLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(circuit: CircuitEntity, position: Int) {
            binding.circuit = circuit
            binding.NumberId.text = (position + 1).toString()
            binding.edit.setOnClickListener { onEditClick(circuit) }
            binding.executePendingBindings()
        }
    }
}
