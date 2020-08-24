package com.example.arch_store.utils

import android.content.SharedPreferences
import com.example.arch_store.models.User
import javax.inject.Inject


class UserSession @Inject constructor(val sharedPref: SharedPreferences) {


    fun setUserData(user: User) {
        val editor = sharedPref.edit()
        editor.putString("email", user.Email)
        editor.putString("user_name", user.UserName)
        editor.putInt("id", user.id)
        editor.putString("token", user.token);
        editor.apply()
    }

    fun logOut() {
        val editor = sharedPref.edit()
        editor.putString("email", "empty")
        editor.putString("user_name", "empty")
        editor.putInt("id", 0)
        editor.putString("token", "empty");
        editor.apply()
    }

    fun getUserData(): User {
        return User(
            id = sharedPref.getInt("id", 0),
            token = sharedPref.getString("token", "empty")!!,
            Email = sharedPref.getString("email", "empty")!!,
            UserName = sharedPref.getString("user_name", "empty")!!
        )
    }

    fun isAuth(): Boolean {
        return getUserData().token != "empty"
    }


}