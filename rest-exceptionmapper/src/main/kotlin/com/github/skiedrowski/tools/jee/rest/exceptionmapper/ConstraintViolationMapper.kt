package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import javax.validation.ConstraintViolationException
import javax.ws.rs.core.GenericEntity
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class ConstraintViolationMapper : ExceptionMapper<ConstraintViolationException> {

    /**
     * Rather lengthy discussion:
     * * https://stackoverflow.com/questions/3290182/rest-http-status-codes-for-failed-validation-or-invalid-duplicate
     * * https://stackoverflow.com/questions/1959947/whats-an-appropriate-http-status-code-to-return-by-a-rest-api-service-for-a-val
     *
     * 400 - BAD_REQUEST (generic)
     * 406 - Not Acceptable
     * 417 - Expectation Failed
     * 412 - PRECONDITION_FAILED (I think this fits best, but it is not in Response.Status Enum)
     * 422 - UNPROCESSABLE_ENTITY
     */
    override fun toResponse(ex: ConstraintViolationException): Response {
        val constViolations = ex.constraintViolations
        val errorList = constViolations.map {
            ConstraintViolationEntry.build(
                it
            )
        }
        //IMPORTANT: put an ARRAY in here, not a ArrayList. ArrayList and other Collections
        //work if using Java but not if using Kotlin, see https://stackoverflow.com/questions/45282528/jax-rs-with-kotlin-messagebodywriter-not-found
        val entity = object : GenericEntity<Array<ConstraintViolationEntry>>(errorList.toTypedArray()) {}
        return Response.status(Response.Status.EXPECTATION_FAILED)
            .header("cause", "Validation failed")
            .entity(entity)
//                .type(MediaType.APPLICATION_JSON_TYPE)
            .build()
    }
}