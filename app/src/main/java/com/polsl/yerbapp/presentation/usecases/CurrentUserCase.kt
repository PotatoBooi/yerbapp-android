package com.polsl.yerbapp.presentation.usecases

import com.apollographql.apollo.api.toInput
import com.polsl.yerbapp.data.UsersRepository
import com.polsl.yerbapp.domain.models.reponse.graphql.ProfileModel
import com.polsl.yerbapp.domain.models.reponse.graphql.UserModel
import com.polsl.yerbapp.domain.models.reponse.sharedPreferences.CurrentUserInfo
import yerba.type.EditUserInput

class CurrentUserCase(private val usersRepository: UsersRepository) {
    fun getCurrentUserInfo(): CurrentUserInfo? {
        val userInfo = usersRepository.getCurrentUserInfo()
        return if (userInfo.accessToken.isEmpty()) {
            null
        } else {
            userInfo
        }
    }

    suspend fun isUserAuthorized(): Boolean = usersRepository.checkUserAuthorized()
    fun logoutUser() = usersRepository.logoutUser()

    suspend fun getCurrentUser(): UserModel? = usersRepository.getCurrentUser()
    suspend fun editCurrentUser(profile: ProfileModel){
        // TODO mapper
        val profile = profile
        val editUserInput = EditUserInput
            .builder()
            .bitternessImportance(profile.bitternessImportance)
            .aromaImportance(profile.aromaImportance)
            .tasteImportance(profile.tasteImportance)
            .energyImportance(profile.energyImportance)
            .priceImportance(profile.priceImportance)
            .build()

        usersRepository.editCurrentUser(editUserInput)
    }

}