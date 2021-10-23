package com.guerrero.mytrusteddevice.view.challenges

import androidx.recyclerview.widget.RecyclerView
import com.guerrero.mytrusteddevice.databinding.ItemChallengeBinding
import com.guerrero.mytrusteddevice.shared.ChallengeWrapper

class ChallengeViewHolder(
    private val binding: ItemChallengeBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(challenge: ChallengeWrapper, listener: CardListener) {
        with(binding) {
            title.text = challenge.message
            expirationDate.text = challenge.expirationDate
            btnOpen.setOnClickListener { listener.onActionClicked(challenge) }
        }
    }
}
