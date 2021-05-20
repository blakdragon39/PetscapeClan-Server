package com.blakdragon.petscapeclan

import com.blakdragon.petscapeclan.controllers.ClanMemberController
import com.blakdragon.petscapeclan.controllers.requests.AddClanMemberAsAdminRequest
import com.blakdragon.petscapeclan.controllers.requests.AddClanMemberRequest
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
    fun addClanMemberAsAdminInvalidToken() {
        assertThrows<ResponseStatusException> {
            clanMemberController.addClanMemberAsAdmin("invalidToken", clanMemberAsAdminRequest(testUserLogins.user1.id!!))
        }
    }

    @Test
    fun addClanMemberAsAdminUserNotFound() {
        assertThrows<ResponseStatusException> {
            clanMemberController.addClanMemberAsAdmin(testUserLogins.admin.token!!, clanMemberAsAdminRequest("invalidId"))
        }
    }

    @Test
    fun addClanMemberAsAdminUnauthorized() {
        assertThrows<ResponseStatusException> {
            clanMemberController.addClanMemberAsAdmin(testUserLogins.user1.token!!, clanMemberAsAdminRequest(testUserLogins.user1.id!!))
        }
    }

    @Test
    fun addClanMemberAsAdmin() {
        clanMemberController.addClanMemberAsAdmin(testUserLogins.admin.token!!, clanMemberAsAdminRequest(testUserLogins.user1.id!!))
        assertEquals(1, clanMemberDAO.findAll().size)

        clanMemberController.addClanMemberAsAdmin(testUserLogins.admin.token!!, clanMemberAsAdminRequest(testUserLogins.user1.id!!))
        assertEquals(2, clanMemberDAO.findAll().size)
    }

    @Test
    fun addClanMemberInvalidToken() {
        assertThrows<ResponseStatusException> {
            clanMemberController.addClanMember("invalidToken", clanMemberRequest())
        }
    }

    @Test
    fun addClanMember() {
        clanMemberController.addClanMember(userToken = testUserLogins.user1.token!!, clanMemberRequest())
        assertEquals(1, clanMemberDAO.findAll().size)
    }

    private fun clanMemberAsAdminRequest(userId: String): AddClanMemberAsAdminRequest {
        return AddClanMemberAsAdminRequest(
            userId = userId,
            runescapeName = generateRandomString(12),
            rank = Rank.Smiley,
            joinDate = LocalDate.now(),
            lastActivity = LocalDate.now(),
            splitsM = 0,
            infernalCape = false,
            pets = setOf(),
            ironMan = false,
            ironManItems = setOf()
        )
    }

    private fun clanMemberRequest(): AddClanMemberRequest {
        return AddClanMemberRequest(
            runescapeName = generateRandomString(12),
            ironMan = false
        )
    }
}
