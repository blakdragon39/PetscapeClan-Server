package com.blakdragon.petscapeoffline.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User(
    @Id val id: String? = null,
    val clanMembers: List<String>,
    val displayName: String,
    val token: String? = null,
    val admin: Boolean = false,
    val superAdmin: Boolean = false
)
