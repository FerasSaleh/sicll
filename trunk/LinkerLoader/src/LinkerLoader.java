import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	private static Program prog;
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
			
			prog = new Program (st.nextToken().trim(), Integer.parseInt(st.nextToken(), 16), cs_addr);
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
				System.out.println("Item: " + i);
				EsTab.insert(i);
				str = str.substring(pos+7);
				//System.out.println(str);
			}
		} 
	}
	
	public static void processLine2(String str){
		if (str.startsWith("H")){
			cs_addr = Integer.valueOf((EsTab.find(str.substring(1,str.indexOf(' '))).getHexValue()), 16);
			execAddr= cs_addr;
		} else if (str.startsWith("T")){
			str = str.substring(1);
			char[] chars = str.toCharArray();
			
		}
			
			
			
			
			/*} else if ( str.startsWith("T")){
			str = str.substring(1);
			String memLoc = str.substring(0, 6);
			memLoc = addCSADDR(memLoc);
			LinkedList<Byte[]> objCode = new LinkedList<Byte[]>();
			str=str.substring(8);
			System.out.print("Memory Location: " + memLoc + " ");
			while (str.length() > 8){
				Byte[] word = new Byte[4];
				word[0]=(Byte.decode("#"+str.substring(0, 2)));
				word[1]=(Byte.decode("#"+str.substring(2, 4)));
				word[2]=(Byte.decode("#"+str.substring(4, 6)));
				word[3]=(Byte.decode("#"+str.substring(6, 8)));
				objCode.add(word);
				System.out.print(((Byte[]) objCode.getLast())[0]);
				System.out.print(((Byte[]) objCode.getLast())[1]);
				System.out.print(((Byte[]) objCode.getLast())[2]);
				System.out.print(((Byte[]) objCode.getLast())[3]+ " ");
				str=str.substring(8);
			}
			System.out.println();
			memory.put(memLoc,objCode);
			
		}*/
	}
	
	private static String addHexStrings(String s1, String s2){
		return Integer.toHexString((Integer.valueOf(s1, 16)+Integer.valueOf(s2, 16)));
	}
	private static String addCSADDR(String s1){
		return Integer.toHexString((Integer.valueOf(s1, 16)+cs_addr));
	}
	
}
