package com.nttn.pkot.data.model

import com.squareup.moshi.Json

data class MainData(
    @Json(name = "floorId") val floorId: Int,
    @Json(name = "roomId") val roomId: Int,
    @Json(name = "floorName") val floorName: String,
    @Json(name = "roomName") val roomName: String,
    @Json(name = "description") val description: String,
    @Json(name = "navigation") val navigation: String,
    @Json(name = "checked") var checked: Boolean = false,
    @Json(name = "children") val children: ArrayList<MainData>?
)

