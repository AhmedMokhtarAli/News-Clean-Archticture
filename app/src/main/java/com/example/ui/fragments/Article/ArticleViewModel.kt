package com.example.ui.fragments.Article

import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.data.model.Article
import com.example.domain.usecases.ManageSavedUsaCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val manageSavedUsaCase: ManageSavedUsaCase) :
    BaseViewModel() {

    fun saveArticle(article: Article) = viewModelScope.launch {
            manageSavedUsaCase.addToSaved(article)
    }
}