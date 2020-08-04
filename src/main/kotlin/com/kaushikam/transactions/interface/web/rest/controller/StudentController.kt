package com.kaushikam.transactions.`interface`.web.rest.controller

import com.kaushikam.transactions.`interface`.web.rest.facade.student.StudentDTO
import com.kaushikam.transactions.application.IStudentService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/student")
class StudentController(
    private val studentService: IStudentService
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun saveStudent(@Valid @RequestBody student: StudentDTO): ResponseEntity<StudentDTO> {
        val studentEntity = studentService.saveStudent(student.name, student.age)
        return ResponseEntity(StudentDTO(studentEntity), HttpStatus.CREATED)
    }

}