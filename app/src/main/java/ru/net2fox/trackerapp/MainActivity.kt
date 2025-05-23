package ru.net2fox.trackerapp

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.net2fox.trackerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val activityPreferences: SharedPreferences = getSharedPreferences("ru.net2fox.trackerapp_preferences", Activity.MODE_PRIVATE)
        Log.d("TrackerApp", activityPreferences.getString("dark_theme", "").toString())
        if (activityPreferences.getString("dark_theme", "").toString() == "system") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else if (activityPreferences.getString("dark_theme", "").toString() == "on") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        setSupportActionBar(binding.toolbar)
        val navView = binding.bottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }

    //override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    //    menuInflater.inflate(R.menu.appbar_menu, menu)
    //    return super.onCreateOptionsMenu(menu)
    //}

    //override fun onOptionsItemSelected(item: MenuItem): Boolean {
    //    if(item.itemId == R.id.action_delete) {

    //    }
    //    return super.onOptionsItemSelected(item)
    //}
}