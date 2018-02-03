package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.ws.rs.core.Response

class ConstraintViolationMapperTest {
    @Test
    fun toResponse() {
        val violation1 = mock<ConstraintViolation<*>>()
        val violation2 = mock<ConstraintViolation<*>>()
        val ex = mock<ConstraintViolationException> {
            on { message } doReturn "message"
            val constraintViolations = setOf(violation1, violation2)
            on { this.constraintViolations } doReturn constraintViolations
        }
        val response = ConstraintViolationMapper().toResponse(ex)

        assertThat(response.status, equalTo(Response.Status.EXPECTATION_FAILED.statusCode))
        assertThat(response.getHeaderString("cause"), equalTo("Validation failed"))
//        response.readEntity(JsonArray::class.java) ==> check violations
    }
}