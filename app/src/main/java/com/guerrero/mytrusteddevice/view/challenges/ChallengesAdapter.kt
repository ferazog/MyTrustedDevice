package com.guerrero.mytrusteddevice.view.challenges

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.guerrero.mytrusteddevice.databinding.ItemChallengeBinding
import com.guerrero.mytrusteddevice.shared.ChallengeWrapper


class ChallengesAdapter(
    private val listener: CardListener
) : ListAdapter<ChallengeWrapper, ChallengeViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<ChallengeWrapper>() {

        override fun areItemsTheSame(
            oldItem: ChallengeWrapper,
            newItem: ChallengeWrapper
        ): Boolean {
            // TODO: date should not be an id
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(
            oldItem: ChallengeWrapper,
            newItem: ChallengeWrapper
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        return ChallengeViewHolder(
            ItemChallengeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            listener
        )
    }
}