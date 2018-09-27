package com.sebastianopighi.domain.models

data class ApiError(
        var errorCode: ApiErrorCode = ApiErrorCode.GENERIC,
        var errorMessage: String? = GENERIC_NETWORKING_ERROR
) {
    companion object {
        const val GENERIC_NETWORKING_ERROR = "An error occoured while loading data. Please retry."
    }
}