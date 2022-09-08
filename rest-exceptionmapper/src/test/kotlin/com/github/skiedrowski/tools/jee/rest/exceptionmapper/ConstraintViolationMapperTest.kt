package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.ws.rs.core.Response

class ConstraintViolationMapperTest {
    @Test
    fun toResponse() {
        val violation1 = mockk<ConstraintViolation<*>>(relaxed = true)
        val violation2 = mockk<ConstraintViolation<*>>(relaxed = true)
        val ex = mockk<ConstraintViolationException> {
            every { message } returns "message"
        }
        val constraintViolations = setOf(violation1, violation2)
        every { ex.constraintViolations } returns constraintViolations

        val response = ConstraintViolationMapper().toResponse(ex)

        response.status shouldBe Response.Status.EXPECTATION_FAILED.statusCode
        response.getHeaderString("cause") shouldBe "Validation failed"
    }
}