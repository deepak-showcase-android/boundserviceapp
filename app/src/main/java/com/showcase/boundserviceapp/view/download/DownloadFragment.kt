package com.showcase.boundserviceapp.view.download

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.showcase.boundserviceapp.R
import com.showcase.boundserviceapp.databinding.FragmentDashboardBinding
import com.showcase.boundserviceapp.services.DownloadService
import com.showcase.boundserviceapp.services.IProgressProvider
import com.showcase.boundserviceapp.view.base.BaseFragment

class DownloadFragment : BaseFragment(), IProgressProvider {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DownloadViewModel by viewModels()

    private lateinit var downloadService: DownloadService
    private var isBound = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
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

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as DownloadService.LocalBinder
            downloadService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        requireContext().bindService(
            Intent(requireContext(), DownloadService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        super.onStop()
        requireContext().unbindService(connection)
    }

    override fun setListeners() {
        super.setListeners()
        binding.tvStartDownload.setOnClickListener {
            if (isBound) {
                downloadService.startDownload(this)
            }
        }
    }

    override fun onDownloadProgress(value: Int) {
        Toast.makeText(
            requireContext(),
            String.format(getString(R.string.progress_value), value.toString()),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDownloadCompleted() {
        Toast.makeText(requireContext(), getString(R.string.download_completed), Toast.LENGTH_SHORT)
            .show()
    }

    override fun onStartedDownload() {
        Toast.makeText(requireContext(), getString(R.string.download_started), Toast.LENGTH_SHORT)
            .show()
    }
}