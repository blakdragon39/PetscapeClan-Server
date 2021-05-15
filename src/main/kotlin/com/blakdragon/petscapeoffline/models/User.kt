package com.blakdragon.petscapeoffline.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User(
    @Id var id: String? = null,
    @Indexed(unique = true) var token: String? = null,
    @Indexed(unique = true) val email: String?,
    val passwordHash: String? = null,
    val clanMembers: List<String> = listOf(),
    val displayName: String? = null,
    val approved: Boolean = false,
    val admin: Boolean = false,
    val superAdmin: Boolean = false
)
