package com.example.vtb_hack.data


import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface MarketPlaceService {

    @GET("marketplace")
    fun getCars(): Observable<List<Car>>

    @GET("credits")
    fun getCredits(): Single<List<Credit>>

    @POST("credits")
    fun postCredit(@Body creditPost: CreditPost): Single<Credit>

    @POST("car-recognize")
    fun postCar(@Body content: Content): Single<Car>

    @POST("calculate")
    fun calculate(@Body calculate: Calculate): Single<CalculatePost>

    @POST("carloan")
    fun postRequest(@Body carloan: Carloan): Single<RequestStatus>
}