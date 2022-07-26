package br.com.zup.marvel.ui.register.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.zup.marvel.EMAIL_ERROR_MESSAGE
import br.com.zup.marvel.NAME_ERROR_MESSAGE
import br.com.zup.marvel.PASSWORD_ERROR_MESSAGE
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.domain.repository.AuthenticationRepository
import br.com.zup.marvel.domain.repository.AuthenticationRepositoryFactory
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterViewModelTest{
    private val repository: AuthenticationRepository = mockk(relaxed = true)

    @Before
    fun setup(){
        mockkObject(AuthenticationRepositoryFactory)
        every { AuthenticationRepositoryFactory.create() } returns repository
        every { repository.registerUser(any(),any()) } returns mockk(relaxed = true)
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun `when name size until 2 chars should return NAME_ERROR_MESSAGE`(){

        val viewmodel = RegisterViewModel()

        viewmodel.validateDataUser(user = User(name="eu"))

        assert(viewmodel.errorState.value == NAME_ERROR_MESSAGE)
    }

    @Test
    fun `when password size until 7 chars should return PASSWORD_ERROR_MESSAGE`(){

        val viewmodel = RegisterViewModel()

        viewmodel.validateDataUser(user = User(name="CHRISTIAN", password = "1234567", email = "chris@gmail.com"))

        assert(viewmodel.errorState.value == PASSWORD_ERROR_MESSAGE)
    }

    @Test
    fun `when email is empty should return EMAIL_ERROR_MESSAGE`(){

        val viewmodel = RegisterViewModel()

        viewmodel.validateDataUser(user = User(name="CHRISTIAN", password = "1234567", email = "sadsadasfss"))

        assert(viewmodel.errorState.value == EMAIL_ERROR_MESSAGE)
    }

    @Test
    fun `when user has valid fields should call registerUser`(){

        val viewmodel = RegisterViewModel()
        val user = User(name="CHRISTIAN", password = "12345678", email = "chris@gmail.com")
        viewmodel.validateDataUser(user = user)

        verify { repository.registerUser(user.email, user.password) }
    }
}