package org.example.springbootenumvalidation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [ValueOfEnumValidator::class])
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValueOfEnum(
  val enumClass: KClass<out Enum<*>>,
  val message: String = "must be any of enum {enumClass}",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = []
)

class ValueOfEnumValidator : ConstraintValidator<ValueOfEnum, CharSequence> {
  private lateinit var acceptedValues: List<String>
  
  override fun initialize(annotation: ValueOfEnum) {
    acceptedValues = annotation.enumClass.java.enumConstants.map { it.name }
  }
  
  override fun isValid(value: CharSequence?, context: ConstraintValidatorContext): Boolean {
    if (value == null) {
      return true
    }
    
    return acceptedValues.contains(value.toString())
  }
}
