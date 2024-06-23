package com.example.herbar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception



class MedicinskiListAdapter(private var biljke: List<Biljka>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MedicinskiListAdapter.BiljkaViewHolder>() {

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
        val naziv: TextView = itemView.findViewById(R.id.nazivItem)
        val upozorenje: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val korist3: TextView = itemView.findViewById(R.id.korist3Item)
        val korist2: TextView = itemView.findViewById(R.id.korist2Item)
        val korist1: TextView = itemView.findViewById(R.id.korist1Item)
        val card: CardView = itemView.findViewById(R.id.card)
    }
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
        val nazivBiljke: String = biljke[position].slika
        val context: Context = holder.slika.context
        var db = BiljkaDatabase.getInstance(context)
        CoroutineScope(Dispatchers.Main).launch {
            var slika: BiljkaBitmap?
            CoroutineScope(Dispatchers.IO).launch {
                slika = db.biljkaDao().getBiljkaBitmap(biljke[position].id)
                if (slika != null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        holder.slika.setImageBitmap(slika!!.bitmap)
                    }
                } else {
                    val newSlika = TrefleDAO().getImage(biljke[position])
                    val success = db.biljkaDao().addImage(biljke[position].id, newSlika)
                    CoroutineScope(Dispatchers.Main).launch {
                        holder.slika.setImageBitmap(newSlika)
                    }
                }
            }
        }

    }
    fun updateBiljke(biljka: List<Biljka>) {
        this.biljke = biljka
        notifyDataSetChanged()
    }


}