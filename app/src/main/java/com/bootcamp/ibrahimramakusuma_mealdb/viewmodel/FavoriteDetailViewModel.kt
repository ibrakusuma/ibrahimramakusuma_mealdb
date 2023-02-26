package com.bootcamp.ibrahimramakusuma_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bootcamp.ibrahimramakusuma_mealdb.data.LocalDataSource
import com.bootcamp.ibrahimramakusuma_mealdb.data.RemoteDataSource
import com.bootcamp.ibrahimramakusuma_mealdb.data.Repository
import com.bootcamp.ibrahimramakusuma_mealdb.data.database.MealDatabase
import com.bootcamp.ibrahimramakusuma_mealdb.data.database.MealEntity
import com.bootcamp.ibrahimramakusuma_mealdb.data.network.Service
import kotlinx.coroutines.launch

class FavoriteDetailViewModel(application: Application) : AndroidViewModel(application) {

    // API
    private val mealService = Service.MealService
    private val remote = RemoteDataSource(mealService)

    // LOCAL
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)
    private val repository = Repository(remote, local)

    fun deleteFavoriteMeal(mealEntity: MealEntity) {
        viewModelScope.launch {
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}