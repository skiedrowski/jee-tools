package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import jakarta.ejb.EJBException
import jakarta.persistence.OptimisticLockException
import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper

class EJBExceptionMapperTest {

    @Test
    fun `cause ConstraintViolationException`() {
        val expectedResponse = mockk<Response>()
        val ejbExceptionMapper = object : EJBExceptionMapper() {
            override val constraintViolationExceptionMapper: ConstraintViolationMapper
                get() {
                    val constraintViolationMapper = mockk<ConstraintViolationMapper>()
                    every { constraintViolationMapper.toResponse(any()) } returns expectedResponse
                    return constraintViolationMapper
                }
        }

        val ex = EJBException(mockk<ConstraintViolationException>())
        val response = ejbExceptionMapper.toResponse(ex)

        response shouldBe expectedResponse
    }

    @Test
    fun `cause EJBException nested`() {
        val expectedResponse = mockk<Response>()
        val ejbExceptionMapper = object : EJBExceptionMapper() {
            override val ejbExceptionMapper: EJBExceptionMapper
                get() {
                    val ejbExceptionMapper = mockk<EJBExceptionMapper>()
                    every { ejbExceptionMapper.toResponse(any()) } returns expectedResponse
                    return ejbExceptionMapper
                }
        }

        val ex = EJBException(mockk<EJBException>())
        val response = ejbExceptionMapper.toResponse(ex)

        response shouldBe expectedResponse
    }

    @Test
    fun `cause OptimisticLockException`() {
        val expectedResponse = mockk<Response>()
        val ejbExceptionMapper = object : EJBExceptionMapper() {
            override val optimisticLockExceptionMapper: OptimisticLockExceptionMapper
                get() {
                    val optimisticLockExceptionMapper = mockk<OptimisticLockExceptionMapper>()
                    every { optimisticLockExceptionMapper.toResponse(any()) } returns expectedResponse
                    return optimisticLockExceptionMapper
                }
        }

        val ex = EJBException(mockk<OptimisticLockException>())
        val response = ejbExceptionMapper.toResponse(ex)

        response shouldBe expectedResponse
    }

    @Test
    fun `cause registered Exception`() {
        val npe = mockk<NullPointerException>()
        val expResponse = mockk<Response>()

        val npeHandlerMock = mockk<ExceptionMapper<NullPointerException>>()
        every { npeHandlerMock.toResponse(npe) } returns expResponse
        EJBExceptionMapperRegistration.register(NullPointerException::class.java, npeHandlerMock)

        val ejbExceptionMapper = EJBExceptionMapper()

        val ex = EJBException(npe)
        val response = ejbExceptionMapper.toResponse(ex)

        response shouldBe expResponse
    }

    @Test
    fun `cause _other_ exception`() {
        val ejbExceptionMapper = EJBExceptionMapper()

        val ex = EJBException(mockk<IllegalArgumentException>())
        val response = ejbExceptionMapper.toResponse(ex)

        response.status shouldBe Response.Status.INTERNAL_SERVER_ERROR.statusCode
        response.getHeaderString("cause") shouldBe ex.toString()
    }

}
