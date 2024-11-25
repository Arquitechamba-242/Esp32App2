package com.mypackage.espapplication.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parameter(
    var maxValue : Double = 0.0,
    var minValue : Double = 0.0
) : Parcelable

@Parcelize
data class Profile(
    var id : String? = null,
    var name : String = "defaultProfile",
    var alcohol : Parameter = Parameter(),
    var altitud : Parameter = Parameter(),
    var co2 : Parameter = Parameter(),
    var humedaddesuelo : Parameter = Parameter(),
    var presion : Parameter = Parameter(),
    var temperatura : Parameter = Parameter()) : Parcelable
