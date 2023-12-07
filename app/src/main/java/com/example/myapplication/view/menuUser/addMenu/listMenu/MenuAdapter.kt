package com.example.myapplication.view.menuUser.addMenu.listMenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.Menu
import com.example.myapplication.databinding.ItemMenuBinding

typealias OnClickData = (Menu) -> Unit


class MenuAdapter(
    private var onClickData : OnClickData) :
    RecyclerView.Adapter<MenuAdapter.ItemNoteViewHolder>() {

    private var listdata : List<Menu> = ArrayList()

    inner class ItemNoteViewHolder(private var binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(note: Menu) {
            with(binding){


                itemView.setOnClickListener{
                    onClickData(note)
                }
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNoteViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemNoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    override fun onBindViewHolder(holder: ItemNoteViewHolder, position: Int) {
        holder.bind(listdata[position])
    }
}

