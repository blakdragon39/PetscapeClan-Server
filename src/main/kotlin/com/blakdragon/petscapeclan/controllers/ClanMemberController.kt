package com.blakdragon.petscapeclan.controllers

import com.blakdragon.petscapeclan.models.*
import com.blakdragon.petscapeclan.services.ClanMemberService
import com.blakdragon.petscapeclan.services.UserService
import com.blakdragon.petscapeclan.utils.getWiseOldMan
import com.blakdragon.petscapeclan.utils.getPossiblePoints
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

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
            lastSeen = LocalDate.now(),
            bossKc = wiseOldMan?.totalBossKc() ?: 0,
            pets = request.pets,
            achievements = request.achievements,
            points = getPossiblePoints(wiseOldMan, request.joinDate, request.pets, request.achievements),
            alts = request.alts
        )

        return clanMemberService.insert(clanMember)
    }

    @GetMapping
    fun getClanMembers(): List<ClanMember> {
        return clanMemberService.getAll()
    }

    @GetMapping("/{id}")
    fun getClanMember(
        @PathVariable("id") id: String
    ): ClanMember {
        return clanMemberService.getByIdOrThrow(id)
    }

    @GetMapping("/runescapeName/{runescapeName}")
    fun getClanMemberByRunescapeName(
        @PathVariable("runescapeName") runescapeName: String
    ): ClanMember {
        val result = clanMemberService.getByRunescapeName(runescapeName)
        return if (result.isNotEmpty()) result[0] else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No clan member found with that name")
    }

    @PutMapping
    fun updateClanMember(
        @RequestHeader("Authorization") userToken: String,
        @RequestBody request: ClanMemberRequest
    ): ClanMember {
        if (request.id == null) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Clan member not found")
        val clanMember = clanMemberService.getByIdOrThrow(request.id)

        val wiseOldManPlayer = getWiseOldMan(request.runescapeName)

        clanMember.runescapeName = request.runescapeName
        clanMember.rank = request.rank
        clanMember.joinDate = request.joinDate
        clanMember.pets = request.pets
        clanMember.achievements = request.achievements
        clanMember.bossKc = wiseOldManPlayer?.totalBossKc() ?: 0
        clanMember.points = getPossiblePoints(wiseOldManPlayer, request.joinDate, request.pets, request.achievements)
        clanMember.alts = request.alts

        return clanMemberService.update(clanMember)
    }

    @PutMapping("/{id}")
    fun pingClanMember(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ): ClanMember {
        val clanMember = clanMemberService.getByIdOrThrow(id)
        val wiseOldManPlayer = getWiseOldMan(clanMember.runescapeName)

        clanMember.bossKc = wiseOldManPlayer?.totalBossKc() ?: 0
        clanMember.points = getPossiblePoints(wiseOldManPlayer, clanMember.joinDate, clanMember.pets, clanMember.achievements)

        return clanMemberService.update(clanMember)
    }

    @PutMapping("/{id}/lastSeen")
    fun updateLastSeen(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ): ClanMember {
        val clanMember = clanMemberService.getByIdOrThrow(id)
        val wiseOldManPlayer = getWiseOldMan(clanMember.runescapeName)

        clanMember.bossKc = wiseOldManPlayer?.totalBossKc() ?: 0
        clanMember.points = getPossiblePoints(wiseOldManPlayer, clanMember.joinDate, clanMember.pets, clanMember.achievements)
        clanMember.lastSeen = LocalDate.now()

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
