package org.example.springbootenumvalidation

import jakarta.validation.constraints.NotNull

data class Member(
  @field:NotNull(message = "id is required")
  val id: Long,
  @field:NotNull(message = "name is required")
  val name: String,
//  @field:EnumValid(enumClass = Status::class, message = "status is invalid")
//  @EnumNamePattern(regexp = "NEW|DEFAULT")
  @field:ValueOfEnum(enumClass = Status::class, message = "status is invalid")
  val status: String,
)
