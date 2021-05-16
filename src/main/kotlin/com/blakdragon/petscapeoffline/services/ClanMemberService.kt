package com.blakdragon.petscapeoffline.services

import com.blakdragon.petscapeoffline.models.ClanMember
import com.blakdragon.petscapeoffline.utils.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ClanMemberService(private val clanMemberDAO: ClanMemberDAO) : BasicCrud<String, ClanMember> {

    override fun getAll(): List<ClanMember> = clanMemberDAO.findAll()

    override fun getAll(pageable: Pageable): Page<ClanMember> = clanMemberDAO.findAll(pageable)

    override fun getById(id: String): ClanMember? = clanMemberDAO.findByIdOrNull(id)

    override fun insert(obj: ClanMember): ClanMember = clanMemberDAO.insert(obj)

    override fun update(obj: ClanMember): ClanMember {
        val id = obj.id
        if (id != null && clanMemberDAO.existsById(id)) {
            return clanMemberDAO.save(obj)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Clan Member not found")
        }
    }

    override fun deleteById(id: String): ClanMember? {
        return clanMemberDAO.findByIdOrNull(id)?.apply {
            clanMemberDAO.delete(this)
        }
    }
}
