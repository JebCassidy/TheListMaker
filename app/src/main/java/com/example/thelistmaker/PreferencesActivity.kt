package com.example.thelistmaker


import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import com.example.thelistmaker.databinding.ActivityPreferencesBinding

class PreferencesActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener{
    private lateinit var binding: ActivityPreferencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.themeRadioGroup.setOnCheckedChangeListener(this)


    }
    override fun onStart(){
        super.onStart()

        val preferences = getSharedPreferences(
            getString(R.string.preference_storage_name),
            Context.MODE_PRIVATE
        )

        val color = preferences.getString(getString(R.string.bg_color_theme), getString(R.string.bg_theme_classic))
        if(color.equals(getString(R.string.bg_theme_classic))){
            binding.classicThemeRadio.isChecked = true
        } else if(color.equals(getString(R.string.bg_theme_dark))){
            binding.darkThemeRadio.isChecked = true
        } else if(color.equals(getString(R.string.bg_theme_light))){
            binding.lightThemeRadio.isChecked = true
        }

        //val theme = preferences.getString(getString(R.string.bg_color_theme), getString(R.string.bg_theme_classic))
        if(color.equals(getString(R.string.bg_theme_classic))){
            binding.preferencesConstraint.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
            binding.themeColorTextBox.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
            binding.themeRadioGroup.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))


        } else if(color.equals(getString(R.string.bg_theme_dark))){
            binding.preferencesConstraint.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
            binding.themeColorTextBox.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
            binding.themeRadioGroup.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))


        } else if(color.equals(getString(R.string.bg_theme_light))){
            binding.preferencesConstraint.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
            binding.themeColorTextBox.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
            binding.themeRadioGroup.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))

        }
    }



    override fun onCheckedChanged(radioGroup: RadioGroup?, buttonId: Int) {
        val preferences = getSharedPreferences(
            getString(R.string.preference_storage_name),
            Context.MODE_PRIVATE
        )
        with(preferences.edit()){
            if(buttonId == R.id.classicThemeRadio){
                putString(getString(R.string.bg_color_theme), getString(R.string.bg_theme_classic))
            }else if(buttonId == R.id.darkThemeRadio){
            putString(getString(R.string.bg_color_theme), getString(R.string.bg_theme_dark))
        }else if(buttonId == R.id.lightThemeRadio){
            putString(getString(R.string.bg_color_theme), getString(R.string.bg_theme_light))
        }
            apply()
        }
    }

}