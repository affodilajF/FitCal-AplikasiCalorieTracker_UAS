package com.example.myapplication.view.menuUser.listMenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.example.myapplication.data.model.firestore.Menu
import com.example.myapplication.databinding.ItemMenuBinding

typealias OnClickData = (Menu) -> Unit


class MenuAdapter(
    private var onClickData : OnClickData
) :
    RecyclerView.Adapter<MenuAdapter.ItemNoteViewHolder>() {

    private var listdata : List<Menu> = ArrayList()

    inner class ItemNoteViewHolder(private var binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(menu: Menu) {
            with(binding){

                txtMenuName.text = menu.name
                txtCarbsAmount.text = menu.carbsGram + " gr"
                txtProteinAmount.text = menu.proteinGram + " gr"
                txtFatAmount.text = menu.fatGram + " gr"
                txtTotalcal.text = menu.calAmount + " cal"

                Glide.with(itemView.context).asBitmap().load(menu.urlPhoto)
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgFood)

                itemView.setOnClickListener{
                    onClickData(menu)
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

    fun updateData(newNotesList: List<Menu>) {
        listdata= newNotesList
        notifyDataSetChanged()
    }


}

