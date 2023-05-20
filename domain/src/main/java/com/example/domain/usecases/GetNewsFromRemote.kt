package com.example.domain.usecases

import com.example.domain.entity.NewsResponse
import com.example.domain.repository.NewsRepository

class GetNewsFromRemote(val newsRepository: NewsRepository) {
    suspend operator fun invoke(country:String="us",pageNumber:Int=1) = newsRepository.getNewsFromRemote(country,pageNumber)
}