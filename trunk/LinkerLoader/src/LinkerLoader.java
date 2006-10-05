import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class LinkerLoader {

	private static BigInteger memory_start  = new BigInteger("2000");
	private static BigInteger cs_addr = new BigInteger("2000");
	private static int execAddr = 0;
	private static SymTab EsTab = new SymTab();
	public static LinkedHashMap<String, String> memory = new LinkedHashMap<String, String>();
	
	
	
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader ( new FileReader ("ObjProgs.txt"));
			String line = br.readLine();
			while (line != null ){
				processLine(line);
				line=br.readLine();
			}
			EsTab.printTable();
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
			 printMemoryMap();
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
			
			Program prog = new Program (st.nextToken().trim(), stringToBigInt(st.nextToken()), cs_addr);
			EsTab.insert(new Item (prog.programName, bigIntToString(cs_addr)));
			cs_addr=cs_addr.add(prog.length);
			return;
		} else if (str.startsWith("D")){
			str = str.substring(1);
			int pos = 0;
			while (pos <= str.length()){
				pos = str.indexOf(' ');
				if (str.charAt(pos+1)==' ')
					pos = pos+1;
				Item i = new Item (str.substring(0, pos), 
						bigIntToString(stringToBigInt(str.substring(pos, pos+7).trim()).add(cs_addr)));
				System.out.println("Item: " + EsTab.insert(i));
				str = str.substring(pos+7);
			}
		} 
		
	}
	
	public static void processLine2(String str){
		if (str.startsWith("H")){
			cs_addr = stringToBigInt((EsTab.find((str.substring(1,str.indexOf(' '))))).getHexValue());
			//System.out.println(cs_addr);
		} else if (str.startsWith("T")){
			char[] chars = str.toCharArray();
			int ptr;
			BigInteger lineStartAddr, lineSize;
			StringBuilder sb = new StringBuilder();
	        //obtain starting address
	        String tmpStr="";
	        for(ptr=1; ptr<7; ptr++) 
	        	sb.append(chars[ptr]);
	        
	        lineStartAddr=new BigInteger(sb.toString().trim(), 16);
	        lineStartAddr = lineStartAddr.add(cs_addr);
	       
	        sb = new StringBuilder();
	        
	        for(; ptr<9; ptr++)
	        	sb.append(chars[ptr]);
	        lineSize=new BigInteger(sb.toString().trim(), 16);

	        sb = new StringBuilder();
	        for(int i=0; i< lineSize.intValue() ; i++){
	            
	            tmpStr = "";
	            tmpStr += chars[ptr++];
	            tmpStr += chars[ptr++];
	            sb.append(bigIntToString(stringToBigInt(tmpStr.trim())));
	            
	        }
	        if (!memory.containsKey((lineStartAddr)))
	        	memory.put(lineStartAddr.toString(), sb.toString());
	        else {
	        	sb.append(" ");
	        	sb.append(memory.get((lineStartAddr)));
	        	memory.put(lineStartAddr.toString(), sb.toString());
	        }
	       
		} else if ( str.startsWith("M")){
		
			StringBuilder tempAddr = new StringBuilder();
			BigInteger tempByte, numHalfBytes;
			int index =0; 
			for (index=1;index<7;index++){
				tempAddr.append(str.charAt(index));
			}
			tempByte = new BigInteger(tempAddr.toString(), 16);
			tempByte = tempByte.add(cs_addr);
			tempAddr = new StringBuilder();
			
			for (;index<9;index++){
				tempAddr.append(str.charAt(index));
			}
			char sign=' ';
			if (str.contains("+"))
				sign = '+';
			else if (str.contains("-"))
				sign = '-';
			index=0;
			index = str.indexOf(sign);
			tempAddr = new StringBuilder();
			Item i = EsTab.find(str.substring(index+1));
			if (i != null){
				System.out.println(str.substring(7, index));
			}
				
		}
		
		
	}
	private static void printMemoryMap(){
		TreeSet ts = new TreeSet(memory.keySet());
		Iterator itr = ts.iterator();
		while (itr.hasNext()){
			String str = (String) itr.next();
			StringBuilder sb = new StringBuilder();
			String temp = memory.get(str);
			while (temp.length()> 0){
				if (temp.length() < 8){
					sb.append(temp);
					continue;
				}
					
				sb.append(temp.substring(0,8));
				sb.append(" ");
				temp = temp.substring(8);
			}
			System.out.printf(" %x : %s \n", stringToBigInt(str), sb.toString());
			//System.out.println(str + " : "+ memory.get(str));
		}
	}

	/*public static String addHexStrings(String s1, String s2){
		return Integer.toHexString((Integer.valueOf(s1, 16)+Integer.valueOf(s2, 16)));
	}
	public static String addCSADDR(String s1){
		return Integer.toHexString((Integer.valueOf(s1, 16)+cs_addr));
	}*/
	public static String bigIntToString(BigInteger bi){
		String s = bi.toString(16);            
	    if (s.length() % 2 != 0) {
	        s = "0"+s;
	    }
	    return s;
	}
	public static String intToString(int i){
		BigInteger bi = new BigInteger(Integer.toString(i));
		String s = bi.toString(16);            
	    if (s.length() % 2 != 0) {
	        s = "0"+s;
	    }
	    return s;
	}
	public static int intFromString(String s){
		BigInteger bi = new BigInteger(s.getBytes());
	    return bi.intValue();
	}
	public static int intFromString(String s, int i){
		BigInteger bi = new BigInteger(s,i);
	    return bi.intValue();
	}
	public static BigInteger stringToBigInt(String str){
		return  new BigInteger(str, 16);
	}
}
