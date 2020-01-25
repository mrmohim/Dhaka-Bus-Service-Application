package com.example.dhakabusservice

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import com.example.dhakabusservice.fragment.AllBusFragment
import com.example.dhakabusservice.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.collections.ArrayList

class HomeActivity : BaseActivity() {
    override val layoutResource: Int = R.layout.activity_home

    private var busList = ArrayList<String>()
    private var userInfo = ArrayList<String>()

    override fun init(state: Bundle?) {
        busList = SharedData.busList
        userInfo = SharedData.userInfo
    }

    override fun initGUI() {
        toolbar.title = ""
        setSupportActionBar(toolbar)

        Hawk.put("login", userInfo)

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        navigationView.setNavigationItemSelectedListener(this)
        val headerView = navigationView.getHeaderView(0)

        val userName: TextView = headerView.findViewById(R.id.user_name)
        val emailId: TextView = headerView.findViewById(R.id.email_id)
        userName.text = userInfo[0]
        emailId.text = userInfo[1]

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    override fun onSafeClick(v: View) {
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_profile -> {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_notifications -> {
                Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_privacy_policy -> {
                Toast.makeText(this, "Privacy policy", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_send_bus_image -> {
                Toast.makeText(this, "Send Bus Image", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_suggest_new_bus -> {
                Toast.makeText(this, "Suggest new bus", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_settings -> {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_feedback -> {
                Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_rate_us -> {
                Toast.makeText(this, "Rate us", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_about -> {
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                Hawk.put("login", arrayListOf("unknown", "unknown@email.com", "@email.com", "unknown"))
                val mainActivity = Intent(this, MainActivity::class.java)
                startActivity(mainActivity)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        var flag = false
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            flag = true
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        if (sourceListView.isVisible) {
            flag = true
            sourceListView.visibility = View.GONE
        }
        if (destinationListView.isVisible) {
            flag = true
            destinationListView.visibility = View.GONE
        }
        if (!flag) {
            super.onBackPressed()
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    app_title_home.text = resources.getString(R.string.home)

                    val fragment = HomeFragment(this, busList)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_all_bus -> {
                    app_title_home.text = resources.getString(R.string.all_bus)

                    val fragment = AllBusFragment(this, busList)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName).commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
