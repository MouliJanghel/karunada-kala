package com.example.karunada_kala.ui.marketplace

import android.content.Intent
import android.net.Uri
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.karunada_kala.adapters.ProductAdapter
import com.example.karunada_kala.databinding.FragmentMarketplaceBinding
import com.example.karunada_kala.viewmodel.ArtViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MarketplaceFragment : Fragment() {

    private var _binding: FragmentMarketplaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArtViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductAdapter { product ->
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${product.artisanContact}")
            }
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MarketplaceFragment.adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collectLatest { products ->
                    if (products.isEmpty()) {
                        // Dummy data for demo if Firestore is empty
                        val dummyProducts = listOf(
                            com.example.karunada_kala.model.Product("1", "Bidriware Vase", "Exquisite Bidriware handicraft from Bidar.", "₹2500", "https://example.com/bidri.jpg", "9876543210"),
                            com.example.karunada_kala.model.Product("2", "Ilkal Saree", "Traditional hand-woven Ilkal saree with Kasuti embroidery.", "₹3500", "https://example.com/ilkal.jpg", "9876543211"),
                            com.example.karunada_kala.model.Product("3", "Channapatna Toys", "Eco-friendly wooden toys from Channapatna.", "₹500", "https://example.com/toys.jpg", "9876543212")
                        )
                        adapter.submitList(dummyProducts)
                        binding.tvEmptyProducts.isVisible = false
                    } else {
                        adapter.submitList(products)
                        binding.tvEmptyProducts.isVisible = false
                    }
                }
            }
        }

        // Observe Loading State
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    binding.progressBar.isVisible = isLoading
                    if (isLoading) {
                        binding.tvEmptyProducts.isVisible = false
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
