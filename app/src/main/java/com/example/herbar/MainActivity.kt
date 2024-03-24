package com.example.herbar

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var biljkeRV: RecyclerView
    private lateinit var medicinskiListAdapter: MedicinskiListAdapter
    private lateinit var kuharskiListAdapter: KuharskiListAdapter
    private lateinit var botanickiListAdapter: BotanickiListAdapter
    private var biljkeList =  getBiljke()
    private lateinit var spinner: Spinner
    private var options = arrayOf("Medicina","Kuhanje","Botanika")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biljkeRV = findViewById(R.id.biljkeRV)
        biljkeRV.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        medicinskiListAdapter = MedicinskiListAdapter(listOf())
        kuharskiListAdapter = KuharskiListAdapter(listOf())
        botanickiListAdapter = BotanickiListAdapter(listOf())


        spinner = findViewById(R.id.modSpinner)
        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(spinner) {
            adapter = aa
            onItemSelectedListener = this@MainActivity
            setSelection(0, false)
        }

    }
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            0 ->{
                biljkeRV.adapter = medicinskiListAdapter
                medicinskiListAdapter.updateBiljke(biljkeList)
            }
            1 -> {
                biljkeRV.adapter = kuharskiListAdapter
                kuharskiListAdapter.updateBiljke(biljkeList)
            }
            2 -> {
                biljkeRV.adapter=botanickiListAdapter
                botanickiListAdapter.updateBiljke(biljkeList)
            }
        }
    }

    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()
    }
}