package com.example.herbar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MedicinskiListAdapter(private var biljke: List<Biljka>) : RecyclerView.Adapter<MedicinskiListAdapter.BiljkaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.medicinski_view, parent, false)
        return BiljkaViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv;
        holder.upozorenje.text = biljke[position].medicinskoUpozorenje;
        try {
            holder.korist1.text = biljke[position].medicinskeKoristi[0].opis.toString()
        }
        catch (e: Exception){
            holder.korist1.text=""
        }
        try {
            holder.korist2.text = biljke[position].medicinskeKoristi[1].opis.toString()
        }
        catch (e: Exception){
            holder.korist2.text=""
        }
        try {
            holder.korist3.text = biljke[position].medicinskeKoristi[2].opis.toString()
        }
        catch (e: Exception){
            holder.korist3.text=""
        }

    }
    fun updateBiljke(biljka: List<Biljka>) {
        this.biljke = biljka
        notifyDataSetChanged()
    }
    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slika: ImageView = itemView.findViewById(R.id.slikaItem)
        val naziv: TextView = itemView.findViewById(R.id.nazivItem)
        val upozorenje: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val korist3: TextView = itemView.findViewById(R.id.korist3Item)
        val korist2: TextView = itemView.findViewById(R.id.korist2Item)
        val korist1: TextView = itemView.findViewById(R.id.korist1Item)
    }
}