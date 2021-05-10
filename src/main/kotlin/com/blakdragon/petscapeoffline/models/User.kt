package com.blakdragon.petscapeoffline.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User(
    @Id val id: String? = null,
    @Indexed(unique = true) var token: String? = null,
    var clanMembers: List<String> = listOf(),
    var displayName: String? = null,
    var approved: Boolean = false,
    var admin: Boolean = false,
    var superAdmin: Boolean = false
)
