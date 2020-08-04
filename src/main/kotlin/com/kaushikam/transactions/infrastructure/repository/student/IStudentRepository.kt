package com.kaushikam.transactions.infrastructure.repository.student

import com.kaushikam.transactions.domain.model.student.Student
import org.springframework.data.repository.CrudRepository

interface IStudentRepository: CrudRepository<Student, Int>