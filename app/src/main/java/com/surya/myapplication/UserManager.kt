package com.surya.myapplication

import android.content.Context
import androidx.datastore.createDataStore
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context : Context) {

    //Create the dataStore
    private val dataStore = context.createDataStore(name = "user_prefs")

    companion object{

        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
        val USER_AGE_KEY = preferencesKey<String>("USER_AGE")
        val USER_GENDER_KEY = preferencesKey<Boolean>("USER_GENDER")
    }

    suspend fun storeUser(age : String, name: String, isMale: Boolean ){

        dataStore.edit {
            it[USER_NAME_KEY] = name
            it[USER_AGE_KEY] = age
            it[USER_GENDER_KEY] = isMale
        }
    }

    val userNameFlow: Flow<String> = dataStore.data.map { it[USER_NAME_KEY] ?:""}

    val userAgeFlow: Flow<String> = dataStore.data.map { it[USER_AGE_KEY] ?:"" }

    val userGenderFlow: Flow<Boolean> = dataStore.data.map { it[USER_GENDER_KEY] ?: false }

}