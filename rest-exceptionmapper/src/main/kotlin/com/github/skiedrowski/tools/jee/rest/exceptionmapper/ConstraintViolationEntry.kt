package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import jakarta.json.bind.annotation.JsonbPropertyOrder
import jakarta.validation.ConstraintViolation
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonbPropertyOrder("fieldName", "wrongValue", "errorMessage")
class ConstraintViolationEntry(
    var fieldName: String = "", //default value for Jsonb
    var wrongValue: String = "", //default value for Jsonb
    var errorMessage: String = "" //default value for Jsonb
) {

    companion object {
        fun build(violation: ConstraintViolation<*>): ConstraintViolationEntry {
            val fieldName = if (violation.propertyPath != null) violation.propertyPath.toString() else "<?field>"
            val invalidValue =
                if (violation.invalidValue != null) violation.invalidValue.toString() else "<?invalidValue>"
            val message = if (violation.message != null) violation.message else "<?message>"

            return ConstraintViolationEntry(
                fieldName,
                invalidValue,
                message
            )
        }
    }
}
