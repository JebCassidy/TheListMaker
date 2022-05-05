package com.example.thelistmaker

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.thelistmaker.databinding.ActivityAboutBinding
import com.example.thelistmaker.databinding.ActivityMainBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val preferences = getSharedPreferences(
            getString(R.string.preference_storage_name),
            Context.MODE_PRIVATE
        )

        val color = preferences.getString(getString(R.string.bg_color_theme), getString(R.string.bg_theme_classic))
        if(color.equals(getString(R.string.bg_theme_classic))){
            binding.aboutConstraint.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
            binding.aboutInfoText.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
            binding.aboutTitleTextView.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
        } else if(color.equals(getString(R.string.bg_theme_dark))){
            binding.aboutConstraint.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
            binding.aboutInfoText.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
            binding.aboutTitleTextView.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))

        } else if(color.equals(getString(R.string.bg_theme_light))){
            binding.aboutConstraint.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
            binding.aboutInfoText.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
            binding.aboutTitleTextView.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
        }
    }
}