package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper

object EJBExceptionMapperRegistration {
    private val registeredExceptionMappers = mutableMapOf<Class<out Throwable>, ExceptionMapper<out Throwable>>()

    fun register(excClass: Class<out Throwable>, excMapper: ExceptionMapper<out Throwable>) {
        registeredExceptionMappers[excClass] = excMapper
    }

    fun <T : Throwable> buildResponse(excClass: Class<T>, ex: T): Response? {
        val exceptionMapper = registeredExceptionMappers[excClass] as ExceptionMapper<Throwable>?
        return exceptionMapper?.toResponse(ex)
    }
}