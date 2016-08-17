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

/**
 * <p>
 * Description: Generating mutants API for command line version
 * </p>
 *
 * @author Lin Deng
 * @version 1.0
 */

package mujava.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.beust.jcommander.JCommander;

import mujava.MutationSystem;
import mujava.OpenJavaException;
import mujava.TraditionalMutantsGeneratorCLI;
import mujava.ClassMutantsGenerator;
import mujava.ClassMutantsGeneratorCLI;

public class genmutes {
	static String sessionName = new String();
	static String muJavaHomePath = new String();

	public static void main(String[] args) throws Exception {
		genmutesCom jct = new genmutesCom();
		new JCommander(jct, args);

		// check session name
		if (jct.getParameters().size() > 1) {
			Util.Error("Has more parameters than needed.");
			return;
		}

		// set session name
		sessionName = jct.getParameters().get(0);

		Util.loadConfig(sessionName);
		muJavaHomePath = Util.mujavaHome;

		// check if debug mode
		if (jct.isDebug()) {
			Util.debug = true;
		}

		// get all existing session name
		File folder = new File(muJavaHomePath);
		// check if the config file has defined the correct folder
		if (!folder.isDirectory()) {
			Util.Error("ERROR: cannot locate the folder specified in " + sessionName + ".config");
			return;
		}
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles == null) {
			Util.Error("ERROR: no files in the muJava home folder: " + muJavaHomePath);
			return;
		}
		List<String> fileNameList = new ArrayList<>();
		for (File file : listOfFiles) {
			fileNameList.add(file.getName());
		}

		// check if session is already created.
		if (!fileNameList.contains(sessionName)) {
			Util.Error("Session does not exist.");
			return;
		}

		// @author Evan Valvis
		String sessionFolder = muJavaHomePath + "/" + sessionName;
		String basePath = new File(sessionFolder + "/src/").getCanonicalPath();

		List<File> listOfFilesInSession = new ArrayList<File>();
		Util.Debug("Sessionfolder: " + sessionFolder);
		Util.Debug("BasePath: " + basePath);
		Util.listFiles(basePath, listOfFilesInSession);
		Util.Debug("SrcFolder '" + basePath + "' contains " + listOfFilesInSession.size() + " files");
		
		ArrayList<String> fileList = new ArrayList<String>();
		for (int i = 0; i < listOfFilesInSession.size(); i++) {
			String temp = listOfFilesInSession.get(i).getCanonicalPath().replace("\\", "/");
			if (temp.endsWith(".java")) {
				fileList.add(temp.substring(basePath.length() + 1)); //.substring(sessionFolder.length(), temp.length());
			}
		}
		String[] file_list = (String[]) fileList.toArray(new String[0]);

		// get all mutation operators selected
		HashMap<String, List<String>> ops = new HashMap<String, List<String>>(); // used for add random percentage and maximum


		String[] paras = new String[] {"1", "0"};
		if (jct.getAll() || jct.getAllAll()) // all is selected, add all operators
		{
			// if all is selected, all mutation operators are added
			ops.put("AORB", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("AORS", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("AOIU", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("AOIS", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("AODU", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("AODS", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("ROR", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("COR", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("COD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("COI", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("SOR", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("LOR", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("LOI", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("LOD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("ASRS", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("SDL", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("ODL", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("VDL", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("CDL", new ArrayList<String>(Arrays.asList(paras)));
			// ops.put("SDL", jct.getAll());
		}
		if (jct.getAllAll()) { // add all class mutants too
			ops.put("IHI", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("IHD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("IOD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("IOP", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("IOR", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("ISI", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("ISD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("IPC", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("PNC", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("PMD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("PPD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("PCI", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("PCC", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("PCD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("PRV", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("OMR", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("OMD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("OAN", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("JTI", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("JTD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("JSI", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("JSD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("JID", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("JDC", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("EOA", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("EOC", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("EAM", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("EMM", new ArrayList<String>(Arrays.asList(paras)));
		}
		if (!(jct.getAll() || jct.getAllAll())) { // if not all, add selected ops to the list
			// Traditional Mutants
			if (jct.getAORB()) ops.put("AORB", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getAORS()) ops.put("AORS", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getAOIU()) ops.put("AOIU", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getAOIS()) ops.put("AOIS", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getAODU()) ops.put("AODU", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getAODS()) ops.put("AODS", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getROR()) ops.put("ROR", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getCOR()) ops.put("COR", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getCOD()) ops.put("COD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getCOI()) ops.put("COI", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getSOR()) ops.put("SOR", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getLOR()) ops.put("LOR", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getLOI()) ops.put("LOI", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getLOD()) ops.put("LOD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getASRS()) ops.put("ASRS", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getSDL()) ops.put("SDL", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getVDL()) ops.put("VDL", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getODL()) ops.put("ODL", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getCDL()) ops.put("CDL", new ArrayList<String>(Arrays.asList(paras)));

			// Class Mutants
			if (jct.getIHI()) 	ops.put("IHI", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getIHD()) 	ops.put("IHD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getIOD()) 	ops.put("IOD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getIOP()) 	ops.put("IOP", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getIOR()) 	ops.put("IOR", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getISI()) 	ops.put("ISI", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getISD()) 	ops.put("ISD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getIPC()) 	ops.put("IPC", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getPNC()) 	ops.put("PNC", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getPMD()) 	ops.put("PMD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getPPD()) 	ops.put("PPD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getPCI()) 	ops.put("PCI", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getPCC()) 	ops.put("PCC", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getPCD()) 	ops.put("PCD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getPRV()) 	ops.put("PRV", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getOMR()) 	ops.put("OMR", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getOMD()) 	ops.put("OMD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getOAN()) 	ops.put("OAN", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getJTI()) 	ops.put("JTI", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getJTD()) 	ops.put("JTD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getJSI()) 	ops.put("JSI", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getJSD()) 	ops.put("JSD", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getJID()) 	ops.put("JID", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getJDC()) 	ops.put("JDC", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getEOA()) 	ops.put("EOA", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getEOC()) 	ops.put("EOC", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getEAM()) 	ops.put("EAM", new ArrayList<String>(Arrays.asList(paras)));
			if (jct.getEMM()) 	ops.put("EMM", new ArrayList<String>(Arrays.asList(paras)));
		}

		// add default option "all" (traditional)
		if (ops.size() == 0) {
			ops.put("AORB", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("AORS", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("AOIU", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("AOIS", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("AODU", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("AODS", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("ROR", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("COR", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("COD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("COI", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("SOR", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("LOR", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("LOI", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("LOD", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("ASRS", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("SDL", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("ODL", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("VDL", new ArrayList<String>(Arrays.asList(paras)));
			ops.put("CDL", new ArrayList<String>(Arrays.asList(paras)));
		}

		// String[] tradional_ops = ops.toArray(new String[0]);
		// set system
		setJMutationStructureAndSession(sessionName);
		// MutationSystem.setJMutationStructureAndSession(sessionName);
		MutationSystem.recordInheritanceRelation();
		// generate mutants
		generateMutants(file_list, ops);

		// System.exit(0);
	}

	private static void setJMutationStructureAndSession(String sessionName) {

		// MutationSystem.SYSTEM_HOME

		muJavaHomePath = muJavaHomePath + "/" + sessionName;
		MutationSystem.SYSTEM_HOME = muJavaHomePath;
		MutationSystem.SRC_PATH = muJavaHomePath + "/src";
		MutationSystem.CLASS_PATH = muJavaHomePath + "/classes";
		MutationSystem.MUTANT_HOME = muJavaHomePath + "/result";
		MutationSystem.TESTSET_PATH = muJavaHomePath + "/testset";

	}

	public static void generateMutants(String[] file_list,
			HashMap<String, List<String>> traditional_ops) {

		for (int i = 0; i < file_list.length; i++) {
			// file_name = ABSTRACT_PATH - MutationSystem.SRC_PATH
			// For example: org/apache/bcel/Class.java
			String file_name = file_list[i];
			try {
				System.out.println((i + 1) + " : " + file_name);
				// [1] Examine if the target class is interface or abstract
				// class
				// In that case, we can't apply mutation testing.

				// Generate class name from file_name
				String temp = file_name.substring(0, file_name.length() - 5);
				String class_name = "";
				for (int j = 0; j < temp.length(); j++) {
					if ((temp.charAt(j) == '\\') || (temp.charAt(j) == '/')) {
						class_name = class_name + ".";
					} else {
						class_name = class_name + temp.charAt(j);
					}
				}
				//Util.Debug("Constructing package from '" + temp + "' gives '" + class_name + "'");				

				muJavaHomePath = muJavaHomePath + "/" + sessionName;
				int class_type = MutationSystem.getClassTypeFromSource(sessionName + "/src/" + file_name);

				if (class_type == MutationSystem.NORMAL) { // do nothing
				} else if (class_type == MutationSystem.MAIN) {
					System.out.println(" -- " + file_name + " class contains 'static void main()' method.");
					System.out.println(
							"		Pleas note that mutants are not generated for the 'static void main()' method");
				} else {
					switch (class_type) {
						case MutationSystem.INTERFACE:
							System.out.println("\t-- Can't apply because class is 'interface' ");
							break;
						case MutationSystem.ABSTRACT:
							System.out.println("\t-- Can't apply because class is 'abstract' class ");
							break;
						case MutationSystem.APPLET:
							System.out.println("\t-- Can't apply because class is 'applet' class ");
							break;
						case MutationSystem.GUI:
							System.out.println("\t-- Can't apply because class is 'GUI' class ");
							break;
						case -1:
							System.out.println("\t-- Can't apply because class not found ");
							break;
					}

					deleteDirectory();
					continue;
				}

				// [2] Apply mutation testing
				setMutationSystemPathFor(file_name);

				File original_file = new File(MutationSystem.SRC_PATH, file_name);

				String[] opArray = traditional_ops.keySet().toArray(new String[0]);

				TraditionalMutantsGeneratorCLI tmGenEngine;
				tmGenEngine = new TraditionalMutantsGeneratorCLI(original_file, opArray);
				tmGenEngine.makeMutants();
				tmGenEngine.compileMutants();

				ClassMutantsGeneratorCLI tmGenEngineC;
				tmGenEngineC = new ClassMutantsGeneratorCLI(original_file, opArray);
				tmGenEngineC.makeMutants();
				tmGenEngineC.compileMutants();

				// Lin add printing total mutants
				int total_mutants = 0;
					{
					// get all file names of traditional mutants
					File folder = new File(MutationSystem.MUTANT_HOME + "/" + class_name + "/" + MutationSystem.TM_DIR_NAME);
					File[] listOfMethods = folder.listFiles();

					//ArrayList<String> fileNameList = new ArrayList<>();
					for (File method : listOfMethods) {
						//fileNameList.add(method.getName());
						if(method.isDirectory())
						{
							File[] listOfMutants = method.listFiles();
							total_mutants = total_mutants+listOfMutants.length;

						}
					}
				}
				{
					// get all file names of class mutants
					File folder = new File(MutationSystem.MUTANT_HOME + "/" + class_name + "/" + MutationSystem.CM_DIR_NAME);
					File[] listOfMethods = folder.listFiles();

					//ArrayList<String> fileNameList = new ArrayList<>();
					for (File method : listOfMethods) {
						//fileNameList.add(method.getName());
						if(method.isDirectory())
						{
							total_mutants++;
						}
					}
				}

				// File muTotalFile = new File(MutationSystem.MUTANT_PATH,"mutation_log");
				// String strLine;
				// LineNumberReader lReader = new LineNumberReader(new FileReader(muTotalFile));
				// int line = 0;
				// while ((strLine=lReader.readLine()) != null)
				// {
				// line++;
				// }

				System.out.println("------------------------------------------------------------------");
				System.out.println(
						"Total mutants gnerated for " + file_name + ": " + Integer.toString(total_mutants));



			} catch (OpenJavaException oje) {
				System.out.println("[OJException] " + file_name + " " + oje.toString());
				// System.out.println("Can't generate mutants for " +file_name +
				// " because OpenJava " + oje.getMessage());
				deleteDirectory();
			} catch (Exception exp) {
				System.out.println("[Exception] " + file_name + " " + exp.toString());
				exp.printStackTrace();
				// System.out.println("Can't generate mutants for " +file_name +
				// " due to exception" + exp.getClass().getName());
				// exp.printStackTrace();
				deleteDirectory();
			} catch (Error er) {
				System.out.println("[Error] " + file_name + " " + er.toString());
				// System.out.println("Can't generate mutants for " +file_name +
				// " due to error" + er.getClass().getName());
				deleteDirectory();
			}
		}
		// runB.setEnabled(true);
		// parent_frame.cvPanel.refreshEnv();
		// parent_frame.tvPanel.refreshEnv();
		// System.out
		// .println("------------------------------------------------------------------");
		// System.out.println(" All files are handled"); // need to say how many
		// mutants are generated

	}

	static void deleteDirectory() {
		File originalDir = new File(MutationSystem.MUTANT_HOME + "/" + MutationSystem.DIR_NAME + "/"
				+ MutationSystem.ORIGINAL_DIR_NAME);
		while (originalDir.delete()) { // do nothing?
		}

		File cmDir = new File(MutationSystem.MUTANT_HOME + "/" + MutationSystem.DIR_NAME + "/"
				+ MutationSystem.CM_DIR_NAME);
		while (cmDir.delete()) { // do nothing?
		}

		File tmDir = new File(MutationSystem.MUTANT_HOME + "/" + MutationSystem.DIR_NAME + "/"
				+ MutationSystem.TM_DIR_NAME);
		while (tmDir.delete()) { // do nothing?
		}

		File myHomeDir = new File(MutationSystem.MUTANT_HOME + "/" + MutationSystem.DIR_NAME);
		while (myHomeDir.delete()) { // do nothing?
		}
	}

	static void setMutationSystemPathFor(String file_name) {
		try {
			String temp;
			temp = file_name.substring(0, file_name.length() - ".java".length());
			temp = temp.replace('/', '.');
			temp = temp.replace('\\', '.');
			int separator_index = temp.lastIndexOf(".");

			if (separator_index >= 0) {
				MutationSystem.CLASS_NAME = temp.substring(separator_index + 1, temp.length());
			} else {
				MutationSystem.CLASS_NAME = temp;
			}

			String mutant_dir_path = MutationSystem.MUTANT_HOME + "/" + temp;
			File mutant_path = new File(mutant_dir_path);
			mutant_path.mkdir();

			String class_mutant_dir_path = mutant_dir_path + "/" + MutationSystem.CM_DIR_NAME;
			File class_mutant_path = new File(class_mutant_dir_path);
			class_mutant_path.mkdir();

			String traditional_mutant_dir_path = mutant_dir_path + "/" + MutationSystem.TM_DIR_NAME;
			File traditional_mutant_path = new File(traditional_mutant_dir_path);
			traditional_mutant_path.mkdir();

			String original_dir_path = mutant_dir_path + "/" + MutationSystem.ORIGINAL_DIR_NAME;
			File original_path = new File(original_dir_path);
			original_path.mkdir();

			MutationSystem.CLASS_MUTANT_PATH = class_mutant_dir_path;
			MutationSystem.TRADITIONAL_MUTANT_PATH = traditional_mutant_dir_path;
			MutationSystem.ORIGINAL_PATH = original_dir_path;
			MutationSystem.DIR_NAME = temp;
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
