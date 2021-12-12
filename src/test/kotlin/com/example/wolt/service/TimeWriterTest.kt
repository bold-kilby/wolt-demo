package com.example.wolt.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TimeWriterTest {

    private val timeWriter = TimeWriter()

    @Test
    fun `An AM time should be written correctly`() {
        val writtenTime = timeWriter.write(3600)

        val expectedWrittenTime = "1 AM"
        Assertions.assertEquals(expectedWrittenTime, writtenTime)
    }

    @Test
    fun `A PM time should be written correctly`() {
        val writtenTime = timeWriter.write(72000)

        val expectedWrittenTime = "8 PM"
        Assertions.assertEquals(expectedWrittenTime, writtenTime)
    }

    @Test
    fun `A time with minutes should be written correctly`() {
        val writtenTime = timeWriter.write(73800)

        val expectedWrittenTime = "8:30 PM"
        Assertions.assertEquals(expectedWrittenTime, writtenTime)
    }

    @Test
    fun `Seconds should not be considered`() {
        val writtenTime = timeWriter.write(1)

        val expectedWrittenTime = "12 AM"
        Assertions.assertEquals(expectedWrittenTime, writtenTime)
    }
}