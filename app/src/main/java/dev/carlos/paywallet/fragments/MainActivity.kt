package dev.carlos.paywallet.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.MenuProvider
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import dev.carlos.paywallet.fragments.dataBinding.ActivityMainBinding
import dev.carlos.paywallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var isBottomBarVisible: Boolean = true
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: dev.carlos.paywallet.databinding.ActivityMainBinding
    private val auth = Firebase?.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        })
        /*
                setSupportActionBar(binding.toolbar)
                val topDestinations = setOf(
                    R.id.fragment_login,
                    R.id.fragment_menu,
                    R.id.fragment_of_the_day,
                    R.id.fragment_Product,
                    R.id.fragment_Instructions,
                    R.id.fragment_Added_Error,
                    R.id.fragment_Added_Success,
                    R.id.fragment_Order
                )

                val navController = findNavController(R.id.nav_host_fragment_content_main)
                appBarConfiguration = AppBarConfiguration(topLevelDestinationIds = topDestinations)
                setupActionBarWithNavController(navController, appBarConfiguration)
                binding.bottomNavigation.setupWithNavController(navController)

                findNavController(R.id.nav_host_fragment_content_main)
                    .addOnDestinationChangedListener { _, destination, _ ->
                        binding.fab.setOnClickListener { }
                        when (destination.id) {
                            R.id.fragmentPreguntas, R.id.fragmentCuestionarioCompletado,
                            R.id.fragmentSalaEspera, R.id.FragmentLogin, R.id.FragmentRegistro, R.id.fragmentCuestionario -> {
                                binding.fab.hide()
                                binding.bottomNavigation.hide()
                            }
                            R.id.fragmentCuestionarios, R.id.fragmentSalas -> {
                                binding.fab.hide()
                                binding.bottomNavigation.show()
                            }
                            R.id.fragmentGrupos -> {
                                binding.fab.show()
                                binding.bottomNavigation.show()
                                binding.fab.setOnClickListener {
                                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.fragmentGruposDialog)
                                }
                            }
                            else -> {
                                binding.fab.show()
                                binding.bottomNavigation.show()
                            }
                        }
                    }

         */
    }

    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_logout -> {
                auth?.signOut()
                removeUserId()
                findNavController(R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_global_FragmentLogin)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

     */
}

const val UserCredentialsPreferences = "user-credentials"
fun Activity.removeUserId() {
    val preferences = getSharedPreferences(UserCredentialsPreferences, Context.MODE_PRIVATE)
    preferences.edit(commit = true) { remove("userId") }
}

fun Activity.getUserId(): String {
    val preferences = getSharedPreferences(UserCredentialsPreferences, Context.MODE_PRIVATE)
    return preferences.getString("userId", "") ?: ""
}

fun Activity.setUserId(userId: String) {
    val preferences = getSharedPreferences(UserCredentialsPreferences, Context.MODE_PRIVATE)
    preferences.edit(commit = true) { putString("userId", userId) }
}

fun MainActivity.removeMenu() {
    addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menu.clear()
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return false
        }
    })
}

fun BottomNavigationView.show() {
    if(visibility != View.VISIBLE) {
        animate()
            .translationY(0f)
            .withEndAction { visibility = View.VISIBLE }
            .duration = 100
    }
}

fun BottomNavigationView.hide() {
    if(visibility != View.INVISIBLE) {
        animate()
            .translationY(height.toFloat())
            .withEndAction { visibility = View.INVISIBLE }
            .duration = 100
    }
}