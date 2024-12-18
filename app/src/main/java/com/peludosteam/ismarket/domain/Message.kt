package com.peludosteam.ismarket.domain

import com.google.firebase.Timestamp

data class Message(
    var id: String = "",
    var content: String = "",
    var date: Timestamp = Timestamp.now(),
    var imageID:String? = null,
    var imageURL:String? = null,
    var authorID:String = "",
    var isMine:Boolean = false
)