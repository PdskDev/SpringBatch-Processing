package com.nadetdev.springbatch.config;

import java.io.File;

import com.nadetdev.springbatch.listener.FirstJobListener;
import com.nadetdev.springbatch.listener.FirstStepListener;
import com.nadetdev.springbatch.model.StudentCsv;
import com.nadetdev.springbatch.processor.FirstItemProcessor;
import com.nadetdev.springbatch.reader.FirstItemReader;
import com.nadetdev.springbatch.service.SecondTasklet;
import com.nadetdev.springbatch.writer.FirstItemWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;

@Configuration
public class SampleJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory setBuilderFactory;
	
	@Autowired
	private SecondTasklet secondTasklet;
	
	@Autowired
	private FirstJobListener firstJobListener;
	
	@Autowired
	private FirstStepListener firstStepListener; 
	
	@Autowired
	private FirstItemReader firstItemReader;
	
	@Autowired
	private FirstItemProcessor firstItemProcessor;
	
	@Autowired
	private FirstItemWriter firstItemWriter;


	@Bean
	public Job firstJob() {

		return jobBuilderFactory.get("First Job")
				.incrementer(new RunIdIncrementer())
				.start(firstStep())
				.next(secondStep())
				.listener(firstJobListener)
				.build();
	}

	private Step firstStep() {

		return setBuilderFactory.get("First Step")
				.tasklet(firstTask())
				.listener(firstStepListener)
				.build();
	}

	private Tasklet firstTask() {

		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

				System.out.println("This is first Tasklet step");
				System.out.println("SEC = " + chunkContext.getStepContext().getStepExecutionContext());
				return RepeatStatus.FINISHED;
			}
		};
	}

	private Step secondStep() {

		return setBuilderFactory.get("Second Step")
				.tasklet(secondTasklet)
				.build();
	}
	
	@Bean
	public Job secondJob() {

		return jobBuilderFactory.get("Second Job")
				.incrementer(new RunIdIncrementer())
				.start(firstChunckStep())
				.build();
	}
	
	
	private Step firstChunckStep() {

		return setBuilderFactory.get("First Chunk Step")
				.<StudentCsv, StudentCsv>chunk(3)
				//.reader(firstItemReader)
				.reader(flatFileItemReader())
				//.processor(firstItemProcessor)
				.writer(firstItemWriter)
				.build();
	}
	
	
	
	public FlatFileItemReader<StudentCsv> flatFileItemReader(){
		
		FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<>();
		
		flatFileItemReader.setResource(new FileSystemResource(
				new File("D:\\Devs\\LocalGitRepository\\SpringBatch-Processing-Udemy\\SpringBatchTuto\\InputFile\\students2.csv")));
		
		flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames("id", "last_name", "first_name", "email");
						setDelimiter(DELIMITER_COMMA);
					}
				});
				
				setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>() {
					{
						setTargetType(StudentCsv.class);
					}
				});
		}
			
		});
		
		flatFileItemReader.setLinesToSkip(1);
		
		return flatFileItemReader;
	}

	
	
}
