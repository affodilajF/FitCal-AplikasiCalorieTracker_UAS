package com.example.myapplication.view.menuUser.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.lifecycle.Observer
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
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        viewModel.getUserDataByUserId()
        observeDataUI()

        with(binding){
            txtPersonaldata.setOnClickListener {
                if (layoutPersonaldata.visibility == View.VISIBLE) {
                    val fadeOut = AlphaAnimation(1f, 0f)
                    fadeOut.duration = 1
                    layoutPersonaldata.startAnimation(fadeOut)

                    layoutPersonaldata.visibility = View.GONE
                    imagebtnviewPersonaldata.setImageResource(R.drawable.baseline_keyboard_arrow_right_24)
                } else {
                    TransitionManager.beginDelayedTransition(layoutPersonaldata)

                    layoutPersonaldata.visibility = View.VISIBLE
                    imagebtnviewPersonaldata.setImageResource(R.drawable.baseline_close_24_2)

                }
            }

            txtAccount.setOnClickListener {
                if (layoutAccount.visibility == View.VISIBLE) {
                    val fadeOut = AlphaAnimation(1f, 0f)
                    fadeOut.duration = 1
                    layoutAccount.startAnimation(fadeOut)

                    layoutAccount.visibility = View.GONE
                    imgbtnviewAccount.setImageResource(R.drawable.baseline_keyboard_arrow_right_24)
                } else {
                    TransitionManager.beginDelayedTransition(layoutAccount)
                    layoutAccount.visibility = View.VISIBLE
                    imgbtnviewAccount.setImageResource(R.drawable.baseline_close_24_2)

                }
            }



        }



        binding.btnLogout.setOnClickListener {

            viewModel.logout()
            val intent = Intent(requireActivity(),AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }

        return view
    }


    private fun observeDataUI(){
        with(binding){
            viewModel.userProfileListLiveData.observe(viewLifecycleOwner, Observer {myData
                ->
                val userObject = myData[0]
                txtUsn.text = userObject.userName
                txtGmail.text = userObject.email
                txtDitTarget.text = userObject.dietGoal

                height.text = userObject.height + " cm"
                cweight.text = userObject.currentWeight + " kg"
                tweight.text = userObject.targetedWeight + " kg"
                txtTargetcal.text = userObject.dayTargetedCalorie


                txtAccountGmail.text = userObject.email
                txtAccountPhoneNumber.text = userObject.userPhone



            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}