package com.example.herbar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class KuharskiListAdapter(private var biljke: List<Biljka>, private val listener: OnItemClickListener) : RecyclerView.Adapter<KuharskiListAdapter.BiljkaViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.kuharski_view, parent, false)
        return BiljkaViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv;
        holder.profilOkusa.text = biljke[position].profilOkusa.opis;
        try {
            holder.korist1.text = biljke[position].jela[0]
        }
        catch (e: Exception){
            holder.korist1.text=""
        }
        try {
            holder.korist2.text = biljke[position].jela[1]
        }
        catch (e: Exception){
            holder.korist2.text=""
        }
        try {
            holder.korist3.text = biljke[position].jela[2]
        }
        catch (e: Exception){
            holder.korist3.text=""
        }
        val nazivBiljke: String = biljke[position].slika
        val context: Context = holder.slika.context
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = TrefleDAO().getImage(biljke[position])
            holder.slika.setImageBitmap(bitmap)
        }

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
        val profilOkusa: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val korist3: TextView = itemView.findViewById(R.id.jelo3Item)
        val korist2: TextView = itemView.findViewById(R.id.jelo2Item)
        val korist1: TextView = itemView.findViewById(R.id.jelo1Item)
    }
}