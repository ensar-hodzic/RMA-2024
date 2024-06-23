package com.example.herbar

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NovaBiljkaActivity() : AppCompatActivity() {
    lateinit var medicinskaKoristLV: ListView
    lateinit var zemljisniTipLV: ListView
    lateinit var klimatskiTipLV: ListView
    lateinit var profilOkusaLV: ListView
    lateinit var jelaLV: ListView

    lateinit var jelaBtn: Button
    lateinit var dodajBtn: Button
    lateinit var uslikaj: Button

    lateinit var slika: ImageView
    var imaSlika=false

    lateinit var jeloET: EditText
    lateinit var nazivET: EditText
    lateinit var porodicaET: EditText
    lateinit var upozorenjeET: EditText

    private val selectedMedicinskeKoristi: MutableList<MedicinskaKorist> = mutableListOf()
    private val selectedZemljisniTip: MutableList<Zemljiste> = mutableListOf()
    private val selectedKlimatskiTip: MutableList<KlimatskiTip> = mutableListOf()
    private val selectedProfilOkusaBiljke: MutableList<ProfilOkusaBiljke> = mutableListOf()
    private val jelaList: MutableList<String> = mutableListOf()

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nova_biljka)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        medicinskaKoristLV = findViewById(R.id.medicinskaKoristLV)
        val medicinskaKoristAdapter = EnumAdapter(
            this,
            R.layout.item_checkbox,
            MedicinskaKorist.values().toList(),
            selectedMedicinskeKoristi
        ) {
            getMedicinskaKoristDescription(it)
        }
        medicinskaKoristLV.adapter = medicinskaKoristAdapter

        klimatskiTipLV = findViewById(R.id.klimatskiTipLV)
        val klimatskiTipAdapter = EnumAdapter(
            this,
            R.layout.item_checkbox,
            KlimatskiTip.values().toList(),
            selectedKlimatskiTip
        ) {
            getKlimatskiTipDescription(it)
        }
        klimatskiTipLV.adapter = klimatskiTipAdapter

        zemljisniTipLV = findViewById(R.id.zemljisniTipLV)
        val zemljisniTipAdapter = EnumAdapter(
            this,
            R.layout.item_checkbox,
            Zemljiste.values().toList(),
            selectedZemljisniTip
        ) {
            getZemljisniTipDescription(it)
        }
        zemljisniTipLV.adapter = zemljisniTipAdapter

        profilOkusaLV = findViewById(R.id.profilOkusaLV)
        val profilOkusaAdapter = EnumAdapter(
            this,
            R.layout.item_checkbox,
            ProfilOkusaBiljke.values().toList(),
            selectedProfilOkusaBiljke
        ) {
            getProfilOkusaDescription(it)
        }
        profilOkusaAdapter.setAllowMultipleSelection(false)
        profilOkusaLV.adapter = profilOkusaAdapter

        jeloET = findViewById(R.id.jeloET)
        jelaBtn = findViewById(R.id.dodajJeloBtn)
        jelaLV = findViewById(R.id.jelaLV)

        var toChange = false
        var selectedMeal = ""
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jelaList)
        jelaLV.adapter = adapter
        jelaLV.setOnItemClickListener { parent, view, position, id ->
            selectedMeal=jelaList[position]
            jelaBtn.setText("Izmijeni jelo")
            jeloET.setText(selectedMeal)
            toChange=true
        }

        jelaBtn.setOnClickListener {
            if(toChange){
                if(jeloET.text.isNotEmpty()) {
                    if(jeloET.text.toString().length<3 || jeloET.text.toString().length>20){
                        jeloET.setError("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")
                    }
                    else if (jelaList.any{it.equals(jeloET.text.toString(),ignoreCase = true)}){
                        jeloET.setError("Ne mogu se dodati dva ista jela")
                    }
                    else{
                        jelaBtn.setText("Dodaj jelo")
                        jelaList.remove(selectedMeal)
                        toChange=false
                        jelaList.add(jeloET.text.toString())
                    }
                }
                else{
                    jelaBtn.setText("Dodaj jelo")
                    jelaList.remove(selectedMeal)
                    toChange=false
                }

            }
            else if(jeloET.text.isNotEmpty()){
                if(jeloET.text.toString().length<3 || jeloET.text.toString().length>20){
                    jeloET.setError("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")
                }
                else if (jelaList.any{it.equals(jeloET.text.toString(),ignoreCase = true)}){
                    jeloET.setError("Ne mogu se dodati dva ista jela")
                }
                else{
                    jelaList.add(jeloET.text.toString())
                }
            }
            adapter.notifyDataSetChanged()
        }

        uslikaj = findViewById(R.id.uslikajBiljkuBtn)
        slika = findViewById(R.id.slikaIV)
        uslikaj.setOnClickListener{
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),100)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, pic_id)
            }

        }

        nazivET=findViewById(R.id.nazivET)
        porodicaET=findViewById(R.id.porodicaET)
        upozorenjeET=findViewById(R.id.medicinskoUpozorenjeET)

        dodajBtn = findViewById(R.id.dodajBiljkuBtn)
        dodajBtn.setOnClickListener {
            if(validation()) {
                var trenBiljka=Biljka(
                    nazivET.text.toString(),
                    porodicaET.text.toString(),
                    upozorenjeET.text.toString(),
                    selectedMedicinskeKoristi,
                    selectedProfilOkusaBiljke[0],
                    jelaList,
                    selectedKlimatskiTip,
                    selectedZemljisniTip,
                    ""
                )
                /*CoroutineScope(Dispatchers.IO).launch {
                    trenBiljka= TrefleDAO().fixData(trenBiljka)
                    awaitAll()
                }
                */var db = BiljkaDatabase.getInstance(this)
                /*lifecycleScope.launch(Dispatchers.IO) {
                    db.biljkaDAO().saveBiljka(trenBiljka)
                    runOnUiThread {
                        finish()
                    }
                }*/
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        trenBiljka = withContext(Dispatchers.IO) {
                            TrefleDAO().fixData(trenBiljka)
                        }
                        db.biljkaDao().saveBiljka(trenBiljka)
                        withContext(Dispatchers.Main) {
                            finish()
                        }
                    } catch (e: Exception) {
                        Log.d("Belaj", "ja belaja")

                    }
                }


            }
        }

    }

    private fun getMedicinskaKoristDescription(medicinskaKorist: MedicinskaKorist): String {
        return medicinskaKorist.opis
    }

    private fun getKlimatskiTipDescription(klimatskiTip: KlimatskiTip): String {
        return klimatskiTip.opis
    }

    private fun getZemljisniTipDescription(zemljisniTip: Zemljiste): String {
        return zemljisniTip.naziv
    }

    private fun getProfilOkusaDescription(profil: ProfilOkusaBiljke): String {
        return profil.opis
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pic_id) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            imageBitmap?.let {
                slika.setImageBitmap(it)
                imaSlika=true
                //Toast.makeText(this@NovaBiljkaActivity,draw.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validation():Boolean {
        var dobar=true
        if (nazivET.text.toString().length < 3 || nazivET.text.toString().length > 40) {
            nazivET.setError("Dužina teksta mora biti veća od 2 karaktera i kraća od 40 karaktera")
            dobar=false
        }
        if(extractStringInsideParentheses(nazivET.text.toString())==null){
            nazivET.setError("Unesite latinski naziv u zagradi")
            dobar=false
        }
        if (porodicaET.text.toString().length < 3 || porodicaET.text.toString().length > 20) {
            porodicaET.setError("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")
            dobar=false
        }
        if (upozorenjeET.text.toString().length < 3 || upozorenjeET.text.toString().length > 20) {
            upozorenjeET.setError("Dužina teksta mora biti veća od 2 karaktera i kraća od 20 karaktera")
            dobar=false
        }
        if (selectedMedicinskeKoristi.size < 1) {
            if(dobar) {
                Toast.makeText(
                    this@NovaBiljkaActivity,
                    "Izaberite barem jednu medicinsku korist",
                    Toast.LENGTH_SHORT
                ).show()
                dobar = false
            }
        }
        if (selectedKlimatskiTip.size < 1) {
            if(dobar) {
                Toast.makeText(
                    this@NovaBiljkaActivity,
                    "Izaberite barem jedan klimatski tip",
                    Toast.LENGTH_SHORT
                ).show()
                dobar = false
            }
        }
        if (selectedZemljisniTip.size < 1) {
            if(dobar) {
                Toast.makeText(
                    this@NovaBiljkaActivity,
                    "Izaberite barem jedan zemljišni tip",
                    Toast.LENGTH_SHORT
                ).show()
                dobar = false
            }
        }
        if (selectedProfilOkusaBiljke.size < 1) {
            if(dobar) {
                Toast.makeText(
                    this@NovaBiljkaActivity,
                    "Izaberite profil okusa",
                    Toast.LENGTH_SHORT
                )
                    .show()
                dobar = false
            }
        }
        if (jelaList.size < 1) {
            if(dobar) {
                Toast.makeText(
                    this@NovaBiljkaActivity,
                    "Dodajte barem jedno jelo",
                    Toast.LENGTH_SHORT
                )
                    .show()
                dobar = false
            }
        }
        if (!imaSlika) {
            if(dobar) {
                Toast.makeText(this@NovaBiljkaActivity, "Dodajte sliku", Toast.LENGTH_SHORT).show()
                dobar = false
            }
        }
        return dobar
    }

    companion object {
        private const val pic_id = 123
    }
}