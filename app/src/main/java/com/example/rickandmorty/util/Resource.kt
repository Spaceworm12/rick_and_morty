package com.example.rickandmorty.util

import android.content.Context
import android.widget.Toast


sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Data<T>(val data: T) : Resource<T>()
    data class Error(val error: Throwable) : Resource<Nothing>()

    companion object {
        val Success: Resource<Unit> = Data(Unit)
    }
}


fun String.showToast(context: Context) {
    Toast.makeText(
        context,
        this,
        Toast.LENGTH_SHORT
    ).show()
}
