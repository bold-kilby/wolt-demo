package com.example.wolt.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ListWritingTest {

    @Test
    fun `A list should be written on separate lines`() {
        val list = listOf("message 1", "message 2")
        val writtenList = list.writeOnSeparateLines()

        val expectedWrittenList = """
            message 1
            message 2
        """.trimIndent()
        Assertions.assertEquals(expectedWrittenList, writtenList)
    }
}