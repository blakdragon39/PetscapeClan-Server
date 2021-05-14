package com.blakdragon.petscapeoffline.controllers

import com.blakdragon.petscapeoffline.controllers.requests.GoogleLoginRequest
import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.services.UserService
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.apache.http.client.HttpResponseException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/login")
class LoginController(private val userService: UserService) {

    private val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
        .setAudience(listOf("285206861319-4rssk6blmo670m0o5itqqllmf9dvuj8s.apps.googleusercontent.com"))
        .build()

    @PostMapping("/google")
    fun login(@RequestBody request: GoogleLoginRequest): User {
        val idToken = verifier.verify(request.idToken)
        if (idToken != null) {
            return User(
                id = "1",
                token = "sometoken",
                displayName = idToken.payload["name"] as String
            )
        } else {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }
    }
}
