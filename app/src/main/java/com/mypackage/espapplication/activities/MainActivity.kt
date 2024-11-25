package com.mypackage.espapplication.activities

import kotlin.math.round
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.androidplot.xy.BoundaryMode
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYPlot
import com.androidplot.xy.XYSeries
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mypackage.espapplication.R
import com.mypackage.espapplication.models.Profile
import com.mypackage.espapplication.models.SensorValues
import java.util.Locale


class MainActivity : ComponentActivity() {
    private lateinit var dbRef : DatabaseReference


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
    private lateinit var profileName : TextView
    private lateinit var currentProfile: Profile
    private lateinit var data: SensorValues
    private lateinit var xyPlot: XYPlot

    private var arrayAlcohol : ArrayList<Number>? = arrayListOf(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)
    private var arrayAltitud : ArrayList<Number>? = arrayListOf(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)
    private var arrayCO2 : ArrayList<Number>? = arrayListOf(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)
    private var arrayHumedadSuelo : ArrayList<Number>? = arrayListOf(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)
    private var arrayPresion : ArrayList<Number>? = arrayListOf(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)
    private var arrayTemperatura : ArrayList<Number>? = arrayListOf(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)

    private lateinit var series1 : XYSeries
    private lateinit var series2: XYSeries
    private lateinit var series3 : XYSeries
    private lateinit var series4 : XYSeries
    private lateinit var series5: XYSeries
    private lateinit var series6 : XYSeries

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            currentProfile = it.data!!.extras!!.get("profile") as Profile
            profileName.text = currentProfile.name
            compareAndActualize(data,currentProfile)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initAll()
        profileButton.setOnClickListener {
            val intentToProfiles = Intent(this, ProfileActivity::class.java)
            launcher.launch(intentToProfiles)
        }
        setValues()

    }


    private fun initAll() {
        dbRef = FirebaseDatabase.getInstance().getReference("data")
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
        profileName = findViewById(R.id.tvProfileName)
        currentProfile = Profile()

        xyPlot = findViewById(R.id.plot)
        //xyPlot.renderMode = Plot.RenderMode.USE_BACKGROUND_THREAD
        series1 = SimpleXYSeries(arrayAlcohol, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1")
        series2 = SimpleXYSeries(arrayAltitud, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1")
        series3 = SimpleXYSeries(arrayCO2,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series3")
        series4 = SimpleXYSeries(arrayHumedadSuelo,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series4")
        series5 = SimpleXYSeries(arrayPresion,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series5")
        series6 = SimpleXYSeries(arrayTemperatura,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series6")

        xyPlot.addSeries(series1, LineAndPointFormatter(this,R.xml.formatter1))
        xyPlot.addSeries(series2, LineAndPointFormatter(this,R.xml.formatter2))
        xyPlot.addSeries(series3, LineAndPointFormatter(this,R.xml.formatter3))
        xyPlot.addSeries(series4, LineAndPointFormatter(this,R.xml.formatter4))
        xyPlot.addSeries(series5, LineAndPointFormatter(this,R.xml.formatter5))
        xyPlot.addSeries(series6, LineAndPointFormatter(this,R.xml.formatter6))

        xyPlot.setRangeBoundaries(-5,BoundaryMode.FIXED,11,BoundaryMode.FIXED)
        xyPlot.setDomainBoundaries(0,10,BoundaryMode.FIXED)
    }

    private fun setValues() {
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                data  = snapshot.getValue(SensorValues::class.java)!!
                compareAndActualize(data,currentProfile)
                appendInPlot(data)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("To be implemented")
            }
        })

    }
    private fun appendInPlot(data: SensorValues) {
        removeAndAppend(arrayAlcohol,round(data.alcohol * 10)/100)
        removeAndAppend(arrayAltitud,round(data.altitud * 10)/100)
        removeAndAppend(arrayCO2,round(data.co2*100)/10000)
        removeAndAppend(arrayHumedadSuelo,round(data.humedaddesuelo*10)/100)
        removeAndAppend(arrayPresion, round(data.presion)/1000)
        removeAndAppend(arrayTemperatura,round(data.temperatura*10)/100)

        xyPlot.removeSeries(series1)
        xyPlot.removeSeries(series2)
        xyPlot.removeSeries(series3)
        xyPlot.removeSeries(series4)
        xyPlot.removeSeries(series5)
        xyPlot.removeSeries(series6)

        series1 = SimpleXYSeries(arrayAlcohol,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Alc")
        series2 = SimpleXYSeries(arrayAltitud,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Alt")
        series3 = SimpleXYSeries(arrayCO2,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"CO2")
        series4 = SimpleXYSeries(arrayHumedadSuelo,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"H.S.")
        series5 = SimpleXYSeries(arrayPresion,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"P.")
        series6 = SimpleXYSeries(arrayTemperatura,SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"T.")

        xyPlot.addSeries(series1,LineAndPointFormatter(this,R.xml.formatter1))
        xyPlot.addSeries(series2,LineAndPointFormatter(this,R.xml.formatter2))
        xyPlot.addSeries(series3,LineAndPointFormatter(this,R.xml.formatter3))
        xyPlot.addSeries(series4,LineAndPointFormatter(this,R.xml.formatter4))
        xyPlot.addSeries(series5,LineAndPointFormatter(this,R.xml.formatter5))
        xyPlot.addSeries(series6,LineAndPointFormatter(this,R.xml.formatter6))
        xyPlot.redraw()
    }

    private fun removeAndAppend(array: ArrayList<Number>?, value: Double) {
        array!!.removeAt(0)
        array.add(value)
    }
    private fun compareAndActualize(data: SensorValues?,profile : Profile) {

        textField1.text = String.format(Locale.ENGLISH,"%f",data!!.alcohol)
        textField2.text = String.format(Locale.ENGLISH,"%f",data.altitud)
        textField3.text = String.format(Locale.ENGLISH,"%f",data.co2)
        textField4.text = String.format(Locale.ENGLISH,"%f",data.humedaddesuelo)
        textField5.text = String.format(Locale.ENGLISH,"%f",data.presion)
        textField6.text = String.format(Locale.ENGLISH,"%f",data.temperatura)
        if(data.alcohol>profile.alcohol.maxValue){
            textField1.setTextColor(getColor(R.color.red))
            textField1Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.alcohol<profile.alcohol.minValue){
            textField1.setTextColor(getColor(R.color.red))
            textField1Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField1.setTextColor(getColor(R.color.teal_200))
            textField1Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }

        if(data.altitud>profile.altitud.maxValue){
            textField2.setTextColor(getColor(R.color.red))
            textField2Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.altitud<profile.altitud.minValue){
            textField2.setTextColor(getColor(R.color.red))
            textField2Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField2.setTextColor(getColor(R.color.teal_200))
            textField2Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }

        if(data.co2>profile.co2.maxValue){
            textField3.setTextColor(getColor(R.color.red))
            textField3Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.co2<profile.co2.minValue){
            textField3.setTextColor(getColor(R.color.red))
            textField3Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField3.setTextColor(getColor(R.color.teal_200))
            textField3Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }

        if(data.humedaddesuelo>profile.humedaddesuelo.maxValue){
            textField4.setTextColor(getColor(R.color.red))
            textField4Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.humedaddesuelo<profile.humedaddesuelo.minValue){
            textField4.setTextColor(getColor(R.color.red))
            textField4Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField4.setTextColor(getColor(R.color.teal_200))
            textField4Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }

        if(data.presion>profile.presion.maxValue){
            textField5.setTextColor(getColor(R.color.red))
            textField5Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.presion<profile.presion.minValue){
            textField5.setTextColor(getColor(R.color.red))
            textField5Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField5.setTextColor(getColor(R.color.teal_200))
            textField5Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }

        if(data.temperatura>profile.temperatura.maxValue){
            textField6.setTextColor(getColor(R.color.red))
            textField6Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_up))
        }else if(data.temperatura<profile.temperatura.minValue){
            textField6.setTextColor(getColor(R.color.red))
            textField6Vec.setImageDrawable(getDrawable(R.drawable.out_of_range_down))
        }else{
            textField6.setTextColor(getColor(R.color.teal_200))
            textField6Vec.setImageDrawable(getDrawable(R.drawable.normal_range))
        }
    }

}
