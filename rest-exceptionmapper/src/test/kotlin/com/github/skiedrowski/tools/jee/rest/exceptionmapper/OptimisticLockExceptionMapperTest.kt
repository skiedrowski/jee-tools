package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month
import javax.persistence.OptimisticLockException
import javax.ws.rs.core.Response

class OptimisticLockExceptionMapperTest {
    @Test
    fun toResponse() {
        val ex = mockk<OptimisticLockException> {
            every { message } returns "message"
            every { entity } returns LocalDate.of(2017, Month.DECEMBER, 5)
        }
        val response = OptimisticLockExceptionMapper().toResponse(ex)

        response.status shouldBe Response.Status.CONFLICT.statusCode
        response.getHeaderString("cause") shouldBe "conflict occurred: 2017-12-05"
        response.getHeaderString("additional-info") shouldBe "message"
    }
}