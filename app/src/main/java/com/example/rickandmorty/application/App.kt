package com.example.rickandmorty.application

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomMasterTable
import com.example.rickandmorty.data.db.Dao
import com.example.rickandmorty.data.db.Db
import com.example.rickandmorty.data.network.networkrepo.RickAndMortyApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://rickandmortyapi.com/api/"

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: Db? = null
        private var sharedPreferences: SharedPreferences? = null
        private var api: RickAndMortyApi? = null

        fun dao(): Dao {
            checkDb()
            return db!!.exampleDao()
        }
        fun getDb(): Db {
            checkDb()
            return db!!
        }

        private fun checkDb() {
            if (db == null) {
                val builder = Room.databaseBuilder(
                    appInstance!!.applicationContext,
                    Db::class.java,
                    RoomMasterTable.TABLE_NAME
                )
                db = builder
                    .allowMainThreadQueries()
                    .build()
            }
        }

        fun getSettings(): SharedPreferences {
            if (sharedPreferences == null) {
                sharedPreferences =
                    appInstance!!.applicationContext.getSharedPreferences("THEME", MODE_PRIVATE)
            }
            return sharedPreferences!!
        }

        fun getRickAndMortyApi(): RickAndMortyApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level= HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return api
                ?: Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL).client(client)
                    .build()
                    .create(RickAndMortyApi::class.java)
        }
    }
}