package com.nadetdev.springbatch.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import com.nadetdev.springbatch.model.StudentCsv;

import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

@Component
public class SkipListener {

	@OnSkipInRead
	public void skipInRead(Throwable th) {

		if (th instanceof FlatFileParseException) {
			createFile(
					"D:\\Devs\\LocalGitRepository\\SpringBatch-Processing-Udemy\\SpringBatchTuto\\ChunkJob\\FirstChunkStep\\reader\\SkipInRead.txt",
					((FlatFileParseException) th).getInput());
		}
	}

	@OnSkipInProcess
	public void skipInProcess(StudentCsv studentCsv, Throwable th) {
		
			createFile(
					"D:\\Devs\\LocalGitRepository\\SpringBatch-Processing-Udemy\\SpringBatchTuto\\ChunkJob\\FirstChunkStep\\processor\\SkipInProcess.txt",
					studentCsv.toString());
	}

	public void createFile(String filePath, String data) {
		try (FileWriter fileWriter = new FileWriter(new File(filePath), true)) {

			fileWriter.write(data + ", " + new Date() + "\n");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
