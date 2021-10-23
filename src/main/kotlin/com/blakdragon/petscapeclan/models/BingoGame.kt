package com.blakdragon.petscapeclan.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

val WINNING_LINES = listOf(
    listOf(0, 1 , 2, 3, 4),
    listOf(5, 6, 7, 8, 9),
    listOf(10, 11, 12, 13, 14),
    listOf(15, 16, 17, 18, 19),
    listOf(20, 21, 22, 23, 24),
    listOf(0, 5, 10, 15, 20),
    listOf(1, 6, 11, 16, 21),
    listOf(2, 7, 12, 17, 22),
    listOf(3, 8, 13, 18, 23),
    listOf(4, 9, 14, 19, 24),
    listOf(0, 6, 12, 18, 24),
    listOf(4, 8, 12, 16, 20)
)

@Document
class BingoGame(
    @Id var id: String? = null,
    var name: String,
    var type: BingoGameType,
    var freeSpace: Boolean,
    var blackout: Boolean,
    var cards: MutableList<BingoCard> = mutableListOf(),
) {

    var parentCard: List<BingoSquare>? = null //if it exists, all new cards use this as a template

    fun winners(): List<BingoCard> {
        return cards.filter { it.isWinner(blackout) }
    }

    fun toResponse(): BingoGameResponse = BingoGameResponse(this)
}

@Suppress("unused")
class BingoGameResponse(bingoGame: BingoGame) {
    val id: String = bingoGame.id.toString()
    val name: String = bingoGame.name
    val cards: List<BingoCardResponse> = bingoGame.cards.map { it.toResponse() }
    val type: BingoGameType = bingoGame.type
    val freeSpace: Boolean = bingoGame.freeSpace
    val parentCard: List<BingoSquareResponse>? = bingoGame.parentCard?.map { it.toResponse() }
}

@Document
class BingoCard(
    @Id var id: String? = null,
    var username: String,
    var squares: List<BingoSquare>
) {

    fun isWinner(blackout: Boolean): Boolean {
        return if (blackout) {
            squares.all { it.completed }
        } else {
            WINNING_LINES.any { inds ->
                inds.all { i -> squares[i].completed }
            }
        }
    }

    fun toResponse(): BingoCardResponse = BingoCardResponse(this)
}

@Suppress("unused")
class BingoCardResponse(bingoCard: BingoCard) {
    val id: String = bingoCard.id.toString()
    val username: String = bingoCard.username
    var squares: List<BingoSquareResponse>? = bingoCard.squares.map { it.toResponse() }
}

@Document
class BingoSquare {
    companion object {
        val FreeSquare = BingoSquare().apply {
            completed = true
        }
    }

    @Id var id: String? = null
//    var boss: Boss? = null
//    var item: Drop? = null
    var task: String? = null
    var completed = false

    fun toResponse(): BingoSquareResponse = BingoSquareResponse(this)
}

@Suppress("unused")
class BingoSquareResponse(bingoSquare: BingoSquare) {
    val id: String = bingoSquare.id.toString()
//    val boss: BossModel? = bingoSquare.boss?.toModel()
//    val item: DropModel? = bingoSquare.item?.toModel()
    val task: String? = bingoSquare.task
    val completed: Boolean = bingoSquare.completed
}

enum class BingoGameType { BOSSES, ITEMS, COMBINED, OTHER }

class CustomSquare(
    val boss: String? = null,
    val item: String? = null,
    val task: String? = null
)
