package com.example.dhakabusservice

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import com.example.dhakabusservice.fragment.SignInFragment
import com.example.dhakabusservice.fragment.SignUpFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val layoutResource: Int = R.layout.activity_main

    override fun init(state: Bundle?) {
        /** Setup Start */
        if(SharedData.busList.size == 0) {
            SharedData.busList = ArrayList(listOf(*resources.getStringArray(R.array.bus_list)))
        }

        btnHome.setOnClickListener(this)
        btnGuest.setOnClickListener(this)
        btnStart.setOnClickListener(this)
    }
    override fun initGUI() {
        Hawk.init(this).build()
        if (Hawk.count() == 0L) Hawk.put(
            "login",
            arrayListOf("unknown", "unknown@email.com", "@email.com", "unknown")
        )
        SharedData.userInfo = Hawk.get("login")
        if (SharedData.userInfo[0] != "unknown" && SharedData.userInfo[1] != "unknown@email.com" && SharedData.userInfo[2] != "@email.com") {
            val homeActivity = Intent(this, HomeActivity::class.java)
            startActivity(homeActivity)
        }

        progressBarLayout.visibility = View.VISIBLE
        Handler().postDelayed({
            progressBarLayout.visibility = View.GONE
            content_frame_main.visibility = View.VISIBLE
        }, 500)

        toolbar_main.title = ""
        setSupportActionBar(toolbar_main)

        navigationView_main.setNavigationItemSelectedListener(this)
        val actionBarDrawerToggleMain = ActionBarDrawerToggle(
            this,
            drawer_layout_main,
            toolbar_main,
            R.string.nav_open,
            R.string.nav_close
        )
        drawer_layout_main.addDrawerListener(actionBarDrawerToggleMain)
        actionBarDrawerToggleMain.syncState()

        btnHome.visibility = View.GONE
        bottomNavigationView_main.visibility = View.GONE
    }

    override fun onSafeClick(v: View) {
        when (v.id) {
            R.id.btnHome -> {
                welcomeContainer.visibility = View.VISIBLE
                container_main.visibility = View.GONE
                btnHome.visibility = View.GONE
                bottomNavigationView_main.visibility = View.GONE
            }
            R.id.btnStart -> {
                progressBarLayout.visibility = View.VISIBLE

                bottomNavigationView_main.setOnNavigationItemSelectedListener(
                    mOnNavigationItemSelectedListenerMain
                )
                bottomNavigationView_main.selectedItemId = R.id.navigation_sign_in

                Handler().postDelayed({
                    progressBarLayout.visibility = View.GONE

                    welcomeContainer.visibility = View.GONE
                    container_main.visibility = View.VISIBLE
                    btnHome.visibility = View.VISIBLE
                    bottomNavigationView_main.visibility = View.VISIBLE
                }, 300)
            }
            R.id.btnGuest -> {
                Hawk.put(
                    "login",
                    arrayListOf("Guest", "guest@email.com", "@email.com", "guest")
                )
                SharedData.userInfo = Hawk.get("login")

                progressBarLayout.visibility = View.VISIBLE
                Toast.makeText(this, "You are in Guest mode!", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    val homeActivity = Intent(this, HomeActivity::class.java)
                    startActivity(homeActivity)
                }, 300)
            }
        }
    }

    override fun onPause() {
        this.finish()
        super.onPause()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_privacy_policy_main -> {
                Toast.makeText(this, "Privacy policy", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_settings_main -> {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_feedback_main -> {
                Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_rate_us_main -> {
                Toast.makeText(this, "Rate us", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_about_main -> {
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout_main.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout_main.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_main.closeDrawer(GravityCompat.START)
        } else {
            this.finish()
            super.onBackPressed()
        }
    }

    private val mOnNavigationItemSelectedListenerMain =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_sign_in -> {
                    welcomeContainer.visibility = View.GONE
                    container_main.visibility = View.VISIBLE
                    btnHome.visibility = View.VISIBLE
                    if (container_main.isVisible) {
                        app_title_main.text = resources.getString(R.string.sign_in)
                    }

                    val fragment = SignInFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_main, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_sign_up -> {
                    welcomeContainer.visibility = View.GONE
                    container_main.visibility = View.VISIBLE
                    btnHome.visibility = View.VISIBLE
                    if (container_main.isVisible) {
                        app_title_main.text = resources.getString(R.string.sign_up)
                    }

                    val fragment = SignUpFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_main, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
