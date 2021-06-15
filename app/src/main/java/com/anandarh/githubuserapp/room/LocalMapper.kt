package com.anandarh.githubuserapp.room

import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.utilities.EntityMapper

class LocalMapper : EntityMapper<UserEntity, UserModel> {
    override fun mapFromEntity(entity: UserEntity): UserModel {
        return UserModel(
            avatarUrl = entity.avatarUrl,
            company = entity.company,
            followers = entity.followers,
            following = entity.following,
            id = entity.id,
            location = entity.location,
            login = entity.login,
            name = entity.name,
            publicRepos = entity.publicRepos

        )
    }

    override fun mapToEntity(domainModel: UserModel): UserEntity {
        return UserEntity(
            avatarUrl = domainModel.avatarUrl,
            company = domainModel.company,
            followers = domainModel.followers,
            following = domainModel.following,
            id = domainModel.id,
            location = domainModel.location,
            login = domainModel.login,
            name = domainModel.name,
            publicRepos = domainModel.publicRepos
        )
    }

    fun mapFromEntityList(entities: List<UserEntity>): List<UserModel> {
        return entities.map { mapFromEntity(it) }
    }

}