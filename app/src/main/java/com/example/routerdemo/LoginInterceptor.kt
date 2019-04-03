package com.example.routerdemo

import com.gecng.routeannotation.IInterceptor
import com.gecng.routeannotation.Interceptor

@Suppress("unused")
@Interceptor(name = "app/login")
class LoginInterceptor : IInterceptor {
    override fun onIntercept(): Boolean {
        return UserManager.id != 0
    }
}