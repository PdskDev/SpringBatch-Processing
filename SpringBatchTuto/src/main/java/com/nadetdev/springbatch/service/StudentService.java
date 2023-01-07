package com.nadetdev.springbatch.service;

import java.util.ArrayList;
import java.util.List;

import com.nadetdev.springbatch.model.StudentResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StudentService {
	
	List<StudentResponse> listStudent;
	
	public List<StudentResponse> restCallToGetStudents() {
	
		RestTemplate restTemplate = new RestTemplate();
		
		StudentResponse[] studentResponseArray = restTemplate.getForObject("http://localhost:8081/api/v1/students", StudentResponse[].class);
		
		listStudent = new ArrayList<>();
		
		for(StudentResponse sr : studentResponseArray) {
			
			listStudent.add(sr);
		}
		
		return listStudent;
	}
	
	public StudentResponse getStudentOneByOne() {
		
		if(listStudent == null) {
			restCallToGetStudents();
		}
		
		if(listStudent != null && !listStudent.isEmpty()) {
			
		return listStudent.remove(0);
		}
		
		return null;
	}

}
