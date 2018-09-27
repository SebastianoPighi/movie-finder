package com.sebastianopighi.domain.models

enum class ApiErrorCode (val code: Int) {
    GENERIC(0), BAD_REQUEST(400), UNAUTHORIZED(401), FORBIDDEN(403), NOT_FOUND(404);

    companion object {
        fun map(errorCode: Int): ApiErrorCode {
            when (errorCode) {
                400 -> return BAD_REQUEST
                401 -> return UNAUTHORIZED
                403 -> return FORBIDDEN
                404 -> return NOT_FOUND
                else -> return GENERIC
            }
        }
    }
}