package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.data.enums.IntervalType
import bx.app.data.model.LevelModel
import bx.app.data.repository.LevelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LevelViewModel(private val repo: LevelRepository) : ViewModel() {
    private var _level = MutableStateFlow<LevelModel>(LevelModel(
        intervalType = 1, // TODO: need a enum for that!
        intervalNumber = 1,
        deckId = 0
    ))
    private val _level = MutableStateFlow<LevelModel>(getInitialLevel())

    val levels: StateFlow<List<LevelModel>> = repo.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    var level: StateFlow<LevelModel> = _level

    fun getLevelById(id: Long) = viewModelScope.launch { _level.value = repo.getById(id) }
    fun insertLevel(level: LevelModel) = viewModelScope.launch { repo.insert(level) }
    fun updateLevel(level: LevelModel) = viewModelScope.launch { repo.update(level) }
    fun deleteLevel(level: LevelModel) = viewModelScope.launch { repo.delete(level) }
    fun getInitialLevel(): LevelModel {
        return LevelModel(
            name = "",
            intervalType = IntervalType.WEEK,
            intervalNumber = 1,
            deckId = 0
        )
    }
}