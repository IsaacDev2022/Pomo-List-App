package com.pomolist.feature_task.presentation.register_edit_task

sealed class RegisterEvent {
    data class EnteredName(val text: String) : RegisterEvent()
    data class EnteredDescription(val text: String) : RegisterEvent()
    data class EnteredPriority(val text: String) : RegisterEvent()
    data class EnteredDate(val text: String) : RegisterEvent()
    data class EnteredTime(val text: String) : RegisterEvent()
    object SaveTask : RegisterEvent()
}