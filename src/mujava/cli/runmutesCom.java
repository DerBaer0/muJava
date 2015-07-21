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
 * Description: Pre-defined arguments options for runmutes command
 * </p>
 *
 * @author Lin Deng
 * @version 1.0
 *
 */
class runmutesCom {
	@Parameter
	private List<String> parameters = new ArrayList<String>();

	@Parameter(names = "-dead",  description = "Run mutants with dead mode")
	private boolean dead;
	@Parameter(names = "-fresh",  description = "Run mutants with fresh mode")
	private boolean fresh;
	@Parameter(names = "-default",  description = "Run mutants with default mode")
	private boolean defaultMode;

	@Parameter(names = "-debug", description = "Debug mode")
	private boolean debug = false;

	@Parameter(names = "-equiv",  description = "run equivalent mutants")
	private boolean equiv;

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

	@Parameter(names = "-all",  description = "Generate mutants of ALL MUTATION OPERATORS")
	private boolean all;
	@Parameter(names = "-allall",  description = "Generate mutants of ALL MUTATION OPERATORS (traditional and class)")
	private boolean allall;

	@Parameter(names = "-p", arity = 1,  description = "Rondom percentage")
	private double p;

	@Parameter(names = "-target", arity = 1,  description = "Rondom percentage")
	private String target;

	@Parameter(names = "-testset", arity = 1,  description = "Rondom percentage")
	private String testset;

	@Parameter(names = "--help", help = true)
	private boolean help;

	// add timeout option
	@Parameter(names = "-timeout", arity = 1, description = "Customized timeout")
	private int timeout = -1;

	public boolean isEquiv() {
		return equiv;
	}
	public void setEquiv(boolean equiv) {
		this.equiv = equiv;
	}
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
	public boolean isAORB()
	{
		return AORB;
	}
	public void setAORB(boolean aORB)
	{
		AORB = aORB;
	}
	public boolean isAORS()
	{
		return AORS;
	}
	public void setAORS(boolean aORS)
	{
		AORS = aORS;
	}
	public boolean isAOIU()
	{
		return AOIU;
	}
	public void setAOIU(boolean aOIU)
	{
		AOIU = aOIU;
	}
	public boolean isAOIS()
	{
		return AOIS;
	}
	public void setAOIS(boolean aOIS)
	{
		AOIS = aOIS;
	}
	public boolean isAODU()
	{
		return AODU;
	}
	public void setAODU(boolean aODU)
	{
		AODU = aODU;
	}
	public boolean isAODS()
	{
		return AODS;
	}
	public void setAODS(boolean aODS)
	{
		AODS = aODS;
	}
	public boolean isROR()
	{
		return ROR;
	}
	public void setROR(boolean rOR)
	{
		ROR = rOR;
	}
	public boolean isCOR()
	{
		return COR;
	}
	public void setCOR(boolean cOR)
	{
		COR = cOR;
	}
	public boolean isCOD()
	{
		return COD;
	}
	public void setCOD(boolean cOD)
	{
		COD = cOD;
	}
	public boolean isCOI()
	{
		return COI;
	}
	public void setCOI(boolean cOI)
	{
		COI = cOI;
	}
	public boolean isSOR()
	{
		return SOR;
	}
	public void setSOR(boolean sOR)
	{
		SOR = sOR;
	}
	public boolean isLOR()
	{
		return LOR;
	}
	public void setLOR(boolean lOR)
	{
		LOR = lOR;
	}
	public boolean isLOI()
	{
		return LOI;
	}
	public void setLOI(boolean lOI)
	{
		LOI = lOI;
	}
	public boolean isLOD()
	{
		return LOD;
	}
	public void setLOD(boolean lOD)
	{
		LOD = lOD;
	}
	public boolean isASRS()
	{
		return ASRS;
	}
	public void setASRS(boolean aSRS)
	{
		ASRS = aSRS;
	}
	public boolean isSDL()
	{
		return SDL;
	}
	public void setSDL(boolean sDL)
	{
		SDL = sDL;
	}
	public boolean isAll()
	{
		return all;
	}
	public void setAll(boolean all)
	{
		this.all = all;
	}

	public boolean isAllAll()
	{
		return allall;
	}
	public void setAllAll(boolean allall)
	{
		this.allall = allall;
	}

	public double getP()
	{
		return p;
	}
	public void setP(double p)
	{
		this.p = p;
	}
	public String getTarget()
	{
		return target;
	}
	public void setTarget(String target)
	{
		this.target = target;
	}
	public String getTestset()
	{
		return testset;
	}
	public void setTestset(String testset)
	{
		this.testset = testset;
	}
	public boolean isHelp()
	{
		return help;
	}
	public void setHelp(boolean help)
	{
		this.help = help;
	}

	  public boolean isDead()
	{
		return dead;
	}
	public void setDead(boolean dead)
	{
		this.dead = dead;
	}
	public boolean isFresh()
	{
		return fresh;
	}
	public void setFresh(boolean fresh)
	{
		this.fresh = fresh;
	}
	public boolean isDefaultMode()
	{
		return defaultMode;
	}
	public void setDefaultMode(boolean defaultMode)
	{
		this.defaultMode = defaultMode;
	}
	public boolean isVDL() {
		return VDL;
	}
	public void setVDL(boolean vDL) {
		VDL = vDL;
	}
	public boolean isCDL() {
		return CDL;
	}
	public void setCDL(boolean cDL) {
		CDL = cDL;
	}
	public boolean isODL() {
		return ODL;
	}
	public void setODL(boolean oDL) {
		ODL = oDL;
	}

	// Class mutants
	public boolean isIHI() {
		return IHI;
	}
	public void setIHI(boolean b) {
		IHI = b;
	}

	public boolean isIHD() {
		return IHD;
	}
	public void setIHD(boolean b) {
		IHD = b;
	}

	public boolean isIOD() {
		return IOD;
	}
	public void setIOD(boolean b) {
		IOD = b;
	}

	public boolean isIOP() {
		return IOP;
	}
	public void setIOP(boolean b) {
		IOP = b;
	}

	public boolean isIOR() {
		return IOR;
	}
	public void setIOR(boolean b) {
		IOR = b;
	}

	public boolean isISI() {
		return ISI;
	}
	public void setISI(boolean b) {
		ISI = b;
	}

	public boolean isISD() {
		return ISD;
	}
	public void setISD(boolean b) {
		ISD = b;
	}

	public boolean isIPC() {
		return IPC;
	}
	public void setIPC(boolean b) {
		IPC = b;
	}

	public boolean isPNC() {
		return PNC;
	}
	public void setPNC(boolean b) {
		PNC = b;
	}

	public boolean isPMD() {
		return PMD;
	}
	public void setPMD(boolean b) {
		PMD = b;
	}

	public boolean isPPD() {
		return PPD;
	}
	public void setPPD(boolean b) {
		PPD = b;
	}

	public boolean isPCI() {
		return PCI;
	}
	public void setPCI(boolean b) {
		PCI = b;
	}

	public boolean isPCC() {
		return PCC;
	}
	public void setPCC(boolean b) {
		PCC = b;
	}

	public boolean isPCD() {
		return PCD;
	}
	public void setPCD(boolean b) {
		PCD = b;
	}

	public boolean isPRV() {
		return PRV;
	}
	public void setPRV(boolean b) {
		PRV = b;
	}

	public boolean isOMR() {
		return OMR;
	}
	public void setOMR(boolean b) {
		OMR = b;
	}

	public boolean isOMD() {
		return OMD;
	}
	public void setOMD(boolean b) {
		OMD = b;
	}

	public boolean isOAN() {
		return OAN;
	}
	public void setOAN(boolean b) {
		OAN = b;
	}

	public boolean isJTI() {
		return JTI;
	}
	public void setJTI(boolean b) {
		JTI = b;
	}

	public boolean isJTD() {
		return JTD;
	}
	public void setJTD(boolean b) {
		JTD = b;
	}

	public boolean isJSI() {
		return JSI;
	}
	public void setJSI(boolean b) {
		JSI = b;
	}

	public boolean isJSD() {
		return JSD;
	}
	public void setJSD(boolean b) {
		JSD = b;
	}

	public boolean isJID() {
		return JID;
	}
	public void setJID(boolean b) {
		JID = b;
	}

	public boolean isJDC() {
		return JDC;
	}
	public void setJDC(boolean b) {
		JDC = b;
	}

	public boolean isEOA() {
		return EOA;
	}
	public void setEOA(boolean b) {
		EOA = b;
	}

	public boolean isEOC() {
		return EOC;
	}
	public void setEOC(boolean b) {
		EOC = b;
	}

	public boolean isEAM() {
		return EAM;
	}
	public void setEAM(boolean b) {
		EAM = b;
	}

	public boolean isEMM() {
		return EMM;
	}
	public void setEMM(boolean b) {
		EMM = b;
	}


	// add argument for customized timeout
	public int getTimeout()
	{
		return timeout;
	}
	public void setTimeout(int Timeout)
	{
		this.timeout = Timeout;
	}
	
	  
	}