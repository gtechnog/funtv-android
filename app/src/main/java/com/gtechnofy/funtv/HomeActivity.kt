package com.gtechnofy.funtv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gtechnofy.funtv.databinding.HomeActivityBinding
import com.gtechnofy.funtv.ui.HomeFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }
}