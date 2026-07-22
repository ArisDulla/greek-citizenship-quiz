package com.aris.greekcitizenshipquiz.domain.model


sealed class SyncResult {


    data object Updated : SyncResult()


    data object NoUpdates : SyncResult()


    data class Error(
        val message: String
    ) : SyncResult()

}