package com.blakdragon.petscapeclan.controllers

import com.blakdragon.petscapeclan.controllers.requests.CustomBingoGameRequest
import com.blakdragon.petscapeclan.controllers.requests.SquareRequest
import com.blakdragon.petscapeclan.controllers.requests.UsernameRequest
import com.blakdragon.petscapeclan.controllers.responses.BingoGameIdResponse
import com.blakdragon.petscapeclan.models.*
import com.blakdragon.petscapeclan.services.BingoService
import com.blakdragon.petscapeclan.utils.BINGO_NUM_SQUARES
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/bingo")
class BingoController(
    private val bingoService: BingoService
) {

    @GetMapping
    fun getAllBingoGames(): List<BingoGameIdResponse> {
        return bingoService.getAll().map { it.toIdResponse() }
    }

    @PostMapping("/new_custom_game")
    fun createCustomBingoGame(
        @RequestBody request: CustomBingoGameRequest
    ): BingoGame {
        if (request.squares.size != BINGO_NUM_SQUARES) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Must provide 25 squares")
        }

        val game = BingoGame(
            name = request.name,
            type = BingoGameType.OTHER,
            freeSpace = false,
            blackout = request.blackout
        )

//        val bosses = Boss.values().toList()
//        val items = getItems(bosses)

        game.parentCard = request.squares.map { customSquare ->
            val square = BingoSquare()
//            square.boss = bosses.find { it.displayName == customSquare.boss }
//            square.item = items.find { it.item == customSquare.item }
            square.task = customSquare.task

            if (square.task != null) {
//            if (square.boss != null || square.item != null || square.task != null) {
                square
            } else {
                BingoSquare.FreeSquare
            }
        }

        return bingoService.insert(game)
    }

    @PostMapping("/{id}")
    fun addBingoCard(
        @PathVariable("id") gameId: String,
        @RequestBody request: UsernameRequest
    ) : BingoCard {
        val game = bingoService.getByIdOrThrow(gameId)

        if (game.cards.any { it.username.equals(request.username, ignoreCase = true) }) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Card already exists for that user in this game")
        }

        val card = BingoCard(
            username = request.username,
            squares = game.parentCard ?: emptyList() //todo BingoUtils.generateSquares()
        )

        game.cards.add(card)
        bingoService.update(game)

        return card
    }

    @PostMapping("/{id}/complete")
    fun completeSquare(
        @PathVariable("id") gameId: String,
        @RequestBody request: SquareRequest
    ): BingoCard {
        val game = bingoService.getByIdOrThrow(gameId)
        val card = game.cards.find { it.username == request.username } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found")
        val square =  try { card.squares[request.index - 1] } catch (e: IndexOutOfBoundsException) { throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid square index") }
        square.completed = true

        bingoService.update(game) //todo test this actually updates the square?
        return card
    }

    @PostMapping("/{id}/uncomplete")
    fun uncompleteSquare(
        @PathVariable("id") gameId: String,
        @RequestBody request: SquareRequest
    ): BingoCard {
        val game = bingoService.getByIdOrThrow(gameId)
        val card = game.cards.find { it.username == request.username } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found")
        val square =  try { card.squares[request.index - 1] } catch (e: IndexOutOfBoundsException) { throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid square index") }
        square.completed = false

        bingoService.update(game) //todo test this actually updates the square?
        return card
    }

    @GetMapping("/{id}/players")
    fun listAllPlayers(@PathVariable("id") gameId: String): List<String> {
        return bingoService.getByIdOrThrow(gameId).cards.map { it.username }
    }
}
