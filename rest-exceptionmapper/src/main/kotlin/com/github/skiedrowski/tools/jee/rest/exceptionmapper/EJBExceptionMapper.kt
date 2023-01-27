package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import jakarta.ejb.EJBException
import jakarta.persistence.OptimisticLockException
import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
open class EJBExceptionMapper : ExceptionMapper<EJBException> {

    protected open val constraintViolationExceptionMapper = ConstraintViolationMapper()
    protected open val ejbExceptionMapper: EJBExceptionMapper by lazy { EJBExceptionMapper() } //avoid stack overflow ,-)
    protected open val optimisticLockExceptionMapper = OptimisticLockExceptionMapper()

    override fun toResponse(ex: EJBException): Response {
        val cause: Throwable? = ex.cause
        return when (cause) {
            null -> Response.serverError().header("cause", ex.toString()).build()
            is ConstraintViolationException -> constraintViolationExceptionMapper.toResponse(cause)
            is EJBException -> ejbExceptionMapper.toResponse(cause)
            is OptimisticLockException -> optimisticLockExceptionMapper.toResponse(cause)
            else -> {
                EJBExceptionMapperRegistration.buildResponse(cause.javaClass, cause)
                        ?: Response.serverError().header("cause", ex.toString()).build()
            }
        }
    }
}