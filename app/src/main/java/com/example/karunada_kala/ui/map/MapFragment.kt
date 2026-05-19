package com.example.karunada_kala.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.karunada_kala.R
import com.example.karunada_kala.databinding.FragmentMapBinding
import com.example.karunada_kala.model.Artisan
import com.example.karunada_kala.utils.bitmapDescriptorFromVector
import com.example.karunada_kala.viewmodel.ArtViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArtViewModel by viewModels()
    private var googleMap: GoogleMap? = null
    private var artisanList: List<Artisan> = emptyList()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            enableMyLocation()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        
        binding.fabMyLocation.setOnClickListener {
            checkLocationPermission()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    binding.progressBar.isVisible = isLoading
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        
        // Karnataka Center
        val karnataka = LatLng(15.3173, 75.7139)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(karnataka, 7f))

        googleMap?.setOnMarkerClickListener { marker: Marker ->
            val artisan = artisanList.find { it.name == marker.title }
            artisan?.let {
                val bottomSheet = ArtisanProfileBottomSheetFragment.newInstance(it)
                bottomSheet.show(parentFragmentManager, "ArtisanProfile")
            }
            true
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.artisans.collectLatest { artisans ->
                    artisanList = artisans
                    addMarkers(artisans)
                }
            }
        }
        
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                enableMyLocation()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun enableMyLocation() {
        try {
            googleMap?.isMyLocationEnabled = true
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun addMarkers(artisans: List<Artisan>) {
        googleMap?.clear()
        artisans.forEach { artisan ->
            val position = LatLng(artisan.latitude, artisan.longitude)
            
            val markerOptions = MarkerOptions()
                .position(position)
                .title(artisan.name)
                .snippet(artisan.artForm)

            // Custom markers based on type
            when (artisan.type) {
                "Workshop" -> {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                }
                "Performance" -> {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                }
                "Seller" -> {
                    val customIcon = bitmapDescriptorFromVector(requireContext(), R.drawable.ic_shop)
                    if (customIcon != null) {
                        markerOptions.icon(customIcon)
                    } else {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    }
                }
                else -> {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                }
            }
            
            googleMap?.addMarker(markerOptions)
        }
    }

    override fun onResume() {
        super.onResume()
        _binding?.mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        _binding?.mapView?.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.mapView?.onDestroy()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        _binding?.mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        _binding?.mapView?.onSaveInstanceState(outState)
    }
}
