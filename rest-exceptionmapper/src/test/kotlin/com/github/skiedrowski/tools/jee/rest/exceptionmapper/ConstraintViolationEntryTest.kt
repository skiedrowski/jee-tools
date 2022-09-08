package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import javax.json.bind.JsonbBuilder
import javax.validation.ConstraintViolation
import javax.validation.Path

class ConstraintViolationEntryTest {
    @Test
    fun `build empty constraint violation`() {
        val emptyConstraintViolation = mockk<ConstraintViolation<*>>(relaxed = true) {
            every { propertyPath } returns null
            every { invalidValue } returns null
            every { message } returns null
        }

        val entry = ConstraintViolationEntry.build(emptyConstraintViolation)

        entry.fieldName shouldBe "<?field>"
        entry.wrongValue shouldBe "<?invalidValue>"
        entry.errorMessage shouldBe "<?message>"
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

        val emptyConstraintViolation = mockk<ConstraintViolation<*>>()
        every { emptyConstraintViolation.propertyPath } returns propertyPath
        every { emptyConstraintViolation.invalidValue } returns invalidValue
        every { emptyConstraintViolation.message } returns "the message"

        val entry = ConstraintViolationEntry.build(emptyConstraintViolation)

        entry.fieldName shouldBe "propertyPath"
        entry.wrongValue shouldBe "this is the invalid value"
        entry.errorMessage shouldBe "the message"
    }

    @Test
    fun jsonb() {
        val entry = ConstraintViolationEntry("fieldNameX", "wrongValueX", "errorMsgX")
        val jsonb = JsonbBuilder.create()

        val jsonStr = jsonb.toJson(entry)
        jsonStr shouldBe "{\"fieldName\":\"fieldNameX\",\"wrongValue\":\"wrongValueX\",\"errorMessage\":\"errorMsgX\"}"

        val fromJson = jsonb.fromJson(jsonStr, ConstraintViolationEntry::class.java)
        fromJson.fieldName shouldBe "fieldNameX"
        fromJson.wrongValue shouldBe "wrongValueX"
        fromJson.errorMessage shouldBe "errorMsgX"
    }

}