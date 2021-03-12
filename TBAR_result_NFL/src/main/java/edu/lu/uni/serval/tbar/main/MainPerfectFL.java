package edu.lu.uni.serval.tbar.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.lu.uni.serval.tbar.TBarFixer;
import edu.lu.uni.serval.tbar.TBarFixer.Granularity;
import edu.lu.uni.serval.tbar.config.Configuration;

/**
 * Fix bugs with the known bug positions.
 * 
 * @author kui.liu
 *
 */
public class MainPerfectFL {
	
	private static Logger log = LoggerFactory.getLogger(MainPerfectFL.class);
	private static Granularity granularity = Granularity.File;
	
	public static void main(String[] args) {//		if (args.length != 4) {
//		System.err.println("Arguments: \n"
//		+ "\t<Bug_Data_Path>: the directory of checking out Defects4J bugs. \n"
//		+ "\t<Bug_ID>: bug id of each Defects4J bug, such as Chart_1. \n"
//		+ "\t<defects4j_Home>: the directory of defects4j git repository.\n"
//		+ "\t<isTestFixPatterns>: true - try all fix patterns for each bug, false - perfect fault localization configuration.");
//System.exit(0);
//}
String bugDataPath = "/home/shinera01/research/TBar/TBar/D4J/projects/";
String bugId = "";
String defects4jHome = "/home/shinera01/research/TBar/TBar/D4J/defects4j/";
int number = 0;

boolean isTestFixPatterns = true;//Boolean.valueOf(args[3]);//
String granularityStr = "Line";

if ("line".equalsIgnoreCase(granularityStr) || "l".equalsIgnoreCase(granularityStr)) {
granularity = Granularity.Line;
if (isTestFixPatterns) Configuration.outputPath += "FixPatterns/";
else Configuration.outputPath += "PerfectFL/";
}

number = 25;
while(true) {
bugId = "Chart";
number += 1;
bugId = bugId +"_" + Integer.toString(number);

System.out.println(bugId);

if(number == 1 || number ==4 || number == 8 || number == 9 || number == 11 || number == 12 || number == 19 || number == 20 || number == 24 || number == 26 )
{// correct bug id
	
	}
else {continue;}


if(number == 26) {break;}

fixBug(bugDataPath, defects4jHome, bugId, isTestFixPatterns);




}


number = 117;
while(true) {

bugId = "Closure";
number += 1;
bugId = bugId +"_" + Integer.toString(number);

System.out.println(bugId);



if (number == 2 || number == 4 || number == 10 || number == 11 || number == 13 || number == 38 || number == 40 || number == 46 || number == 62 || number == 63 || number == 70 || number == 73 || number == 102 || number == 115 || number == 117)
{
	//correct bug id 15개

}
else if(number > 133) {
	break;
}

else {
	continue;
}




fixBug(bugDataPath, defects4jHome, bugId, isTestFixPatterns);



}



number = 106;
while(true) {
bugId = "Math";

number += 1;
bugId = bugId +"_" + Integer.toString(number);


System.out.println(bugId);


if(number == 4 || number == 5 || number == 11 || number == 57 || number ==58 || number == 65 || number == 70 || number == 75 || number == 77 || number == 79 || number == 82 || number == 85 || number == 89)
{
//correct patch 12
}
else if(number>106) {
	break;
}

else {
	continue;
}

fixBug(bugDataPath, defects4jHome, bugId, isTestFixPatterns);



}	


number = 65;
while(true){
bugId = "Lang";

number += 1;
bugId = bugId +"_" + Integer.toString(number);



if (number == 6 || number == 10 || number == 24 || number == 26 || number == 33 || number == 39 || number ==47 || number == 51 || number == 57 || number == 59 )
{
	//correct bug 10

}

else if(number>65)
{
	break;
}

else {
	continue;
}


System.out.println(bugId);


fixBug(bugDataPath, defects4jHome, bugId, isTestFixPatterns);


}	

number = 27;
while(true) {
bugId = "Time";
number += 1;
bugId = bugId +"_"+ Integer.toString(number);

System.out.println(bugId);



if(number == 7 || number == 19 || number == 26) {
	//correct bugId 3
}
else if (number > 27)
{
	break;
}

else {
	continue;
}

fixBug(bugDataPath, defects4jHome, bugId, isTestFixPatterns);





}	


number = 0;
while(true) {
	bugId = "Mockito";
	number += 1;
	bugId = bugId +"_"+ Integer.toString(number);


System.out.println(bugId);

if(number == 26 ||  number == 29 || number == 38)
{
	//correct bugId 3
}
else if(number > 38){
	break;
}

else {
	continue;
}


fixBug(bugDataPath, defects4jHome, bugId, isTestFixPatterns);



}	
	
	
	}

	public static void fixBug(String bugDataPath, String defects4jHome, String bugIdStr, boolean isTestFixPatterns) {
		String[] elements = bugIdStr.split("_");
		String projectName = elements[0];
		String Name = bugIdStr;
		int bugId;
		try {
			bugId = Integer.valueOf(elements[1]);
		} catch (NumberFormatException e) {
			System.err.println("Please input correct buggy project ID, such as \"Chart_1\".");
			return;
		}
		bugId = Integer.valueOf(elements[1]);
		
		
		TBarFixer fixer = new TBarFixer(bugDataPath, projectName, bugId, defects4jHome);

		fixer.dataType = "TBar";
		fixer.isTestFixPatterns = isTestFixPatterns;
		switch (granularity) {
		case Line:
			fixer.granularity = Granularity.Line;
			break;
//		case File:
//			fixer.granularity = Granularity.File;
//			break;
		default:
			return;
		}
		

		
		if (Integer.MAX_VALUE == fixer.minErrorTest) {
			System.out.println("Failed to defects4j compile bug " + bugIdStr);
			return;
		}
		
		
		fixer.metric = Configuration.faultLocalizationMetric;
		fixer.fixProcess();
		
		int fixedStatus = fixer.fixedStatus;
		switch (fixedStatus) {
		case 0:
			log.info("=======Failed to fix bug " + bugIdStr);
			break;
		case 1:
			log.info("=======Succeeded to fix bug " + bugIdStr);
			break;
		case 2:
			log.info("=======Partial succeeded to fix bug " + bugIdStr);
			break;
		}
	}

}
