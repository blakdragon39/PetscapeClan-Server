package com.blakdragon.petscapeclan.models.enums

enum class Rank(
    val label: String,
    val order: Int,
    val points: Int
) {
    Bronze(
        label = "Bronze",
        order = 0,
        points = 1
    ),
    Iron(
        label = "Iron",
        order = 1,
        points = 10
    ),
    Steel(
        label = "Steel",
        order = 2,
        points = 20
    ),
    Gold(
        label = "Gold",
        order = 3,
        points = 35
    ),
    Mithril(
        label = "Mithril",
        order = 4,
        points = 45
    ),
    Adamant(
        label = "Adamant",
        order = 5,
        points = 55
    ),
    Rune(
        label = "Rune",
        order = 6,
        points = 70
    ),
    Dragon(
        label = "Dragon",
        order = 7,
        points = 85
    ),
    Admin(
        label = "Admin",
        order = 8,
        points = Int.MAX_VALUE
    ),
    DeputyOwner(
        label = "Deputy Owner",
        order = 9,
        points = Int.MAX_VALUE
    ),
    Owner(
        label = "Owner",
        order = 10,
        points = Int.MAX_VALUE
    );

    fun toResponse(): RankResponse = RankResponse(this)
}

class RankResponse(
    val rank: Rank
) {
    val label = rank.label
    val order = rank.order
    val points = rank.points
}
