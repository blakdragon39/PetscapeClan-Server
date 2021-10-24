package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.controllers.responses.BingoGameIdResponse
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

    fun toIdResponse(): BingoGameIdResponse = BingoGameIdResponse(id, name)
}

class BingoCard(
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
}

class BingoSquare {
    companion object {
        val FreeSquare = BingoSquare().apply {
            completed = true
        }
    }

//    var boss: Boss? = null
//    var item: Drop? = null
    var task: String? = null
    var completed = false
}

enum class BingoGameType { BOSSES, ITEMS, COMBINED, OTHER }

class CustomSquare(
    val boss: String? = null,
    val item: String? = null,
    val task: String? = null
)
