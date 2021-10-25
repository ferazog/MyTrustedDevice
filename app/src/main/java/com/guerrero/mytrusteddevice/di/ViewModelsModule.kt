package com.guerrero.mytrusteddevice.di

import androidx.lifecycle.ViewModel
import com.guerrero.mytrusteddevice.view.challenges.ChallengesViewModel
import com.guerrero.mytrusteddevice.view.details.ChallengeDetailsViewModel
import com.guerrero.mytrusteddevice.view.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChallengesViewModel::class)
    abstract fun bindChallengesViewModel(viewModel: ChallengesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChallengeDetailsViewModel::class)
    abstract fun bindChallengeDetailsViewModel(viewModel: ChallengeDetailsViewModel): ViewModel
}
