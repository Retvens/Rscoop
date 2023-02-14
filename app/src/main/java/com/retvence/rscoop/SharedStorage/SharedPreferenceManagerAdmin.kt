package com.retvence.rscoop.SharedStorage

import android.content.Context
import com.retvence.rscoop.DataCollections.UserLoginData

class SharedPreferenceManagerAdmin private constructor(private val context : Context){

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("__v", -1) != -1
        }

    val user: UserLoginData
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return UserLoginData(
                sharedPreferences.getString("Email",null),
                sharedPreferences.getString("Password", null),
                sharedPreferences.getInt("__v",-1),
                sharedPreferences.getString("_id",null)
            )
        }

    fun saveUser(user: UserLoginData) {

        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("Email", user.Email)
        editor.putString("Password", user.Password)
        editor.putInt("__v",user.__v)
        editor.putString("_id",user._id)

        editor.apply()

    }

    fun clear() {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPreferenceManagerAdmin? = null
        @Synchronized
        fun getInstance(context: Context): SharedPreferenceManagerAdmin {
            if (mInstance == null) {
                mInstance = SharedPreferenceManagerAdmin(context)
            }
            return mInstance as SharedPreferenceManagerAdmin
        }
    }


}