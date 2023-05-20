package com.example.domain.utilis

import com.example.domain.entity.NewsResponse

sealed class NewsResource<T>(
    val data:T?=null,
    val message:String?=null
){
    class Success<T>(data: T): NewsResource<T>(data)
    class Erorr<T>(data: T?=null,message: String):NewsResource<T>(data,message)
    class Loadding<T>:NewsResource<T>()
}
