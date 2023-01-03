package com.nadetdev.springbatch.writer;

import java.util.List;

import com.nadetdev.springbatch.model.StudentCsv;
import com.nadetdev.springbatch.model.StudentJson;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
//public class FirstItemWriter implements ItemWriter<StudentCsv>{
public class FirstItemWriter implements ItemWriter<StudentJson> {

	@Override
	// public void write(List<? extends StudentCsv> items) throws Exception {
	public void write(List<? extends StudentJson> items) throws Exception {
		System.out.println("Inside Item Writer");
		items.stream().forEach(System.out::println);

	}

}
