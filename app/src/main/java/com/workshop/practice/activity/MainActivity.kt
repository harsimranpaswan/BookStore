package com.workshop.practice.activity

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.workshop.practice.R
import com.workshop.practice.fragment.AboutFragment
import com.workshop.practice.fragment.DashboardFragment
import com.workshop.practice.fragment.FavouritesFragment
import com.workshop.practice.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    lateinit var toolbar_layout: Toolbar
    lateinit var coordinator_layout: CoordinatorLayout
    lateinit var drawer_layout: DrawerLayout
    lateinit var navigation_view: NavigationView
    lateinit var text_view: TextView
    lateinit var frame_layout: FrameLayout
    var previousMenuItem: MenuItem? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        text_view = findViewById(R.id.text_view)
        coordinator_layout = findViewById(R.id.coordinator_layout)
        drawer_layout = findViewById(R.id.drawer_layout)
        navigation_view = findViewById(R.id.navigation_view)
        toolbar_layout = findViewById(R.id.toolbar_layout)
        frame_layout = findViewById(R.id.frame_layout)

        openDashboard()


        actionToToolbar()

        //for hamburger
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity, drawer_layout, R.string.open_drawer, R.string.close_drawer
        )
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        openMenus()
    }


    fun actionToToolbar() {
        setSupportActionBar(toolbar_layout)
        supportActionBar?.title = "The Book Store"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun openDashboard() {
        var fragment = DashboardFragment()
        var transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, DashboardFragment()).commit()
        supportActionBar?.title = "Dashboard"
        drawer_layout.closeDrawers()
        navigation_view.setCheckedItem(R.id.dashboard)
    }

    fun openMenus() {
        navigation_view.setNavigationItemSelectedListener {

            if (previousMenuItem != null) previousMenuItem?.isChecked = false
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.dashboard -> openDashboard()
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, FavouritesFragment()).commit()
                    supportActionBar?.title = "Favourites"
                    drawer_layout.closeDrawers()
                }
                R.id.profile -> {
                    val profileManager = supportFragmentManager.beginTransaction()
                    val profileFragment = ProfileFragment()
                    var bundle = intent.getBundleExtra("bundle")
                    profileFragment.arguments = bundle
                    profileManager.replace(R.id.frame_layout, profileFragment).commit()
                    supportActionBar?.title = "Profile"
                    drawer_layout.closeDrawers()
                    Toast.makeText(this, "Hi, "+bundle?.getString("mail"), Toast.LENGTH_SHORT).show()
                }
                R.id.about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, AboutFragment()).commit()
                    supportActionBar?.title = "About"
                    drawer_layout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawer_layout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame_layout)
        when (frag) {
            !is DashboardFragment -> openDashboard()
            else -> super.onBackPressed()
        }
    }

}
