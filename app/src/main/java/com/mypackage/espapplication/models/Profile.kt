package com.mypackage.espapplication.models

open class Parameter{
    val maxValue : Double = 0.0
    val minValue : Double = 0.0
}
class Profile (
    val name : String = "defaultProfile",
    val Alcohol : Parameter = Parameter(),
    val Altitud : Parameter = Parameter(),
    val C02 : Parameter = Parameter(),
    val HumedadDeSuelo : Parameter = Parameter(),
    val Presion : Parameter = Parameter(),
    val Temperatura : Parameter = Parameter())
