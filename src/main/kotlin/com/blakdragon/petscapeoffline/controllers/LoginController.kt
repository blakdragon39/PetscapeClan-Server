package com.blakdragon.petscapeoffline.controllers

import com.blakdragon.petscapeoffline.controllers.requests.GoogleLoginRequest
import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.services.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/login")
class LoginController(private val userService: UserService) {

    @PostMapping("/google")
    fun login(@RequestBody request: GoogleLoginRequest): User {
//        val user = userService.getById(firebaseToken.uid) ?: User(firebaseToken.uid).apply {
//            token = UUID.randomUUID().toString()
//            displayName = firebaseToken.name
//        }
//
//        return userService.insertOrUpdate(user)
        return User(id = request.idToken, displayName = "Blakdragon39 Obvs")
    }
}
