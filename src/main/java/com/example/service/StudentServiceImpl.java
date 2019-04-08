package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exception.StudentException;
import com.example.model.Student;
import com.example.repository.StudentDao;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Override
	@Transactional
	@CachePut(value = "caching", key = "#student.rollno")
	public Student createStudent(Student student) {
		return studentDao.save(student);
	}

	@Override
	@Cacheable(value = "caching", key = "#id")
	public Student getStudent(Integer id) throws StudentException {
		Optional<Student> list = studentDao.findById(id);
		if (list.isPresent() && id != null) {
			return list.get();
		} else {
			throw new StudentException("Bank not found");
		}
	}

}
