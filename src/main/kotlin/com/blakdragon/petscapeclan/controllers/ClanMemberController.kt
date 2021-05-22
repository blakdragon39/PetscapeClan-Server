package com.blakdragon.petscapeclan.controllers

import com.blakdragon.petscapeclan.models.*
import com.blakdragon.petscapeclan.services.ClanMemberService
import com.blakdragon.petscapeclan.services.UserService
import com.blakdragon.petscapeclan.utils.getWiseOldMan
import com.blakdragon.petscapeclan.utils.getPossiblePoints
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/clanMembers")
class ClanMemberController(
    private val userService: UserService,
    private val clanMemberService: ClanMemberService
) {

    @PostMapping
    fun addClanMember(
        @RequestHeader("Authorization") userToken: String,
        @RequestBody request: ClanMemberRequest
    ): ClanMember {
        val requestUser = userService.getByToken(userToken) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token")
        if (!requestUser.isAdmin) throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request unauthorized")

        val wiseOldMan = getWiseOldMan(request.runescapeName)

        val clanMember = ClanMember(
            runescapeName = request.runescapeName,
            rank = request.rank,
            joinDate = request.joinDate,
            bossKc = wiseOldMan?.totalBossKc() ?: 0,
            pets = request.pets,
            achievements = request.achievements,
            points = getPossiblePoints(wiseOldMan, request.joinDate, request.pets, request.achievements)
        )

        return clanMemberService.insert(clanMember)
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
        @RequestBody request: ClanMemberRequest
    ): ClanMember {
        if (request.id == null) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Clan member not found")
        val clanMember = clanMemberService.getById(request.id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Clan member not found")

        val wiseOldManPlayer = getWiseOldMan(request.runescapeName)

        clanMember.runescapeName = request.runescapeName
        clanMember.rank = request.rank
        clanMember.joinDate = request.joinDate
        clanMember.pets = request.pets
        clanMember.achievements = request.achievements
        clanMember.bossKc = wiseOldManPlayer?.totalBossKc() ?: 0
        clanMember.points = getPossiblePoints(wiseOldManPlayer, request.joinDate, request.pets, request.achievements)

        return clanMemberService.update(clanMember)
    }

    @DeleteMapping("/{id}")
    fun deleteClanMember(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ) {
        clanMemberService.deleteById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Clan member not found")
    }
}
