package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.CoinApp
import com.example.cryptoapp.presentation.CoinDetailFragment
import com.example.cryptoapp.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class, WorkersModule::class])
interface ApplicationComponent {

    fun inject(coinPriceListActivity: CoinPriceListActivity)

    fun inject(coinDetailFragment: CoinDetailFragment)

    fun inject(application: CoinApp)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}