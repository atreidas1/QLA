package qla.modules.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class LogFile implements Iterable<Logline> {
	private LogConfiguration logConfiGuration;
	private BufferedReader reader;
	private String nextLine;
	private int lineNumber;
	private long fileSize;
	private long processedBytesCount;

	public LogFile(String pathToFile, LogConfiguration logConfiGuration) throws FileNotFoundException, UnsupportedEncodingException {
		String fileEncoding = logConfiGuration.getEncoding();
		if(fileEncoding == null || fileEncoding.isEmpty()) {
			fileEncoding = "UTF-8";
		}
		File file = new File(pathToFile);
		fileSize = file.length();
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), fileEncoding));
		this.logConfiGuration = logConfiGuration;
	}

	public LogConfiguration getLogConfiGuration() {
		return logConfiGuration;
	}

	public void setLogConfiGuration(LogConfiguration logConfiGuration) {
		this.logConfiGuration = logConfiGuration;
	}

	public boolean hasNextLine()  {
		if(this.nextLine == null) {
			try
			{
				this.nextLine = readLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return this.nextLine != null;
	}

	protected String nextLine() throws IOException{
		String line = null;
		if(this.nextLine != null){
			line = this.nextLine;
			this.nextLine = null;
			return line;
		}
		return readLine();
	}

	public Logline nextLogline() {
		try
		{
			if(hasNextLine()) {
				String line = nextLine();
				if(isNewLogline(line)){
					Logline logline = buildLoglineObject(line);
					while(hasNextLine()){
						if(isNewLogline(nextLine)) {
							return logline;
						} else {
							logline.addLineToMessage(nextLine());
						}
					}
					return logline;
				} else {
					return new Logline(line, logConfiGuration);
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private boolean isNewLogline(String line) {
		if(logConfiGuration.getNewLoglinePatern() == null) {
			throw new IllegalStateException("Pattern for matching new logline is null!");
		}
		return logConfiGuration.getNewLoglinePatern().matcher(line).find();
	}

	private Logline buildLoglineObject(String line) {
		Logline logline = new Logline(line, logConfiGuration);
		logline.setNumber(lineNumber);
		return logline;
	}

	protected String readLine() throws IOException {
		String line =  reader.readLine();
		if(line != null){
			lineNumber ++;
			processedBytesCount += line.getBytes().length;
		}
		return line;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public long getProcessedBytesCount() {
		return processedBytesCount;
	}

	public void setProcessedBytesCount(long processedBytesCount) {
		this.processedBytesCount = processedBytesCount;
	}

	@Override
	public Iterator<Logline> iterator()
	{
		return new Iterator<Logline>()
		{
			@Override
			public boolean hasNext()
			{
				return hasNextLine();
			}

			@Override
			public Logline next()
			{
				return nextLogline();
			}
		};
	}

	@Override
	public void forEach(Consumer<? super Logline> action)
	{
		for (Logline logline : this)
		{
			action.accept(logline);
		}
	}

	@Override
	public Spliterator<Logline> spliterator()
	{
		throw new UnsupportedOperationException("This functionality hasn't been implemented yet.");
	}

}
