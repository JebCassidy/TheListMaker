package com.example.thelistmaker

import java.io.Serializable

class List(
    var title : String = "",
    var listData : String = ""
) : Serializable {
    override fun toString(): String {
        return "List (title=${title}, listData=${listData}"
    }
}