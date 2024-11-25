package com.mypackage.espapplication.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.mypackage.espapplication.R
import com.mypackage.espapplication.models.Profile
import java.util.Locale

class DisplayProfileActivity : ComponentActivity() {
    private lateinit var nameField : TextView
    private lateinit var alcoholMaxField : TextView
    private lateinit var alcoholMinField : TextView
    private lateinit var altitudMaxField : TextView
    private lateinit var altitudMinField : TextView
    private lateinit var CO2MaxField : TextView
    private lateinit var CO2MinField : TextView
    private lateinit var humedadMaxField : TextView
    private lateinit var humedadMinField : TextView
    private lateinit var presionlMaxField : TextView
    private lateinit var presionMinField : TextView
    private lateinit var temperaturaMaxField : TextView
    private lateinit var temperaturaMinField : TextView
    private lateinit var profileSelected : Profile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_display_profile)
        profileSelected = intent.extras!!.get("profileSelected") as Profile
        initAll()
        setValues()
    }
    private fun setValues() {
        nameField.text = profileSelected.name
        alcoholMaxField.text = String.format(Locale.ENGLISH,"%f",profileSelected.alcohol.maxValue)
        alcoholMinField.text = String.format(Locale.ENGLISH,"%f",profileSelected.alcohol.minValue)

        altitudMaxField.text = String.format(Locale.ENGLISH,"%f",profileSelected.altitud.maxValue)
        altitudMinField.text = String.format(Locale.ENGLISH,"%f",profileSelected.altitud.minValue)

        CO2MaxField.text = String.format(Locale.ENGLISH,"%f",profileSelected.co2.maxValue)
        CO2MinField.text = String.format(Locale.ENGLISH,"%f",profileSelected.co2.minValue)

        humedadMaxField.text = String.format(Locale.ENGLISH,"%f",profileSelected.humedaddesuelo.maxValue)
        humedadMinField.text = String.format(Locale.ENGLISH,"%f",profileSelected.humedaddesuelo.minValue)

        presionlMaxField.text = String.format(Locale.ENGLISH,"%f",profileSelected.presion.maxValue)
        presionMinField.text = String.format(Locale.ENGLISH,"%f",profileSelected.presion.minValue)

        temperaturaMaxField.text = String.format(Locale.ENGLISH,"%f",profileSelected.temperatura.maxValue)
        temperaturaMinField.text = String.format(Locale.ENGLISH,"%f",profileSelected.temperatura.minValue)
    }

    private fun initAll() {
        nameField = findViewById(R.id.displayProfileName)
        alcoholMaxField = findViewById(R.id.displayMaxAlcohol)
        alcoholMinField = findViewById(R.id.displayMinAlcohol)
        altitudMaxField = findViewById(R.id.displayMaxAltitud)
        altitudMinField = findViewById(R.id.displayMinAltitud)
        CO2MaxField = findViewById(R.id.displayMaxCO2)
        CO2MinField = findViewById(R.id.displayMinCO2)
        humedadMaxField = findViewById(R.id.displayMaxHumedad)
        humedadMinField = findViewById(R.id.displayMinHumedad)
        presionlMaxField = findViewById(R.id.displayMaxPresion)
        presionMinField = findViewById(R.id.displayMinPresion)
        temperaturaMaxField = findViewById(R.id.displayMaxTemp)
        temperaturaMinField = findViewById(R.id.displayMinTemp)
    }
}
