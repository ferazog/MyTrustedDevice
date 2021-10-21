package com.guerrero.mytrusteddevice.di

import androidx.lifecycle.ViewModel
import com.guerrero.mytrusteddevice.viewmodel.MyViewModel
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
    @ViewModelKey(MyViewModel::class)
    abstract fun bindProductsViewModel(viewModel: MyViewModel): ViewModel
}
