package com.angelodev.helpapp.data.repository

import com.angelodev.helpapp.data.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveUser(userId: String, profile: UserProfile): Result<Unit> {
        return try {
            val data = hashMapOf(
                "Fullname" to profile.fullname,
                "Contact" to profile.contact,
                "Email" to profile.email
            )
            firestore.collection("users").document(userId).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun observeUser(userId: String): Flow<UserProfile?> = callbackFlow {
        val listener = firestore.collection("users").document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val profile = snapshot?.let {
                    if (it.exists()) {
                        UserProfile(
                            fullname = it.getString("Fullname") ?: "",
                            contact = it.getString("Contact") ?: "",
                            email = it.getString("Email") ?: ""
                        )
                    } else null
                }
                trySend(profile)
            }
        awaitClose { listener.remove() }
    }
}
