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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

// import openjava.test.stringPlay.stringPlay;

import com.beust.jcommander.JCommander;

import mujava.MutationSystem;
import mujava.TestExecuterCLI;
import mujava.test.NoMutantDirException;
import mujava.test.NoMutantException;
import mujava.test.TestResultCLI;

/**
 * <p>
 * Description: run mutants API for command line version
 * </p>
 *
 * @author Lin Deng
 * @version 1.0
 */
/*
 * Three execution modes:
 *
 * \item \textit{$-$default }
 *
 * ``\textit{$-$default}'' defines the default behavior of \textit{runmutes}. Each time it's run, it
 * reads in the result files. For each mutant m, test case t combination, if m is dead or
 * equivalent, t is NOT run on m. If m is live, t is run on m. At the end of execution, the new
 * result will be written out to the same result file with updates. E.G. first run, 200 mutants, 100
 * killed; second run, 200-100 mutants only, 50 killed; third run, 100-50 mutants, etc.
 *
 * \item \textit{$-$dead }
 *
 * ``\textit{$-$dead}'' defines the dead mode of \textit{runmutes}. Each time it's run, it reads in
 * the result files. For each mutant m, test case t combination, if m is dead, or live, t IS run on
 * m. Not run on equivalent mutants. This lets us find all the tests that kill m. At the end of
 * execution, the new result will be written out to the same result file with updates. E.G. first
 * run, 200 mutants, 100 killed; second run, 200 mutants again; third run, 200, etc.
 *
 * \item \textit{$-$fresh }
 *
 * ``\textit{$-$fresh}'' defines the fresh mode of \textit{runmutes}. Each time it's run, it does
 * NOT read in the result files. After execution, the result files are saved as new files, with the
 * current timestamp.
 *
 *
 * Optional parameter "-p": random choose mutants. Should compatible with 3 modes.
 *
 * -p + default: if p=0.5, total 100 mutants first run, 100*0.5=50 get selected to run, 40 killed;
 * second run, (50-40) *0.5 = 5 get selected to run, 1 killed; third run, (5-1)*0.5=2 get selected
 * to run, etc.
 *
 *
 * -p + dead: if p=0.5, total 100 mutants first run, 100*0.5=50 get selected to run, 40 killed;
 * second run, another 100*0.5=50 get selected to run, 30 killed; third run, another 100*0.5=50 get
 * selected to run, 35 killed, etc.
 *
 * -p + fresh: if p=0.5, total 100 mutants first run, 100*0.5=50 get selected to run, 40 killed;
 * second run, another 100*0.5=50 get selected to run, 30 killed; third run, another 100*0.5=50 get
 * selected to run, 35 killed, etc.
 *
 */

public class runmutes {

  public static String mode = "default";
  public static String muJavaHomePath = new String();
  public static boolean isSingleTestSet = true;
  public static boolean runEq = false;

  // default timeout
  private static int timeout_sec = 3000;

  /**
	* @param args
	* @throws Exception
	*/
  public static void main(String[] args) throws Exception {
    runmutesCom jct = new runmutesCom();

    new JCommander(jct, args);

    String targetClassName = null;
    String testSetName = null;
    double percentage = 1;
    String testSessionName = null;

    Util.loadConfig();
    muJavaHomePath = Util.mujavaHome;
    // check if debug mode
    if (jct.isDebug()) {
      Util.debug = true;
    }

    // add support for timeout
    // Lin 05232015
    if (jct.getTimeout() == -1)
    // -1 means there is no input for timeout
    // then do nothing, just use the default one
    {

    } else { // if there IS an option for timeout
      timeout_sec = 1000 * jct.getTimeout();
      if (timeout_sec <= 0)
        // if not a valid timeout, make it 3000
        timeout_sec = 3000;
    }

    // if only one parameter, it must be the session name
    // then, no testset specified, run all tests in testset folder
    List<String> testSetList = new ArrayList<>();
    if (jct.getParameters().size() == 1) {
      // read all test names
      testSessionName = jct.getParameters().get(0);
      // @author Evan Valvis
      String folder = muJavaHomePath + "/" + testSessionName + "/testset/";
      List<File> listOfFiles = new ArrayList<File>();
      Util.listFiles(folder, listOfFiles);

      for (File file : listOfFiles) {
        String fileName = file.getName();
        if (fileName.contains(".class")) {
          fileName = fileName.replace(".class", "");
          testSetList.add(fileName);
        }

      }
      isSingleTestSet = false;

    } else { // test set is specified
      // check the number of parameters
      if (jct.getParameters().size() > 2) {
        Util.Error("incorrect parameters.");
        return;
      }

      // set names
      testSetName = jct.getParameters().get(0);
      testSessionName = jct.getParameters().get(1);

      // make sure test file exists
      // @author Evan Valvis
      String folder = muJavaHomePath + "/" + testSessionName + "/testset/";
      List<File> listOfFiles = new ArrayList<File>();
      Util.listFiles(folder, listOfFiles);
      if (!hasTestFile(listOfFiles, testSetName, folder)) {
        Util.Error("can't find test file: " + testSetName);
        return;
      }
    }

    // get all existing session name
    File folder = new File(muJavaHomePath);
    // check if the config file has defined the correct folder
    if (!folder.isDirectory()) {
      Util.Error("ERROR: cannot locate the folder specified in mujava.config");
      return;
    }
    File[] listOfFiles = folder.listFiles();
    // null checking
    // check the specified folder has files or not
    if (listOfFiles == null) {
      Util.Error("ERROR: no files in the muJava home folder " + muJavaHomePath);
      return;
    }
    List<String> fileNameList = new ArrayList<>();
    for (File file : listOfFiles) {
      fileNameList.add(file.getName());
    }

    // check if session is already created
    if (!fileNameList.contains(testSessionName)) {
      Util.Error("Session does not exist.");
      return;
    }

    String[] file_list = new String[1];
    // @author Evan Valvis
    String sessionFolder = muJavaHomePath + "/" + testSessionName + "/classes/";
    List<File> listOfFilesInSession = new ArrayList<File>();
    Util.listFiles(sessionFolder, listOfFilesInSession);
    file_list = new String[listOfFilesInSession.size()];
    for (int i = 0; i < listOfFilesInSession.size(); i++) {
      String temp = listOfFilesInSession.get(i).getAbsolutePath().replace("\\", "/");
      file_list[i] = temp.substring(sessionFolder.length(), temp.length());
    }

    if (jct.getP() > 0 && jct.getP() <= 1)
      percentage = jct.getP();
    else if (jct.getP() == 0)
      percentage = 1;
    else {
      Util.Error("Percentage must between 0 and 1");
      return;
    }

    ArrayList<String> typeList = new ArrayList<String>();
		if (jct.isAll() || jct.isAllAll()) // all is selected, add all operators
	 {

		// if all is selected, all mutation operators are added
		typeList.add("AORB");
		typeList.add("AORS");
		typeList.add("AOIU");
		typeList.add("AOIS");
		typeList.add("AODU");
		typeList.add("AODS");
		typeList.add("ROR");
		typeList.add("COR");
		typeList.add("COD");
		typeList.add("COI");
		typeList.add("SOR");
		typeList.add("LOR");
		typeList.add("LOI");
		typeList.add("LOD");
		typeList.add("ASRS");
		typeList.add("SDL");
		typeList.add("VDL");
		typeList.add("ODL");
		typeList.add("CDL");
		}
		if (jct.isAllAll()) { // add the class mutation operators
			typeList.add("IHI");
			typeList.add("IHD");
			typeList.add("IOD");
			typeList.add("IOP");
			typeList.add("IOR");
			typeList.add("ISI");
			typeList.add("ISD");
			typeList.add("IPC");
			typeList.add("PNC");
			typeList.add("PMD");
			typeList.add("PPD");
			typeList.add("PCI");
			typeList.add("PCC");
			typeList.add("PCD");
			typeList.add("PRV");
			typeList.add("OMR");
			typeList.add("OMD");
			typeList.add("OAN");
			typeList.add("JTI");
			typeList.add("JTD");
			typeList.add("JSI");
			typeList.add("JSD");
			typeList.add("JID");
			typeList.add("JDC");
			typeList.add("EOA");
			typeList.add("EOC");
			typeList.add("EAM");
			typeList.add("EMM");
		}
		if (!(jct.isAll() || jct.isAllAll())) { // if not all, add selected ops to the list
		if (jct.isAORB()) {
		  typeList.add("AORB");
		}
		if (jct.isAORS()) {
		  typeList.add("AORS");
		}
		if (jct.isAOIU()) {
		  typeList.add("AOIU");
		}
		if (jct.isAOIS()) {
		  typeList.add("AOIS");
		}
		if (jct.isAODU()) {
		  typeList.add("AODU");
		}
		if (jct.isAODS()) {
		  typeList.add("AODS");
		}
		if (jct.isROR()) {
		  typeList.add("ROR");
		}
		if (jct.isCOR()) {
		  typeList.add("COR");
		}
		if (jct.isCOD()) {
		  typeList.add("COD");
		}
		if (jct.isCOI()) {
		  typeList.add("COI");
		}
		if (jct.isSOR()) {
		  typeList.add("SOR");
		}
		if (jct.isLOR()) {
		  typeList.add("LOR");
		}
		if (jct.isLOI()) {
		  typeList.add("LOI");
		}
		if (jct.isLOD()) {
		  typeList.add("LOD");
		}
		if (jct.isASRS()) {
		  typeList.add("ASRS");
		}
		if (jct.isSDL()) {
		  typeList.add("SDL");
		}
		if (jct.isVDL()) {
		  typeList.add("VDL");
		}
		if (jct.isCDL()) {
		  typeList.add("CDL");
		}
		if (jct.isODL()) {
		  typeList.add("ODL");
		}

			// Class Mutants
			if (jct.isIHI()) {
				typeList.add("IHI");
			}
			if (jct.isIHD()) {
				typeList.add("IHD");
			}
			if (jct.isIOD()) {
				typeList.add("IOD");
			}
			if (jct.isIOP()) {
				typeList.add("IOP");
			}
			if (jct.isIOR()) {
				typeList.add("IOR");
			}
			if (jct.isISI()) {
				typeList.add("ISI");
			}
			if (jct.isISD()) {
				typeList.add("ISD");
			}
			if (jct.isIPC()) {
				typeList.add("IPC");
			}
			if (jct.isPNC()) {
				typeList.add("PNC");
			}
			if (jct.isPMD()) {
				typeList.add("PMD");
			}
			if (jct.isPPD()) {
				typeList.add("PPD");
			}
			if (jct.isPCI()) {
				typeList.add("PCI");
			}
			if (jct.isPCC()) {
				typeList.add("PCC");
			}
			if (jct.isPCD()) {
				typeList.add("PCD");
			}
			if (jct.isPRV()) {
				typeList.add("PRV");
			}
			if (jct.isOMR()) {
				typeList.add("OMR");
			}
			if (jct.isOMD()) {
				typeList.add("OMD");
			}
			if (jct.isOAN()) {
				typeList.add("OAN");
			}
			if (jct.isJTI()) {
				typeList.add("JTI");
			}
			if (jct.isJTD()) {
				typeList.add("JTD");
			}
			if (jct.isJSI()) {
				typeList.add("JSI");
			}
			if (jct.isJSD()) {
				typeList.add("JSD");
			}
			if (jct.isJID()) {
				typeList.add("JID");
			}
			if (jct.isJDC()) {
				typeList.add("JDC");
			}
			if (jct.isEOA()) {
				typeList.add("EOA");
			}
			if (jct.isEOC()) {
				typeList.add("EOC");
			}
			if (jct.isEAM()) {
				typeList.add("EAM");
			}
			if (jct.isEMM()) {
				typeList.add("EMM");
			}
    }

		// default option, all traditional
    if (typeList.size() == 0) {
      typeList.add("AORB");
      typeList.add("AORS");
      typeList.add("AOIU");
      typeList.add("AOIS");
      typeList.add("AODU");
      typeList.add("AODS");
      typeList.add("ROR");
      typeList.add("COR");
      typeList.add("COD");
      typeList.add("COI");
      typeList.add("SOR");
      typeList.add("LOR");
      typeList.add("LOI");
      typeList.add("LOD");
      typeList.add("ASRS");
      typeList.add("SDL");
      typeList.add("VDL");
      typeList.add("CDL");
      typeList.add("ODL");
    }

    setJMutationStructureAndSession(testSessionName);
    // MutationSystem.recordInheritanceRelation(); // have this line will
    // mess up things totally, need to check why!!!

    // decide which mode to run
    if (jct.isDefaultMode()) {
      mode = "default";
    } else if (jct.isDead())
      mode = "dead";
    else if (jct.isFresh()) {
      mode = "fresh";
    }

    if (jct.isDead() && jct.isFresh())
      mode = "fresh";

    // decide if need to run eq mutants
    if (jct.isEquiv())
      runEq = true;

    String[] types = new String[typeList.size()];
    types = typeList.toArray(types);
    for (String str : file_list) {
      targetClassName = str;

      if (!targetClassName.contains(".class")) {
        continue;
      }
      // need to remove .class extension
      if (targetClassName.contains(".class")) {
        targetClassName =
            targetClassName.substring(0, targetClassName.length() - ".class".length());
      }

      if (isSingleTestSet)
        runTests(targetClassName, testSetName, types, percentage, mode);
      else {
        for (int i = 0; i < testSetList.size(); i++) {
          runTests(targetClassName, testSetList.get(i), types, percentage, mode);
        }
      }
    }
    // System.exit(0);
    return;

  }

  private static boolean hasTestFile(List<File> listOfFiles, String testSetName,
      String sessionFolder) throws Exception {

    if (listOfFiles.size() == 0)
      throw new Exception("invalid test folder");
    for (File file : listOfFiles) {
      // @author Evan Valvis
      String temp = file.getAbsolutePath().replace("\\", "/");
      String testSet = temp.substring(sessionFolder.length(), temp.length());
      if (testSet.equals(testSetName + ".class"))
        return true;
    }
    return false;
  }

  static void runTests(String targetClassName, String testSetName, String[] mutantTypes,
      double percentage, String mode) throws NoMutantException, NoMutantDirException, IOException {

    Util.Print("Class Name: " + targetClassName);
    Util.Print("Test Name: " + testSetName);
    Util.Print("-----------------------------------------------");
    // read file
    File folder = new File(MutationSystem.MUTANT_HOME + "/" + targetClassName.replace("/", ".")
        + "/" + MutationSystem.TM_DIR_NAME);
    ArrayList<String> methodNameList = new ArrayList<>();
		ArrayList<String> classMutList = new ArrayList<>();
		// get all method names
			File folderTrad = new File(MutationSystem.MUTANT_HOME + "/" + targetClassName + "/" + MutationSystem.TM_DIR_NAME);
			File[] listOfMethods = folderTrad.listFiles();

			for (File method : listOfMethods) {
				methodNameList.add(method.getName());
			}
		// get all class mutations
			File folderClass = new File(MutationSystem.MUTANT_HOME + "/" + targetClassName + "/" + MutationSystem.CM_DIR_NAME);
			File[] listOfClassMuts = folderClass.listFiles();

			for (File method : listOfClassMuts) {
				classMutList.add(method.getName());
			}

    /*
     * no result files before, no result to read. fresh mode, no need to read anything, but need to
     * create time stamp when output files
     */
    if (!methodNameList.contains("mutant_list") || mode.equals("fresh")) {
      // mode="fresh-default";
      // Util.Print("no file mode");
      TestExecuterCLI test_engine = new TestExecuterCLI(targetClassName.replace("/", "."));
      test_engine.setTimeOut(timeout_sec);

      // add method list to engine, used for saving result at the end
      test_engine.methodList = new ArrayList<>();
      test_engine.methodList2 = new ArrayList<>();
      Util.setUpVectors();
      Util.setUpMaps();
      for (File method : listOfMethods) {
        if (method.isDirectory()) {
          test_engine.methodList.add(method.getName());
          test_engine.methodList2.add(method.getName());
        }
      }
			for (File method : listOfClassMuts) {
				if (method.isDirectory()) {
					test_engine.classMutsList.add(method.getName());
				}
			}
      // First, read (load) test suite class.
      Util.DebugPrint(targetClassName + " " + testSetName);
      test_engine.readTestSet(testSetName);

      TestResultCLI test_result = new TestResultCLI();

      test_engine.computeOriginalTestResults();
      System.out.print("Running");
      test_result = test_engine.runTraditionalMutants("All method", mutantTypes, percentage);
			test_engine.runClassMutants("All method", mutantTypes, percentage);
      return;
    }

    if (mode.equals("default")) // run default mode, read result first, then
                                // if m is dead, t not run on it.
    {

      // read file
      TestResultCLI tr = new TestResultCLI();
      //
      tr.path = MutationSystem.MUTANT_HOME + "/" + targetClassName.replace("/", ".") + "/"
          + MutationSystem.TM_DIR_NAME + "/mutant_list";
      tr.getResults();

      // need to check if eq option is enabled, if so, need to run eq
      // mutants
      if (runEq) {
        tr.live_mutants.addAll(tr.eq_mutants);
        tr.eq_mutants = new Vector();
      }

      tr.live_mutants = trimLiveMutants(tr.live_mutants, mutantTypes); // eliminate
                                                                       // mutants
                                                                       // of
                                                                       // other
                                                                       // types
                                                                       // not
                                                                       // listed

      // run
      TestExecuterCLI test_engine = new TestExecuterCLI(targetClassName.replace("/", "."));
      test_engine.setTimeOut(timeout_sec);

      // First, read (load) test suite class.
      Util.DebugPrint(targetClassName + " " + testSetName);
      test_engine.readTestSet(testSetName);

      TestResultCLI test_result = new TestResultCLI();
      System.out.print("Running");
      test_engine.computeOriginalTestResults();
      test_result =
          test_engine.runTraditionalMutants("All method", mutantTypes, percentage, tr.live_mutants);
      // }
    } else if (mode.equals("dead")) // dead mode
    {
      // // read file
      // TestResultCLI tr = new TestResultCLI();
      // //
      // tr.path = MutationSystem.MUTANT_HOME + "/" + targetClassName +
      // "/" + MutationSystem.TM_DIR_NAME
      // + "/mutant_list";
      // // read out all old results in file
      // tr.getResults();

      // dead mode, run live + dead + equiv
      // Vector newMutants = new Vector<>();
      // newMutants.addAll(tr.killed_mutants);

      // if -equiv is an option, need run equivalent mutants too
      if (runEq) {
        System.out.println("eq mode is enabled");
        // newMutants.addAll(tr.eq_mutants);
      }
      // newMutants.addAll(tr.live_mutants);

      // newMutants = trimLiveMutants(newMutants, mutantTypes); // trim
      // mutants
      // based on
      // types,
      // delete
      // mutants
      // that are
      // not in
      // the type
      // set

      // run
      TestExecuterCLI test_engine = new TestExecuterCLI(targetClassName.replace("/", "."));
      test_engine.setTimeOut(timeout_sec);

      // First, read (load) test suite class.
      Util.DebugPrint(targetClassName + " " + testSetName);
      test_engine.readTestSet(testSetName);

      // TestResultCLI test_result = new TestResultCLI();
      System.out.print("Running");
      test_engine.computeOriginalTestResults();
      test_engine.runTraditionalMutants("All method", mutantTypes, percentage);
    }
  }

  private static Vector trimLiveMutants(Vector live_mutants, String[] mutantTypes) {
    Vector newLivemutants = new Vector<>();

    for (Object str : live_mutants) {
      for (String type : mutantTypes) {
        if (((String) str).contains(type)) {
          newLivemutants.add(str);
          break;
        }
      }
    }

    return newLivemutants;
  }

  private static void setJMutationStructureAndSession(String sessionName) {
    muJavaHomePath = muJavaHomePath + "/" + sessionName;
    MutationSystem.SYSTEM_HOME = muJavaHomePath;
    MutationSystem.SRC_PATH = muJavaHomePath + "/src";
    MutationSystem.CLASS_PATH = muJavaHomePath + "/classes";
    MutationSystem.MUTANT_HOME = muJavaHomePath + "/result";
    MutationSystem.TESTSET_PATH = muJavaHomePath + "/testset";
  }

  // save csv file
  public static void saveTestResults(String targetClassName, Map<String, String> finalTestResults,
      Map<String, String> finalMutantResults, String method) throws IOException {

    // // results as to how many mutants are killed by each test
    Map<String, ArrayList<String>> oldResults = new HashMap<String, ArrayList<String>>();
    // // results as to how many tests can kill each single mutant
    // Map<String, String> oldFinalMutantResults = new HashMap<String,
    // String>();
    //

    ArrayList<String> testList = new ArrayList<String>();

    if (mode.equals("fresh")) // fresh mode, need to save time stamp
    {
      if (!TestExecuterCLI.methodList2.contains(method)) {
        System.out.println("ERROR");
        return;
      }

      TestExecuterCLI.methodList2.remove(method);

      // merge results
      mergeMaps(finalTestResults, finalMutantResults);

      // if no methods left, save file
      // else continue
      if (TestExecuterCLI.methodList2.size() == 0) {
        // get time
        Calendar nowtime = new GregorianCalendar();
        File f = new File(MutationSystem.MUTANT_HOME + "/" + targetClassName.replace("/", ".") + "/"
            + MutationSystem.TM_DIR_NAME + "/" + "result_list_" + nowtime.get(Calendar.YEAR) + "_"
            + (nowtime.get(Calendar.MONTH) + 1) + "_" + nowtime.get(Calendar.DATE) + "_"
            + nowtime.get(Calendar.HOUR) + "_" + nowtime.get(Calendar.MINUTE) + "_"
            + nowtime.get(Calendar.SECOND) + ".csv");
        // System.out.println("can't find the mutant result file");

        FileOutputStream fout = new FileOutputStream(f);
        StringBuffer fileContent = new StringBuffer();
        fileContent.append("Mutant,");
        // first line, write all tests
        for (Map.Entry<String, String> entry : finalTestResults.entrySet()) {
          fileContent.append(entry.getKey() + ",");
          testList.add(entry.getKey());
        }

        // 2 extra columns
        fileContent.append("Total,Equiv?\r\n");

        for (Map.Entry<String, String> entry : finalMutantResults.entrySet()) {
          fileContent.append(entry.getKey() + ",");
          List<String> tempkillingResult = Arrays.asList(entry.getValue().split(",\\s+"));

          List<String> killingResult = new ArrayList<String>();
          // remove spaces
          for (int i = 0; i < tempkillingResult.size(); i++) {
            if (!tempkillingResult.get(i).equals(""))
              killingResult.add(tempkillingResult.get(i));
          }

          for (String test : testList) {
            if (killingResult.contains(test)) {
              fileContent.append("1,");
            } else {
              fileContent.append(" ,");
            }
          }

          fileContent.append(killingResult.size() + ","); // 1 space
                                                          // for
                                                          // total
          fileContent.append(" "); // 1 space for equiv

          // fileContent.append(entry.getValue());
          fileContent.append("\r\n");
        }
        fout.write(fileContent.toString().getBytes("utf-8"));
        fout.close();
      }

      return;

    }

    if (!mode.equals("fresh")) // default mode or dead mode, no time stamp
    {
      // read file
      String s = null;
      File f = new File(MutationSystem.MUTANT_HOME + "/" + targetClassName.replace("/", ".") + "/"
          + MutationSystem.TM_DIR_NAME + "/" + "result_list" + ".csv");

      // no file exists, means very first run
      if (!f.exists()) {
        // System.out.println("can't find the mutant result file");

        // default mode, do not create with timestamp
        File file = new File(MutationSystem.TRADITIONAL_MUTANT_PATH, "result_list" + ".csv");

        FileOutputStream fout = new FileOutputStream(file);
        StringBuffer fileContent = new StringBuffer();
        fileContent.append("Mutant,");
        // first line, write all tests
        for (Map.Entry<String, String> entry : finalTestResults.entrySet()) {
          fileContent.append(entry.getKey() + ",");
          testList.add(entry.getKey());
        }

        // 2 extra columns
        fileContent.append("Total,Equiv?\r\n");

        for (Map.Entry<String, String> entry : finalMutantResults.entrySet()) {
          fileContent.append(entry.getKey() + ",");
          List<String> tempkillingResult = Arrays.asList(entry.getValue().split(",\\s+"));

          List<String> killingResult = new ArrayList<String>();
          // remove spaces
          for (int i = 0; i < tempkillingResult.size(); i++) {
            if (!tempkillingResult.get(i).equals(""))
              killingResult.add(tempkillingResult.get(i));
          }

          for (String test : testList) {
            if (killingResult.contains(test)) {
              fileContent.append("1,");
            } else {
              fileContent.append(" ,");
            }
          }

          fileContent.append(killingResult.size() + ","); // 1 space
                                                          // for total
          fileContent.append(" "); // 1 space for equiv

          // fileContent.append(entry.getValue());
          fileContent.append("\r\n");
        }
        fout.write(fileContent.toString().getBytes("utf-8"));
        fout.close();
        return;

      }
      // -----------------
      // exist result files, need to read old results first
      oldResults = new HashMap<>();
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

      while ((s = br.readLine()) != null) // read lines
      {
        String[] temp = s.split(",");
        ArrayList<String> tempList = new ArrayList<>();
        for (int i = 1; i < temp.length; i++) {
          tempList.add(temp[i]);
        }
        oldResults.put(temp[0], tempList); // e.g. will be aois_1 (key),
                                           // 1, ,1... (value)
      }

      // need to compare test cases, either more or less
      ArrayList<String> oldTitles = oldResults.get("Mutant");

      // initiate new title
      ArrayList<String> newTitle = new ArrayList<>();
      for (int i = 0; i < oldTitles.size(); i++)
        newTitle.add(oldTitles.get(i));

      // if there are new tests added, need to re-write the title line

      for (Map.Entry<String, String> entry : finalTestResults.entrySet()) { // check
                                                                            // every
                                                                            // test
                                                                            // name
        testList.add(entry.getKey()); // add test name to it
        if (!oldTitles.contains(entry.getKey())) {
          newTitle.add(0, entry.getKey()); // title would be new list
                                           // of
                                           // tests, add new test
                                           // name to the first row
        }
      }

      // update entire title line
      oldResults.put("Mutant", newTitle);

      for (Entry<String, ArrayList<String>> entry : oldResults.entrySet()) {
        // for those not executed old mutants, add spaces at the front
        if (!finalMutantResults.keySet().contains(entry.getKey())
            && !entry.getKey().equals("Mutant")) {
          ArrayList<String> oldEntryVal = entry.getValue();
          for (int i = 0; i < newTitle.size() - 2; i++) {
            if (!oldTitles.contains(newTitle.get(i))) {
              oldEntryVal.add(i, " ");
            }
          }
          // update current entry
          oldResults.put(entry.getKey(), oldEntryVal);
        }
      }

      // for each runned mutants, new sub-set
      for (Map.Entry<String, String> entry : finalMutantResults.entrySet()) {
        List<String> tempkillingResult = Arrays.asList(entry.getValue().split(",\\s+"));

        List<String> killingResult = new ArrayList<String>();
        // remove spaces at the end
        for (int i = 0; i < tempkillingResult.size(); i++) {
          if (!tempkillingResult.get(i).equals(""))
            killingResult.add(tempkillingResult.get(i));
          // killing result will be which test killed current mutant
          // e.g. test1 test2 ...
        }

        // may have old result here
        if (oldResults.containsKey(entry.getKey()))
        // if new result have overlap
        {
          // get old results
          ArrayList<String> oldResult = oldResults.get(entry.getKey());
          // oldResult here is 1, , 1, 2...
          // need to get old test names
          ArrayList<String> oldKillingTestNames = new ArrayList<>();
          for (int i = 0; i < oldResult.size() - 2; i++) {
            if (oldResult.get(i).equals("1")) {
              oldKillingTestNames.add(oldTitles.get(i));
            }
          }

          // for each test, if
          for (int i = 0; i < newTitle.size() - 2; i++) {
            if (killingResult.contains(newTitle.get(i)) && !oldTitles.contains(newTitle.get(i)))
              oldResult.add(i, "1");
            else if (!killingResult.contains(newTitle.get(i))
                && !oldTitles.contains(newTitle.get(i))) {
              oldResult.add(i, " ");
            } else if (killingResult.contains(newTitle.get(i))
                && oldTitles.contains(newTitle.get(i))) {
              // need to reset it
              oldResult.set(i, "1");
            }
          }
          // calculate total

          int sum = 0;

          for (int i = 0; i < oldResult.size() - 2; i++)
          // for (String result : oldResult)
          {
            if (oldResult.get(i).equals("1"))
              sum++;
          }

          oldResult.set(oldResult.size() - 2, Integer.toString(sum));

          // eq mode, need update csv file when necessary
          if (runEq == true && oldResult.get(oldResult.size() - 1).contains("Y") && sum != 0) {
            oldResult.set(oldResult.size() - 1, "");
          }

          oldResults.put(entry.getKey(), oldResult); // renew record
                                                     // set

        } else { // if this is a new mutant, need to add entirely
          ArrayList<String> newEntryVal = new ArrayList<>();
          for (int i = 0; i < newTitle.size(); i++) {
            if (killingResult.contains(newTitle.get(i)))
              newEntryVal.add("1");
            else {
              newEntryVal.add(" ");
            }

          }

          // calculate total
          int sum = 0;

          for (int i = 0; i < newEntryVal.size() - 2; i++)
          // for (String result : oldResult)
          {
            if (newEntryVal.get(i).equals("1"))
              sum++;
          }

          newEntryVal.set(newEntryVal.size() - 2, Integer.toString(sum));

          oldResults.put(entry.getKey(), newEntryVal);

        }

      }

      br.close();
      f.delete();
      // update csv file
      // default mode, do not create with timestamp
      // File newf = new File(MutationSystem.TRADITIONAL_MUTANT_PATH,
      // "result_list" + ".csv");

      FileOutputStream fout = new FileOutputStream(f);
      StringBuffer fileContent = new StringBuffer();

      // build title
      fileContent.append("Mutant");
      for (String test : newTitle)
        fileContent.append("," + test);

      fileContent.append("\r\n");
      // build content
      for (Entry<String, ArrayList<String>> oldEntry : oldResults.entrySet()) {
        if (oldEntry.getKey().equals("Mutant"))
          continue;
        fileContent.append(oldEntry.getKey());
        for (String str : oldEntry.getValue()) {
          fileContent.append("," + str);
        }

        fileContent.append("\r\n");
      }

      fout.write(fileContent.toString().getBytes("utf-8"));
      fout.close();

    }

  }

  private static void mergeMaps(Map<String, String> finalTestResults,
      Map<String, String> finalMutantResults) {

    // do final test results

    for (Map.Entry<String, String> entry : finalTestResults.entrySet()) // for
                                                                        // each
                                                                        // entry
    {
      if (Util.finalTestResultsMap.containsKey(entry.getKey())) // if have
                                                                // same
                                                                // key,
                                                                // need
                                                                // to
                                                                // merge
      {
        String oldResultString = Util.finalTestResultsMap.get(entry.getKey());
        String newResultString = entry.getValue();
        String[] oldResultsArr = oldResultString.split(",\\s+");
        String[] newResultsArr = newResultString.split(",\\s+");
        ArrayList<String> oldResults = new ArrayList<>(Arrays.asList(oldResultsArr));
        ArrayList<String> newResults = new ArrayList<>(Arrays.asList(newResultsArr));
        for (String str : newResults) {
          if (!oldResults.contains(str))
            oldResults.add(str);
        }
        String finalString = new String();
        for (String str : oldResults) {
          finalString = finalString + str + ", ";
        }
        finalString = finalString.substring(0, finalString.length() - 2);
        Util.finalTestResultsMap.put(entry.getKey(), finalString); // finally,
                                                                   // add
                                                                   // it
                                                                   // back
      } else { // no same key, directly add
        Util.finalTestResultsMap.put(entry.getKey(), entry.getValue());
      }
    }

    // do final mutant results
    for (Map.Entry<String, String> entry : finalMutantResults.entrySet()) // for
                                                                          // each
                                                                          // entry
    {
      if (Util.finalMutantResultsMap.containsKey(entry.getKey())) // if
                                                                  // have
                                                                  // same
                                                                  // key,
                                                                  // need
                                                                  // to
                                                                  // merge
      {
        String oldResultString = Util.finalMutantResultsMap.get(entry.getKey());
        String newResultString = entry.getValue();
        String[] oldResultsArr = oldResultString.split(",\\s+");
        String[] newResultsArr = newResultString.split(",\\s+");
        ArrayList<String> oldResults = new ArrayList<>(Arrays.asList(oldResultsArr));
        ArrayList<String> newResults = new ArrayList<>(Arrays.asList(newResultsArr));
        for (String str : newResults) {
          if (!oldResults.contains(str))
            oldResults.add(str);
        }
        String finalString = new String();
        for (String str : oldResults) {
          finalString = finalString + str + ", ";
        }
        finalString = finalString.substring(0, finalString.length() - 2);
        Util.finalMutantResultsMap.put(entry.getKey(), finalString); // finally,
                                                                     // add
                                                                     // it
                                                                     // back
      } else {
        Util.finalMutantResultsMap.put(entry.getKey(), entry.getValue());
      }
    }

  }

}
