package com.androiddesenv.opinando.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.androiddesenv.opinando.R
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private val fragments = mapOf(
            FORM_FRAGMENT to ::FormFragment,
            LIST_FRAGMENT  to ::ListFragment,
            SETTINGS_FRAGMENT to ::SettingsFragment,
            ONLINE_FRAGMENT to ::OnlineFragment)

    companion object {
        const val FORM_FRAGMENT = R.id.menuitem_newitem
        const val LIST_FRAGMENT = R.id.menuitem_listitem
        const val GPS_PERMISSION_REQUEST = 1231
        const val SETTINGS_FRAGMENT = R.id.menuitem_settings
        const val ONLINE_FRAGMENT = R.id.menuitem_online
    }

    fun navigateTo(item: Int) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        this.runOnUiThread {
            val bottomNavigationMenu = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            val menuItem = bottomNavigationMenu.menu.findItem(item)

            menuItem.isChecked = true
        }

        val fragmentInstance: Fragment = fragments[item]?.invoke()!!
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, fragmentInstance)
                .commit()
    }

    private fun askForGPSPermission() {
        if(ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MainActivity.GPS_PERMISSION_REQUEST
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            GPS_PERMISSION_REQUEST -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,
                            "Permissão para usar o GPS concedida",
                            Toast.LENGTH_SHORT)
                            .show()
                } }
        } }

    private fun configureBottomMenu() {
        val bottomNavigationMenu = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationMenu.setOnNavigationItemSelectedListener {
            navigateTo(it.itemId)
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseTheme()
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            navigateTo(FORM_FRAGMENT)
        }

        configureBottomMenu()
        configureAutoHiddenKeyboard()
        askForGPSPermission()
    }

    private fun configureAutoHiddenKeyboard() {
        val mainContainer = findViewById<ConstraintLayout>(R.id.main_container)
        mainContainer.setOnTouchListener { v, event ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    private fun chooseTheme() {
        val nightMode = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(SettingsFragment.NIGHT_MODE_PREF, false)
        if(nightMode) {
            setTheme(R.style.AppThemeNight_NoActionBar)
        } else {
            setTheme(R.style.AppTheme_NoActionBar)
        }
    }

    fun setNightMode(){
        recreate()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }
    fun navigateWithBackStack(destiny: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, destiny)
                .addToBackStack(null)
                .commit()
    }
}

