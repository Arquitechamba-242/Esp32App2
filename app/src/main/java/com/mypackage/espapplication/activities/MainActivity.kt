package com.mypackage.espapplication.activities

import android.content.Intent
import android.hardware.Sensor
import android.os.Bundle
import android.os.Handler
import android.widget.Button
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

class MainActivity : ComponentActivity() {
    private lateinit var dbRef : DatabaseReference

    private lateinit var textStatus : TextView
    private lateinit var cardViewFields : CardView

    private  lateinit var  textField1 : TextView
    private  lateinit var  textField2 : TextView
    private  lateinit var  textField3 : TextView
    private  lateinit var  textField4 : TextView
    private  lateinit var  textField5 : TextView
    private  lateinit var  textField6 : TextView
    private lateinit var profileButton : Button
    private lateinit var currentProfile: Profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initAll()
        profileButton.setOnClickListener {
            val intentToProfiles = Intent(this,ProfileActivity :: class.java)
            startActivity(intentToProfiles)
        }

        if(isConnected()){
            textStatus.setText(R.string.STATUS_CONNECTING)
            textStatus.visibility = TextView.GONE
            cardViewFields.visibility = CardView.VISIBLE
            setValues()

        }
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
        profileButton = findViewById(R.id.profileButton)
    }

    private fun isConnected() : Boolean{
        return true
    }
    private fun setValues() {
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //nota : debemos cambiar los paths
                val data = snapshot.child("test").getValue(SensorValues::class.java)
                // aqui se setea la data
                //luego hay que comparar respecto al profile elegido
                compareAndActualize(data,currentProfile)
                //y fin
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not implemented")
            }
        })

    }
    private fun compareAndActualize(data: SensorValues?,profile : Profile) {
        //necesito la estructura de datos realizada para hacer esto
    }

}
