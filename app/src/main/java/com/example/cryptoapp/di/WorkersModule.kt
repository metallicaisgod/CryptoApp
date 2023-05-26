package com.example.cryptoapp.di

import com.example.cryptoapp.data.worker.ChildWorkerFactory
import com.example.cryptoapp.data.worker.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkersModule {

    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    @Binds
    fun bindRefreshDataWorkerFactory(impl: RefreshDataWorker.Factory): ChildWorkerFactory
}