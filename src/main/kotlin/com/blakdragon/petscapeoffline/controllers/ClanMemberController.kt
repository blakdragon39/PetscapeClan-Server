package com.blakdragon.petscapeoffline.controllers

import com.blakdragon.petscapeoffline.controllers.requests.AddClanMemberAsAdminRequest
import com.blakdragon.petscapeoffline.controllers.requests.AddClanMemberRequest
import com.blakdragon.petscapeoffline.controllers.requests.UserIdRequest
import com.blakdragon.petscapeoffline.controllers.responses.UserResponse
import com.blakdragon.petscapeoffline.models.ClanMember
import com.blakdragon.petscapeoffline.models.enums.Rank
import com.blakdragon.petscapeoffline.services.ClanMemberService
import com.blakdragon.petscapeoffline.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

@Controller
@RequestMapping("api/clanMembers")
class ClanMemberController(
    private val userService: UserService,
    private val clanMemberService: ClanMemberService
) {

    @PostMapping("/admin")
    fun addClanMemberAsAdmin(
        @RequestHeader("Authorization") userToken: String,
        @RequestBody request: AddClanMemberAsAdminRequest
    ): ClanMember {
        val requestUser = userService.getByToken(userToken) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token")
        if (!requestUser.isAdmin) throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request unauthorized")

        var clanMember = ClanMember(
            userId = request.userId,
            runescapeName = request.runescapeName,
            rank = request.rank,
            joinDate = request.joinDate,
            lastActivity = request.lastActivity,
            splitsM = request.splitsM,
            infernalCape = request.infernalCape,
            pets = request.pets,
            ironMan = request.ironMan,
            ironManItems = request.ironManItems
        )

        clanMember = clanMemberService.insert(clanMember)

        val user = request.userId?.let { userService.getById(it) }
        user?.apply {
            clanMembers.add(clanMember.id!!)
            userService.update(this)
        }

        return clanMember
    }

    @PostMapping
    fun addClanMember(
        @RequestHeader("Authorization") userToken: String,
        @RequestBody request: AddClanMemberRequest
    ): UserResponse {
        var user = userService.getByToken(userToken) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token")

        var clanMember = ClanMember(
            userId = user.id!!,
            runescapeName = request.runescapeName,
            rank = Rank.Smiley,
            joinDate = LocalDate.now(),
            lastActivity = LocalDate.now(),
            splitsM = 0,
            infernalCape = false,
            pets = setOf(),
            ironMan = request.ironMan,
            ironManItems = setOf()
        )

        clanMember = clanMemberService.insert(clanMember)

        user.clanMembers.add(clanMember.id!!)
        user = userService.update(user)

        return UserResponse(user)
    }

    @GetMapping
    fun getClanMembers(@RequestBody request: UserIdRequest): List<ClanMember> {
        return clanMemberService.getByUserId(request.userId)
    }
}
