package com.nadetdev.springbatch.processor;

import com.nadetdev.springbatch.entity.postgres.Student;
import com.nadetdev.springbatch.model.StudentCsv;
import com.nadetdev.springbatch.model.StudentJdbc;
import com.nadetdev.springbatch.model.StudentJson;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
//public class FirstItemProcessor implements ItemProcessor<Integer, Long>{
//public class FirstItemProcessor implements ItemProcessor<StudentJdbc, StudentJson> {
//public class FirstItemProcessor implements ItemProcessor<StudentCsv, StudentJson> {
public class FirstItemProcessor implements ItemProcessor<Student, com.nadetdev.springbatch.entity.mysql.Student> {

	@Override
	// public Long process(Integer item) throws Exception {
	// public StudentJson process(StudentJdbc item) throws Exception {
	//public StudentJson process(StudentCsv item) throws Exception {
	public com.nadetdev.springbatch.entity.mysql.Student process(Student item) throws Exception {
		
		System.out.println(item.getId());
		/*
		 * System.out.println("Inside Item Processor");
		 * 
		 * 
		 * if(item.getId() == 3) {
		 * 
		 * throw new NullPointerException(); }
		 * 
		 * 
		 * StudentJson studentJson = new StudentJson();
		 * 
		 * studentJson.setId(item.getId());
		 * studentJson.setFirstName(item.getFirstName());
		 * studentJson.setLastName(item.getLastName());
		 * studentJson.setEmail(item.getEmail());
		 * 
		 * return studentJson;
		 */
		
		com.nadetdev.springbatch.entity.mysql.Student student = new com.nadetdev.springbatch.entity.mysql.Student();
		
		student.setId(item.getId());
		student.setFirstName(item.getFirstName());
		student.setLastName(item.getLastName());
		student.setDeptId(item.getDeptId());
		student.setEmail(item.getEmail());
		student.setIsActive(
				item.getIsActive() != null ?
				Boolean.valueOf(item.getIsActive()) : false
				);
		
		return student;
	}

}
