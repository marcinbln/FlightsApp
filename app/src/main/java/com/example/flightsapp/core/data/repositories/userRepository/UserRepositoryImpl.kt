package com.example.flightsapp.core.data.repositories.userRepository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val FULL_NAME = "fullName"
const val EMAIL_ADDRESS = "emailAddress"
const val PHONE_NUMBER = "phoneNumber"
const val USER_INFORMATION_COLLECTION = "userInformation"

class UserRepositoryImpl @Inject constructor() : UserRepository {

    private val db = Firebase.firestore

    override suspend fun saveUserInfo(
        userId: String,
        fullName: String,
        email: String,
        phone: String
    ) {
        val userInformation = hashMapOf(
            FULL_NAME to fullName,
            EMAIL_ADDRESS to email,
            PHONE_NUMBER to phone
        )
        db.collection(USER_INFORMATION_COLLECTION).document(userId).set(userInformation).await()
    }
}
