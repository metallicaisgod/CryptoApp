package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideCoinInfoDao(application: Application): CoinInfoDao{
        return AppDatabase.getInstance(application).getCoinInfoDao()
    }

    @ApplicationScope
    @Provides
    fun provideApiService(): ApiService{
        return ApiFactory.apiService
    }
}