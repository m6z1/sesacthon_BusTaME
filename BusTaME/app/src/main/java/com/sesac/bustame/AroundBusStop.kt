package com.sesac.bustame

data class ItemList(
    val itemList: List<AroundBusStop>
)

data class AroundBusStop(
    val posX: String,
    val stationTp: String,
    val arsId: String,
    val posY: String,
    val stationNm: String,
    val dist: String,
    val gpsX: String,
    val gpsY: String,
    val stationId: String
)
