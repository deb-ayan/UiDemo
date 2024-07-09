package com.example.testui.presentation.screens.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testui.databinding.FragmentApplicationsBinding
import com.example.testui.model.Applications
import com.example.testui.presentation.adapter.ApplicationsAdapter
import com.example.testui.presentation.viewmodel.ApplicationViewModel
import com.example.testui.utils.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplicationsFragment : Fragment(), ApplicationsAdapter.OnCheckChanged {
    private lateinit var binding: FragmentApplicationsBinding
    private val applicationViewModel: ApplicationViewModel by viewModels()
    private lateinit var localList: List<Applications>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentApplicationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applicationViewModel.getApplications()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                applicationViewModel.apiState.collect { response ->
                    when(response){
                        is NetworkResponse.Idle->{}
                        is NetworkResponse.Loading->{
                            binding.progress.visibility  = View.VISIBLE
                        }
                        is NetworkResponse.Success->{
                            binding.progress.visibility  = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            val apps = response.data?.info?.app_list
                            if (apps != null) {
                                localList = apps.map { Applications(it.app_name, it.app_icon, it.status == "Active" || it.status == "active") }
                                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                                binding.recyclerView.adapter = ApplicationsAdapter(localList, this@ApplicationsFragment)
                            }else{
                                binding.recyclerView.visibility = View.GONE
                                binding.noAppTv.visibility = View.VISIBLE
                            }
                        }
                        is NetworkResponse.Error->{
                            binding.progress.visibility  = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            binding.noAppTv.visibility = View.VISIBLE
                            Log.e("NETWORK_ERROR", "onViewCreated: ${response.message}", )
                        }
                    }
                }
            }
        }

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (localList.isNotEmpty()){
                    binding.noAppTv.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    val filteredApps = localList.filter { it.appName.lowercase().startsWith(s.toString().lowercase()) }
                    if (filteredApps.isNotEmpty()){
                        binding.recyclerView.adapter = ApplicationsAdapter(filteredApps, this@ApplicationsFragment)
                    }else{
                        binding.recyclerView.visibility = View.GONE
                        binding.noAppTv.visibility = View.VISIBLE
                    }
                }else{
                    binding.recyclerView.visibility = View.GONE
                    binding.noAppTv.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })

    }

    override fun onCheck(position: Int, state: Boolean) {
        localList[position].isConnected = state
    }

}