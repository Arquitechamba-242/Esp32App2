package com.mypackage.espapplication.models

class SensorValues (
    val IS_ON : Boolean = false,
    val temperature : Double = 0.0,
    val pressure : Double = 0.0,
    val altitude : Double = 0.0,
    val soil_humidity : Double = 0.0){
    companion object{
        fun toSensorValue(data : String) : SensorValues{

            return SensorValues()
        }
    }
}
