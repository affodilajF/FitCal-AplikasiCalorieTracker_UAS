package com.example.myapplication.view.menuUser.addMenu2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.FragmentListMenuBinding


class ListMenuFragment : Fragment() {

    private lateinit var binding: FragmentListMenuBinding
    private val viewModel: AddMenuViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListMenuBinding.inflate(inflater, container, false)
        val view = binding.root





        return view
    }


}