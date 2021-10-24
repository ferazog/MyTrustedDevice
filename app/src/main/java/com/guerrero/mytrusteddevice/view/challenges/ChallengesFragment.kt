package com.guerrero.mytrusteddevice.view.challenges

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.guerrero.mytrusteddevice.databinding.FragmentChallengesBinding
import com.guerrero.mytrusteddevice.di.ViewModelFactory
import com.guerrero.mytrusteddevice.shared.ChallengeWrapper
import com.guerrero.mytrusteddevice.view.details.ChallengeDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChallengesFragment : Fragment(), CardListener {

    private lateinit var binding: FragmentChallengesBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: ChallengesViewModel by activityViewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChallengesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.swipeToRefreshContainer.setOnRefreshListener {
            viewModel.getPendingChallenges()
        }
        observeHideTip()
        viewModel.validateHideTip()
        binding.tipCard.btnDismiss.setOnClickListener { closeTipCard() }
        observeViewState()
        viewModel.getPendingChallenges()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = ChallengesAdapter(this)
    }

    private fun closeTipCard() {
        binding.tipCard.root.animate().run {
            alpha(0f)
            duration = 500
            setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.tipCard.root.visibility = View.GONE
                    }
                })
            viewModel.dismissTip()
        }
    }

    private fun observeHideTip() {
        viewModel.getHideTipObservable().observe(viewLifecycleOwner, { hideTip ->
            if (!hideTip) {
                binding.tipCard.root.visibility = View.VISIBLE
            }
        })
    }

    private fun observeViewState() {
        viewModel.getChallengesViewStateObservable().observe(viewLifecycleOwner, { viewState ->
            when (viewState) {
                is ChallengesViewState.Loading -> setStateLoading()
                is ChallengesViewState.Error -> setStateError(viewState.message)
                is ChallengesViewState.EmptyState -> showEmptyState()
                is ChallengesViewState.Success -> showChallenges(viewState.challenges)
                is ChallengesViewState.Logout -> logout()
            }
        })
    }

    private fun setStateLoading() {
        binding.run {
            swipeToRefreshContainer.isRefreshing = true
            emptyState.root.visibility = View.GONE
        }
    }

    private fun showEmptyState() {
        binding.run {
            swipeToRefreshContainer.isRefreshing = false
            emptyState.root.visibility = View.VISIBLE
        }
    }

    private fun setStateError(message: String) {
        binding.swipeToRefreshContainer.isRefreshing = false
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showChallenges(challenges: List<ChallengeWrapper>) {
        binding.swipeToRefreshContainer.isRefreshing = false
        with(binding.recyclerView) {
            (adapter as? ChallengesAdapter)?.submitList(challenges)
        }
    }

    private fun logout() {
        val action = ChallengesFragmentDirections.actionChallengesFragmentToHomeFragment()
        binding.root.findNavController().navigate(action)
    }

    override fun onActionClicked(challenge: ChallengeWrapper) {
        startActivity(Intent(requireActivity(), ChallengeDetailsActivity::class.java))
    }
}
