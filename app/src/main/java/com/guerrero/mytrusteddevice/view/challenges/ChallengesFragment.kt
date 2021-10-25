package com.guerrero.mytrusteddevice.view.challenges

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
}
