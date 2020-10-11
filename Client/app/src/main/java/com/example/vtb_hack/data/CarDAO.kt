package com.example.vtb_hack.data

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CarDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(carDB: CarDB)

    @Delete
    fun delete(carDB: CarDB)

    @Query("delete from cardb")
    fun deleteAll()

    @Query("select * from cardb where id = :id")
    fun getById(id: Long): Single<CarDB>

    @Query("select * from cardb")
    fun getAll(): Flowable<List<CarDB>>
}