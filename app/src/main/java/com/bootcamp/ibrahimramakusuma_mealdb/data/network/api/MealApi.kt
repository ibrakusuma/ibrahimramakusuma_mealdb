package com.bootcamp.ibrahimramakusuma_mealdb.data.network.api

import com.bootcamp.ibrahimramakusuma_mealdb.model.MealsItemId
import com.bootcamp.ibrahimramakusuma_mealdb.model.ResponseMeal
import com.bootcamp.ibrahimramakusuma_mealdb.model.ResponseMealId
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("filter.php?c=Seafood")
    suspend fun getMeal() : Response<ResponseMeal>

    @GET("lookup.php")
    suspend fun getMealId(
        @Query("i") i:String
    ): Response<ResponseMealId>
}