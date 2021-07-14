package com.blakdragon.petscapeclan.models.enums

enum class Rank(
    val label: String,
    val order: Int
) {
    Bronze(
        label = "Bronze",
        order = 0
    ),
    Iron(
        label = "Iron",
        order = 1
    ),
    Steel(
        label = "Steel",
        order = 2
    ),
    Gold(
        label = "Gold",
        order = 3
    ),
    Mithril(
        label = "Mithril",
        order = 4
    ),
    Adamant(
        label = "Adamant",
        order = 5
    ),
    Rune(
        label = "Rune",
        order = 6
    ),
    Dragon(
        label = "Dragon",
        order = 7
    ),
    Admin(
        label = "Admin",
        order = 8
    ),
    DeputyOwner(
        label = "Deputy Owner",
        order = 9
    ),
    Owner(
        label = "Owner",
        order = 10
    );

    fun toResponse(): RankResponse = RankResponse(this)
}

class RankResponse(
    val rank: Rank
) {
    val label = rank.label
    val order = rank.order
}
