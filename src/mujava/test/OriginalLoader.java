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


package mujava.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.security.ProtectionDomain;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;

import mujava.MutationSystem;
import mujava.cli.Util;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author Yu-Seung Ma
 * @author Nan Li modified on 06/30/2013 for adding getResource(String)
 * @version 1.0
 */

public class OriginalLoader extends ClassLoader {

	public OriginalLoader() throws Exception {
		super(null);
			//super(new URL[]{new URL("file://home/derbaer/phd/samples/javaLibs/commons-codec-1.10-src/src/main/resources/")});
		try {
			//addClassPath("/home/derbaer/phd/samples/javaLibs/commons-codec-1.10-src/src/main/resources");
//			addURL(new URL("/home/derbaer/phd/samples/javaLibs/commons-codec-1.10-src/src/main/resources"));
		} catch (Exception e) {
			Util.Error("Error within Original Loader: " + e);
			e.printStackTrace();
		}
	}

/*	public void addURL(String classPath) throws Exception {
		Method addClass = null;
		ClassLoader cl = null;
		File f = null;

		addClass = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {URL.class});
		addClass.setAccessible(true);
		f = new File(classPath);
		if (!f.exists()) {
			Util.Error("Classpath '" + classPath + "' does not exist");
			return;
		} else {
			Util.Error("Classpath adding '" + classPath + "'");
		}
		//cl = ClassLoader.getSystemClassLoader();
		addClass.invoke(this, new Object[] { f.toURL() });
	}*/

	public synchronized Class loadTestClass(String name) throws ClassNotFoundException {
		Class result;
		Util.DebugPrint("Loading Test Class " + name);
		try {
			byte[] data = getClassData(name.replace("/", "."), MutationSystem.TESTSET_PATH);
			// @author Evan Valvis
			// defineClass does not accept / only .
			result = defineClass(name.replace("/", "."), data, 0, data.length);
//			Util.DebugPrint("\tLoaded " + name + " by0 " + result.getClassLoader());
			if (result == null) {
				throw new ClassNotFoundException(name);
			}
			//resolveClass(result);
		} catch (IOException e) {
			throw new ClassNotFoundException();
		}
		return result;
	}

	@Override
	public synchronized Class loadClass(String name) throws ClassNotFoundException {
//		Util.DebugPrint("Loading normal Class " + name + " with " + this);
		// See if type has already been loaded by
		// this class loader
		Class result = findLoadedClass(name);
		if (result != null) {
			// Return an already-loaded class
//			Util.DebugPrint("\tLoaded by1 " + result.getClassLoader());
			return result;
		}

// FIXME: hardcoded stuff
if (name.startsWith("java") || name.startsWith("org.hamcrest")) {
		try {
			result = findSystemClass(name);
//			Util.DebugPrint("\tLoaded by2 " + result.getClassLoader());
			return result;
		} catch (ClassNotFoundException e) {
			// keep looking
		}
}
		try {
			byte[] data;
			try {
				// Try to load it
				data = getClassData(name, MutationSystem.CLASS_PATH);
			} catch (FileNotFoundException e) {
				data = getClassData(name, MutationSystem.TESTSET_PATH);
			}
			result = defineClass(name, data, 0, data.length);
//			Util.DebugPrint("\tLoaded " + name + " by3 " + result.getClassLoader());
			if (result == null)
				throw new ClassNotFoundException(name);
			//resolveClass(result);
			return result;
		} catch (IOException e) {
			throw new ClassNotFoundException();
		}
	}



	private byte[] getClassData(String name, String directory)
			throws FileNotFoundException, IOException {
		String filename = name.replace('.', File.separatorChar) + ".class";
//		Util.DebugPrint("file name: " + filename);

		// Create a file object relative to directory provided
		File f = new File(directory, filename);
		// Get stream to read from
		FileInputStream fis = new FileInputStream(f);

		BufferedInputStream bis = new BufferedInputStream(fis);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			int c = bis.read();
			while (c != -1) {
				out.write(c);
				c = bis.read();
			}
		} catch (IOException e) {
			return null;
		}
		return out.toByteArray();
	}

	/**
	 * Overrides getResource (String) to get non-class files including resource bundles from property
	 * files
	 */
	@Override
	public URL getResource(String name) {
		URL url = null;
		File resource = new File(MutationSystem.CLASS_PATH, name);
		if (resource.exists()) {
			try {
				return resource.toURI().toURL();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return url;
	}

}


