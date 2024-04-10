package com.abhi.apps10x.screens

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhi.apps10x.R
import com.abhi.apps10x.databinding.ActivityMainBinding
import com.abhi.apps10x.util.safeCollectFlow
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val adapter = TempListAdapter()
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter

        safeCollectFlow(viewModel.uiState) {it ->
            it.isLoading.apply {
                if (this) {
                    binding.list.visibility = View.GONE
                    binding.temp.visibility = View.GONE
                    binding.city.visibility = View.GONE
                    binding.progress.visibility = View.VISIBLE
                } else {
                    it.isError.apply {
                        if (this) {
                            binding.list.visibility = View.GONE
                            binding.temp.visibility = View.GONE
                            binding.city.visibility = View.GONE
                            binding.progress.visibility = View.GONE
                            val mySnackbar = Snackbar.make(binding.root,
                                R.string.something_went_wrong, Snackbar.LENGTH_SHORT)
                            mySnackbar.setAction(R.string.undo_string) { viewModel.retry() }
                            mySnackbar.show()

                        } else {
                            binding.list.visibility = View.VISIBLE
                            binding.temp.visibility = View.VISIBLE
                            binding.city.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                            binding.city.text = it.weatherResponse.name
                            binding.temp.text = String.format("%.0f",
                                it.weatherResponse.main?.temp?.minus(273.15) ?: 0.0
                            )
                            adapter.setNews(it.forecastValues)
                        }
                    }

                }
            }

        }
    }
}