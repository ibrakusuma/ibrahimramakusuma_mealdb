package com.bootcamp.ibrahimramakusuma_mealdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bootcamp.ibrahimramakusuma_mealdb.adapter.MealAdapter
import com.bootcamp.ibrahimramakusuma_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ibrahimramakusuma_mealdb.databinding.ActivityMainBinding
import com.bootcamp.ibrahimramakusuma_mealdb.model.MealsItem
import com.bootcamp.ibrahimramakusuma_mealdb.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val mealAdapter by lazy { MealAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.favoriteIcon.setOnClickListener {
            val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(intent)
        }

        mainViewModel.mealList.observe(this@MainActivity) {result ->
            when(result) {
                is NetworkResult.Loading ->  {
                    handleUi(
                        recyclerView = false,
                        progressBar = true,
                        errorTv = false
                    )
                }

                is NetworkResult.Error -> {
                    binding.errorText.text = result.errorMessage
                    handleUi(
                        recyclerView = false,
                        progressBar = false,
                        errorTv = true
                    )
                }

                is NetworkResult.Success -> {

                    binding.rvHomeMeal.apply {
                        adapter = mealAdapter
                        layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                        setHasFixedSize(true)
                    mealAdapter.setData(result.data?.meals as List<MealsItem>)
                    }

                    handleUi(
                        recyclerView = true,
                        progressBar = false,
                        errorTv = false
                    )
                }
            }
        }

//        Service.mealService().getMeal().enqueue(object : Callback<ResponseMeal>{
//            override fun onResponse(call: Call<ResponseMeal>, response: Response<ResponseMeal>) {
//                Log.d("RESPONSE", response.isSuccessful.toString())
//                if(response.isSuccessful) {
//                    val meal = response.body()
//                    val dataMeal = meal?.meals
//                    val mealAdapter = MealAdapter()
//                    mealAdapter.setData(dataMeal as List<MealsItem>)
//                    binding.rvHomeMeal.apply {
//                        layoutManager = LinearLayoutManager(this@MainActivity)
//                        setHasFixedSize(true)
//                        adapter = mealAdapter
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseMeal>, t: Throwable) {
//                Log.d("gagal", "onFailure: " + t.localizedMessage)
//            }
//
//        })
    }

    private fun handleUi(
        recyclerView: Boolean,
        progressBar: Boolean,
        errorTv: Boolean
    ){
        binding.apply {
            rvHomeMeal.isVisible = recyclerView
            progressbar.isVisible = progressBar
            errorText.isVisible = errorTv
        }
    }
}