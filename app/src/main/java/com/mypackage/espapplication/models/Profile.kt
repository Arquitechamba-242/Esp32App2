package com.mypackage.espapplication.models

open class Parameter{
    val maxValue : Int = 0
    val minValue : Int = 0
}
class Profile (
    val name : String = "defaultProfile",
    val temperature : Parameter = Parameter(),
    val pressure : Parameter = Parameter(),
    val altitude : Parameter = Parameter(),
    val soil_humidity : Parameter = Parameter()) : Parameter()
