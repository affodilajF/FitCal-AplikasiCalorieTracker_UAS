package com.example.myapplication.view.menuAdmin.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomepageAdminBinding
import com.example.myapplication.databinding.FragmentProfileAdminBinding
import com.example.myapplication.view.auth.AuthActivity

class ProfileAdminFragment : Fragment() {


    private lateinit var binding : FragmentProfileAdminBinding
    private lateinit var viewModel: ProfileAdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProfileAdminViewModel::class.java)
        binding = FragmentProfileAdminBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}