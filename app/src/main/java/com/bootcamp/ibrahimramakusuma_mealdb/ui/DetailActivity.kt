package com.bootcamp.ibrahimramakusuma_mealdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bootcamp.ibrahimramakusuma_mealdb.R
import com.bootcamp.ibrahimramakusuma_mealdb.data.database.MealEntity
import com.bootcamp.ibrahimramakusuma_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ibrahimramakusuma_mealdb.databinding.ActivityDetailBinding
import com.bootcamp.ibrahimramakusuma_mealdb.model.MealsItem
import com.bootcamp.ibrahimramakusuma_mealdb.model.MealsItemId
import com.bootcamp.ibrahimramakusuma_mealdb.model.ResponseMealId
import com.bootcamp.ibrahimramakusuma_mealdb.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var mealDetail: MealsItemId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Detail Meal"
            setDisplayHomeAsUpEnabled(true)

        }

        val meal = intent.getParcelableExtra<MealsItem>(EXTRA_MEAL)

        detailViewModel.fetchMealDetail(meal?.idMeal!!)

        detailViewModel.mealDetail.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    handleUi(
                        layoutWrapper = false,
                        progressBar = true,
                        errorTv = false,
                        errorImg = false
                    )
                }

                is NetworkResult.Error -> {
                    handleUi(
                        layoutWrapper = false,
                        progressBar = false,
                        errorTv = true,
                        errorImg = true
                    )
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Success -> {
                    val selectedMeal = result.data?.meals!![0]
                    binding.mealDetail = selectedMeal
                    val selectedMeal2 = result.data.meals[0]
                    mealDetail = selectedMeal2!!
                    handleUi(
                        layoutWrapper = true,
                        progressBar = false,
                        errorTv = false,
                        errorImg = false
                    )
                }
            }
        }
        isFavoriteMeal(meal)
    }

    private fun isFavoriteMeal(mealSelected: MealsItem) {
        detailViewModel.favoriteMealList.observe(this) { result ->
            val meal = result.find { favorite ->
                favorite.meal.idMeal == mealSelected.idMeal
            }
            if (meal != null) {
                binding.addFavoriteBtn.apply {
                    setText(R.string.remove_from_favorite)
                    setBackgroundColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.red_star
                        )
                    )
                    setOnClickListener {
                        deleteFavoriteMeal(meal.id)
                    }
                }
            } else {
                binding.addFavoriteBtn.apply {
                    setText(R.string.add_to_favorite)
                    setBackgroundColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.orange
                        )
                    )
                    setOnClickListener {
                        insertFavoriteMeal()
                    }
                }
            }
        }
    }

    private fun deleteFavoriteMeal(mealEntityId: Int) {
        val mealEntity = MealEntity(mealEntityId,mealDetail)
        detailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite", Toast.LENGTH_SHORT).show()
    }

    private fun insertFavoriteMeal() {
        val mealEntity = MealEntity(meal = mealDetail)
        detailViewModel.insertFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully added to favorite", Toast.LENGTH_SHORT).show()
    }

    private fun handleUi(
        layoutWrapper: Boolean,
        progressBar: Boolean,
        errorTv: Boolean,
        errorImg: Boolean
    ) {
        binding.apply {
            mealDetailWrapper.isVisible = layoutWrapper
            progressbar.isVisible = progressBar
            errorText.isVisible = errorTv
            errorIcon.isVisible = errorImg
        }
    }

    companion object {
        const val EXTRA_MEAL = "meal"
    }
}