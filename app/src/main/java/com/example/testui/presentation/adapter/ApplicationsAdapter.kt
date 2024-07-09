package com.example.testui.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testui.databinding.ListItemBinding
import com.example.testui.model.Applications

class ApplicationsAdapter(
    private val apps: List<Applications>,
    private val onCheckChanged: OnCheckChanged
): RecyclerView.Adapter<ApplicationsAdapter.ApplicationsViewHolder>() {
    inner class ApplicationsViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(app: Applications){
            binding.appName.text = app.appName
            binding.appIcon.load(app.appIcon)
            binding.switchButton.isChecked = app.isConnected
            binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
                onCheckChanged.onCheck(adapterPosition, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationsViewHolder {
        val item =  ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplicationsViewHolder(item)
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    override fun onBindViewHolder(holder: ApplicationsViewHolder, position: Int) {
        holder.bind(apps[position])
    }

    interface OnCheckChanged{
        fun onCheck(position: Int, state: Boolean)
    }

}