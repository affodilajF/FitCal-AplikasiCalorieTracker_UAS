package com.example.myapplication.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.view.menuUser.HomepageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.btnLogin.setOnClickListener {
            val email = binding.editTxtGmail.text.toString()
            val password = binding.editTxtPassword.text.toString()

            //Validasi email
            if (email.isEmpty()){
                binding.editTxtGmail.error = "Email Harus Diisi"
                binding.editTxtGmail.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.editTxtGmail.error = "Email Tidak Valid"
                binding.editTxtGmail.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()){
                binding.editTxtPassword.error = "Password Harus Diisi"
                binding.editTxtPassword.requestFocus()
                return@setOnClickListener
            }

            navigateRole()

            viewModel.loginUser(email, password) { result ->
                if (result) {
                    viewModel.fetchUserRoleFromFirestore()
                }
            }}


        return view
    }


    private fun navigateRole(){
        viewModel.userRole.observe(viewLifecycleOwner, Observer { role ->
            val destinationActivity = viewModel.navigateBasedOnRole()
            destinationActivity?.let {
                val intent = Intent(requireContext(), it)
                startActivity(intent)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }




}