package com.pds.proyectone

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.pds.proyectone.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonConvert.setOnClickListener {
            viewModel.convert(
                binding.etCryptoId.text.toString().trim(),
                binding.etAmount.text.toString().trim()
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when (event) {
                    is MainViewModel.CurrencyEvent.Success -> {
                        setViewForLoading(false, event.resultText)
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        setViewForLoading(false, event.failureText)
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        setViewForLoading(true)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setViewForLoading(isLoading: Boolean, displayText: String = "") {
        binding.progress.isVisible = isLoading
        binding.buttonConvert.isEnabled = !isLoading
        binding.tvResult.text = displayText
    }
}