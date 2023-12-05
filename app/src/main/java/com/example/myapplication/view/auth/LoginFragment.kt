package com.example.myapplication.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

//        val sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
//        val isLoggedIn = sharedPreferencesHelper.getLoginStatus()

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

            viewModel.loginUser(email, password) { result ->
                if (result) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                }
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel





    }

}