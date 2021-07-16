package com.blakdragon.petscapeclan.controllers

import com.blakdragon.petscapeclan.models.*
import com.blakdragon.petscapeclan.services.ClanMemberService
import com.blakdragon.petscapeclan.services.UserService
import com.blakdragon.petscapeclan.utils.updateClanMemberStats
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

        val clanMember = ClanMember(
            runescapeName = request.runescapeName,
            rank = request.rank,
            joinDate = request.joinDate,
            lastSeen = LocalDate.now(),
            bossKc = 0,
            pets = request.pets,
            achievements = request.achievements,
            points = 0,
            alts = request.alts
        )

        updateClanMemberStats(clanMember)

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

        clanMember.runescapeName = request.runescapeName
        clanMember.rank = request.rank
        clanMember.joinDate = request.joinDate
        clanMember.pets = request.pets
        clanMember.achievements = request.achievements
        clanMember.alts = request.alts

        updateClanMemberStats(clanMember)

        return clanMemberService.update(clanMember).toResponse()
    }

    @PutMapping("/{id}/update")
    fun pingClanMember(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ): ClanMemberResponse {
        userService.getAdminByTokenOrThrow(userToken)

        val clanMember = clanMemberService.getByIdOrThrow(id)
        updateClanMemberStats(clanMember)
        return clanMemberService.update(clanMember).toResponse()
    }

    @PutMapping("/{id}/lastSeen")
    fun updateLastSeen(
        @RequestHeader("Authorization") userToken: String,
        @PathVariable("id") id: String
    ): ClanMemberResponse {
        userService.getAdminByTokenOrThrow(userToken)

        val clanMember = clanMemberService.getByIdOrThrow(id)
        clanMember.lastSeen = LocalDate.now()
        updateClanMemberStats(clanMember)

        return clanMemberService.update(clanMember).toResponse()
    }

    @PutMapping("/lastSeen")
    fun updateLastSeenBulk(
        @RequestHeader("Authorization") userToken: String,
        @RequestBody request: List<String>
    ): List<ClanMemberResponse> {
        userService.getAdminByTokenOrThrow(userToken)

        request.forEach { id ->
            val clanMember = clanMemberService.getByIdOrThrow(id)
            clanMember.lastSeen = LocalDate.now()
            updateClanMemberStats(clanMember) //this might hit WiseOldMan too many times if their are a lot of clan members in this request
            clanMemberService.update(clanMember)
        }

        return clanMemberService.getAll().map { it.toResponse() }
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
