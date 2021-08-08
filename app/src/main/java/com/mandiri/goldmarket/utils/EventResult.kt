package com.mandiri.goldmarket.utils

sealed class EventResult{
    object Idle: EventResult()
    object Loading: EventResult()
    data class Success(val data: Any? = null): EventResult()
    data class ErrorMessage(val errMsg: Any): EventResult()
}

