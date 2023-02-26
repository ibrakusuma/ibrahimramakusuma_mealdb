package com.bootcamp.ibrahimramakusuma_mealdb.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bootcamp.ibrahimramakusuma_mealdb.model.MealsItemId
import com.bootcamp.ibrahimramakusuma_mealdb.model.ResponseMealId
import com.bootcamp.ibrahimramakusuma_mealdb.utils.Constant.MEAL_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = MEAL_TABLE_NAME)
@Parcelize
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val meal: MealsItemId
) : Parcelable
