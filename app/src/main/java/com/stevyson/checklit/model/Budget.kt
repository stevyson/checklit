package com.stevyson.checklit.model

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject

class Budget(): RealmObject {
    var _id: ObjectId = ObjectId.create()
    var totalBudget: Double = 0.0

    constructor(
        amount: Double
    ): this() {
        this.totalBudget = amount
    }
}