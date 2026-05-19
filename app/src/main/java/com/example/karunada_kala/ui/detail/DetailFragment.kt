package com.example.karunada_kala.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.karunada_kala.R
import com.example.karunada_kala.adapters.ArtisanSmallAdapter
import com.example.karunada_kala.databinding.FragmentDetailBinding
import com.example.karunada_kala.ui.map.ArtisanProfileBottomSheetFragment
import com.example.karunada_kala.viewmodel.ArtViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: ArtViewModel by viewModels()
    private var player: ExoPlayer? = null
    private lateinit var artisanAdapter: ArtisanSmallAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        artisanAdapter = ArtisanSmallAdapter { artisan ->
            val bottomSheet = ArtisanProfileBottomSheetFragment.newInstance(artisan)
            bottomSheet.show(childFragmentManager, "ArtisanProfile")
        }

        binding.rvRelatedArtisans.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = artisanAdapter
        }

        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_workshopSignupFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allArtForms.collectLatest { artForms ->
                    val artForm = artForms.find { it.id == args.artFormId }
                    artForm?.let { item ->
                        binding.detailName.text = item.name
                        binding.detailRegion.text = item.region
                        binding.detailDescription.text = item.description
                        binding.detailImage.load(item.imageUrl)
                        binding.collapsingToolbar.title = item.name
                        
                        if (item.videoUrl.isNotEmpty()) {
                            binding.playerView.visibility = View.VISIBLE
                            initializePlayer(item.videoUrl)
                        } else {
                            binding.playerView.visibility = View.GONE
                        }

                        // Filter and show related artisans
                        viewModel.artisans.collectLatest { artisans ->
                            val related = artisans.filter { 
                                it.artForm.contains(item.name, ignoreCase = true) 
                            }
                            artisanAdapter.submitList(related)
                        }
                    }
                }
            }
        }
    }

    @OptIn(UnstableApi::class)
    private fun initializePlayer(videoUrl: String) {
        if (player == null) {
            player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
                binding.playerView.player = exoPlayer
                val mediaItem = MediaItem.fromUri(videoUrl)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player?.release()
        player = null
        _binding = null
    }
}
