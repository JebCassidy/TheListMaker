package com.example.thelistmaker

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.*
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thelistmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val data = mutableListOf<List>()
    private lateinit var adapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data.add(List("Groceries","Ham Eggs Bacon"))
        data.add(List("Workout", "Mon. Tues. Thurs."))
        data.add(List("Study", "Wed. Fri. Sun."))

        val layoutManager = LinearLayoutManager(this)
        binding.listRecyclerView.setLayoutManager(layoutManager)

        val divider = DividerItemDecoration(
            applicationContext, layoutManager.orientation
        )
        binding.listRecyclerView.addItemDecoration(divider)

        adapter = MyAdapter()
        binding.listRecyclerView.setAdapter(adapter)
    }

    override fun onStart() {
        super.onStart()

        val preferences = getSharedPreferences(
            getString(R.string.preference_storage_name),
            Context.MODE_PRIVATE
        )

        val color = preferences.getString(getString(R.string.bg_color_theme), getString(R.string.bg_theme_classic))
        if(color.equals(getString(R.string.bg_theme_classic))){
            binding.listRecyclerView.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
            binding.nameLabel.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
            binding.constraintLayout.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
            binding.deleteTextView.setBackgroundColor(Color.parseColor(getString(R.string.color_teal)))
        } else if(color.equals(getString(R.string.bg_theme_dark))){
            binding.listRecyclerView.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
            binding.nameLabel.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
            binding.constraintLayout.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
            binding.deleteTextView.setBackgroundColor(Color.parseColor(getString(R.string.color_gray)))
        } else if(color.equals(getString(R.string.bg_theme_light))){
            binding.listRecyclerView.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
            binding.nameLabel.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
            binding.constraintLayout.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
            binding.deleteTextView.setBackgroundColor(Color.parseColor(getString(R.string.color_white)))
        }
    }

    private fun addList(){
        val intent = Intent(this, ListActivity::class.java)
            intent.putExtra(
                getString(R.string.purpose_intent_key),
                getString(R.string.purpose_add))
            //startActivity(intent)

            startForAddResult.launch(intent)
        }

    val startForAddResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result : ActivityResult ->

            if(result.resultCode == RESULT_OK){
                val intent = result.data

                val list = intent?.getSerializableExtra(
                    getString(R.string.list_intent_key)
                ) as List

                data.add(list)
                adapter.notifyDataSetChanged()
            }
        }

    val startForUpdateResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result : ActivityResult ->

            if(result.resultCode == RESULT_OK){
                val intent = result.data

                val list = intent?.getSerializableExtra(
                    getString(R.string.list_intent_key)
                ) as List

                val position = intent?.getIntExtra(
                    getString(R.string.position_intent_key), -2
                )
                data[position].title = list.title
                data[position].listData = list.listData

                adapter.notifyItemChanged(position)
            }
        }

    inner class MyViewHolder(val itemView : View) :
        RecyclerView.ViewHolder(itemView),
            View.OnClickListener, View.OnLongClickListener{

                init{
                    itemView.findViewById<View>(R.id.the_layout)
                        .setOnClickListener(this)
                    itemView.findViewById<View>(R.id.the_layout)
                        .setOnLongClickListener(this)
        }

        fun setList(list : List){
            itemView.findViewById<TextView>(R.id.listTitleTextView)
                .setText("${list.title}")
        }


        override fun onClick(view: View?) {
            if(view != null) {
                val intent = Intent(view.context, ListActivity::class.java)
                val list = data[adapterPosition]
                intent.putExtra(
                    getString(R.string.list_intent_key),
                    list
                )
                intent.putExtra(
                    getString(R.string.position_intent_key),
                adapterPosition
                )
                intent.putExtra(
                    getString(R.string.purpose_intent_key),
                    getString(R.string.purpose_update)
                )
                startForUpdateResult.launch(intent)
            }
        }
        //deletes list
        override fun onLongClick(view: View?): Boolean {
            if (view != null) {
                val list = data[adapterPosition]
                val title = "${list.title}"

                val builder = AlertDialog.Builder(view.context)
                builder.setTitle("Confirm delete")
                    .setMessage("Are you sure you want to delete ${title}?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok) { dialogInterface, whichButton ->
                        data.removeAt(adapterPosition)
                        adapter.notifyItemRemoved(adapterPosition)
                    }
                builder.show()
            }
            return true
        }
    }



    inner class MyAdapter(): RecyclerView.Adapter<MyViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.setList(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }

    }







    //options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.preferences_menu_item){
            val intent = Intent(this, PreferencesActivity::class.java)
            startActivity(intent)
            return true
        } else if(item.itemId == R.id.about_menu_item){
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            return true
        } else if(item.itemId == R.id.create_new_menu_item){
            /*val intent = Intent(this, NewListActivity::class.java)
            startActivity(intent)*/
                addList()
            return true
            }
        return super.onOptionsItemSelected(item)
    }
}