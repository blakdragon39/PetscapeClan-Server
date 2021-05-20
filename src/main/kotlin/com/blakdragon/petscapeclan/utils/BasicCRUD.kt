package com.blakdragon.petscapeclan.utils

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BasicCrud<K, T> {
    fun getAll(): List<T>
    fun getAll(pageable: Pageable): Page<T>
    fun getById(id: K): T?
    fun insert(obj: T): T
    fun update(obj: T): T
    fun deleteById(id: K): T?
}
