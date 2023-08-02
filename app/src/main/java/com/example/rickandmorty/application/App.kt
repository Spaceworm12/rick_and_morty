package com.example.rickandmorty.application

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomMasterTable
import com.example.rickandmorty.data.db.ExampleDao
import com.example.rickandmorty.data.db.ExampleDataBase
import com.example.rickandmorty.data.network.RickAndMortyApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://rickandmortyapi.com/api"
class App : Application() {

    override fun onCreate(){
        super.onCreate()
        appInstance = this
    }
    companion object {

        private var appInstance: App? = null
        private var db: ExampleDataBase? = null
        private var sharedPreferences: SharedPreferences? = null
        private var api: RickAndMortyApi? = null

        fun getExampleDao(): ExampleDao {
            checkDb()
            return db!!.exampleDao()
        }

        private fun checkDb() {
            if (db == null) {
                val builder = Room.databaseBuilder(
                    appInstance!!.applicationContext,
                    ExampleDataBase::class.java,
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
            return api
                ?: Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                    .create(RickAndMortyApi::class.java)
        }
    }
}