package com.mypackage.espapplication.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.espapplication.R
import com.mypackage.espapplication.models.Parameter
import com.mypackage.espapplication.models.Profile

import java.util.Locale

class DisplayProfileActivity : ComponentActivity() {
    private lateinit var dbRefToProf : DatabaseReference
    private lateinit var nameField : EditText
    private lateinit var alcoholMaxField : EditText
    private lateinit var alcoholMinField : EditText
    private lateinit var altitudMaxField : EditText
    private lateinit var altitudMinField : EditText
    private lateinit var co2MaxField : EditText
    private lateinit var co2MinField : EditText
    private lateinit var humedadMaxField : EditText
    private lateinit var humedadMinField : EditText
    private lateinit var presionlMaxField : EditText
    private lateinit var presionMinField : EditText
    private lateinit var temperaturaMaxField : EditText
    private lateinit var temperaturaMinField : EditText

    private lateinit var deleteButton: Button
    private lateinit var modifyButton: Button


    private lateinit var profileSelected : Profile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_display_profile)
        profileSelected = intent.extras!!.get("profileSelected") as Profile
        initAll()
        setValues()
        deleteButton.setOnClickListener{
            dbRefToProf.child(profileSelected.id!!).removeValue()
            finish()
        }
        modifyButton.setOnClickListener{
            val profileToModify = Profile(profileSelected.id,nameField.text.toString(),Parameter(alcoholMaxField.text.toString().toDouble(),alcoholMinField.text.toString().toDouble()),
                Parameter(altitudMaxField.text.toString().toDouble(),altitudMinField.text.toString().toDouble()),
                Parameter(co2MaxField.text.toString().toDouble(),co2MinField.text.toString().toDouble()),
                Parameter(humedadMaxField.text.toString().toDouble(),humedadMinField.text.toString().toDouble()),
                Parameter(presionlMaxField.text.toString().toDouble(), presionMinField.text.toString().toDouble()),
                Parameter(temperaturaMaxField.text.toString().toDouble(),temperaturaMinField.text.toString().toDouble()))
            dbRefToProf.child(profileSelected.id!!).setValue(profileToModify)
            finish()
        }
        setListenerForEdit(nameField)
        setListenerForEdit(alcoholMaxField)
        setListenerForEdit(alcoholMinField)
        setListenerForEdit(altitudMaxField)
        setListenerForEdit(altitudMinField)
        setListenerForEdit(co2MaxField)
        setListenerForEdit(co2MinField)
        setListenerForEdit(humedadMaxField)
        setListenerForEdit(humedadMinField)
        setListenerForEdit(presionlMaxField)
        setListenerForEdit(presionMinField)
        setListenerForEdit(temperaturaMaxField)
        setListenerForEdit(temperaturaMinField)
    }
    private fun setListenerForEdit(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                modifyButton.visibility = Button.VISIBLE
            }
            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

    }


    private fun setValues() {
        nameField.setText(profileSelected.name)
        alcoholMaxField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.alcohol.maxValue))
        alcoholMinField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.alcohol.minValue))

        altitudMaxField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.altitud.maxValue))
        altitudMinField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.altitud.minValue))

        co2MaxField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.co2.maxValue))
        co2MinField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.co2.minValue))

        humedadMaxField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.humedaddesuelo.maxValue))
        humedadMinField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.humedaddesuelo.minValue))

        presionlMaxField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.presion.maxValue))
        presionMinField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.presion.minValue))

        temperaturaMaxField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.temperatura.maxValue))
        temperaturaMinField.setText(String.format(Locale.ENGLISH,"%f",profileSelected.temperatura.minValue))
    }

    private fun initAll() {
        dbRefToProf = FirebaseDatabase.getInstance().getReference("Profiles")
        nameField = findViewById(R.id.displayProfileName)
        alcoholMaxField = findViewById(R.id.displayMaxAlcohol)
        alcoholMinField = findViewById(R.id.displayMinAlcohol)
        altitudMaxField = findViewById(R.id.displayMaxAltitud)
        altitudMinField = findViewById(R.id.displayMinAltitud)
        co2MaxField = findViewById(R.id.displayMaxCO2)
        co2MinField = findViewById(R.id.displayMinCO2)
        humedadMaxField = findViewById(R.id.displayMaxHumedad)
        humedadMinField = findViewById(R.id.displayMinHumedad)
        presionlMaxField = findViewById(R.id.displayMaxPresion)
        presionMinField = findViewById(R.id.displayMinPresion)
        temperaturaMaxField = findViewById(R.id.displayMaxTemp)
        temperaturaMinField = findViewById(R.id.displayMinTemp)
        deleteButton = findViewById(R.id.deleteProfileButton)
        modifyButton = findViewById(R.id.modifyProfileButton)
    }
}
