package io.iotdrive.mddmapskt.services

interface MddApi {
    suspend fun authenticate(): Boolean
}