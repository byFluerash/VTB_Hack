package com.example.vtb_hack.data


import io.reactivex.Observable
import retrofit2.http.GET

interface MarketPlaceService {

    @GET("marketplace")
    fun getCars(): Observable<List<Car>>
}