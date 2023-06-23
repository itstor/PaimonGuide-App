package com.itstor.paimonguide.domain.model

import com.google.gson.annotations.SerializedName

data class Character(
    val id: String,
    val name: String,
    val description: String,
    val weapon: Weapon,
    val rarity: Rarity,
    val vision: Vision,
    val stats: Stats,
    val burst: Burst,
    val normalAttack: Attack,
    val elementalSkill: ElementalSkill
)

enum class Weapon {
    @SerializedName("Sword")
    SWORD,
    @SerializedName("Bow")
    BOW,
    @SerializedName("Catalyst")
    CATALYST,
    @SerializedName("Claymore")
    CLAYMORE,
    @SerializedName("Polearm")
    POLEARM
}

enum class Rarity {
    @SerializedName("4")
    FOUR,
    @SerializedName("5")
    FIVE,
}

enum class Vision {
    @SerializedName("Anemo")
    ANEMO,
    @SerializedName("Geo")
    GEO,
    @SerializedName("Electro")
    ELECTRO,
    @SerializedName("Pyro")
    PYRO,
    @SerializedName("Cryo")
    CRYO,
    @SerializedName("Hydro")
    HYDRO,
    @SerializedName("Dendro")
    DENDRO
}

