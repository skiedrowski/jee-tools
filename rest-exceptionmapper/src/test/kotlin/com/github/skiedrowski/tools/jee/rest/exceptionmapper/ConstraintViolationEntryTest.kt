package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import javax.json.bind.JsonbBuilder
import javax.validation.ConstraintViolation
import javax.validation.Path

class ConstraintViolationEntryTest {
    @Test
    fun `build empty constraint violation`() {
        val emptyConstraintViolation = mock<ConstraintViolation<*>>()

        val entry = ConstraintViolationEntry.build(emptyConstraintViolation)

        assertThat(entry.fieldName, equalTo("<?field>"))
        assertThat(entry.wrongValue, equalTo("<?invalidValue>"))
        assertThat(entry.errorMessage, equalTo("<?message>"))
    }

    @Test
    fun `build complete constraint violation`() {
        val propertyPath = object : Path {
            override fun iterator(): MutableIterator<Path.Node> {
                throw RuntimeException("should not be called")
            }

            override fun toString() = "propertyPath"
        }

        val invalidValue = object : Any() {
            override fun toString() = "this is the invalid value"
        }

        val emptyConstraintViolation = mock<ConstraintViolation<*>> {
            on { this.propertyPath } doReturn propertyPath
            on { this.invalidValue } doReturn invalidValue
            on { this.message } doReturn "the message"
        }

        val entry = ConstraintViolationEntry.build(emptyConstraintViolation)

        assertThat(entry.fieldName, equalTo("propertyPath"))
        assertThat(entry.wrongValue, equalTo("this is the invalid value"))
        assertThat(entry.errorMessage, equalTo("the message"))
    }

    @Test
    fun jsonb() {
        val entry = ConstraintViolationEntry("fieldNameX", "wrongValueX", "errorMsgX")
        val jsonb = JsonbBuilder.create()

        val jsonStr = jsonb.toJson(entry)
        assertThat(
            jsonStr,
            equalTo("{\"fieldName\":\"fieldNameX\",\"wrongValue\":\"wrongValueX\",\"errorMessage\":\"errorMsgX\"}")
        )

        val fromJson = jsonb.fromJson(jsonStr, ConstraintViolationEntry::class.java)
        assertThat(fromJson.fieldName, equalTo("fieldNameX"))
        assertThat(fromJson.wrongValue, equalTo("wrongValueX"))
        assertThat(fromJson.errorMessage, equalTo("errorMsgX"))

    }

}