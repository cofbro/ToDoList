package com.example.todolist.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author chy
 * dataStore preferenceDataStore
 * @param "passwordKey" -> 待办事项
 *        "isLoginKey" -> 登录状态
 *        "userNamedKey" -> 用户名
 *        "userPasswordKey" -> 密码
 */
val Context.preferenceDataStore: DataStore<Preferences> by preferencesDataStore("dataStore_recording")

val passwordKey = stringPreferencesKey("todoStr")
val isLoginKey = booleanPreferencesKey("isLogin")
val userNamedKey = stringPreferencesKey("userName")
val userPasswordKey = stringPreferencesKey("userPsd")

fun loadData(context: Context, scope: CoroutineScope, callback: (String?) -> Unit) {
    scope.launch(Dispatchers.IO) {
        val passwordPreference = context.preferenceDataStore.data.first {
            true
        }
        withContext(Dispatchers.Main) {
            callback(passwordPreference[passwordKey])
        }
    }
}

fun saveData(context: Context, str: String, scope: CoroutineScope) {
    scope.launch(Dispatchers.IO) {
        context.preferenceDataStore.edit {
            it[passwordKey] = str
        }
    }
}

fun loadUser(context: Context, callback: (Boolean?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
        val passwordPreference = context.preferenceDataStore.data.first {
            true
        }
        withContext(Dispatchers.Main) {
            callback(passwordPreference[isLoginKey])
        }
    }
}

fun saveUser(context: Context, b: Boolean) {
    CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
        context.preferenceDataStore.edit {
            it[isLoginKey] = b
        }
    }
}

fun loadUserName(context: Context, callback: (String?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
        val passwordPreference = context.preferenceDataStore.data.first {
            true
        }
        withContext(Dispatchers.Main) {
            callback(passwordPreference[userNamedKey])
        }
    }
}

fun saveUserName(context: Context, name: String) {
    CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
        context.preferenceDataStore.edit {
            it[userNamedKey] = name
        }
    }
}

fun loadUserPsd(context: Context, callback: (String?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
        val passwordPreference = context.preferenceDataStore.data.first {
            true
        }
        withContext(Dispatchers.Main) {
            callback(passwordPreference[userPasswordKey])
        }
    }
}

fun saveUserPsd(context: Context, psd: String) {
    CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
        context.preferenceDataStore.edit {
            it[userPasswordKey] = psd
        }
    }
}