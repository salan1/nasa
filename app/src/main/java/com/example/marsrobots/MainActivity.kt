package com.example.marsrobots

import android.os.Bundle
import androidx.activity.viewModels
import com.example.marsrobots.databinding.ActivityMainBinding
import com.example.marsrobots.nasa.viewmodels.NasaViewModel
import com.example.utils.ui.BaseActivity
import com.example.utils.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: NasaViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
