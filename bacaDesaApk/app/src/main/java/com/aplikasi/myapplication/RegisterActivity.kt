package com.aplikasi.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val daftar: Button = findViewById(R.id.daftar)
        val email: EditText = findViewById(R.id.email)
        val username: EditText = findViewById(R.id.username)
        val noTelepon: EditText = findViewById(R.id.noTelepon)
        val password: EditText = findViewById(R.id.password)
        val passwordKonfirmasi: EditText = findViewById(R.id.passwordKonfirmasi)
        val loginButton: TextView = findViewById(R.id.login)

        daftar.setOnClickListener {
            val email = email.text.toString()
            val username = username.text.toString()
            val phone = noTelepon.text.toString()
            val password = password.text.toString()
            val confirmPassword = passwordKonfirmasi.text.toString()

            if (password == confirmPassword) {
                ApiService.instance.registerUser(email, username, phone, password).enqueue(object : Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(applicationContext, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        } else {
                            Toast.makeText(applicationContext, "Registrasi Gagal", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(applicationContext, "Password tidak cocok", Toast.LENGTH_SHORT).show()
            }
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}