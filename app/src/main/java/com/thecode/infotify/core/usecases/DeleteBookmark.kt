package com.thecode.infotify.core.usecases

import com.thecode.infotify.core.data.NewsRepository
import javax.inject.Inject

class DeleteBookmark @Inject constructor(
    private val repository: NewsRepository
) {
    fun deleteBookmark(url: String){
        repository.deleteBookmark(url)
    }
}
