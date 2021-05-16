package com.blakdragon.petscapeoffline.services

import com.blakdragon.petscapeoffline.models.ClanMember
import com.blakdragon.petscapeoffline.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDAO : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
    fun findByToken(token: String): User?
}

interface ClanMemberDAO : MongoRepository<ClanMember, String>
