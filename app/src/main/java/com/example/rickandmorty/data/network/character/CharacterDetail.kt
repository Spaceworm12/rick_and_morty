package com.example.rickandmorty.data.network.character

import com.google.gson.annotations.SerializedName

data class CharacterDetail(
    @SerializedName("base_info")
    val status: String,
    val species: String,
    val type: String,
    val gender:String,
    val id: Int,
    @SerializedName("additional_info")
    val location: Any,
    val episode: Array<Any>,
    val origin: Any,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharacterDetail

        if (status != other.status) return false
        if (species != other.species) return false
        if (type != other.type) return false
        if (gender != other.gender) return false
        if (location != other.location) return false
        if (!episode.contentEquals(other.episode)) return false
        if (origin != other.origin) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + species.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + episode.contentHashCode()
        result = 31 * result + origin.hashCode()
        return result
    }
}
