package com.example.testui.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.testui.databinding.ListItemBinding
import com.example.testui.model.Applications

class ApplicationsAdapter(
    private val apps: List<Applications>,
    private val onCheckChanged: OnCheckChanged
): RecyclerView.Adapter<ApplicationsAdapter.ApplicationsViewHolder>() {
    inner class ApplicationsViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(app: Applications, position: Int){
            binding.appName.text = app.appName
           // binding.appIcon.load(app.appIcon)
            binding.appIcon.load(app.appIcon){
                listener(object : ImageRequest.Listener{
                    override fun onError(request: ImageRequest, result: ErrorResult) {
                        binding.loader.visibility = View.GONE
                        binding.appIcon.visibility = View.GONE
                    }

                    override fun onStart(request: ImageRequest) {
                        binding.appIcon.visibility = View.GONE
                        binding.loader.visibility = View.VISIBLE
                    }

                    override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                        binding.loader.visibility = View.GONE
                        binding.appIcon.visibility = View.VISIBLE
                    }
                })
            }
            binding.switchButton.isChecked = app.isConnected
            binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
                onCheckChanged.onCheck(layoutPosition, isChecked)
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
        holder.bind(apps[position], position)
    }

    interface OnCheckChanged{
        fun onCheck(position: Int, state: Boolean)
    }

}