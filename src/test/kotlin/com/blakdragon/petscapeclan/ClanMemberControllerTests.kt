package com.blakdragon.petscapeclan

import com.blakdragon.petscapeclan.controllers.ClanMemberController
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.enums.Rank
import com.blakdragon.petscapeclan.services.ClanMemberDAO
import com.blakdragon.petscapeclan.services.UserDAO
import com.blakdragon.petscapeclan.services.UserService
import com.blakdragon.petscapeclan.utils.TestUserLogins
import com.blakdragon.petscapeclan.utils.generateRandomString
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import kotlin.test.assertEquals


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClanMemberControllerTests {

    @Autowired private lateinit var clanMemberController: ClanMemberController
    @Autowired private lateinit var userService: UserService
    @Autowired private lateinit var clanMemberDAO: ClanMemberDAO
    @Autowired private lateinit var userDAO: UserDAO

    private val testUserLogins: TestUserLogins by lazy { TestUserLogins(userService) }

    @AfterEach
    fun afterEach() {
        clanMemberDAO.deleteAll()
    }

    @AfterAll
    fun afterAll() {
        userDAO.deleteAll()
    }

    @Test
    fun addClanMemberInvalidToken() {
        assertThrows<ResponseStatusException> {
            clanMemberController.addClanMember("invalidToken", clanMemberAsAdminRequest())
        }
    }

    @Test
    fun addClanMemberUnauthorized() {
        assertThrows<ResponseStatusException> {
            clanMemberController.addClanMember(testUserLogins.user1.token!!, clanMemberAsAdminRequest())
        }
    }

    @Test
    fun addClanMember() {
        clanMemberController.addClanMember(testUserLogins.admin.token!!, clanMemberAsAdminRequest())
        assertEquals(1, clanMemberDAO.findAll().size)

        clanMemberController.addClanMember(testUserLogins.admin.token!!, clanMemberAsAdminRequest())
        assertEquals(2, clanMemberDAO.findAll().size)
    }

    private fun clanMemberAsAdminRequest(): ClanMember {
        return ClanMember(
            runescapeName = generateRandomString(12),
            rank = Rank.Bronze,
            joinDate = LocalDate.now(),
            infernalCape = false,
        )
    }
}
