package com.example.myapplication.view.menuUser.addMenu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.view.auth.LoginFragment
import com.example.myapplication.view.auth.RegisterFragment

class MenuTabAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    val page = arrayOf(ListMenuFragment(), AddMenuFormFragment())

    override fun getItemCount(): Int {
        return page.size
    }

    override fun createFragment(position: Int): Fragment {
        return page[position]
    }



}