package com.blakdragon.petscapeclan.utils


/**
 * https://stackoverflow.com/a/54400933/1284244
 */
fun generateRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}
