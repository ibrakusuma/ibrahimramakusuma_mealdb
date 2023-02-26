package com.bootcamp.ibrahimramakusuma_mealdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bootcamp.ibrahimramakusuma_mealdb.R
import com.bootcamp.ibrahimramakusuma_mealdb.adapter.FavoriteAdapter
import com.bootcamp.ibrahimramakusuma_mealdb.data.database.MealEntity
import com.bootcamp.ibrahimramakusuma_mealdb.databinding.ActivityFavoriteBinding
import com.bootcamp.ibrahimramakusuma_mealdb.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private val favoriteMealAdapter by lazy { FavoriteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Favorite Meal"
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteViewModel.favoriteMealList.observe(this) { result ->
            if (result.isEmpty()) {
                binding.apply {
                    rvFavoriteMeal.isVisible = false
                    emptyTv.isVisible = true
                    emptyIcon.isVisible = true
                }
            } else {
                binding.rvFavoriteMeal.apply {
                    adapter = favoriteMealAdapter
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(
                        this@FavoriteActivity
                    )
                }

                favoriteMealAdapter.apply {
                    setData(result)
                    setOnItemClickCallback(object : FavoriteAdapter.IOnFavoriteItemCallBack {
                        override fun onFavoriteItemClickCallback(data: MealEntity) {
                            val intent = Intent(this@FavoriteActivity, FavoriteDetailActivity::class.java)
                            intent.putExtra(FavoriteDetailActivity.EXTRA_FAVORITE_MEAL, data)
                            startActivity(intent)
                        }
                    })
                }
            }
        }
    }
}