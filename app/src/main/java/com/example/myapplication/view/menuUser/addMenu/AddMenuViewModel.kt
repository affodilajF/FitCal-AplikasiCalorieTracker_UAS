package com.example.myapplication.view.menuUser.addMenu

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Menu

class AddMenuViewModel : ViewModel() {

    private var _isUserCustom = false
    private lateinit var dataMenu : Menu

    var isUserCustom: Boolean
        get() = _isUserCustom
        set(value) {
            _isUserCustom = value
        }

    fun setMenu(){
//        dataMenu = Menu()
    }





}