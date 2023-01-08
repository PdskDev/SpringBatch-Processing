package com.nadetdev.springbatch.config;


import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.sql.DataSource;

import com.nadetdev.springbatch.listener.FirstJobListener;
import com.nadetdev.springbatch.listener.FirstStepListener;
import com.nadetdev.springbatch.model.StudentCsv;
import com.nadetdev.springbatch.model.StudentJdbc;
import com.nadetdev.springbatch.model.StudentJson;
import com.nadetdev.springbatch.model.StudentResponse;
import com.nadetdev.springbatch.model.StudentXml;
import com.nadetdev.springbatch.processor.FirstItemProcessor;
import com.nadetdev.springbatch.reader.FirstItemReader;
import com.nadetdev.springbatch.service.SecondTasklet;
//import com.nadetdev.springbatch.service.StudentService;
import com.nadetdev.springbatch.writer.FirstItemWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.JsonObjectMarshaller;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


@Configuration
public class SampleJob {

	//private static final String DELIMITER_COMMA = ",";

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
	
	//@Autowired
	//private StudentService studentService;
	
	/*
	 * @Autowired private DataSource dataSource;
	 */
	
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "spring.univertsitydatasource")
	public DataSource univertsityDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public Job firstJob() {

		return jobBuilderFactory.get("First Job").incrementer(new RunIdIncrementer()).start(firstStep())
				.next(secondStep()).listener(firstJobListener).build();
	}

	private Step firstStep() {

		return setBuilderFactory.get("First Step").tasklet(firstTask()).listener(firstStepListener).build();
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

		return setBuilderFactory.get("Second Step").tasklet(secondTasklet).build();
	}

	@Bean
	public Job secondJob() {

		return jobBuilderFactory.get("Second Job").incrementer(new RunIdIncrementer()).start(firstChunckStep()).build();
	}

	private Step firstChunckStep() {

		return setBuilderFactory.get("First Chunk Step")
				//.<StudentCsv, StudentCsv>chunk(3)
				//.<StudentJson, StudentJson>chunk(3)
				//.<StudentXml, StudentXml>chunk(3)
				//.<StudentJdbc, StudentJdbc>chunk(3)
				.<StudentCsv, StudentCsv>chunk(3)
				// .reader(firstItemReader)
				.reader(flatFileItemReader(null))
				//.reader(jsonItemReader(null))
				//.reader(staxEventItemReader(null))
				//.reader(jdbcCursorItemReader())
				//.reader(itemReaderAdapter())
				//.processor(firstItemProcessor)
				//.writer(firstItemWriter)
				//.writer(flatFileItemWriter(null))
				//.writer(jsonFileItemWriter(null))
				//.writer(staxEventItemWriter(null))
				.writer(jdbcBatchItemWriter())
				.build();
	}

	@StepScope
	@Bean
	public FlatFileItemReader<StudentCsv> flatFileItemReader(
			@Value("#{jobParameters['inputFile']}") FileSystemResource csvResourceFile) {

		FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<>();

		/*
		 * flatFileItemReader.setResource(new FileSystemResource( new File(
		 * "D:\\\\Devs\\\\LocalGitRepository\\\\SpringBatch-Processing-Udemy\\\\SpringBatchTuto\\\\InputFile\\\\students.cs"
		 * )));
		 */

		flatFileItemReader.setResource(csvResourceFile);

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

		// Alternative code
		/*
		 * DefaultLineMapper<StudentCsv> defaultLineMapper = new
		 * DefaultLineMapper<StudentCsv>();
		 * 
		 * DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		 * delimitedLineTokenizer.setNames("id", "last_name", "first_name", "email");
		 * delimitedLineTokenizer.setDelimiter(DELIMITER_COMMA);
		 * 
		 * defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		 * 
		 * BeanWrapperFieldSetMapper<StudentCsv> fieldSetMapper = new
		 * BeanWrapperFieldSetMapper<StudentCsv>();
		 * fieldSetMapper.setTargetType(StudentCsv.class);
		 * 
		 * defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		 * 
		 * flatFileItemReader.setLineMapper(defaultLineMapper);
		 * flatFileItemReader.setLinesToSkip(1);
		 */

		flatFileItemReader.setLinesToSkip(1);

		return flatFileItemReader;
	}
	
	@StepScope
	@Bean
	public JsonItemReader<StudentJson> jsonItemReader(
			@Value("#{jobParameters['inputFileJson']}") FileSystemResource jsonResourceFile) {
		
		JsonItemReader<StudentJson> jsonItemReader = new JsonItemReader<StudentJson>();
		
		jsonItemReader.setResource(jsonResourceFile);
		jsonItemReader.setJsonObjectReader(
				new JacksonJsonObjectReader<>(StudentJson.class));
		
		jsonItemReader.setMaxItemCount(5);
		jsonItemReader.setCurrentItemCount(2);
		
		return jsonItemReader;
	}
	
	
	@StepScope
	@Bean
	public StaxEventItemReader<StudentXml> staxEventItemReader(
			@Value("#{jobParameters['inputFileXml']}") FileSystemResource xmlResourceFile) {
		
		StaxEventItemReader<StudentXml> staxEventItemReader = new StaxEventItemReader<StudentXml>();
		staxEventItemReader.setResource(xmlResourceFile);
		staxEventItemReader.setFragmentRootElementName("student");
		staxEventItemReader.setUnmarshaller(new Jaxb2Marshaller() {
			{
				setClassesToBeBound(StudentXml.class);
			}
		});
		
		return staxEventItemReader;
	}
	
	
	public JdbcCursorItemReader<StudentJdbc> jdbcCursorItemReader() {
		
		JdbcCursorItemReader<StudentJdbc> jdbcCursorItemReader = new JdbcCursorItemReader<StudentJdbc>();
		
		jdbcCursorItemReader.setDataSource(univertsityDataSource());
		jdbcCursorItemReader.setSql(
				"select id, first_name as firstName, last_name as lastName, email from student");
		
		jdbcCursorItemReader.setRowMapper(
				new BeanPropertyRowMapper<StudentJdbc>(){
				{
					setMappedClass(StudentJdbc.class);
				}
				});
		
		//jdbcCursorItemReader.setCurrentItemCount(2);
		//jdbcCursorItemReader.setMaxItemCount(10);
		
		return jdbcCursorItemReader;
	}
	
	
	/*//API Resource Reader
	 * public ItemReaderAdapter<StudentResponse> itemReaderAdapter() {
	 * 
	 * ItemReaderAdapter<StudentResponse> itemReaderAdapter = new
	 * ItemReaderAdapter<>();
	 * 
	 * itemReaderAdapter.setTargetObject(studentService);
	 * itemReaderAdapter.setTargetMethod("getStudentOneByOne");
	 * itemReaderAdapter.setArguments(new Object[] {1L, "Test"});
	 * 
	 * return itemReaderAdapter; }
	 */
	
	
	@StepScope
	@Bean
	public FlatFileItemWriter<StudentJdbc> flatFileItemWriter(
			@Value("#{jobParameters['outputFile']}") FileSystemResource csvResourceFile) {
		
		FlatFileItemWriter<StudentJdbc> flatFileItemWriter = new FlatFileItemWriter<StudentJdbc>();
		
		flatFileItemWriter.setResource(csvResourceFile);
		
		flatFileItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
			
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("Id,First Name,Last Name,Email");
			}
		});
		
		flatFileItemWriter.setLineAggregator(new DelimitedLineAggregator<StudentJdbc>() {
			{ 
				//setDelimiter("|");
				setFieldExtractor(new BeanWrapperFieldExtractor<StudentJdbc>() {
					{ 
						setNames(new String[] {"id", "firstName", "lastName", "email"});
					}
				});
			}
		});
		
		flatFileItemWriter.setFooterCallback(new FlatFileFooterCallback() {
			@Override
			public void writeFooter(Writer writer) throws IOException {
				writer.write("Created @ " + new Date());
			}
		});
		return flatFileItemWriter;
	}
	
	@StepScope
	@Bean
	public JsonFileItemWriter<StudentJson> jsonFileItemWriter(
			@Value("#{jobParameters['outputFile']}") FileSystemResource jsonResourceFile) {
		
		JsonFileItemWriter<StudentJson> jsonFileItemWrite = new JsonFileItemWriter<>(jsonResourceFile, 
				new JacksonJsonObjectMarshaller<StudentJson>());
		
		return jsonFileItemWrite;
	}
	
	@StepScope
	@Bean
	public StaxEventItemWriter<StudentJdbc> staxEventItemWriter(
			@Value("#{jobParameters['outputFile']}") FileSystemResource xmlResourceFile) {
		
		StaxEventItemWriter<StudentJdbc> staxEventItemWriter = new StaxEventItemWriter<StudentJdbc>();
		
		staxEventItemWriter.setResource(xmlResourceFile);
		staxEventItemWriter.setRootTagName("students");
		staxEventItemWriter.setMarshaller(new Jaxb2Marshaller() {
			{ 
				setClassesToBeBound(StudentJdbc.class);
			}
		});
		
		return staxEventItemWriter;
	}
	
	@StepScope
	@Bean
	public JdbcBatchItemWriter<StudentCsv> jdbcBatchItemWriter(){
		
		JdbcBatchItemWriter<StudentCsv> jdbcBatchItemWriter = new JdbcBatchItemWriter<StudentCsv>();
		
		jdbcBatchItemWriter.setDataSource(univertsityDataSource());
		jdbcBatchItemWriter.setSql(
				"insert into student(id, first_name, last_name, email) values (:id, :firstName, :lastName, :email)");
		
		jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<StudentCsv>());
		
		return jdbcBatchItemWriter;
	}
	
	
	
	
	/*
	 * @StepScope
	 * 
	 * @Bean public JdbcBatchItemWriter<StudentJdbc> jdbcBatchItemWriter() {
	 * 
	 * JdbcBatchItemWriter<StudentJdbc> jdbcBatchItemWriter = new
	 * JdbcBatchItemWriter<StudentJdbc>();
	 * 
	 * return jdbcBatchItemWriter; }
	 */
	
	
	
	

}
