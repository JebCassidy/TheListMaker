package com.example.thelistmaker

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.thelistmaker.databinding.ActivityListBinding
import com.example.thelistmaker.List as List

class ListActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityListBinding

    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()

        val purpose = intent.getStringExtra(
            getString(R.string.purpose_intent_key)
        )
        if (purpose.equals(getString(R.string.purpose_add))) {
            setTitle("Add New List")
            binding.listDataEditText.isVisible = false
            binding.listTitleEditText.isVisible = false
        } else {
            setTitle("Current List")
            binding.listHeaderTextView.setText("Edit the current " +
                    "list and press the back button to save.")

            binding.groceryButton.isVisible = false
            binding.choresButton.isVisible = false
            binding.workoutButton.isVisible = false
            binding.blankListButton.isVisible = false
            binding.cancelListButton.isVisible = false


            val list = intent.getSerializableExtra(
                getString(R.string.list_intent_key)
            ) as List

            binding.listTitleEditText.setText(list.title)
            binding.listDataEditText.setText(list.listData)

            position = intent.getIntExtra(
                getString(R.string.position_intent_key), -1
            )
        }

        val grocery : View = binding.groceryButton
        grocery.setOnClickListener(this)
        val workOut : View = binding.workoutButton
        workOut.setOnClickListener(this)
        val chores : View = binding.choresButton
        chores.setOnClickListener(this)
        val blankList : View = binding.blankListButton
        blankList.setOnClickListener(this)
        val cancel : View = binding.cancelListButton
        cancel.setOnClickListener(this)

    }
    override fun onStart() {
        super.onStart()

        val preferences = getSharedPreferences(
            getString(R.string.preference_storage_name),
            Context.MODE_PRIVATE
        )

        val color = preferences.getString(getString(R.string.bg_color_theme), getString(R.string.bg_theme_classic))
        if(color.equals(getString(R.string.bg_theme_classic))){
            binding.listConstraint.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
            binding.listHeaderTextView.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
            binding.listTitleEditText.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
            binding.listDataEditText.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))

        } else if(color.equals(getString(R.string.bg_theme_dark))){
            binding.listConstraint.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
            binding.listHeaderTextView.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
            binding.listTitleEditText.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
            binding.listDataEditText.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))

        } else if(color.equals(getString(R.string.bg_theme_light))){
            binding.listConstraint.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
            binding.listHeaderTextView.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
            binding.listTitleEditText.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
            binding.listDataEditText.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
        }
    }

    override fun onClick(view: View?) {
        binding.groceryButton.isVisible = false
        binding.choresButton.isVisible = false
        binding.workoutButton.isVisible = false
        binding.blankListButton.isVisible = false
        binding.listDataEditText.isVisible = true
        binding.listTitleEditText.isVisible = true
        binding.listHeaderTextView.setText("Press back to save or cancel to cancel the list.")
        if (view?.getId() == R.id.groceryButton) {
            binding.listTitleEditText.setText("Grocery")
            binding.listDataEditText.setText("Milk\nBread\nWater\nDog Food")
        }
        if (view?.getId() == R.id.workoutButton) {
            binding.listTitleEditText.setText("Work Out")
            binding.listDataEditText.setText("Exercise:\nDuration/Reps:\nDays per week:")
        }
        if (view?.getId() == R.id.choresButton) {
            binding.listTitleEditText.setText("Chores")
            binding.listDataEditText.setText("Chore:\nFrequency:\nCompleted by:")
        }
        if (view?.getId() == R.id.blankListButton) {

        }
        if (view?.getId() == R.id.cancelListButton) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirm cancel")
                .setMessage("Are you sure you want to cancel the list?")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok){ dialogInterface, whichButton ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            builder.show()


        }
    }


        override fun onBackPressed() {
            val title = binding.listTitleEditText.getText()
                .toString().trim()
            if (title.isEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(
                    "Title cannot be empty"
                )
                    .setPositiveButton(android.R.string.ok, null)
                builder.show()
                return
            }
            val data = binding.listDataEditText.getText()
                .toString().trim()
            if (data.isEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(
                    "Data cannot be empty"
                )
                    .setPositiveButton(android.R.string.ok, null)
                builder.show()
                return
            }
            val list = List(title, data)

            val intent = Intent()

            intent.putExtra(
                getString(R.string.list_intent_key),
                list
            )

            intent.putExtra(
                getString(R.string.position_intent_key),
                position
            )

            setResult(RESULT_OK, intent)
            super.onBackPressed()
        }


    }
