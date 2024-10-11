package com.sample.kinopoisk.core.common.tests

import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.common.result.asResult
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class ResultKtTest {

    @Test
    fun resultCatchesErrors() = runTest {
        val results = flow {
            emit(1)
            throw Exception("Test Done")
        }
        .asResult()
        .toList()

        assertEquals(Result.Loading, results[0])
        assertEquals(Result.Success(1), results[1])
        assertEquals("Test Done", (results[2] as Result.Error).exception.message)
    }
}
