package com.example.uasdpm2011500020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class activity_EntriDataDosen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entri_data_dosen)

        val modeEdit = intent.hasExtra("nidn") && intent.hasExtra("nama") &&
                intent.hasExtra("jabatan") && intent.hasExtra("golpat") &&
                intent.hasExtra("pendidikan") && intent.hasExtra("keahlian") &&
                intent.hasExtra("studi")
        title = if(modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etnidn = findViewById<EditText>(R.id.etnidn)
        val etNmDosen = findViewById<EditText>(R.id.etNmDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolkat = findViewById<Spinner>(R.id.spnGolkat)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val etKeahlian = findViewById<EditText>(R.id.etKeahlian)
        val etprodi = findViewById<EditText>(R.id.etProdi)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val etjabatan = arrayOf("Tenaga Pengajar","Asisten Ahli","Lektor","Lektor Kepala","Guru Besar")
        val pangkat = arrayOf("III/a - Penata Muda","III/b - Penata Muda Tingkat I","III/c - Penata","III/d - Penata Tingkat I",
            "IV/a - Pembina","IV/b - Pembina Tingkat I","IV/c - Pembina Utama Muda","IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama")
        val adpGolpat = ArrayAdapter(
            this@activity_EntriDataDosen,
            android.R.layout.simple_spinner_dropdown_item,
            pangkat
        )
        spnGolkat.adapter = adpGolpat

        val adpJabatan = ArrayAdapter(
            this@activity_EntriDataDosen,
            android.R.layout.simple_spinner_dropdown_item,
            etjabatan
        )
        spnJabatan.adapter = adpJabatan

        if(modeEdit) {
            val Nidn = intent.getStringExtra("nidn")
            val nama = intent.getStringExtra("nama")
            val jabatan = intent.getStringExtra("jabatan")
            val golkat = intent.getStringExtra("golkat")
            val pendidikan= intent.getStringExtra("pendidikan")
            val keahlian = intent.getStringExtra("keahlian")
            val prodi = intent.getStringExtra("prodi")

            etnidn.setText(Nidn)
            etNmDosen.setText(nama)
            spnJabatan.setSelection(etjabatan.indexOf(jabatan))
            spnGolkat.setSelection(pangkat.indexOf(golkat))
            if(pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etKeahlian.setText(keahlian)
            etprodi.setText(prodi)
        }
        etnidn.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if("${etnidn.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty()
                && "${etKeahlian.text}".isNotEmpty() && "${etprodi.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)) {
                val db = DbHelper(this@activity_EntriDataDosen)
                db.nidn = "${etnidn.text}"
                db.nmDosen = "${etNmDosen.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.golkat = spnGolkat.selectedItem as String
                db.pendhir = if(rdS2.isChecked) "S2" else "S3"
                db.keahlian = "${etKeahlian.text}"
                db.prodi = "${etprodi.text}"
                if(if(!modeEdit) db.simpan() else db.ubah("${etnidn.text}")) {
                    Toast.makeText(
                        this@activity_EntriDataDosen,
                        "Data Dosen pengampu berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@activity_EntriDataDosen,
                        "Data Dosen Pengampu kuliah gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@activity_EntriDataDosen,
                    "Data Dosen Pengampu belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}