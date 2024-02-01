package org.example.springbootenumvalidation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [EnumValidator::class])
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidEnum(
  val message: String = "Invalid value. This is not permitted.",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = [],
  val enumClass: KClass<out Enum<*>>
)

