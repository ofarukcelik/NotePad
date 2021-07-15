package com.task.noteapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R

class SelectedImageAdapter(var images: ArrayList<Bitmap>, var context: Context) :
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
    holder.imgPicked.setImageBitmap(images[position])
    holder.icDelete?.setOnClickListener {
      images.remove(images[position])
      notifyDataSetChanged()
    }
  }

  override fun getItemCount(): Int {
    return images.size
  }
}