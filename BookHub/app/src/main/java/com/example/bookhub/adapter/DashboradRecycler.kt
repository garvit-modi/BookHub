package com.example.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.R
import com.example.bookhub.model.Book
import com.squareup.picasso.Picasso

class DashboradRecycler(val context: Context, val itemList: ArrayList<Book>) :
    RecyclerView.Adapter<DashboradRecycler.DashboradViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboradViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_dashboard_single_row, parent, false)
        return DashboradViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboradViewHolder, position: Int) {

        val book = itemList[position]
        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text = book.bookPrice
        holder.txtBookRating.text = book.booRating
//        holder.txtBookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).into(holder.txtBookImage);

        holder.llContext.setOnClickListener {
            Toast.makeText(context, "Clicked on ${holder.txtBookName.text} ", Toast.LENGTH_SHORT)
                .show()
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class DashboradViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtBookName: TextView = view.findViewById(R.id.txtRecycleRow)
        val txtBookAuthor: TextView = view.findViewById(R.id.textView1)
        val txtBookPrice: TextView = view.findViewById(R.id.textView2)
        val txtBookRating: TextView = view.findViewById(R.id.txtRating)
        val txtBookImage: ImageView = view.findViewById(R.id.imgText)
        val llContext : RelativeLayout = view.findViewById(R.id.llContent)


    }

}