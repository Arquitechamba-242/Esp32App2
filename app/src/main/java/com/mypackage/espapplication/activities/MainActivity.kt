package com.mypackage.espapplication.activities

import android.content.Intent
import android.hardware.Sensor
import android.os.Bundle
import android.os.Handler
import android.renderscript.Sampler.Value
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.cardview.widget.CardView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mypackage.espapplication.R
import com.mypackage.espapplication.models.Profile
import com.mypackage.espapplication.models.SensorValues
import org.w3c.dom.Text
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var dbRef : DatabaseReference

    private lateinit var textStatus : TextView
    private lateinit var cardViewFields : CardView

    private  lateinit var  textField1 : TextView
    private lateinit var textField1Vec : ImageView
    private  lateinit var  textField2 : TextView
    private lateinit var textField2Vec : ImageView
    private  lateinit var  textField3 : TextView
    private lateinit var textField3Vec : ImageView
    private  lateinit var  textField4 : TextView
    private lateinit var textField4Vec : ImageView
    private  lateinit var  textField5 : TextView
    private lateinit var textField5Vec : ImageView
    private  lateinit var  textField6 : TextView
    private lateinit var textField6Vec : ImageView
    private lateinit var profileButton : Button
    private lateinit var currentProfile: Profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initAll()
        profileButton.setOnClickListener {
            val intentToProfiles = Intent(this, ProfileActivity::class.java)
            startActivity(intentToProfiles)
        }

        setValues()
    }


    private fun initAll() {
        dbRef = FirebaseDatabase.getInstance().getReference("Data")
        textStatus = findViewById(R.id.statusText)
        cardViewFields = findViewById(R.id.cardViewFields)
        textField1 = findViewById(R.id.tvField1)
        textField2 = findViewById(R.id.tvField2)
        textField3 = findViewById(R.id.tvField3)
        textField4 = findViewById(R.id.tvField4)
        textField5 = findViewById(R.id.tvField5)
        textField6 = findViewById(R.id.tvField6)
        textField1Vec = findViewById(R.id.tvField1Vec)
        textField2Vec = findViewById(R.id.tvField2Vec)
        textField3Vec = findViewById(R.id.tvField3Vec)
        textField4Vec = findViewById(R.id.tvField4Vec)
        textField5Vec = findViewById(R.id.tvField5Vec)
        textField6Vec = findViewById(R.id.tvField6Vec)
        profileButton = findViewById(R.id.profileButton)
        currentProfile = Profile()
    }

    private fun setValues() {
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data  = snapshot.child("test").getValue(SensorValues::class.java)
                if(data!!.Estado == 1){
                    textStatus.setText(R.string.STATUS_CONNECTING)
                    textStatus.visibility = TextView.GONE
                    cardViewFields.visibility = CardView.VISIBLE
                }
                compareAndActualize(data,currentProfile)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("To be implemented")
            }
        })

    }
    private fun compareAndActualize(data: SensorValues?,profile : Profile) {
        textField1.text = String.format(Locale.ENGLISH,"%f",data!!.Alcohol)
        textField2.text = String.format(Locale.ENGLISH,"%f",data.Altitud)
        textField3.text = String.format(Locale.ENGLISH,"%f",data.CO2)
        textField4.text = String.format(Locale.ENGLISH,"%f",data.HumedadDeSuelo)
        textField5.text = String.format(Locale.ENGLISH,"%f",data.Presion)
        textField6.text = String.format(Locale.ENGLISH,"%f",data.Temperatura)
        if(data!!.Alcohol>profile.Alcohol.maxValue){
            textField1.setTextColor(getColor(R.color.red))
            textField1Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.Alcohol<profile.Alcohol.minValue){
            textField1.setTextColor(getColor(R.color.red))
            textField1Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField1.setTextColor(getColor(R.color.teal_200))
            textField1Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }

        if(data.Altitud>profile.Altitud.maxValue){
            textField2.setTextColor(getColor(R.color.red))
            textField2Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.Altitud<profile.Altitud.minValue){
            textField2.setTextColor(getColor(R.color.red))
            textField2Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField2.setTextColor(getColor(R.color.teal_200))
            textField2Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }

        if(data.CO2>profile.C02.maxValue){
            textField3.setTextColor(getColor(R.color.red))
            textField3Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.CO2<profile.C02.minValue){
            textField3.setTextColor(getColor(R.color.red))
            textField3Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField3.setTextColor(getColor(R.color.teal_200))
            textField3Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }

        if(data.HumedadDeSuelo>profile.HumedadDeSuelo.maxValue){
            textField4.setTextColor(getColor(R.color.red))
            textField4Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.HumedadDeSuelo<profile.HumedadDeSuelo.minValue){
            textField4.setTextColor(getColor(R.color.red))
            textField4Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField4.setTextColor(getColor(R.color.teal_200))
            textField4Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }

        if(data.Presion>profile.Presion.maxValue){
            textField5.setTextColor(getColor(R.color.red))
            textField5Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.Presion<profile.Presion.minValue){
            textField5.setTextColor(getColor(R.color.red))
            textField5Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField5.setTextColor(getColor(R.color.teal_200))
            textField5Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }

        if(data.Temperatura>profile.Temperatura.maxValue){
            textField6.setTextColor(getColor(R.color.red))
            textField6Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.Temperatura<profile.Temperatura.minValue){
            textField6.setTextColor(getColor(R.color.red))
            textField6Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField6.setTextColor(getColor(R.color.teal_200))
            textField6Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }
    }

}
