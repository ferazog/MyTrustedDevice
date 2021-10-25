package com.guerrero.mytrusteddevice.view.register

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.guerrero.mytrusteddevice.databinding.FragmentHomeBinding
import com.guerrero.mytrusteddevice.di.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val LOGO_ANIMATION_DURATION = 1000L

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: RegisterViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDeviceRegistered()
        setRegisterButtonListener()
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
                        viewModel.checkDeviceRegistered()
                    }
                }
            )
        }
    }

    private fun observeDeviceRegistered() {
        viewModel.getDeviceRegisteredObservable()
            .observe(viewLifecycleOwner, { isDeviceRegistered ->
                when (isDeviceRegistered) {
                    true -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToChallengesFragment()
                        binding.root.findNavController().navigate(action)
                    }
                    false -> {
                        with(binding) {
                            progressBar.visibility = View.INVISIBLE
                            btnRegister.visibility = View.VISIBLE
                        }
                    }
                }
            })
    }

    private fun setRegisterButtonListener() {
        binding.btnRegister.let { btnRegister ->
            btnRegister.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToRegisterFragment()
                btnRegister.findNavController().navigate(action)
            }
        }
    }
}
