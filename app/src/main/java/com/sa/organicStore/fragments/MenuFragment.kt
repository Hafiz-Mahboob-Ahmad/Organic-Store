package com.sa.organicStore.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sa.organicStore.R
import com.sa.organicStore.activities.WelcomeActivity
import com.sa.organicStore.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }
        Snackbar.make(requireView(), "Successfully logged out", Snackbar.LENGTH_SHORT).show()
        navigateToWelcomeScreen()
    }

    private fun navigateToWelcomeScreen() {
        startActivity(Intent(requireActivity(), WelcomeActivity::class.java))
        requireActivity().finish()
    }
}