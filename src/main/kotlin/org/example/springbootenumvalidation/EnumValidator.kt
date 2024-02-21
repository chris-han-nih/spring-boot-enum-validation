package org.example.springbootenumvalidation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

//@Component
//class EnumValidator : ConstraintValidator<EnumValid, Enum<*>> {
//  private lateinit var annotation: EnumValid
//
//  override fun initialize(constraintAnnotation: EnumValid) {
//    this.annotation = constraintAnnotation
//  }
//
//  override fun isValid(value: Enum<*>?, context: ConstraintValidatorContext): Boolean {
//    val enumValues = this.annotation.enumClass.java.enumConstants
//    return enumValues?.any { it == value } == true
//  }
//}

import kotlin.reflect.KClass

class EnumValidator : ConstraintValidator<EnumValid, Enum<*>> {
  private lateinit var enumClass: KClass<out Enum<*>>
  
  override fun initialize(constraintAnnotation: EnumValid) {
    enumClass = constraintAnnotation.enumClass
  }
  
  override fun isValid(value: Enum<*>?, context: ConstraintValidatorContext): Boolean {
    if (value == null) return false
    return enumClass.java.enumConstants.any { it.name == value.name }
  }
}
