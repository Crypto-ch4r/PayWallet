package dev.carlos.paywallet.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dev.carlos.paywallet.R
import dev.carlos.paywallet.fragments.adapters.RecyclerFoodItemAdapter
import dev.carlos.paywallet.fragments.interfaces.MenuApi
import dev.carlos.paywallet.fragments.interfaces.RequestType
import dev.carlos.paywallet.fragments.services.FirebaseDBService
import dev.carlos.paywallet.fragments.ui.LoginFragment

class MainActivity : AppCompatActivity(), RecyclerFoodItemAdapter.OnItemClickListener, MenuApi {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    private var allItems = ArrayList<MenuItem>()
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var recyclerFoodAdapter: RecyclerFoodItemAdapter

    private lateinit var foodCategoryCV: CardView
    private lateinit var showAllLL: LinearLayout

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment())
                .commitNow()
                //loadMenu()
        } else {
            setContentView(R.layout.activity_main)

            auth = FirebaseAuth.getInstance()
            databaseRef = FirebaseDatabase.getInstance().reference

            loadMenu()
        }
    }

    private fun loadMenu() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(SavedPreference.toString(), Context.MODE_PRIVATE)

        itemRecyclerView = findViewById(R.id.rvdelDia)
        recyclerFoodAdapter = RecyclerFoodItemAdapter(
            applicationContext,
            allItems,
            sharedPreferences.getInt("loadItemImages",0),
            this
        )
        itemRecyclerView.adapter = recyclerFoodAdapter
        itemRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerFoodAdapter.filterList(allItems) //display complete list
        loadOnlineMenu()
    }

    private fun RecyclerFoodItemAdapter(
        context: Context?,
        itemList: java.util.ArrayList<MenuItem>,
        loadDefaultImage: Int,
        listener: MainActivity
    ): RecyclerFoodItemAdapter {
        TODO("Not yet implemented")
    }

    private fun loadOnlineMenu() {
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setTitle("Loading Menu...")
        progressDialog.setMessage("Please wait while we load the menu. ^_^")
        progressDialog.create()
        progressDialog.show()

        FirebaseDBService().readAllMenu(this, RequestType.READ)
    }
    override fun onItemClick(item: dev.carlos.paywallet.fragments.dataClass.MenuItem) {
        TODO("Not yet implemented")
    }

    override fun onPlusBtnClick(item: dev.carlos.paywallet.fragments.dataClass.MenuItem) {
        TODO("Not yet implemented")
    }

    override fun onMinusBtnClick(item: dev.carlos.paywallet.fragments.dataClass.MenuItem) {
        TODO("Not yet implemented")
    }

    override fun onFetchSuccessListener(
        list: ArrayList<dev.carlos.paywallet.fragments.dataClass.MenuItem>,
        requestType: RequestType
    ) {
        TODO("Not yet implemented")
    }
}