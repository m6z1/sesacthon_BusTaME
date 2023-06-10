package com.sesac.bustame

data class BusArriveInfo(
    val itemList: List<Item>
)

data class Item(
    val busRouteAbrv: String, //버스 번호
    val traTime1: String, //도착예정버스
    val vehId2: String,
    val vehId1: String,
    val traTime2: String,
    val isFullFlag1: String, //도착 만차여부
    val isFullFlag2: String,
    val arrmsgSec1: String,
    val arrmsgSec2: String,
    val arrmsg2: String, //두번째 도착 버스 정보 메시지
    val adirection: String, //방향
    val arrmsg1: String, //첫번째 도착 버스 정보 메시지
    val arsId: String,
    val rtNm: String,
    val busType1: String, //첫번째 도착 예정 버스 차량 유형
    val busType2: String,
    val isLast1: String,
    val sectNm: String,
    val isLast2: String,
    val nextBus: String,
    val term: String,
    val routeType: String,
    val gpsX: String,
    val isArrive1: String,
    val gpsY: String,
    val isArrive2: String,
    val stationNm1: String, //버스 정류장 이름
    val sectOrd2: String,
    val stationTp: String,
    val rerdieDiv1: String,
    val nxtStn: String, //다음 정류장 이름
    val rerdieDiv2: String,
    val sectOrd1: String,
    val stId: String,
    val staOrd: String,
    val firstTm: String,
    val posX: String,
    val repTm1: String,
    val posY: String,
    val traSpd1: String,
    val rerideNum2: String,
    val stNm: String,
    val lastTm: String,
    val rerideNum1: String,
    val traSpd2: String,
    val deTourAt: String,
    val congestion: String,
    val busRouteId: String,
    val stationNm2: String
)
