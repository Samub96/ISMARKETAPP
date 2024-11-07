import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peludosteam.ismarket.domain.User
import com.peludosteam.ismarket.repository.UserRepository
import com.peludosteam.ismarket.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class ProfileViewModel(
    val userRepository: UserRepository = UserRepositoryImpl()
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    fun getCurrentUser() {
        viewModelScope.launch {
            val me = userRepository.getCurrentUser()
            _user.value = me
        }
    }
}


