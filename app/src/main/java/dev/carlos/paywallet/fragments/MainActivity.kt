package dev.carlos.paywallet.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dev.carlos.paywallet.R
import dev.carlos.paywallet.fragments.adapters.RecyclerFoodItemAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.fragment_login -> bottomNavigation.visibility = View.INVISIBLE
                else -> bottomNavigation.visibility = View.VISIBLE
            }
        }
    }
}