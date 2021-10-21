package com.guerrero.mytrusteddevice.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.guerrero.mytrusteddevice.R
import com.guerrero.mytrusteddevice.databinding.ActivityMainBinding
import com.guerrero.mytrusteddevice.di.ViewModelFactory
import com.guerrero.mytrusteddevice.shared.User
import com.guerrero.mytrusteddevice.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: MyViewModel by viewModels { factory }

    private var pushToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBtnLoginListener()
        observeViewState()
        getPushToken()
        createChannel()
    }

    private fun getPushToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Snackbar.make(
                    binding.root,
                    task.exception?.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()

            } else {
                pushToken = task.result
            }
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

    private fun setupBtnLoginListener() {
        with(binding) {
            btnLogin.setOnClickListener {
                val user = User(
                    fieldName.editText?.text.toString(),
                    fieldPassword.editText?.text.toString()
                )
                viewModel.registerUser(user, pushToken)
            }
        }
    }

    private fun observeViewState() {
        viewModel.getViewStateObservable().observe(this, { viewState ->
            when (viewState) {
                is ViewState.Loading -> setStateLoading()
                is ViewState.Error -> setStateError(viewState.message)
                is ViewState.Normal -> enableViews()
                is ViewState.Success -> setStateSuccess(viewState.isDone)
            }
        })
    }

    private fun setStateLoading() {
        disableViews()
        binding.run {
            btnLogin.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun setStateError(message: String) {
        enableViews()
        binding.run {
            btnLogin.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setStateSuccess(isDone: Boolean) {
        enableViews()
        Toast.makeText(this, isDone.toString(), Toast.LENGTH_LONG).show()
    }

    private fun disableViews() {
        with(binding) {
            fieldName.isEnabled = false
            fieldPassword.isEnabled = false
            btnLogin.isEnabled = false
        }
    }

    private fun enableViews() {
        with(binding) {
            fieldName.isEnabled = true
            fieldPassword.isEnabled = true
            btnLogin.isEnabled = true
        }
    }
}
