package com.example.ui.fragments.saved

import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.data.model.Article
import com.example.domain.usecases.GetNewsUseCase
import com.example.domain.usecases.ManageSavedUsaCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val manageSavedUsaCase: ManageSavedUsaCase
) :
    BaseViewModel() {

    private val _savedNews = MutableStateFlow<List<Article>>(emptyList())
    val savedNews get() = _savedNews.asStateFlow()

    fun getSavedArticles() = run {
        viewModelScope.launch {
            getNewsUseCase.getFromNewsFromLocal().collectLatest {
                _savedNews.value = it

            }
        }
    }

    fun removeFromSaved(article: Article) = viewModelScope.launch {
        manageSavedUsaCase.removeFromSaved(article)
    }

    fun restoreArticle(article: Article)=viewModelScope.launch {
        manageSavedUsaCase.addToSaved(article)
    }
}