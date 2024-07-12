package com.example.testrawg.domain.model

object ErrorCodes {
    object Http {
        val SERVER_ERROR = 500..599
        const val INTERNAL_SERVER = 501
        const val BAD_GATEWAY = 502
        const val RESOURCE_NOT_FOUND = 404
    }
}
