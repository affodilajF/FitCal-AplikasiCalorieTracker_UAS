package com.example.myapplication.view.menuUser.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.example.myapplication.data.model.room.MenuData
import com.example.myapplication.databinding.ItemMenu2Binding



typealias OnClickData = (MenuData) -> Unit

class MenuDataAdapter(
    private var onClickData : OnClickData) :
    RecyclerView.Adapter<MenuDataAdapter.ItemNoteViewHolder>() {

    private var listdata : List<MenuData> = ArrayList()

    inner class ItemNoteViewHolder(private var binding: ItemMenu2Binding) : RecyclerView.ViewHolder(binding.root){

        fun bind(menu: MenuData) {
            with(binding){

                txtMenuName.text = menu.name
                txtMealCategory.text = menu.category
                txtDate.text = menu.date
                txtTotalcal.text = menu.calAmount.toString()
                idServing.text = menu.servings.toString() + " servings"

                Glide.with(itemView.context).asBitmap().load(menu.urlPhoto)
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .override(300, 300)
                    .centerCrop()
                    .into(imgFood)

                itemView.setOnClickListener{
                    onClickData(menu)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNoteViewHolder {
        val binding = ItemMenu2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemNoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    override fun onBindViewHolder(holder: ItemNoteViewHolder, position: Int) {
        holder.bind(listdata[position])
    }

    fun updateData(newNotesList: List<MenuData>) {
        listdata= newNotesList
        notifyDataSetChanged()
    }


}

