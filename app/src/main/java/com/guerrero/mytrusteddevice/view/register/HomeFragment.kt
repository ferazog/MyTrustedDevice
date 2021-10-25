package com.guerrero.mytrusteddevice.view.register

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.guerrero.mytrusteddevice.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

private const val LOGO_ANIMATION_DURATION = 1000L

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startWelcomeAnimation()
    }

    private fun startWelcomeAnimation() {
        binding.logo.animate().run {
            alpha(1f)
            duration = LOGO_ANIMATION_DURATION
            setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.progressBar.visibility = View.VISIBLE

                    }
                }
            )
        }
    }
}
