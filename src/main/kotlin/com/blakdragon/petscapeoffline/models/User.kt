package com.blakdragon.petscapeoffline.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User(
    @Id var id: String? = null,
    @Indexed(unique = true) var token: String? = null,
    @Indexed(unique = true) val email: String?,
    @Indexed(unique = true) val discordId: String? = null,
    val passwordHash: String? = null,
    val clanMembers: MutableList<String> = mutableListOf(),
    val displayName: String? = null,
    val isApproved: Boolean = false,
    val isAdmin: Boolean = false,
    val isSuperAdmin: Boolean = false
)
