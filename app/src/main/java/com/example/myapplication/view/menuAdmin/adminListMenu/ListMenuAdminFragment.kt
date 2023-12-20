package com.example.myapplication.view.menuAdmin.adminListMenu

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHomepageAdminBinding
import com.example.myapplication.view.menuAdmin.adminAddMenu.AdminAddMenuActivity
import com.example.myapplication.view.menuAdmin.adminUpdateMenu.AdminUpdateMenuDialogFragment
import com.example.myapplication.view.menuUser.listMenu.MenuAdapter

class ListMenuAdminFragment : Fragment() {

    private lateinit var binding : FragmentHomepageAdminBinding
    private lateinit var viewModel: ListMenuAdminViewModel
    private lateinit var adapterMenuItem : MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ListMenuAdminViewModel::class.java]
//        viewModel.initializeDBRoom(requireContext())


        binding = FragmentHomepageAdminBinding.inflate(inflater, container, false)
        val view = binding.root

//        rec view
        observeMenus()
        viewModel.getAllMenus()

        adapterMenuItem = MenuAdapter { menu ->
            val dialogFragment = AdminUpdateMenuDialogFragment()
            val args = Bundle()
            args.putSerializable("menu object", menu)
            dialogFragment.arguments = args
            dialogFragment.show(requireActivity().supportFragmentManager, "AdminUpdateMenuDialogFragment")
        }


        binding.rvItemMenu.apply {
            adapter = adapterMenuItem
            layoutManager = LinearLayoutManager(requireContext())
        }


        binding.btnAdd.setOnClickListener {
            val intent = Intent(requireContext(), AdminAddMenuActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        return view
    }

    private fun observeMenus(){
        viewModel.menuListLiveData.observe(viewLifecycleOwner){
                menus ->
            adapterMenuItem.updateData(menus)

        }
    }

}