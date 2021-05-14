package com.blakdragon.petscapeoffline.controllers

import com.blakdragon.petscapeoffline.controllers.requests.GoogleLoginRequest
import com.blakdragon.petscapeoffline.controllers.requests.LoginRequest
import com.blakdragon.petscapeoffline.controllers.requests.RegisterRequest
import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.services.UserService
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.mindrot.jbcrypt.BCrypt
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("api/login")
class LoginController(private val userService: UserService) {

    private val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
        .setAudience(listOf("285206861319-4rssk6blmo670m0o5itqqllmf9dvuj8s.apps.googleusercontent.com"))
        .build()

    //todo email verification?
    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterRequest): UserResponse {
        val passwordHash = BCrypt.hashpw(request.password, BCrypt.gensalt(12))
        val user = userService.insert(User(
            email = request.email,
            passwordHash = passwordHash,
            displayName = request.displayName
        ))

        return UserResponse(user)
    }

    @PostMapping("/google")
    fun googleLogin(@RequestBody request: GoogleLoginRequest): UserResponse {
        val idToken = verifier.verify(request.idToken)
        val email = idToken.payload["email"] as String

        if (idToken != null) {
            var user = userService.getByEmail(email)
            if (user == null) {
                user = User(email = email)
            }

            user.token = UUID.randomUUID().toString()
            userService.insertOrUpdate(user)

            return UserResponse(user)
        } else {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }
    }

    @PostMapping()
    fun login(@RequestBody request: LoginRequest): UserResponse {
        val incorrectResponse = ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect email or password")
        val user = userService.getByEmail(request.email) ?: throw incorrectResponse

        if (!BCrypt.checkpw(request.password, user.passwordHash)) {
            throw incorrectResponse
        }

        user.token = UUID.randomUUID().toString()

        userService.update(user)

        return UserResponse(user)
    }
}
