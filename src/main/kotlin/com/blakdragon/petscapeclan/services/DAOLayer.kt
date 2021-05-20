package com.blakdragon.petscapeclan.services

import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDAO : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
    fun findByToken(token: String): User?
}

interface ClanMemberDAO : MongoRepository<ClanMember, String>
