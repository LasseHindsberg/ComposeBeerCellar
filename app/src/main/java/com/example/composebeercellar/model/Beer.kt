package com.example.composebeercellar.model

import android.hardware.biometrics.BiometricManager.Strings

data class Beer(
    val id: Int,
    val user: String,
    val brewery: String,
    val name: String,
    val style: String,
    val abv: Int,
    val volume: Int,
    val pictureUrl: String,
    val howMany: Int
) {
    constructor(
        user: String,
        brewery: String,
        name: String,
        style: String,
        abv: Int,
        volume: Int,
        pictureUrl: String,
        howMany: Int
    ) : this(-1, user, brewery, name, style, abv, volume, pictureUrl, howMany)

    override fun toString(): String {
        return "$id Owner: $user, About Beer: $brewery, $name, $style, $abv, $volume, $howMany PictureUrl: $pictureUrl"
    }
}
