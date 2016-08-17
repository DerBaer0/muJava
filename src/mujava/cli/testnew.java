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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.beust.jcommander.JCommander;

/**
 * <p>
 * Description: Create new test session API for command line version Creates a new test session. It
 * means, it creates all the files necessary to run a test on a java program.
 * </p>
 *
 * @author Lin Deng
 * @version 1.0
 */
public class testnew {
	static String sessionName = new String();
	static String muJavaHomePath = new String();

	public static void main(String[] args) throws IOException {
		testnewCom jct = new testnewCom();
		new JCommander(jct, args);

		// check if debug mode
		if (jct.isDebug() || jct.isDebugMode()) {
			Util.debug = true;
		}
	
		// set first parameter as the session name
		sessionName = jct.getParameters().get(0);

		Util.loadConfig(sessionName);
		muJavaHomePath = Util.mujavaHome;

		ArrayList<String> srcFiles = new ArrayList<>();

		for (int i = 1; i < jct.getParameters().size(); i++) {
			srcFiles.add(jct.getParameters().get(i));
		}

		// get all existing session name
		File folder = new File(muJavaHomePath);
		if (!folder.isDirectory()) {
			Util.Error("ERROR: cannot locate the folder specified in mujava.config");
			return;
		}
		File[] listOfFiles = folder.listFiles();
		// check the specified folder has files or not
		if (listOfFiles == null) {
			Util.Error("ERROR: no files in the muJava home folder " + muJavaHomePath);
			return;
		}
		List<String> fileNameList = new ArrayList<>();
		for (File file : listOfFiles) {
			fileNameList.add(file.getName());
		}

		// check if the session is new or not
		if (fileNameList.contains(sessionName)) {
			Util.Error("Session already exists. (Folder " + sessionName + " in " + muJavaHomePath + ")");
		} else {
			// create sub-directory for the session
			setupSessionDirectory(sessionName);

			// move src files into session folder
			for (String srcFile : srcFiles) {
				File source = new File(srcFile);
			
				boolean result = false;
				if (source.isDirectory()) {
					source = new File(srcFile + "/" + Util.srcBase);
					String session_dir_path = muJavaHomePath + "/" + sessionName;
					File dest = new File(session_dir_path + "/src/");
					FileUtils.copyDirectory(source, dest);
					if (!Util.doCompile) { // FIXME: need else path?
						// copy (hopefully previously compiled) classes
						File destCls = new File(session_dir_path + "/classes/");
						String clsDir = source + "/classes"; // wild guess
						if (Util.classesDir != null) {
							clsDir = srcFile + "/" + Util.classesDir;
						}
						FileUtils.copyDirectory(new File(clsDir), destCls);
					}
					result = true;
				} else {
					// @author Evan Valvis
					// we want to be able to keep the source files' packages
					String temp = source.getAbsolutePath().replace("\\", "/");
					String packages = temp.substring(temp.indexOf("src/") + 4);
					// if there are no packages, we want this string to contain just a /
					String packageDirectories = "/";
					if (packages.lastIndexOf("/") != -1) {
						packageDirectories += packages.substring(0, packages.lastIndexOf("/"));
					}
					File desc = new File(muJavaHomePath + "/" + sessionName + "/src" + packageDirectories);
					FileUtils.copyFileToDirectory(source, desc);

					// compile src files
					if (Util.doCompile) {
						result = compileSrc(srcFile);
						if (!result) {
							Util.Error("Failed compiling source location " + srcFile + ". Aborting.");
							return;
						}
					} else {
						File destCls = new File(muJavaHomePath + "/" + sessionName + "/classes" + packageDirectories);
						File clsSrc = new File(source.getAbsolutePath().replace(".java", ".class"));
						FileUtils.copyFileToDirectory(clsSrc, destCls);
						result = true;
					}
				}
			}
			Util.Print("Created Session with " + srcFiles.size() + " source locations");
		}

		// System.exit(0);
	}

	private static void setupSessionDirectory(String sessionName) {
		String session_dir_path = muJavaHomePath + "/" + sessionName;
		// Util.Print(mutant_dir_path);

		// build the session folders

		makeDir(new File(session_dir_path));
		makeDir(new File(session_dir_path + "/src"));
		makeDir(new File(session_dir_path + "/classes"));
		makeDir(new File(session_dir_path + "/result"));
		makeDir(new File(session_dir_path + "/testset"));

	}

	/*
	 * compile the src and put it into session's classes folder
	 */
	public static boolean compileSrc(String srcName) {
		String session_dir_path = muJavaHomePath + "/" + sessionName;

		// check if absolute path or not
		if (!srcName.endsWith(".java")) {
			srcName += ".java";
		}
		File file = new File(srcName + ".java");
		if (!file.isAbsolute()) {
			srcName = muJavaHomePath + "/src" + java.io.File.separator + srcName;
		}

		String[] args = new String[] {"-d", session_dir_path + "/classes", srcName};
		int status = com.sun.tools.javac.Main.compile(args);

		if (status != 0) {
			Util.Error("Can't compile src file, please compile manually.");
			return false;
		} else {
			Util.Print("Source file is compiled successfully.");
		}
		return true;

	}

	/*
	 * build the directory
	 */

	static void makeDir(File dir) {
		Util.DebugPrint("\nMake " + dir.getAbsolutePath() + " directory...");
		boolean newly_made = dir.mkdir();
		if (!newly_made) {
			Util.Error(dir.getAbsolutePath() + " directory exists already.");
		} else {
			Util.DebugPrint("Making " + dir.getAbsolutePath() + " directory " + " ...done.");
		}
	}

}
