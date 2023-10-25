package com.stevyson.checklit


import com.stevyson.checklit.model.Budget
import com.stevyson.checklit.model.Category
import com.stevyson.checklit.model.Expense
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

val config = RealmConfiguration.create(schema = setOf(Expense::class, Category::class, Budget::class))
val db: Realm = Realm.open(config)