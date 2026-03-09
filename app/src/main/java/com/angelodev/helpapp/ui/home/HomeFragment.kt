package com.angelodev.helpapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.angelodev.helpapp.R
import com.angelodev.helpapp.databinding.FragmentHomeBinding
import com.angelodev.helpapp.ui.about.AboutActivity
import com.angelodev.helpapp.ui.project.CircuitInputActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.create.setOnClickListener {
            viewModel.clearProjectData()
            startActivity(Intent(requireActivity(), CircuitInputActivity::class.java))
        }

        binding.about.setOnClickListener {
            startActivity(Intent(requireActivity(), AboutActivity::class.java))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
