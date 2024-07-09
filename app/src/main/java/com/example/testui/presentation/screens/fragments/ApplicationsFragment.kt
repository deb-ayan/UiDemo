package com.example.testui.presentation.screens.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testui.R
import com.example.testui.databinding.FragmentApplicationsBinding
import com.example.testui.model.Applications
import com.example.testui.presentation.adapter.ApplicationsAdapter

class ApplicationsFragment : Fragment() {
    private lateinit var binding: FragmentApplicationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentApplicationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apps = listOf(
            Applications("Assistant", R.drawable.assistant, false),
            Applications("Calculator", R.drawable.calculator, false),
            Applications("Calculator", R.drawable.cal, false),
            Applications("Calendar", R.drawable.calendar, false),
            Applications("Chrome", R.drawable.chrome, false),
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ApplicationsAdapter(requireContext(),apps)

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredApps = apps.filter { it.appName.lowercase().contains(s.toString().lowercase()) }
                binding.recyclerView.adapter = ApplicationsAdapter(requireContext(),filteredApps)
            }

            override fun afterTextChanged(s: Editable?) {}

        })

    }

}