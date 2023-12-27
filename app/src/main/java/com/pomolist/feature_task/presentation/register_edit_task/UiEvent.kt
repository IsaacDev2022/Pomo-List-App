package com.pomolist.feature_task.presentation.register_edit_task

sealed class UiEvent {
    object SaveTask: UiEvent()
    object UpdateTask: UiEvent()
}