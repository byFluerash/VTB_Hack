package com.example.vtb_hack.data


import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface MarketPlaceService {

    @GET("marketplace")
    fun getCars(): Observable<List<Car>>

    @POST("car-recognize")
    fun postCar(@Body content: Content): Single<Car>
}