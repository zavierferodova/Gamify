package com.zavierdev.gamify.core.di

import androidx.room.Room
import com.zavierdev.gamify.core.BuildConfig
import com.zavierdev.gamify.core.data.GameRepository
import com.zavierdev.gamify.core.data.source.local.LocalDataSource
import com.zavierdev.gamify.core.data.source.local.room.GameDatabase
import com.zavierdev.gamify.core.data.source.remote.RemoteDataSource
import com.zavierdev.gamify.core.data.source.remote.network.ApiConfig
import com.zavierdev.gamify.core.data.source.remote.network.ApiService
import com.zavierdev.gamify.core.domain.repository.IGameRepository
import com.zavierdev.gamify.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<GameDatabase>().gameDao()
    }
    single {
        val dbKey = BuildConfig.DB_KEY
        val passphrase: ByteArray = SQLiteDatabase.getBytes(dbKey.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            GameDatabase::class.java, "Game.db",
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = ApiConfig.HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/RI9CUmPUOpUk2vdVMSZDWj+wtoQO5k9MSCSM9w4grmU=")
            .add(hostname, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .add(hostname, "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IGameRepository> { GameRepository(get(), get(), get()) }
}