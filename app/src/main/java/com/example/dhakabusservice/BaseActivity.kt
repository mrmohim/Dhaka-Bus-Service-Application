package com.example.dhakabusservice

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.SystemClock
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import com.google.android.material.navigation.NavigationView
import android.app.Activity

const val MAX_CLICK_INTERVAL: Long = 500
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    val Any.TAG: String
        get() {
            val tag = javaClass.simpleName
            val maxLength = 23
            return if (tag.length < maxLength) tag else tag.substring(0, maxLength)
        }

    private var lastClickedTime: Long = 0

    @get:LayoutRes
    protected abstract val layoutResource: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
//        setWindowHide()
        lastClickedTime = 0
        init(savedInstanceState)
        initGUI()

//        setWindowHide()
    }

    override fun onResume() {
//        setWindowHide()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onClick(v: View) {
        /*
         * Hide keyboard on click
         */
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.let { lmm ->
            lmm.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        /*
         * Prevent the launch twice if user makes the click very fast
         */
        if (SystemClock.elapsedRealtime() - lastClickedTime < MAX_CLICK_INTERVAL) {
            return
        }
        lastClickedTime = SystemClock.elapsedRealtime()
        onSafeClick(v)
    }

    fun switchActivity(className: Class<*>, extras: Bundle? = null) {
        val intent = Intent(applicationContext, className)
        extras?.let { intent.putExtras(it) }
        startActivity(intent)
    }

    fun setWindowHide() {
        this.window.decorView.apply {
            systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
    }

    fun setwindowHideKeyboard() {
        /*
         * Enable fullscreen mode
         */
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            window.decorView.getWindowVisibleDisplayFrame(rect)
            val screenHeight: Int = window.decorView.rootView.height
            val keypadHeight: Int = screenHeight - rect.bottom
            if (keypadHeight > screenHeight * 0.15) {
                setWindowHide()
            }
        }
    }

    fun View.slideUp(duration: Int = 500) {
        visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected abstract fun init(state: Bundle?)
    protected abstract fun initGUI()
    protected abstract fun onSafeClick(v: View)
}