package com.nadetdev.springbatch.processor;

import com.nadetdev.springbatch.model.StudentCsv;
import com.nadetdev.springbatch.model.StudentJdbc;
import com.nadetdev.springbatch.model.StudentJson;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
//public class FirstItemProcessor implements ItemProcessor<Integer, Long>{
//public class FirstItemProcessor implements ItemProcessor<StudentJdbc, StudentJson> {
public class FirstItemProcessor implements ItemProcessor<StudentCsv, StudentJson> {

	@Override
	// public Long process(Integer item) throws Exception {
	// public StudentJson process(StudentJdbc item) throws Exception {
	public StudentJson process(StudentCsv item) throws Exception {
		
		System.out.println("Inside Item Processor");
		// return Long.valueOf(item);

		StudentJson studentJson = new StudentJson();

		studentJson.setId(item.getId());
		studentJson.setFirstName(item.getFirstName());
		studentJson.setLastName(item.getLastName());
		studentJson.setEmail(item.getEmail());

		return studentJson;
	}

}
