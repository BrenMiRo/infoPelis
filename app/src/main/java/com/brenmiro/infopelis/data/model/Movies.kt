package com.brenmiro.infopelis.data.model

import com.google.gson.annotations.SerializedName

data class MovieSimple (
        @SerializedName("backdrop_path") var backdropPath: String,
        @SerializedName("id") var id: String,
        @SerializedName("poster_path") var posterPath: String,
        @SerializedName("title") var title: String,
        )
data class Movies (
        var results: List<MovieSimple>
)
data class MovieDetail (
        @SerializedName("adult") var adult:Boolean,
        @SerializedName("backdrop_path") var backdropPath: String,
        @SerializedName("genre_ids") var genreIds: List<Int>,
        @SerializedName("id") var id: String,
        @SerializedName("original_language") var originalLanguage: String,
        @SerializedName("original_title") var originalTitle: String,
        @SerializedName("overview") var overview: String,
        @SerializedName("poster_path") var posterPath: String,
        @SerializedName("release_date") var releaseDate: String,
        @SerializedName("title") var title: String,
        @SerializedName("vote_average") var voteAverage: Double,
        @SerializedName("vote_count") var voteCount: Int
)
