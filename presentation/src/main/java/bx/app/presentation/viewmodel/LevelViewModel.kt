package bx.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import bx.app.data.enums.IntervalType
import bx.app.data.model.LevelModel
import bx.app.data.repository.LevelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LevelViewModel(private val repo: LevelRepository) : DebouncedAutoSaveViewModel() {
    private val _deckId = MutableStateFlow(0L)
    private val _intervalExists = MutableStateFlow(false)
    private val _level = MutableStateFlow<LevelModel>(getInitialLevel())

    val intervalExists: StateFlow<Boolean> = _intervalExists
    val level: StateFlow<LevelModel> = _level
    val levels: StateFlow<List<LevelModel>> =
        repo.observeByDeckId(_deckId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun insertLevel(level: LevelModel) = viewModelScope.launch { repo.insert(level) }
    fun getLevelById(id: Long) = viewModelScope.launch { _level.value = repo.getById(id) }
    fun deleteLevelById(id: Long) = viewModelScope.launch { repo.deleteById(id) }
    fun deleteLevelsByDeckId(id: Long) = viewModelScope.launch { repo.deleteByDeckId(id) }

    fun existsByInterval(intervalNumber: Int, intervalType: IntervalType) =
        viewModelScope.launch { _intervalExists.value = repo.existsByInterval(intervalNumber, intervalType) }

    fun setDeckId(id: Long) { _deckId.value = id }
    fun resetLevel() { _level.value = getInitialLevel() }

    fun changeName(value: String) = upsertLevel { _level.value.copy(name = value) }
    fun changeIntervalType(intervalType: IntervalType, intervalNumber: Int = 1) =
        upsertLevel { _level.value.copy(intervalType = intervalType, intervalNumber = intervalNumber) }
    fun changeIntervalNumber(value: Int) = upsertLevel { _level.value.copy(intervalNumber = value) }

    private fun upsertLevel(transform: LevelModel.() -> LevelModel) {
        _level.value = _level.value.transform().copy(deckId = _deckId.value)
        scheduleAutoSave { getLevelById(repo.upsert(_level.value)) }
    }

    fun getInitialLevel(): LevelModel {
        return LevelModel(
            name = "",
            intervalType = IntervalType.DAY,
            intervalNumber = 1,
            deckId = 0
        )
    }
}