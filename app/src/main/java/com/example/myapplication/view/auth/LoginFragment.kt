package com.example.myapplication.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.view.menuAdmin.HomeAdminActivity
import com.example.myapplication.view.menuUser.HomepageActivity

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by activityViewModels()

    private var a : Int = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.btnLogin.setOnClickListener {
            val email = binding.editTxtGmail.text.toString()
            val password = binding.editTxtPassword.text.toString()

            a = a + 1
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


//            if(a > 0){
            viewModel.loginUser(email, password) { result ->
                if (result) {

                    viewModel.getAllAdmins()

                    startActivity(Intent(requireContext(), HomepageActivity::class.java))

//                    if(viewModel.checkAdminOrUser() == "admin"){
//                        startActivity(Intent(requireContext(), HomepageActivity::class.java))
////                        startActivity(Intent(requireContext(), HomeAdminActivity::class.java))
//
//                    } else if (viewModel.checkAdminOrUser() == "user"){
//                        startActivity(Intent(requireContext(), HomeAdminActivity::class.java))
////                        startActivity(Intent(requireContext(), HomepageActivity::class.java))
//
//                    }


//                    viewModel.saveUserIdSharePrefs(viewModel.getUserId())
//                    viewModel.initializeUserProfSharedPrefData()

//                    viewModel.getUserDataByUserId()

//                    viewModel.getUserProfileByUserAuthId()
//                    Toast.makeText(requireContext(), viewModel.ab, Toast.LENGTH_SHORT).show()

//                    retrieve data dari firestore admin
//                }
            }
            }}


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}