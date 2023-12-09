package com.example.myapplication.view.menuUser.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.view.auth.AuthActivity
import com.example.myapplication.view.menuUser.HomepageActivity
import com.example.myapplication.view.menuUser.addMenu.addMenuCustom.AddCustomMenuActivity

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)



        binding.txtName.text = viewModel.getUsn() ?: ""
        binding.txtPhone.text = viewModel.getPhone() ?: ""


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