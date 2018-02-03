package com.github.skiedrowski.tools.jee.rest.exceptionmapper

import javax.validation.ConstraintViolation
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class ConstraintViolationEntry(
    val fieldName: String,
    val wrongValue: String,
    val errorMessage: String
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
