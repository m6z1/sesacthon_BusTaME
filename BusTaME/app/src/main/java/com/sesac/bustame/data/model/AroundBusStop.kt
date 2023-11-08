package com.sesac.bustame.data.model

data class ItemList(
    val itemList: List<AroundBusStop>
)

data class AroundBusStop(
    val posX: String,
    val stationTp: String,
    val arsId: String, //정류소 고유번호
    val posY: String,
    val stationNm: String, //정류소 이름
    val dist: String,
    val gpsX: String,
    val gpsY: String,
    val stationId: String
)
