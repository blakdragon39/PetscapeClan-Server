package com.blakdragon.petscapeclan.controllers

import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.ClanMemberPossiblePoints
import com.blakdragon.petscapeclan.services.ClanMemberService
import com.blakdragon.petscapeclan.services.UserService
import com.blakdragon.petscapeclan.utils.possiblePoints
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/clanMembers")
class ClanMemberController(
    private val userService: UserService,
    private val clanMemberService: ClanMemberService
) {

    @GetMapping("/{id}/checkup")
    fun checkClanMember(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ): ClanMemberPossiblePoints {
        val clanMember = clanMemberService.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Clan member not found")
        val possiblePoints = clanMember.possiblePoints()

        return ClanMemberPossiblePoints(clanMember, possiblePoints)
    }

    @PostMapping
    fun addClanMember(
        @RequestHeader("Authorization") userToken: String,
        @RequestBody request: ClanMember
    ): ClanMember {
        val requestUser = userService.getByToken(userToken) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token")
        if (!requestUser.isAdmin) throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request unauthorized")

        return clanMemberService.insert(request)
    }

    @GetMapping
    fun getClanMembers(@RequestHeader("Authorization") userToken: String): List<ClanMember> {
        return clanMemberService.getAll()
    }

    @GetMapping("/{id}")
    fun getClanMember(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ): ClanMember {
        return clanMemberService.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Clan member not found")
    }

    @PutMapping
    fun updateClanMember(
        @RequestHeader("Authorization") userToken: String,
        @RequestBody request: ClanMember
    ): ClanMember {
        return clanMemberService.update(request)
    }

    @DeleteMapping("/{id}")
    fun deleteClanMember(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ) {
        clanMemberService.deleteById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Clan member not found")
    }
}
