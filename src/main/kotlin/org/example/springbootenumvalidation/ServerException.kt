package org.example.springbootenumvalidation

sealed class ServerException(
  val code: Int,
  override val message: String,
): RuntimeException(message)

data class NotFoundException(
  override val message: String,
): ServerException(404, message)
