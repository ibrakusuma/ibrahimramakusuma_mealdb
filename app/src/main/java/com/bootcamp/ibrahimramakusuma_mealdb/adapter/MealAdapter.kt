package com.bootcamp.ibrahimramakusuma_mealdb.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.ibrahimramakusuma_mealdb.R
import com.bootcamp.ibrahimramakusuma_mealdb.databinding.MealRowLayoutBinding
import com.bootcamp.ibrahimramakusuma_mealdb.model.MealsItem
import com.bootcamp.ibrahimramakusuma_mealdb.ui.DetailActivity
import com.bumptech.glide.Glide

class MealAdapter : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    private var mealList: List<MealsItem> = listOf()
    inner class MealViewHolder(private val binding: MealRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealsItem) {
            binding.apply {
                data = item

                binding.mealItemWrapper.setOnClickListener {
                    val intent = Intent(mealItemWrapper.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MEAL, item)
                    mealItemWrapper.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            MealRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        return holder.bind(mealList[position])
    }

    fun setData(list: List<MealsItem>) {
        mealList = list
        notifyDataSetChanged()
    }
}