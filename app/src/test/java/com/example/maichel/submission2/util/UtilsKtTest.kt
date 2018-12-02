package com.example.maichel.submission2.util

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

class UtilsKtTest {

    @Test
    fun toSimpleString() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.parse("02/28/2018")
        assertEquals("Rab, 28 Feb 2018", toSimpleString(date))
    }
}