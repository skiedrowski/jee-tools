package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import javax.ejb.EJBException
import javax.persistence.OptimisticLockException
import javax.validation.ConstraintViolationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

class EJBExceptionMapperTest {

    @Test
    fun `cause ConstraintViolationException`() {
        val expectedResponse = mock<Response>()
        val ejbExceptionMapper = object : EJBExceptionMapper() {
            override val constraintViolationExceptionMapper: ConstraintViolationMapper
                get() = mock { on { this.toResponse(any()) } doReturn expectedResponse }
        }

        val ex = EJBException(mock<ConstraintViolationException>())
        val response = ejbExceptionMapper.toResponse(ex)

        assertThat(response, equalTo(expectedResponse))
    }

    @Test
    fun `cause EJBException nested`() {
        val expectedResponse = mock<Response>()
        val ejbExceptionMapper = object : EJBExceptionMapper() {
            override val ejbExceptionMapper: EJBExceptionMapper
                get() = mock { on { this.toResponse(any()) } doReturn expectedResponse }
        }

        val ex = EJBException(mock<EJBException>())
        val response = ejbExceptionMapper.toResponse(ex)

        assertThat(response, equalTo(expectedResponse))
    }

    @Test
    fun `cause OptimisticLockException`() {
        val expectedResponse = mock<Response>()
        val ejbExceptionMapper = object : EJBExceptionMapper() {
            override val optimisticLockExceptionMapper: OptimisticLockExceptionMapper
                get() = mock { on { this.toResponse(any()) } doReturn expectedResponse }
        }

        val ex = EJBException(mock<OptimisticLockException>())
        val response = ejbExceptionMapper.toResponse(ex)

        assertThat(response, equalTo(expectedResponse))
    }

    @Test
    fun `cause registered Exception`() {
        val npe = mock<NullPointerException>()
        val expResponse = mock<Response>()

        val npeHandlerMock = mock<ExceptionMapper<NullPointerException>>() {
            on { this.toResponse(npe) } doReturn expResponse
        }
        EJBExceptionMapperRegistration.register(NullPointerException::class.java, npeHandlerMock)

        val ejbExceptionMapper = EJBExceptionMapper()

        val ex = EJBException(npe)
        val response = ejbExceptionMapper.toResponse(ex)

        assertThat(response, equalTo(expResponse))
    }

    @Test
    fun `cause _other_ exception`() {
        val ejbExceptionMapper = EJBExceptionMapper()

        val ex = EJBException(mock<IllegalArgumentException>())
        val response = ejbExceptionMapper.toResponse(ex)

        assertThat(response.status, equalTo(Response.Status.INTERNAL_SERVER_ERROR.statusCode))
        assertThat(response.getHeaderString("cause"), equalTo(ex.toString()))

    }

}
