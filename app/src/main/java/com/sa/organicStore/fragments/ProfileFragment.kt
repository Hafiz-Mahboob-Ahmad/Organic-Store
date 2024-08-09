package com.sa.organicStore.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.sa.organicStore.database.entities.UserEntity
import com.sa.organicStore.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProfile()
    }

    private fun getProfile() {
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val json = sharedPref.getString("user", null)

        val user = json?.let { Gson().fromJson(it, UserEntity::class.java) }
        updateUI(user)
    }

    private fun updateUI(user: UserEntity?) {
        if (user != null) {
            binding.tvName.text = user.name
            binding.tvEmail.text = user.email
        } else {
            binding.tvName.text = "No Name"
            binding.tvEmail.text = "No Email"
        }
    }
}