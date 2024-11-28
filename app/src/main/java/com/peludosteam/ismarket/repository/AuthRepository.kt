    package com.peludosteam.ismarket.repository


    import com.google.firebase.auth.ktx.auth
    import com.google.firebase.ktx.Firebase
    import com.peludosteam.ismarket.domain.User
    import com.peludosteam.ismarket.service.AuthService
    import com.peludosteam.ismarket.service.AuthServiceImpl

    interface AuthRepository {
        suspend fun signup(user: User, password: String)
        suspend fun signin(email: String, password: String)
        fun signout()
        fun getCurrentUserUid(): String?
    }

    class AuthRepositoryImpl(
        private val authService: AuthService = AuthServiceImpl(),
        private val userRepository: UserRepository = UserRepositoryImpl()
    ) : AuthRepository {

        override suspend fun signup(user: User, password: String) {
            // 1. Registro en el módulo de autenticación.
            authService.createUser(user.email, password)

            // 2. Obtenemos el UID del usuario autenticado.
            val uid = Firebase.auth.currentUser?.uid

            // 3. Si el UID no es nulo, guardamos el usuario en Firestore.
            if (uid != null) {
                user.id = uid
                userRepository.createUser(user)
            } else {
                throw IllegalStateException("Error: UID no encontrado después del registro.")
            }
        }

        override suspend fun signin(email: String, password: String) {
            try {
                authService.loginWithEmailAndPassword(email, password)
            } catch (e: Exception) {
                throw Exception("Error al iniciar sesión: ${e.localizedMessage}")
            }
        }

        override fun signout() {
            authService.signout()
        }

        override fun getCurrentUserUid(): String? {
            return Firebase.auth.currentUser?.uid
        }
    }
