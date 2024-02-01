package org.example.springbootenumvalidation

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController {
  @PostMapping("/members")
  fun create(
    @Valid
    @RequestBody
    member: Member
  ) {
    println(member)
  }
}
