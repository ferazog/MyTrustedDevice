package com.guerrero.mytrusteddevice.repository

interface ChallengesRepository {

    fun getFactorSid(): String

    fun logout()

    fun shouldHideTip(): Boolean

    fun hideTip()
}