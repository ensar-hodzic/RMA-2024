package com.example.herbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class BotanickiListAdapter(private var biljke: List<Biljka>) : RecyclerView.Adapter<BotanickiListAdapter.BiljkaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.botanicki_view, parent, false)
        return BiljkaViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv;
        holder.porodica.text = biljke[position].porodica;
        try {
            holder.zemljiste.text = biljke[position].zemljisniTipovi[0].naziv
        }
        catch (e: Exception){
            holder.zemljiste.text=""
        }
        try {
            holder.klima.text = biljke[position].klimatskiTipovi[0].opis
        }
        catch (e: Exception){
            holder.klima.text=""
        }
    }
    fun updateBiljke(biljka: List<Biljka>) {
        this.biljke = biljka
        notifyDataSetChanged()
    }
    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slika: ImageView = itemView.findViewById(R.id.slikaItem)
        val naziv: TextView = itemView.findViewById(R.id.nazivItem)
        val porodica: TextView = itemView.findViewById(R.id.porodicaItem)
        val zemljiste: TextView = itemView.findViewById(R.id.zemljisniTipItem)
        val klima: TextView = itemView.findViewById(R.id.klimatskiTipItem)

    }
}