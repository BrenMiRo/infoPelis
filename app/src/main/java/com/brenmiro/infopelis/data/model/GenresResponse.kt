package com.brenmiro.infopelis.data.model

data class Genre (
    var id: String,
    var name: String
)

data class Genres (
    var results: List<Genre>
)