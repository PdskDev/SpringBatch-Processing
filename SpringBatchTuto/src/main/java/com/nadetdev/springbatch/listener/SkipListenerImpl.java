package com.nadetdev.springbatch.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import com.nadetdev.springbatch.model.StudentCsv;
import com.nadetdev.springbatch.model.StudentJson;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

@Component
public class SkipListenerImpl implements SkipListener<StudentCsv, StudentJson> {

	@Override
	public void onSkipInRead(Throwable th) {
		if (th instanceof FlatFileParseException) {
			createFile(
					"D:\\Devs\\LocalGitRepository\\SpringBatch-Processing-Udemy\\SpringBatchTuto\\ChunkJob\\FirstChunkStep\\reader\\SkipInRead.txt",
					((FlatFileParseException) th).getInput());
		}
		
	}

	@Override
	public void onSkipInWrite(StudentJson item, Throwable th) {
		createFile(
				"D:\\Devs\\LocalGitRepository\\SpringBatch-Processing-Udemy\\SpringBatchTuto\\ChunkJob\\FirstChunkStep\\writer\\SkipInWrite.txt",
				item.toString());
		
	}

	@Override
	public void onSkipInProcess(StudentCsv item, Throwable th) {
		createFile(
				"D:\\Devs\\LocalGitRepository\\SpringBatch-Processing-Udemy\\SpringBatchTuto\\ChunkJob\\FirstChunkStep\\processor\\SkipInProcess.txt",
				item.toString());
		
	}
	
	public void createFile(String filePath, String data) {
		try (FileWriter fileWriter = new FileWriter(new File(filePath), true)) {

			fileWriter.write(data + ", " + new Date() + "\n");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
