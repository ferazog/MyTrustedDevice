package com.guerrero.mytrusteddevice.view.details

import android.app.NotificationManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.guerrero.mytrusteddevice.databinding.ActivityDetailsBinding
import com.guerrero.mytrusteddevice.di.ViewModelFactory
import com.guerrero.mytrusteddevice.push.cancelNotifications
import com.guerrero.mytrusteddevice.shared.ChallengeWrapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val KEY_CHALLENGE_ID = "KEY_CHALLENGE_ID"
const val KEY_FACTOR_SID = "KEY_FACTOR_SID"

@AndroidEntryPoint
class ChallengeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: ChallengeDetailsViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewState()
        getExtras()
        setBtnApproveListener()
        setBtnDenyListener()
    }

    private fun observeViewState() {
        viewModel.getDetailsViewStateObservable().observe(this, { viewModel ->
            when (viewModel) {
                is DetailsViewState.Loading -> showStateLoading()
                is DetailsViewState.Success -> showDetails(viewModel.challenge)
                is DetailsViewState.Error -> showError(viewModel.message)
                is DetailsViewState.ChallengeUpdated -> finish()
            }
        })
    }

    private fun showStateLoading() {
        binding.run {
            progressBar.visibility = View.VISIBLE
            btnApprove.visibility = View.GONE
            btnDeny.visibility = View.GONE
        }
    }

    private fun showDetails(challenge: ChallengeWrapper) {
        binding.run {
            progressBar.visibility = View.GONE
            btnApprove.visibility = View.VISIBLE
            btnDeny.visibility = View.VISIBLE
        }
        with(binding) {
            message.text = challenge.message
            details.text = challenge.formatDetails()
            date.text = challenge.date
            expiration.text = challenge.expirationDate
            status.text = challenge.status.name
        }
    }

    private fun showError(message: String) {
        binding.run {
            progressBar.visibility = View.GONE
            Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getExtras() {
        val challengeId = intent.getStringExtra(KEY_CHALLENGE_ID)
        val factorSid = intent.getStringExtra(KEY_FACTOR_SID)
        if (challengeId != null && factorSid != null) {
            cancelNotifications()
            viewModel.getDetails(challengeId, factorSid)
        }
    }

    private fun cancelNotifications() {
        val notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelNotifications()
    }

    private fun setBtnApproveListener() {
        binding.btnApprove.setOnClickListener {
            viewModel.approve()
        }
    }

    private fun setBtnDenyListener() {
        binding.btnDeny.setOnClickListener {
            viewModel.deny()
        }
    }
}
