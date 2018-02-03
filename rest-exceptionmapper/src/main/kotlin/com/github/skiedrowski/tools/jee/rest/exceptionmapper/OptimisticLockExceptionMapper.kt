package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import javax.persistence.OptimisticLockException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class OptimisticLockExceptionMapper : ExceptionMapper<OptimisticLockException> {
    override fun toResponse(exception: OptimisticLockException): Response {
        return Response.status(Response.Status.CONFLICT)
            .header("cause", "conflict occurred: ${exception.entity}")
            .header("additional-info", exception.message)
            .build()
    }
}