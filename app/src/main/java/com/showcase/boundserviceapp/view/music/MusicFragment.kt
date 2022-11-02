package com.showcase.boundserviceapp.view.music

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.showcase.boundserviceapp.databinding.FragmentHomeBinding
import com.showcase.boundserviceapp.services.MusicService
import com.showcase.boundserviceapp.view.base.BaseFragment

class MusicFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MusicViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setListeners() {
        super.setListeners()
        binding.tvStart.setOnClickListener {
            requireContext().startService(Intent(requireContext(),MusicService::class.java))
        }
        binding.tvStop.setOnClickListener {
            requireContext().stopService(Intent(requireContext(),MusicService::class.java))
        }
    }
}