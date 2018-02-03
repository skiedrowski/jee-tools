package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import java.time.LocalDate
import java.time.Month
import javax.persistence.OptimisticLockException
import javax.ws.rs.core.Response

class OptimisticLockExceptionMapperTest {
    @Test
    fun toResponse() {
        val ex = mock<OptimisticLockException> {
            on { message } doReturn "message"
            on { entity } doReturn LocalDate.of(2017, Month.DECEMBER, 5)
        }
        val response = OptimisticLockExceptionMapper().toResponse(ex)

        assertThat(response.status, equalTo(Response.Status.CONFLICT.statusCode))
        assertThat(response.getHeaderString("cause"), equalTo("conflict occurred: 2017-12-05"))
        assertThat(response.getHeaderString("additional-info"), equalTo("message"))
    }

}