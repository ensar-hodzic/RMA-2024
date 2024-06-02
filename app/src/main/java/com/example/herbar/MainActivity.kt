package com.example.herbar

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object TrefleDAOProvider {
    lateinit var dao: TrefleDAO
}


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, MedicinskiListAdapter.OnItemClickListener,KuharskiListAdapter.OnItemClickListener,BotanickiListAdapter.OnItemClickListener{

    private lateinit var biljkeRV: RecyclerView
    private lateinit var medicinskiListAdapter: MedicinskiListAdapter
    private lateinit var kuharskiListAdapter: KuharskiListAdapter
    private lateinit var botanickiListAdapter: BotanickiListAdapter
    private lateinit var pretragaListAdapter: PretragaListAdapter
    private lateinit var biljkeList: List<Biljka>
    private lateinit var spinner: Spinner
    private lateinit var bojeSpinner: Spinner
    private lateinit var pretragaET: EditText
    private lateinit var brzaPretraga: ImageButton
    private lateinit var bottomBar: ConstraintLayout
    private var options = arrayOf("Medicina","Kuhanje","Botanika")
    private var colors = arrayOf("","red","blue", "yellow", "orange", "purple", "brown", "green")
    private var currentMod = 0
    private lateinit var currentBiljka: Biljka
    private var itemClicked=false
    private lateinit var resetButton: Button
    private lateinit var novaBiljkaButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var defaultBitmap = BitmapFactory.decodeResource(resources, R.drawable.no_image)
        TrefleDAOProvider.dao= TrefleDAO(defaultBitmap)
        bottomBar=findViewById(R.id.bottomBar)

        biljkeRV = findViewById(R.id.biljkeRV)
        biljkeRV.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        medicinskiListAdapter = MedicinskiListAdapter(listOf(),this)
        kuharskiListAdapter = KuharskiListAdapter(listOf(),this)
        botanickiListAdapter = BotanickiListAdapter(listOf(),this)
        pretragaListAdapter = PretragaListAdapter(listOf())

        spinner = findViewById(R.id.modSpinner)
        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(spinner) {
            adapter = aa
            onItemSelectedListener = this@MainActivity
            setSelection(0, false)
        }
        biljkeList= getBiljke()

        bojeSpinner = findViewById(R.id.bojaSPIN)
        var boje = ArrayAdapter(this, android.R.layout.simple_spinner_item, colors)
        boje.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(bojeSpinner) {
            adapter = boje
            setSelection(0, false)
        }

        pretragaET = findViewById(R.id.pretragaET)
        brzaPretraga=findViewById(R.id.brzaPretraga)
        brzaPretraga.setOnClickListener {
            if(pretragaET.text.toString().isNotEmpty() && bojeSpinner.selectedItem!=""){
                Toast.makeText(this, "Pretraga počela", Toast.LENGTH_SHORT).show()
                var list: MutableList<Biljka>
                CoroutineScope(Dispatchers.Main).launch {
                    list= TrefleDAOProvider.dao.getPlantswithFlowerColor(bojeSpinner.selectedItem.toString(),pretragaET.text.toString()).toMutableList()
                    pretragaListAdapter.updateBiljke(list)
                    Toast.makeText(this@MainActivity, list.size.toString()+" rezultata", Toast.LENGTH_SHORT).show()
                    biljkeRV.adapter=pretragaListAdapter
                }
            }
        }

        resetButton=findViewById(R.id.resetBtn)
        resetButton.setOnClickListener {
            itemClicked=false
            biljkeList=getBiljke()
            when(currentMod){
                0->medicinskiListAdapter.updateBiljke(biljkeList)
                1->kuharskiListAdapter.updateBiljke(biljkeList)
                2->{
                    biljkeRV.adapter=botanickiListAdapter
                    botanickiListAdapter.updateBiljke(biljkeList)
                }
            }
        }
        novaBiljkaButton = findViewById(R.id.novaBiljkaBtn)
        novaBiljkaButton.setOnClickListener{
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        biljkeList=getBiljke()
        currentMod=0
        spinner.setSelection(0)
        biljkeRV.adapter = medicinskiListAdapter
        medicinskiListAdapter.updateBiljke(biljkeList)
        bottomBar.visibility=View.GONE
        super.onResume()
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            0 ->{
                currentMod=0
                biljkeRV.adapter = medicinskiListAdapter
                medicinskiListAdapter.updateBiljke(biljkeList)
                bottomBar.visibility=View.GONE
            }
            1 -> {
                currentMod=1
                biljkeRV.adapter = kuharskiListAdapter
                kuharskiListAdapter.updateBiljke(biljkeList)
                bottomBar.visibility=View.GONE
            }
            2 -> {
                currentMod=2
                biljkeRV.adapter=botanickiListAdapter
                botanickiListAdapter.updateBiljke(biljkeList)
                bottomBar.visibility=View.VISIBLE
            }
        }
    }
    override fun onItemClick(position: Int) {
        itemClicked= true
        currentBiljka = biljkeList[position]
        //Toast.makeText(this, biljkeList[position].naziv, Toast.LENGTH_SHORT).show()
        customList(currentBiljka)
        when(currentMod){
            0->medicinskiListAdapter.updateBiljke(biljkeList)
            1->kuharskiListAdapter.updateBiljke(biljkeList)
            2->botanickiListAdapter.updateBiljke(biljkeList)
        }
        //Toast.makeText(this, biljkeList[position].naziv, Toast.LENGTH_SHORT).show()
    }

    fun customList(currBiljka: Biljka){
        when(currentMod){
            0->{
                biljkeList = getBiljke().filter { biljka ->
                    biljka.medicinskeKoristi.any { medKorist ->
                        currBiljka.medicinskeKoristi.any { it.equals(medKorist) }
                    }
                }
            }
            1->{
                biljkeList = getBiljke().filter { biljka ->
                    biljka.jela.any { jelo ->
                        currBiljka.jela.any { it.equals(jelo, ignoreCase = true) }
                    } || biljka.profilOkusa.equals(currBiljka.profilOkusa)
                }
            }
            2->{
                biljkeList = getBiljke().filter { biljka ->
                    biljka.porodica.equals(currBiljka.porodica, ignoreCase = true) &&
                            (biljka.klimatskiTipovi.any { klimatskiTip ->
                                currBiljka.klimatskiTipovi.any { it.equals(klimatskiTip) }
                            } || biljka.zemljisniTipovi.any { zemljiste ->
                                currBiljka.zemljisniTipovi.any { it.equals(zemljiste) }
                            })
                }
            }
        }
    }
}