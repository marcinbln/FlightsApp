package com.example.flightsapp.core.data.repositories.userRepository

import android.provider.SimPhonebookContract.SimRecords.PHONE_NUMBER
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    private val db = Firebase.firestore

    override suspend fun saveUser(
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
        db.collection(USER_INFORMATION_COLLECTION)
            .document(userId)
            .set(userInformation)
            .await()
    }

    companion object {
        private const val FULL_NAME = "fullName"
        private const val EMAIL_ADDRESS = "emailAddress"
        private const val PHONE_NUMBER = "phoneNumber"
        private const val USER_INFORMATION_COLLECTION = "userInformation"
    }
}
