package com.blakdragon.petscapeclan.services

import com.blakdragon.petscapeclan.models.BingoCard
import com.blakdragon.petscapeclan.models.BingoGame
import com.blakdragon.petscapeclan.utils.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class BingoService(private val bingoGameDAO: BingoGameDAO) : BasicCrud<String, BingoGame> {

    override fun getAll(): List<BingoGame> = bingoGameDAO.findAll()

    override fun getAll(pageable: Pageable): Page<BingoGame> = bingoGameDAO.findAll(pageable)

    override fun getById(id: String): BingoGame? = bingoGameDAO.findByIdOrNull(id)

    override fun insert(obj: BingoGame): BingoGame = bingoGameDAO.insert(obj)

    override fun update(obj: BingoGame): BingoGame {
        val id = obj.id
        if (id != null && bingoGameDAO.existsById(id)) {
            return bingoGameDAO.save(obj)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Bingo game not found")
        }
    }

    override fun deleteById(id: String): BingoGame? {
        return bingoGameDAO.findByIdOrNull(id)?.apply {
            bingoGameDAO.delete(this)
        }
    }

    fun getByIdOrThrow(id: String): BingoGame =
        bingoGameDAO.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Bingo game not found")

    fun getByIdAndUsernameOrThrow(id: String, username: String): BingoCard {
        val game = getByIdOrThrow(id)
        return game.cards.firstOrNull { it.username.equals(username, ignoreCase = true) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No card found for that username")
    }
}


