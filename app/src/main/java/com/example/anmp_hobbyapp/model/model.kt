package com.example.anmp_hobbyapp.model

data class User(
    var id:String?,
    var username:String?,
    var password:String?,
    var first_name:String?,
    var last_name:String?,
    var email:String?,
    var photo_url:String?
)

data class Hobby(
    var id:String?,
    var image_url:String?,
    var title:String?,
    var username:String?,
    var description:String?,
    var published:String?,
    var content:List<String>?
)