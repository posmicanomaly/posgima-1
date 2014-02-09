package com.jpospisil.posgima1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SettingsImporter {

	public static int WIDTH = 0;
	public static int HEIGHT = 0;
	private ArrayList<String> settingsFile;
	public SettingsImporter() {
		settingsFile = new ArrayList<String>();
		File file = new File("resources/init.ini");
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine())
			{
				settingsFile.add(scanner.nextLine());
			}
			
			for(int i = 0; i < settingsFile.size(); i++)
			{
				if(i == 0)
					WIDTH = Integer.parseInt(settingsFile.get(i));
				else if(i == 1)
					HEIGHT = Integer.parseInt(settingsFile.get(i));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
