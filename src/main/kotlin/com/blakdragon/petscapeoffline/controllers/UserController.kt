package com.blakdragon.petscapeoffline.controllers

import com.blakdragon.petscapeoffline.services.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("api/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun loginUser(principal: Principal): Principal {

    }
}
