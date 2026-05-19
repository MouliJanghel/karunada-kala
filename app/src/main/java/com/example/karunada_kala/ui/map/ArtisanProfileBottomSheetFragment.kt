package com.example.karunada_kala.ui.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.karunada_kala.databinding.BottomSheetArtisanProfileBinding
import com.example.karunada_kala.model.Artisan
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ArtisanProfileBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetArtisanProfileBinding? = null
    private val binding get() = _binding!!
    private var artisan: Artisan? = null

    companion object {
        fun newInstance(artisan: Artisan): ArtisanProfileBottomSheetFragment {
            val fragment = ArtisanProfileBottomSheetFragment()
            fragment.artisan = artisan
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetArtisanProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artisan?.let { item ->
            binding.artisanName.text = item.name
            binding.artisanArtForm.text = item.artForm
            binding.artisanDescription.text = item.description
            binding.artisanImage.load(item.photoUrl)

            binding.btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${item.phone}")
                }
                startActivity(intent)
            }

            binding.btnShare.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Artisan Profile: ${item.name}")
                    putExtra(Intent.EXTRA_TEXT, "Check out ${item.name}, an expert in ${item.artForm}!")
                }
                startActivity(Intent.createChooser(shareIntent, "Share via"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}