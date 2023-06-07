package com.sesac.bustame

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitService {
    @POST("/busStop")
    fun sendBusStopData(@Body jsonData: JsonObject): Call<ResponseBody>

    @POST("/busStopByPos")
    fun sendUserPosData(@Body locationInfo: LocationInfo): Call<ItemList>

    @POST("/busStopInfo")
    fun sendBusStopIdData(@Body busInfoData : JsonObject) : Call<BusArriveInfo>

}