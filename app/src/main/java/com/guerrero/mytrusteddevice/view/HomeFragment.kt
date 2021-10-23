package com.guerrero.mytrusteddevice.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.guerrero.mytrusteddevice.R
import com.guerrero.mytrusteddevice.databinding.FragmentHomeBinding
import com.guerrero.mytrusteddevice.di.ViewModelFactory
import com.guerrero.mytrusteddevice.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: MyViewModel by activityViewModels { factory }

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

    private fun observeDeviceRegistered() {
        viewModel.getDeviceRegisteredObservable()
            .observe(viewLifecycleOwner, { isDeviceRegistered ->
                if (!isDeviceRegistered) {
                    binding.run {
                        progressBar.visibility = View.GONE
                        btnRegister.visibility = View.VISIBLE
                    }
                } else {
                    binding.root.findNavController()
                        .navigate(R.id.action_homeFragment_to_challengesFragment)
                }
            })
    }

    private fun setRegisterButtonListener() {
        binding.btnRegister.let { btnRegister ->
            btnRegister.setOnClickListener {
                btnRegister.findNavController()
                    .navigate(R.id.action_homeFragment_to_registerFragment)
            }
        }
    }

    private fun startWelcomeAnimation() {
        binding.logo.animate()
            .alpha(1f).
            setDuration(1000)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.progressBar.visibility=View.VISIBLE
                        viewModel.checkDeviceRegistered()
                    }
                }
            )
    }
}
