package org.example.springbootenumvalidation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException
import kotlin.reflect.KClass


//@MustBeDocumented
//@Constraint(validatedBy = [EnumValidator::class])
//@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
//@Retention(AnnotationRetention.RUNTIME)
//annotation class ValidEnum(
//  val message: String = "Invalid value. This is not permitted.",
//  val groups: Array<KClass<*>> = [],
//  val payload: Array<KClass<out Payload>> = [],
//  val enumClass: KClass<out Enum<*>>
//)

@MustBeDocumented
@Constraint(validatedBy = [EnumValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnumValid(
  val enumClass: KClass<out Enum<*>>,
  val message: String = "invalid value",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Any>> = []
)
@MustBeDocumented
@Constraint(validatedBy = [EnumNamePatternValidator::class])
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnumNamePattern(
  val regexp: String,
  val message: String = "must match \"{regexp}\"",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = []
)
class EnumNamePatternValidator : ConstraintValidator<EnumNamePattern, Enum<*>> {
  private lateinit var pattern: Pattern
  
  override fun initialize(annotation: EnumNamePattern) {
    try {
      pattern = Pattern.compile(annotation.regexp)
    } catch (e: PatternSyntaxException) {
      throw IllegalArgumentException("Given regex is invalid", e)
    }
  }
  
  override fun isValid(value: Enum<*>?, context: ConstraintValidatorContext): Boolean {
    if (value == null) {
      return true
    }
    
    val matcher = pattern.matcher(value.name)
    return matcher.matches()
  }
}

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
