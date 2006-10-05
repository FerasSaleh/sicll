import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LinkerLoader {

	private static int memory_start  = Integer.parseInt("2000", 16);
	private static int cs_addr = memory_start;
	private static int execAddr = 0;
	private static SymTab EsTab = new SymTab();
	//private static Program prog;
	public static Hashtable memory = new Hashtable();
	
	
	
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader ( new FileReader ("ObjProgs.txt"));
			String line = br.readLine();
			while (line != null ){
				processLine(line);
				line=br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			BufferedReader br2 = new BufferedReader ( new FileReader ("ObjProgs.txt"));
			String line2 = br2.readLine();
			while (line2 != null ){
				processLine2(line2);
				line2=br2.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void processLine(String str) throws Exception{
		if (str.startsWith("H")){
			str = str.substring(1);
			StringTokenizer st = new StringTokenizer(str);
			
			Program prog = new Program (st.nextToken().trim(), Integer.parseInt(st.nextToken(), 16), cs_addr);
			EsTab.insert(new Item (prog.programName, Integer.toHexString(cs_addr)));
			cs_addr=cs_addr+prog.length;
			System.out.println(prog + " " + Integer.toHexString(cs_addr));
			return;
		} else if (str.startsWith("D")){
			str = str.substring(1);
			int pos = 0;
			while (pos <= str.length()){
				pos = str.indexOf(' ');
				if (str.charAt(pos+1)==' ')
					pos = pos+1;
				Item i = new Item (str.substring(0, pos), 
						Integer.toHexString((Integer.valueOf(str.substring(pos, pos+7).trim(),16)+cs_addr)));
				System.out.println("Item: " + EsTab.insert(i));
				str = str.substring(pos+7);
			}
		} 
	}
	
	public static void processLine2(String str){
		if (str.startsWith("H")){
			cs_addr = Integer.valueOf((EsTab.find(str.substring(1,str.indexOf(' '))).getHexValue()), 16);
			execAddr= cs_addr;
		} else if (str.startsWith("T")){
			//str = str.substring(1);
			char[] chars = str.toCharArray();
			LinkedList ll = new LinkedList();
			int ptr, lineStartAddr, lineSize;
	        //obtain starting address
	        String tmpStr="";
	        for(ptr=1; ptr<7; ptr++) tmpStr += chars[ptr];
	        lineStartAddr=Integer.parseInt(tmpStr.trim(), 16);
	        lineStartAddr+=cs_addr;
	        System.out.println (Integer.toHexString(lineStartAddr));
	        tmpStr = "";
	        for(; ptr<9; ptr++) tmpStr += chars[ptr];
	        lineSize=Integer.parseInt(tmpStr.trim(), 16);
	        //System.out.println(Integer.toHexString(lineSize));
	        //System.out.println(lineSize);
	        for(int i=0; i< lineSize; i++){
	            int thisByte;
	            tmpStr = "";
	            tmpStr += chars[ptr++];
	            tmpStr += chars[ptr++];
	            thisByte = Integer.parseInt(tmpStr.trim(), 16);
	            ll.add(thisByte);
	        }
	        Iterator itr = ll.iterator();
	        String objCode="";
	        while (itr.hasNext()){
	        	objCode += Integer.toHexString(((Integer) itr.next()).intValue());
	        }
	        //System.out.println(objCode);
	        if (!memory.containsKey(Integer.toHexString(lineStartAddr)))
	        	memory.put(Integer.toHexString(lineStartAddr),objCode);
	        else {
	        	String temp = (String) memory.get(Integer.toHexString(lineStartAddr));
	        	objCode = objCode + " " + temp;
	        	memory.put(Integer.toHexString(lineStartAddr), objCode);
	        }
	        printMemoryMap();
		} else if ( str.startsWith("M")){
			str = str.substring(1);
			String tempAddr ="";
			int tempByte =0;
			int index =0; 
			for (index=0;index<6;index++){
				tempAddr += str.charAt(index);
			}
			tempByte = Integer.parseInt(tempAddr, 16);
			//System.out.println(tempAddr);
			//System.out.println(Integer.toHexString(tempByte));
			char sign=' ';
			if (str.contains("+"))
				sign = '+';
			else if (str.contains("-"))
				sign = '-';
			index=0;
			index = str.indexOf(sign);
			Item i = EsTab.find(str.substring(index+1));
			//System.out.println(i);
		}
		
	}
	private static void printMemoryMap(){
		Enumeration enumer = memory.keys();
		while (enumer.hasMoreElements()){
			String str = (String) enumer.nextElement();
			System.out.printf(" %s : %s \n", str, memory.get(str));
		}
	}

	private static String addHexStrings(String s1, String s2){
		return Integer.toHexString((Integer.valueOf(s1, 16)+Integer.valueOf(s2, 16)));
	}
	private static String addCSADDR(String s1){
		return Integer.toHexString((Integer.valueOf(s1, 16)+cs_addr));
	}
	
}
