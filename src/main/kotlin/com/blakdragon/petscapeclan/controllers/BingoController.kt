package com.blakdragon.petscapeclan.controllers

import com.blakdragon.petscapeclan.controllers.requests.CustomBingoGameRequest
import com.blakdragon.petscapeclan.controllers.responses.BingoGameIdResponse
import com.blakdragon.petscapeclan.models.BingoGame
import com.blakdragon.petscapeclan.models.BingoGameResponse
import com.blakdragon.petscapeclan.models.BingoGameType
import com.blakdragon.petscapeclan.models.BingoSquare
import com.blakdragon.petscapeclan.services.BingoService
import com.blakdragon.petscapeclan.utils.BINGO_NUM_SQUARES
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
    ): BingoGameResponse {
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

        return bingoService.insert(game).toResponse()
    }
}
