package com.kaushikam.transactions.application.impl

import com.kaushikam.transactions.application.IStudentService
import com.kaushikam.transactions.domain.model.student.Student
import com.kaushikam.transactions.infrastructure.repository.student.IStudentRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class StudentServiceImpl(
    private val repository: IStudentRepository
): IStudentService {
    override fun saveStudent(name: String, age: Int): Student {
        val student = Student(name = name, age = age)
        return repository.save(student)
    }

    override fun findStudent(id: Int): Student {
        return repository.findById(id).orElseThrow { IllegalArgumentException("There is no such student with id $id") }
    }
}