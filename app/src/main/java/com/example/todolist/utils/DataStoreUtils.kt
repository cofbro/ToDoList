package com.example.todolist.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val Context.preferenceDataStore: DataStore<Preferences> by preferencesDataStore("dataStore_recording")

val passwordKey = stringPreferencesKey("todoStr")

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
