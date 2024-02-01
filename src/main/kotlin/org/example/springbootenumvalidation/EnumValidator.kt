package org.example.springbootenumvalidation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

@Component
class EnumValidator : ConstraintValidator<ValidEnum, Enum<*>> {
  private lateinit var annotation: ValidEnum
  
  override fun initialize(constraintAnnotation: ValidEnum) {
    this.annotation = constraintAnnotation
  }
  
  override fun isValid(value: Enum<*>?, context: ConstraintValidatorContext): Boolean {
    val enumValues = this.annotation.enumClass.java.enumConstants
    return enumValues?.any { it == value } == true
  }
}
