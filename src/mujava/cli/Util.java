/**
 * Copyright (C) 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */



package mujava.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.io.IOUtils;

/**
 * <p>
 * Description: Uitility class for command line version
 * </p>
 *
 * @author Lin Deng
 * @version 1.0
 *
 */
public class Util {
	public static int Total = 0;
	public static boolean debug = false;
	public static String mujavaHome = ".";	// this is where the mutation folder will be located
	public static String srcBase = "";		// Base dir of the java sources (base for packaging paths)
	public static boolean doCompile = true;	// Compile the sources
	public static String classesDir = null;	// directory (relativ) containing precompiled classes (only if 'doCompile=false')
	public static String extraCP = "";		// extra classpath for compiling

	// all mutants in a class
	public static Vector mutants = new Vector();
	// killed mutants in a class
	public static Vector killed_mutants = new Vector();
	// live mutants in a class
	public static Vector live_mutants = new Vector();
	// eq mutants in a class
	public static Vector eq_mutants = new Vector();

	public static Map<String, String> finalTestResultsMap = new HashMap<>();
	public static Map<String, String> finalMutantResultsMap = new HashMap<>();

	public static void setUpVectors() {
		// all mutants in a class
		mutants = new Vector();
		// killed mutants in a class
		killed_mutants = new Vector();
		// live mutants in a class
		live_mutants = new Vector();
		// eq mutants in a class
		eq_mutants = new Vector();
	}

	public static void setUpMaps() {
		finalMutantResultsMap = new HashMap<>();
		finalMutantResultsMap = new HashMap<>();
	}

	public static void Error(String errorMsg) {
		System.err.println(errorMsg);
	}

	public static void DebugPrint(String msg) {
		Debug(msg);
	}

	public static void Debug(String msg) {
		if (debug)
			System.out.println("[DEBUG]\t" + msg);
	}

	public static void Print(String msg) {
		System.out.println(msg);
	}

	/**
	 * load config file
	 */
	static void loadConfig(String session) throws IOException {
		loadConfig("");
	}

	static void loadConfig(String session) throws IOException {
		if (session.equals("")) {
			session = "mujavaCLI";
		}
		try {
			Util.Print("Loading session '" + session + "'");
			FileInputStream inputStream = new FileInputStream(session + ".config");

			String input = IOUtils.toString(inputStream);
			String[] inputs = input.split("\n");

			for (String s : inputs) {
				if (s.indexOf("MuJava_HOME=") == 0) {
					Util.mujavaHome = s.replace("MuJava_HOME=", "");
				} else if (s.indexOf("Debug_mode=") == 0) {
					String deb = s.replace("Debug_mode=", "");
					if (deb.equals("true")) {
						Util.debug = true;
					} else {
						Util.debug = false;
					}
				} else if (s.indexOf("srcBase=") == 0) {
					Util.srcBase = s.replace("srcBase=", "");
				} else if (s.indexOf("extraCP=") == 0) {
					Util.extraCP = s.replace("extraCP=", "");
				} else if (s.indexOf("classesDir=") == 0) {
					Util.classesDir = s.replace("classesDir=", "");
				} else if (s.indexOf("doCompile=") == 0) {
					String dc = s.replace("doCompile=", "");
					if (dc.equals("true")) {
						Util.doCompile = true;
					} else {
						Util.doCompile = false;
					}
				} else {
					System.err.println("Could not parse line '" + s + "' in mujavaCLI.config. Ignoring.");
				}
			}

			inputStream.close();
		} catch (FileNotFoundException e) {
			System.err.println("Warning: " + session + ".config not found. Using default values");
			Util.mujavaHome = System.getenv("MUJAVA_HOME");
			Util.debug = false;
		}
		
		Util.Debug("setting mujavaHome to '" + Util.mujavaHome + "'");
		Util.Debug("setting srcBase to '" + Util.srcBase + "'");
	}

	/**
	 * List all the file of a directory, including sub directories
	 * 
	 * @param directoryName
	 * @param files
	 */
	public static void listFiles(String directoryName, List<File> files) {
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				files.add(file);
			} else if (file.isDirectory()) {
				listFiles(file.getAbsolutePath(), files);
			}
		}
	}
}
