package com.example.karunada_kala.ui.explorer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.karunada_kala.adapters.ArtFormAdapter
import com.example.karunada_kala.databinding.FragmentExplorerBinding
import com.example.karunada_kala.viewmodel.ArtViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExplorerFragment : Fragment() {

    private var _binding: FragmentExplorerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArtViewModel by viewModels()
    private lateinit var adapter: ArtFormAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExplorerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArtFormAdapter { artForm ->
            val action = ExplorerFragmentDirections.actionExplorerFragmentToDetailFragment(artForm.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ExplorerFragment.adapter
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }

        // Observe Art Forms
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allArtForms.collectLatest { artForms ->
                    adapter.submitList(artForms)
                    binding.tvEmptyState.isVisible = artForms.isEmpty() && !viewModel.isLoading.value
                }
            }
        }

        // Observe Loading State
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    binding.swipeRefresh.isRefreshing = isLoading
                    if (!binding.swipeRefresh.isRefreshing) {
                        binding.progressBar.isVisible = isLoading
                    }
                    if (isLoading) {
                        binding.tvEmptyState.isVisible = false
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
