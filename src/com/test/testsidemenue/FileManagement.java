package com.test.testsidemenue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;

public class FileManagement
{
	private String fileName = "siane.txt";
	private Context context;
	private File myDir ;
	
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
	
	public FileManagement(Context context)
	{
		this.context = context;
		myDir = context.getFilesDir();
	}

	public void writeToFile(String[] data)
	{
		try
		{
			if( null != data[2] && !data[2].equalsIgnoreCase("") && !data[2].equalsIgnoreCase("0"))
			{
				if(null != data[1] && !data[1].equalsIgnoreCase("") && !data[1].equalsIgnoreCase("0"))
				{
					long end = Long.parseLong(data[2]);
					data[2] = sdf.format(new Date(end));
					
					long start = Long.parseLong(data[1]);
					data[1] = sdf.format(new Date(start));
					
					long diff = end - start;
					int minutes = (int)(diff / 60 )/1000;
					System.out.println("Call lasted : " + minutes + " minute(s)");
					
					int initialmins = Integer.parseInt(data[0]);
					data[0] = Integer.toString(initialmins - minutes); 
				}
			}
			
			String dir = myDir.getAbsolutePath();
			System.out.println(dir);
			FileOutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			StringBuffer buff = new StringBuffer();
			for(String str : data)
			{
				buff.append(str).append("\n");
			}
			out.write(buff.toString().getBytes());
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	

	public String[] readFromFile()
	{
		String[] ret = {"-1","0","0"};
		try
		{
			InputStream inputStream = context.openFileInput(fileName);
			if (inputStream != null)
			{
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				int count = 0;
				while ((receiveString = bufferedReader.readLine()) != null)
				{
					ret[count] = receiveString;
					count++;
				}

				inputStream.close();
			}
		}
		catch (FileNotFoundException e)
		{
			writeToFile(ret);
		}
		catch (IOException e)
		{
		}

		return ret;
	}

}
