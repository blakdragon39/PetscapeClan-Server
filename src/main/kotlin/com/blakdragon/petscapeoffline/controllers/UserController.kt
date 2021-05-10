package com.blakdragon.petscapeoffline.controllers

import com.blakdragon.petscapeoffline.controllers.requests.LoginRequest
import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.services.UserService
import com.google.firebase.auth.FirebaseAuth
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("api/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun login(@RequestBody request: LoginRequest): User {
        val firebaseToken = FirebaseAuth.getInstance().verifyIdToken(request.firebaseToken)

        val user = userService.getById(firebaseToken.uid) ?: User(firebaseToken.uid).apply {
            token = UUID.randomUUID().toString()
            displayName = firebaseToken.name
        }

        return userService.insertOrUpdate(user)
    }
}
