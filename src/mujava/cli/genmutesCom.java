/**
 * Copyright (C) 2015  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 



package mujava.cli;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
 /**
 * <p>
 * Description: Pre-defined arguments options for genmutes command
 * </p>
 *
 * @author Lin Deng
 * @version 1.0
  */
class genmutesCom {
	@Parameter
	private List<String> parameters = new ArrayList<String>();

	@Parameter(names = "-AORB",  description = "Generate mutants of AORB")
	private boolean AORB;
	@Parameter(names = "-AORS", description = "Generate mutants of AORS")
	private boolean AORS;
	@Parameter(names = "-AOIU", description = "Generate mutants of AOIU")
	private boolean AOIU;
	@Parameter(names = "-AOIS", description = "Generate mutants of AOIS")
	private boolean AOIS;
	@Parameter(names = "-AODU", description = "Generate mutants of AODU")
	private boolean AODU;
	@Parameter(names = "-AODS", description = "Generate mutants of AODS")
	private boolean AODS;
	@Parameter(names = "-ROR",  description = "Generate mutants of ROR")
	private boolean ROR;
	@Parameter(names = "-COR",  description = "Generate mutants of COR")
	private boolean COR;
	@Parameter(names = "-COD",  description = "Generate mutants of COD")
	private boolean COD;
	@Parameter(names = "-COI",  description = "Generate mutants of COI")
	private boolean COI;
	@Parameter(names = "-SOR",  description = "Generate mutants of SOR")
	private boolean SOR;
	@Parameter(names = "-LOR",  description = "Generate mutants of LOR")
	private boolean LOR;
	@Parameter(names = "-LOI",  description = "Generate mutants of LOI")
	private boolean LOI;
	@Parameter(names = "-LOD",  description = "Generate mutants of LOD")
	private boolean LOD;
	@Parameter(names = "-ASRS", description = "Generate mutants of ASRS")
	private boolean ASRS;
	@Parameter(names = "-SDL",  description = "Generate mutants of SDL")
	private boolean SDL;
	@Parameter(names = "-VDL",  description = "Generate mutants of VDL")
	private boolean VDL;
	@Parameter(names = "-CDL",  description = "Generate mutants of CDL")
	private boolean CDL;
	@Parameter(names = "-ODL",  description = "Generate mutants of ODL")
	private boolean ODL;

	@Parameter(names = "-IHI", description = "Generate mutants of IHI")
	private boolean IHI;
	@Parameter(names = "-IHD", description = "Generate mutants of IHD")
	private boolean IHD;
	@Parameter(names = "-IOD", description = "Generate mutants of IOD")
	private boolean IOD;
	@Parameter(names = "-IOP", description = "Generate mutants of IOP")
	private boolean IOP;
	@Parameter(names = "-IOR", description = "Generate mutants of IOR")
	private boolean IOR;
	@Parameter(names = "-ISI", description = "Generate mutants of ISI")
	private boolean ISI;
	@Parameter(names = "-ISD", description = "Generate mutants of ISD")
	private boolean ISD;
	@Parameter(names = "-IPC", description = "Generate mutants of IPC")
	private boolean IPC;
	@Parameter(names = "-PNC", description = "Generate mutants of PNC")
	private boolean PNC;
	@Parameter(names = "-PMD", description = "Generate mutants of PMD")
	private boolean PMD;
	@Parameter(names = "-PPD", description = "Generate mutants of PPD")
	private boolean PPD;
	@Parameter(names = "-PCI", description = "Generate mutants of PCI")
	private boolean PCI;
	@Parameter(names = "-PCC", description = "Generate mutants of PCC")
	private boolean PCC;
	@Parameter(names = "-PCD", description = "Generate mutants of PCD")
	private boolean PCD;
	@Parameter(names = "-PRV", description = "Generate mutants of PRV")
	private boolean PRV;
	@Parameter(names = "-OMR", description = "Generate mutants of OMR")
	private boolean OMR;
	@Parameter(names = "-OMD", description = "Generate mutants of OMD")
	private boolean OMD;
	@Parameter(names = "-OAN", description = "Generate mutants of OAN")
	private boolean OAN;
	@Parameter(names = "-JTI", description = "Generate mutants of JTI")
	private boolean JTI;
	@Parameter(names = "-JTD", description = "Generate mutants of JTD")
	private boolean JTD;
	@Parameter(names = "-JSI", description = "Generate mutants of JSI")
	private boolean JSI;
	@Parameter(names = "-JSD", description = "Generate mutants of JSD")
	private boolean JSD;
	@Parameter(names = "-JID", description = "Generate mutants of JID")
	private boolean JID;
	@Parameter(names = "-JDC", description = "Generate mutants of JDC")
	private boolean JDC;
	@Parameter(names = "-EOA", description = "Generate mutants of EOA")
	private boolean EOA;
	@Parameter(names = "-EOC", description = "Generate mutants of EOC")
	private boolean EOC;
	@Parameter(names = "-EAM", description = "Generate mutants of EAM")
	private boolean EAM;
	@Parameter(names = "-EMM", description = "Generate mutants of EMM")
	private boolean EMM;
	@Parameter(names = "-all",  description = "Generate mutants of ALL MUTATION OPERATORS (only traditional)")
	private boolean all;
	@Parameter(names = "-allall",  description = "Generate mutants of ALL MUTATION OPERATORS (traditional and class)")
	private boolean allall;

	@Parameter(names = "-D",  description = "Generate mutants of ALL classes in the directory")
	private boolean D;

	@Parameter(names = "--help", help = true)
	private boolean help;

	@Parameter(names = "-debug", description = "Debug mode")
	private boolean debug = false;

	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public List<String> getParameters()
	{
		return parameters;
	}
	public void setParameters(List<String> parameters)
	{
		this.parameters = parameters;
	}

	public boolean getAORB()
	{
		return AORB;
	}
	public void setAORB(boolean aORB)
	{
		AORB = aORB;
	}
	public boolean getAORS()
	{
		return AORS;
	}
	public void setAORS(boolean aORS)
	{
		AORS = aORS;
	}
	public boolean getAOIU()
	{
		return AOIU;
	}
	public void setAOIU(boolean aOIU)
	{
		AOIU = aOIU;
	}
	public boolean getAOIS()
	{
		return AOIS;
	}
	public void setAOIS(boolean aOIS)
	{
		AOIS = aOIS;
	}
	public boolean getAODU()
	{
		return AODU;
	}
	public void setAODU(boolean aODU)
	{
		AODU = aODU;
	}
	public boolean getAODS()
	{
		return AODS;
	}
	public void setAODS(boolean aODS)
	{
		AODS = aODS;
	}
	public boolean getROR()
	{
		return ROR;
	}
	public void setROR(boolean rOR)
	{
		ROR = rOR;
	}
	public boolean getCOR()
	{
		return COR;
	}
	public void setCOR(boolean cOR)
	{
		COR = cOR;
	}
	public boolean getCOD()
	{
		return COD;
	}
	public void setCOD(boolean cOD)
	{
		COD = cOD;
	}
	public boolean getCOI()
	{
		return COI;
	}
	public void setCOI(boolean cOI)
	{
		COI = cOI;
	}
	public boolean getSOR()
	{
		return SOR;
	}
	public void setSOR(boolean sOR)
	{
		SOR = sOR;
	}
	public boolean getLOR()
	{
		return LOR;
	}
	public void setLOR(boolean lOR)
	{
		LOR = lOR;
	}
	public boolean getLOI()
	{
		return LOI;
	}
	public void setLOI(boolean lOI)
	{
		LOI = lOI;
	}
	public boolean getLOD()
	{
		return LOD;
	}
	public void setLOD(boolean lOD)
	{
		LOD = lOD;
	}
	public boolean getASRS()
	{
		return ASRS;
	}
	public void setASRS(boolean aSRS)
	{
		ASRS = aSRS;
	}
	public boolean getSDL()
	{
		return SDL;
	}
	public void setSDL(boolean sDL)
	{
		SDL = sDL;
	}
	public boolean getAll()
	{
		return all;
	}
	public void setAll(boolean all)
	{
		this.all = all;
	}

	public boolean getAllAll()
	{
		return allall;
	}
	public void setAllAll(boolean allall)
	{
		this.allall = allall;
	}

	public boolean getD()
	{
		return D;
	}

	public void setD(boolean D)
	{
		this.D = D;
	}
	public boolean getVDL() {
		return VDL;
	}
	public void setVDL(boolean vDL) {
		VDL = vDL;
	}
	public boolean getCDL() {
		return CDL;
	}
	public void setCDL(boolean cDL) {
		CDL = cDL;
	}
	public boolean getODL() {
		return ODL;
	}
	public void setODL(boolean oDL) {
		ODL = oDL;
	}

	// Class mutants
	public boolean getIHI() {
		return IHI;
	}
	public void setIHI(boolean b) {
		IHI = b;
	}

	public boolean getIHD() {
		return IHD;
	}
	public void setIHD(boolean b) {
		IHD = b;
	}

	public boolean getIOD() {
		return IOD;
	}
	public void setIOD(boolean b) {
		IOD = b;
	}

	public boolean getIOP() {
		return IOP;
	}
	public void setIOP(boolean b) {
		IOP = b;
	}

	public boolean getIOR() {
		return IOR;
	}
	public void setIOR(boolean b) {
		IOR = b;
	}

	public boolean getISI() {
		return ISI;
	}
	public void setISI(boolean b) {
		ISI = b;
	}

	public boolean getISD() {
		return ISD;
	}
	public void setISD(boolean b) {
		ISD = b;
	}

	public boolean getIPC() {
		return IPC;
	}
	public void setIPC(boolean b) {
		IPC = b;
	}

	public boolean getPNC() {
		return PNC;
	}
	public void setPNC(boolean b) {
		PNC = b;
	}

	public boolean getPMD() {
		return PMD;
	}
	public void setPMD(boolean b) {
		PMD = b;
	}

	public boolean getPPD() {
		return PPD;
	}
	public void setPPD(boolean b) {
		PPD = b;
	}

	public boolean getPCI() {
		return PCI;
	}
	public void setPCI(boolean b) {
		PCI = b;
	}

	public boolean getPCC() {
		return PCC;
	}
	public void setPCC(boolean b) {
		PCC = b;
	}

	public boolean getPCD() {
		return PCD;
	}
	public void setPCD(boolean b) {
		PCD = b;
	}

	public boolean getPRV() {
		return PRV;
	}
	public void setPRV(boolean b) {
		PRV = b;
	}

	public boolean getOMR() {
		return OMR;
	}
	public void setOMR(boolean b) {
		OMR = b;
	}

	public boolean getOMD() {
		return OMD;
	}
	public void setOMD(boolean b) {
		OMD = b;
	}

	public boolean getOAN() {
		return OAN;
	}
	public void setOAN(boolean b) {
		OAN = b;
	}

	public boolean getJTI() {
		return JTI;
	}
	public void setJTI(boolean b) {
		JTI = b;
	}

	public boolean getJTD() {
		return JTD;
	}
	public void setJTD(boolean b) {
		JTD = b;
	}

	public boolean getJSI() {
		return JSI;
	}
	public void setJSI(boolean b) {
		JSI = b;
	}

	public boolean getJSD() {
		return JSD;
	}
	public void setJSD(boolean b) {
		JSD = b;
	}

	public boolean getJID() {
		return JID;
	}
	public void setJID(boolean b) {
		JID = b;
	}

	public boolean getJDC() {
		return JDC;
	}
	public void setJDC(boolean b) {
		JDC = b;
	}

	public boolean getEOA() {
		return EOA;
	}
	public void setEOA(boolean b) {
		EOA = b;
	}

	public boolean getEOC() {
		return EOC;
	}
	public void setEOC(boolean b) {
		EOC = b;
	}

	public boolean getEAM() {
		return EAM;
	}
	public void setEAM(boolean b) {
		EAM = b;
	}

	public boolean getEMM() {
		return EMM;
	}
	public void setEMM(boolean b) {
		EMM = b;
	}


	}
