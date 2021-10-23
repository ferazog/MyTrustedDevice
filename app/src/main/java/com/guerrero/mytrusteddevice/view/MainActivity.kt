package com.guerrero.mytrusteddevice.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.guerrero.mytrusteddevice.R
import com.guerrero.mytrusteddevice.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createChannel()
        setupToolbarOptions()
    }

    private fun setupToolbarOptions() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when (destination.id) {
                controller.graph.startDestination -> {
                    menu?.findItem(R.id.logout)?.isVisible = false
                }
                R.id.registerFragment -> {
                    menu?.findItem(R.id.logout)?.isVisible = false
                }
                else -> {
                    menu?.findItem(R.id.logout)?.isVisible = true
                }
            }
        }
        NavigationUI.setupActionBarWithNavController(this, navController)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.notification_channel_id)
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                findNavController(R.id.navHostFragment).navigate(R.id.registerFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
