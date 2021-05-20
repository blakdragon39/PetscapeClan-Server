package com.blakdragon.petscapeclan.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User(
    @Id var id: String? = null,
    @Indexed(unique = true) var token: String? = null,
    @Indexed(unique = true) val email: String?,
    val passwordHash: String? = null,
    val displayName: String? = null,
    val isAdmin: Boolean = false,
)
