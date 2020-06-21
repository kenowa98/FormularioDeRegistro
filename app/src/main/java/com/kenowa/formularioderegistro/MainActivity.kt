package com.kenowa.formularioderegistro

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var fecha: String
    private var cal = Calendar.getInstance()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bandera para saber si ya se guardó una fecha
        var flag = false

        // Establecer mi toolbar como toolbar de la actividad
        setSupportActionBar(findViewById(R.id.toolbar))

        // Almacenar la fecha seleccionada en el calendario
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val format = "MM/dd/yyyy"
                val simpleDateFormat = SimpleDateFormat(format, Locale.US)
                fecha = simpleDateFormat.format(cal.time).toString()
                tv_fechaNacimiento.text = fecha
                flag = true
            }

        // Mostrar un calendario cuando se presione el botón
        ibtn_calendario.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Configurar las características de vista del spinner
        val spinner: Spinner = findViewById(R.id.sp_lugarNacimiento)
        ArrayAdapter.createFromResource(
            this,
            R.array.lista_ciudades,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // Registrar la información una vez se oprima el botón de guardar
        btn_guardar.setOnClickListener {
            val nombre = et_nombre.text.toString()
            val correo = et_correo.text.toString()
            val telefono = et_telefono.text.toString()
            val clave = et_clave.text.toString()
            val claveAgain = et_claveAgain.text.toString()
            val ciudad = sp_lugarNacimiento.selectedItem.toString()
            val genero = if (rbtn_masculino.isChecked) "Masculino" else "Femenino"
            var pasatiempos = ""

            if (ch_bailar.isChecked) pasatiempos = "$pasatiempos Bailar"
            if (ch_cantar.isChecked) pasatiempos = "$pasatiempos Cantar"
            if (ch_cocinar.isChecked) pasatiempos = "$pasatiempos Cocinar"
            if (ch_leer.isChecked) pasatiempos = "$pasatiempos Leer"
            if (ch_series.isChecked) pasatiempos = "$pasatiempos Series"
            if (ch_videojuegos.isChecked) pasatiempos = "$pasatiempos PS/Xbox"

            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || clave.isEmpty() ||
                    claveAgain.isEmpty() || !flag || ciudad.isEmpty()) {
                tv_registro.text = "Registro incompleto! \nHay campos sin llenar, vuelva a intentar."
            } else {
                if (clave == claveAgain) {
                    tv_registro.text = "Nombre: $nombre \nEmail: $correo \n" +
                        "Teléfono: $telefono \nFecha de nacimiento: $fecha \n" +
                        "Lugar de nacimiento: $ciudad \nGénero: $genero \nPasatiempos: \n$pasatiempos"
                } else{
                    tv_registro.text = "Las claves no son iguales, intente de nuevo."
                }
            }
        }

    }

}