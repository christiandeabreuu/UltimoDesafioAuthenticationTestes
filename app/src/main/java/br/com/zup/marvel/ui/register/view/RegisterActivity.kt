package br.com.zup.marvel.ui.register.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.marvel.CREATE_USER_ERROR_MESSAGE
import br.com.zup.marvel.LOGIN_ERROR_MESSAGE
import br.com.zup.marvel.databinding.ActivityRegisterBinding
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.ui.home.view.HomeActivity
import br.com.zup.marvel.ui.register.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnRegistration.setOnClickListener {
            val user = getDataUser()
            viewModel.validateDataUser(user)
        }
        initObserver()
    }

    private fun getDataUser(): User {
        return User(
            name = binding.etUsername.text.toString(),
            email = binding.etUseremail.text.toString(),
            password = binding.etPassword.text.toString()
        )
    }

    private fun initObserver() {
        viewModel.registerState.observe(this) {
            goToHomePage(it)
        }
        viewModel.errorState.observe(this) {
            Toast.makeText(this, CREATE_USER_ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHomePage(user: User) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("user key", user)
        }
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}