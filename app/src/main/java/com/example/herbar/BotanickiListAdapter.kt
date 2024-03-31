package com.example.herbar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class BotanickiListAdapter(private var biljke: List<Biljka>, private val listener: BotanickiListAdapter.OnItemClickListener) : RecyclerView.Adapter<BotanickiListAdapter.BiljkaViewHolder>() {

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
        val nazivBiljke: String = biljke[position].slika
        val context: Context = holder.slika.context
        var id: Int = context.resources
            .getIdentifier(nazivBiljke, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier("no_image", "drawable", context.packageName)
        holder.slika.setImageResource(id)
    }
    fun updateBiljke(biljka: List<Biljka>) {
        this.biljke = biljka
        notifyDataSetChanged()
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
        val slika: ImageView = itemView.findViewById(R.id.slikaItem)
        val card: CardView = itemView.findViewById(R.id.card)
        val naziv: TextView = itemView.findViewById(R.id.nazivItem)
        val porodica: TextView = itemView.findViewById(R.id.porodicaItem)
        val zemljiste: TextView = itemView.findViewById(R.id.zemljisniTipItem)
        val klima: TextView = itemView.findViewById(R.id.klimatskiTipItem)

    }
}