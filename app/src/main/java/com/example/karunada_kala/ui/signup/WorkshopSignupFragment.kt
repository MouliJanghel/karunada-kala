package com.example.karunada_kala.ui.signup

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.karunada_kala.R
import com.example.karunada_kala.databinding.FragmentWorkshopSignupBinding
import com.example.karunada_kala.viewmodel.ArtViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class WorkshopSignupFragment : Fragment() {

    private var _binding: FragmentWorkshopSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArtViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkshopSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val date = binding.etDate.text.toString().trim()

            when {
                name.isBlank() || phone.isBlank() || date.isBlank() -> {
                    Snackbar.make(binding.root, R.string.error_fill_all, Snackbar.LENGTH_SHORT).show()
                }
                !Patterns.PHONE.matcher(phone).matches() -> {
                    Snackbar.make(binding.root, R.string.error_invalid_phone, Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.submitWorkshopSignup(name, phone, date) { success ->
                        if (success) {
                            Toast.makeText(requireContext(), R.string.registration_success, Toast.LENGTH_LONG).show()
                            parentFragmentManager.popBackStack()
                        } else {
                            Snackbar.make(binding.root, R.string.registration_failed, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    binding.btnSubmit.isEnabled = !isLoading
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
