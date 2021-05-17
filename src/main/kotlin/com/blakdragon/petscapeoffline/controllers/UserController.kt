package com.blakdragon.petscapeoffline.controllers

import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.services.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getUser(): List<User> {
        return userService.getAll()
    }
}
