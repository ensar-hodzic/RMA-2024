package com.example.herbar

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, MedicinskiListAdapter.OnItemClickListener,KuharskiListAdapter.OnItemClickListener,BotanickiListAdapter.OnItemClickListener{

    private lateinit var biljkeRV: RecyclerView
    private lateinit var medicinskiListAdapter: MedicinskiListAdapter
    private lateinit var kuharskiListAdapter: KuharskiListAdapter
    private lateinit var botanickiListAdapter: BotanickiListAdapter
    private lateinit var biljkeList: List<Biljka>
    private lateinit var spinner: Spinner
    private var options = arrayOf("Medicina","Kuhanje","Botanika")
    private var currentMod = 0
    private lateinit var currentBiljka: Biljka
    private var itemClicked=false
    private lateinit var resetButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biljkeRV = findViewById(R.id.biljkeRV)
        biljkeRV.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        medicinskiListAdapter = MedicinskiListAdapter(listOf(),this)
        kuharskiListAdapter = KuharskiListAdapter(listOf(),this)
        botanickiListAdapter = BotanickiListAdapter(listOf(),this)

        spinner = findViewById(R.id.modSpinner)
        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(spinner) {
            adapter = aa
            onItemSelectedListener = this@MainActivity
            setSelection(0, false)
        }
        biljkeList= getBiljke()

        resetButton=findViewById(R.id.resetBtn)
        resetButton.setOnClickListener {
            itemClicked=false
            biljkeList=getBiljke()
            when(currentMod){
                0->medicinskiListAdapter.updateBiljke(biljkeList)
                1->kuharskiListAdapter.updateBiljke(biljkeList)
                2->botanickiListAdapter.updateBiljke(biljkeList)
            }
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            0 ->{
                currentMod=0
                biljkeRV.adapter = medicinskiListAdapter
                if(itemClicked){
                    customList(currentBiljka)
                    medicinskiListAdapter.updateBiljke(biljkeList)
                }
                else medicinskiListAdapter.updateBiljke(getBiljke())
            }
            1 -> {
                currentMod=1
                biljkeRV.adapter = kuharskiListAdapter
                if(itemClicked){
                    customList(currentBiljka)
                    kuharskiListAdapter.updateBiljke(biljkeList)
                }
                else kuharskiListAdapter.updateBiljke(getBiljke())
            }
            2 -> {
                currentMod=2
                biljkeRV.adapter=botanickiListAdapter
                if(itemClicked){
                    customList(currentBiljka)
                    botanickiListAdapter.updateBiljke(biljkeList)
                }
                else botanickiListAdapter.updateBiljke(getBiljke())            }
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