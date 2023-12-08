package com.example.myapplication.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.view.personalData.PersonalDataActivity

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.btnRegister.setOnClickListener {
            val email = binding.editTxtEmail.text.toString()
            val password = binding.editTxtPassword.text.toString()

            //Validasi email
            if (email.isEmpty()){
                binding.editTxtEmail.error = "Email Harus Diisi"
                binding.editTxtEmail.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.editTxtEmail.error = "Email Tidak Valid"
                binding.editTxtEmail.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()){
                binding.editTxtPassword.error = "Password Harus Diisi"
                binding.editTxtPassword.requestFocus()
                return@setOnClickListener
            }


            viewModel.registerUser(email, password) { result ->
                if (result) {
//                    (activity as AuthActivity).viewPager2.setCurrentItem(1, true)

                    startActivity(Intent(requireContext(), PersonalDataActivity::class.java))
                    viewModel.saveUserIdSharePrefs(viewModel.getUserId())
                    viewModel.saveUserNameSharePrefs(binding.editTextUsername.text.toString())
                    viewModel.saveUserPhoneSharePrefs(binding.editTxtPhone.text.toString())
//                    Toast.makeText(requireContext(), selectedDate.toString(), Toast.LENGTH_SHORT).show()
                }
            }

        }

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)


    }

}