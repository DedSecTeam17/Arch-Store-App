package com.example.arch_store.service.mapping

import com.example.arch_store.models.User
import com.example.arch_store.service.responses.AuthResponse
import com.example.arch_store.utils.EntityMapper
import javax.inject.Inject

class AuthResponseToUser
@Inject
constructor() : EntityMapper<AuthResponse , User>{

     override fun mapFromEntity(entity: AuthResponse): User {
        return User(
            id = entity.user.id,
            UserName = entity.user.username,
            Email = entity.user.email,
            token = entity.jwt
        )
    }

}
