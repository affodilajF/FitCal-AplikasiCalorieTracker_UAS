package com.example.myapplication.view.menuAdmin.LocalAdminListMenu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentLocalMenuBinding
import com.example.myapplication.view.menuAdmin.LocalAdminUpdateMenu.AdminUpdateMenuLocalDialogFragment


class LocalMenuFragment : Fragment() {

    private lateinit var viewModel: LocalMenuViewModel
    private lateinit var binding: FragmentLocalMenuBinding
    private lateinit var adapterMenuItem : MenuAdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LocalMenuViewModel::class.java)
        binding = FragmentLocalMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.initializeDBRoom(requireContext())

        adapterMenuItem = MenuAdminAdapter { menu ->
            val dialogFragment = AdminUpdateMenuLocalDialogFragment()
            val args = Bundle()
            args.putSerializable("menu object", menu)
            dialogFragment.arguments = args
            dialogFragment.show(requireActivity().supportFragmentManager, "AdminUpdateMenuDialogFragment")
        }

        binding.rvItemMenuLocal.apply {
            adapter = adapterMenuItem
            layoutManager = LinearLayoutManager(requireContext())
        }

        observeMenus()

        binding.btnSync.setOnClickListener {
            viewModel.syncRoomToFirestore(viewLifecycleOwner, requireContext())
        }
        return view
    }

    private fun observeMenus(){
        viewModel.getAllLiveData().observe(viewLifecycleOwner){
                menus ->
            adapterMenuItem.updateData(menus)

        }
    }



}