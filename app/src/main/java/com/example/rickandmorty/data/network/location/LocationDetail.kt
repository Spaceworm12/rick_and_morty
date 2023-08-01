package com.example.rickandmorty.data.network.location

import com.example.rickandmorty.data.network.character.Character
import com.google.gson.annotations.SerializedName

data class LocationDetail(
    @SerializedName("base_info")
    val type: String,
    val dimension: String,
    val id: Int,
    @SerializedName("additional_info")
    val residents: Array<Character>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocationDetail

        if (type != other.type) return false
        if (dimension != other.dimension) return false
        if (!residents.contentEquals(other.residents)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + dimension.hashCode()
        result = 31 * result + residents.contentHashCode()
        return result
    }
}

