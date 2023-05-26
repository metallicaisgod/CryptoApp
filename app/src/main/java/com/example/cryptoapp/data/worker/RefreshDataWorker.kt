package com.example.cryptoapp.data.worker

import android.content.Context
import androidx.work.*
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService,
    private val coinMapper: CoinMapper
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val coinNames = apiService.getTopCoinsInfo(limit = 50)
                val coinNamesString = coinMapper.mapNamesListToString(coinNames)
                val jsonContainer = apiService.getFullPriceList(fSyms = coinNamesString)
                val coinList = coinMapper.mapJsonContainerToCoinInfoList(jsonContainer)
                coinInfoDao.insertPriceList(
                    coinList.map {
                        coinMapper.mapDtoToDbModel(it)
                    }
                )
            } catch (_: Exception) {
            }
            delay(10_000)
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }

    class Factory @Inject constructor(
        private val coinInfoDao: CoinInfoDao,
        private val apiService: ApiService,
        private val coinMapper: CoinMapper
    ) : ChildWorkerFactory {

        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefreshDataWorker(
                context,
                workerParameters,
                coinInfoDao,
                apiService,
                coinMapper
            )
        }
    }

}