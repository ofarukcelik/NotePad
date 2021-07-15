package com.task.noteapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.task.noteapp.R
import com.task.noteapp.model.SelectedImage

class SelectedImageAdapter(var images: ArrayList<SelectedImage>, var context: Context) :
  RecyclerView.Adapter<SelectedImageAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imgPicked: ImageView = view.findViewById(R.id.imgPicked)
    var icDelete: ImageView? = view.findViewById(R.id.icDelete)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.selected_images_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    Glide.with(context).load(images!![position].image).into(holder.imgPicked)
    holder.icDelete?.setOnClickListener {
      images.remove(images[position])
      notifyDataSetChanged()
    }
  }

  override fun getItemCount(): Int {
    return images.size
  }
}