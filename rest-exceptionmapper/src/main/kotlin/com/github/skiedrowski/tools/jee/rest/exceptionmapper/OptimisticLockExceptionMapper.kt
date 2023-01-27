package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import jakarta.persistence.OptimisticLockException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class OptimisticLockExceptionMapper : ExceptionMapper<OptimisticLockException> {
    override fun toResponse(exception: OptimisticLockException): Response {
        return Response.status(Response.Status.CONFLICT)
            .header("cause", "conflict occurred: ${exception.entity}")
            .header("additional-info", exception.message)
            .build()
    }
}