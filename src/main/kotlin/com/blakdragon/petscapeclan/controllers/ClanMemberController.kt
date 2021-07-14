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
    ): ClanMemberResponse {
        userService.getAdminByTokenOrThrow(userToken)

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

        return clanMemberService.insert(clanMember).toResponse()
    }

    @GetMapping
    fun getClanMembers(): List<ClanMemberResponse> {
        return clanMemberService.getAll().map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun getClanMember(
        @PathVariable("id") id: String
    ): ClanMemberResponse {
        return clanMemberService.getByIdOrThrow(id).toResponse()
    }

    @GetMapping("/runescapeName")
    fun getClanMemberByRunescapeName(
        @RequestParam("runescapeName") runescapeName: String
    ): ClanMemberResponse {
        val result = clanMemberService.getByRunescapeName(runescapeName)
        if (result.isEmpty()) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No clan member found with that name")
        return result[0].toResponse()
    }

    @PutMapping
    fun updateClanMember(
        @RequestHeader("Authorization") userToken: String,
        @RequestBody request: ClanMemberRequest
    ): ClanMemberResponse {
        userService.getAdminByTokenOrThrow(userToken)

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

        return clanMemberService.update(clanMember).toResponse()
    }

    @PutMapping("/{id}/update")
    fun pingClanMember(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ): ClanMemberResponse {
        userService.getAdminByTokenOrThrow(userToken)

        val clanMember = clanMemberService.getByIdOrThrow(id)
        val wiseOldManPlayer = getWiseOldMan(clanMember.runescapeName)

        clanMember.bossKc = wiseOldManPlayer?.totalBossKc() ?: 0
        clanMember.points = getPossiblePoints(wiseOldManPlayer, clanMember.joinDate, clanMember.pets, clanMember.achievements)

        return clanMemberService.update(clanMember).toResponse()
    }

    @PutMapping("/{id}/lastSeen")
    fun updateLastSeen(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ): ClanMemberResponse {
        userService.getAdminByTokenOrThrow(userToken)

        val clanMember = clanMemberService.getByIdOrThrow(id)
        val wiseOldManPlayer = getWiseOldMan(clanMember.runescapeName)

        clanMember.bossKc = wiseOldManPlayer?.totalBossKc() ?: 0
        clanMember.points = getPossiblePoints(wiseOldManPlayer, clanMember.joinDate, clanMember.pets, clanMember.achievements)
        clanMember.lastSeen = LocalDate.now()

        return clanMemberService.update(clanMember).toResponse()
    }

    @DeleteMapping("/{id}")
    fun deleteClanMember(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ) {
        userService.getAdminByTokenOrThrow(userToken)
        clanMemberService.deleteById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Clan member not found")
    }
}
