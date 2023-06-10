package com.sesac.bustame

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @POST("/busStop")
    fun sendBusStopData(@Body jsonData: JsonObject): Call<ResponseBody>

    @POST("/busStopByPos")
    fun sendUserPosData(@Body locationInfo: LocationInfo): Call<ItemList>

    @POST("/busStopInfo")
    fun sendBusStopIdData(@Body busInfoData: JsonObject): Call<BusArriveInfo>

    @POST("/RideBell")
    fun sendUserRideBellData(@Body rideBellData: RideBellData): Call<ResponseBody>

    @DELETE("/RideBell/{Id}")
    fun deleteUserRideBellData(@Path("Id") Id : Long): Call<ResponseBody>

}


