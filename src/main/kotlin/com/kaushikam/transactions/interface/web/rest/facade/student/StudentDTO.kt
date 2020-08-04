package com.kaushikam.transactions.`interface`.web.rest.facade.student

import com.kaushikam.transactions.domain.model.student.Student
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class StudentDTO(
    val id: Int? = null,

    @field:NotBlank(message = "{student.name.notBlank}")
    val name: String,

    @field:Min(value = 5, message = "{student.age.min}")
    @field:Max(value = 15, message = "{student.age.max}")
    val age: Int
) {
    constructor(student: Student): this(id = student.id, name = student.name, age = student.age)
}