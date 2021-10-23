package com.guerrero.mytrusteddevice.view.challenges

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.guerrero.mytrusteddevice.databinding.FragmentChallengesBinding

class ChallengesFragment : Fragment() {

    private lateinit var binding: FragmentChallengesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChallengesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeToRefreshContainer.setOnRefreshListener {
            //closeTipCard()
            binding.swipeToRefreshContainer.isRefreshing = false
        }
        binding.tipCard.btnDismiss.setOnClickListener { closeTipCard() }
    }

    private fun closeTipCard() {
        binding.tipCard.root.run {
            animate()
                .alpha(0f)
                .setDuration(1000)
                .setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            visibility = View.GONE
                        }
                    })

        }
    }
}
