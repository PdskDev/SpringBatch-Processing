package com.nadetdev.springbatch.writer;

import java.util.List;

import com.nadetdev.springbatch.model.StudentCsv;
import com.nadetdev.springbatch.model.StudentJdbc;
import com.nadetdev.springbatch.model.StudentJson;
import com.nadetdev.springbatch.model.StudentResponse;
import com.nadetdev.springbatch.model.StudentXml;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
//public class FirstItemWriter implements ItemWriter<StudentCsv>{
public class FirstItemWriter implements ItemWriter<StudentJson> {
//public class FirstItemWriter implements ItemWriter<StudentXml> {
//public class FirstItemWriter implements ItemWriter<StudentJdbc> {
//public class FirstItemWriter implements ItemWriter<StudentResponse> {

	@Override
	// public void write(List<? extends StudentCsv> items) throws Exception {
	public void write(List<? extends StudentJson> items) throws Exception {
	//public void write(List<? extends StudentXml> items) throws Exception {
	//public void write(List<? extends StudentJdbc> items) throws Exception {
	//public void write(List<? extends StudentResponse> items) throws Exception {
		System.out.println("Inside Item Writer");
		items.stream().forEach(System.out::println);
	}

}
