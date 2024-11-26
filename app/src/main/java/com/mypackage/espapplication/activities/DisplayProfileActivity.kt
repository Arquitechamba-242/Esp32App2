package com.mypackage.espapplication.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.espapplication.R
import com.mypackage.espapplication.models.Parameter
import com.mypackage.espapplication.models.Profile
import org.w3c.dom.Text
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

    private lateinit var textWatcher1 : TextWatcher
    private lateinit var textWatcher2 : TextWatcher
    private lateinit var textWatcher3 : TextWatcher
    private lateinit var textWatcher4 : TextWatcher
    private lateinit var textWatcher5 : TextWatcher
    private lateinit var textWatcher6 : TextWatcher
    private lateinit var textWatcher7 : TextWatcher
    private lateinit var textWatcher8 : TextWatcher
    private lateinit var textWatcher9 : TextWatcher
    private lateinit var textWatcher10 : TextWatcher
    private lateinit var textWatcher11 : TextWatcher
    private lateinit var textWatcher12 : TextWatcher
    private lateinit var textWatcher13 : TextWatcher

    private lateinit var profileSelected : Profile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_display_profile)
        profileSelected = intent.extras!!.get("profileSelected") as Profile
        initAll()
        setValues()
        deleteButton.setOnClickListener({
            dbRefToProf.child(profileSelected.id!!).removeValue()
            finish()
        })
        modifyButton.setOnClickListener({
            dbRefToProf.child(profileSelected.id!!).setValue(Profile(profileSelected.id,nameField.text.toString(),Parameter(alcoholMaxField.toString().toDouble(),alcoholMinField.toString().toDouble()),
                Parameter(altitudMaxField.toString().toDouble(),altitudMinField.toString().toDouble()),
                Parameter(co2MaxField.toString().toDouble(),co2MinField.toString().toDouble()),
                Parameter(humedadMaxField.toString().toDouble(),humedadMinField.toString().toDouble()),
                Parameter(presionlMaxField.toString().toDouble(), presionMinField.toString().toDouble()),
                Parameter(temperaturaMaxField.toString().toDouble(),temperaturaMinField.toString().toDouble())))
            finish()
        })/*
        textWatcher1 = setListenerForEdit(nameField)
        textWatcher2 = setListenerForEdit(alcoholMaxField)
        textWatcher3 = setListenerForEdit(alcoholMinField)
        textWatcher4 = setListenerForEdit(altitudMaxField)
        textWatcher5 = setListenerForEdit(altitudMinField)
        textWatcher6 = setListenerForEdit(co2MaxField)
        textWatcher7 = setListenerForEdit(co2MinField)
        textWatcher8 = setListenerForEdit(humedadMaxField)
        textWatcher9 = setListenerForEdit(humedadMinField)
        textWatcher10 = setListenerForEdit(presionlMaxField)
        textWatcher11 = setListenerForEdit(presionMinField)
        textWatcher12 = setListenerForEdit(temperaturaMaxField)
        textWatcher13 = setListenerForEdit(temperaturaMinField)*/
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
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //modifyButton.visibility = Button.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
                //deleteAllListeners()
            }
        })

    }

    private fun deleteAllListeners() {
        nameField.removeTextChangedListener(textWatcher1)
        alcoholMaxField.removeTextChangedListener(textWatcher2)
        alcoholMinField.removeTextChangedListener(textWatcher3)
        altitudMaxField.removeTextChangedListener(textWatcher4)
        altitudMinField.removeTextChangedListener(textWatcher5)
        co2MaxField.removeTextChangedListener(textWatcher6)
        co2MinField.removeTextChangedListener(textWatcher7)
        humedadMaxField.removeTextChangedListener(textWatcher8)
        humedadMinField.removeTextChangedListener(textWatcher9)
        presionlMaxField.removeTextChangedListener(textWatcher10)
        presionMinField.removeTextChangedListener(textWatcher11)
        temperaturaMaxField.removeTextChangedListener(textWatcher12)
        temperaturaMinField.removeTextChangedListener(textWatcher13)
    }

    private fun setValues() {
        nameField.hint = profileSelected.name
        alcoholMaxField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.alcohol.maxValue)
        alcoholMinField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.alcohol.minValue)

        altitudMaxField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.altitud.maxValue)
        altitudMinField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.altitud.minValue)

        co2MaxField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.co2.maxValue)
        co2MinField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.co2.minValue)

        humedadMaxField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.humedaddesuelo.maxValue)
        humedadMinField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.humedaddesuelo.minValue)

        presionlMaxField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.presion.maxValue)
        presionMinField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.presion.minValue)

        temperaturaMaxField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.temperatura.maxValue)
        temperaturaMinField.hint = String.format(Locale.ENGLISH,"%f",profileSelected.temperatura.minValue)
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
