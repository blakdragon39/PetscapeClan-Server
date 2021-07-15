package com.blakdragon.petscapeclan.utils

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RunescapeTests {

    @Test
    fun expToLevelTests() {
        assertEquals(0.expToLevel(), 1)
        assertEquals(1.expToLevel(), 1)
        assertEquals(83.expToLevel(), 2)
        assertEquals(1000.expToLevel(), 9)
        assertEquals(13_034_430.expToLevel(), 98)
        assertEquals(13_034_431.expToLevel(), 99)
        assertEquals(13_034_432.expToLevel(), 99)
    }
}
