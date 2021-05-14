package com.blakdragon.petscapeoffline.services

import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.utils.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(private val userDAO: UserDAO) : BasicCrud<String, User> {

    override fun getAll(): List<User> = userDAO.findAll()

    override fun getAll(pageable: Pageable): Page<User> = userDAO.findAll(pageable)

    override fun getById(id: String): User? = userDAO.findByIdOrNull(id)

    override fun insert(obj: User): User = userDAO.insert(obj)

    override fun update(obj: User): User {
        return if (obj.id != null && userDAO.existsById(obj.id)) {
            userDAO.save(obj)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun deleteById(id: String): User? {
        return userDAO.findByIdOrNull(id)?.apply {
            userDAO.delete(this)
        }
    }

    fun insertOrUpdate(user: User): User {
        return if (user.id != null && userDAO.existsById(user.id)) {
            userDAO.save(user)
        } else {
            userDAO.insert(user)
        }
    }

    fun getByEmail(email: String): User? = userDAO.findByEmail(email)
}
