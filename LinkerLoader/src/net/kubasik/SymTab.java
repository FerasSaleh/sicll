package net.kubasik;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author Kevin Kubasik
 * SymTab - A Symbolic Table used to lookup optcodes and their relivant information.
 */
public class SymTab {

	private LinkedList[] table; 
	/**
	 * Default Constuctor, initializing hashtable.
	 */
	public SymTab (){
		table = new LinkedList[137];
		for( int i = 0; i<137;i++){
			table[i] = new LinkedList();
		}
	}
	
	/**
	 * @param str
	 * @return the item if found, or null if none is found
	 */
	public Item find(String str){
		str = str.trim().toLowerCase();
		Item i= null;
		try{
			i = new Item (str.trim());
			if (i != null){
				int pos = 0;
				char[] chars = i.getName().toCharArray();
				for (int index=0; index<4 && index<chars.length;index++)
					pos = pos + chars[index];
				if (pos < 0)
					pos = pos*-1;
				//System.out.println ( i.getName() + pos);
				while (pos > 137){
					pos = pos/10;
				}
				if (table[pos].contains(i)){
					//System.out.println( "Found! " + i.getName() + " at" + pos);
					Iterator it = table[pos].iterator();
					while (it.hasNext()){
						Item i2 = (Item) it.next();
						if (i2.equals(i)){
							return i2; 
						}
					}
				} else {
					System.out.println( i.getName() + " was not found in SymbolTable");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param str
	 * @return item that has been inserted 
	 */
	public Item insert (String str){
		str = str.trim().toLowerCase();
		Item i= null;
		try{
			StringTokenizer tokenizer = new StringTokenizer (str);
			if (tokenizer.countTokens() == 4)
				i = new Item (tokenizer.nextToken().trim() ,tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken());
			if (i != null){
				int pos = 0;
				char[] chars = i.getName().toCharArray();
				for (int index=0; index<4 && index<chars.length;index++)
					pos = pos + chars[index];
				if (pos < 0)
					pos = pos*-1;
				//System.out.println ( i.getName() + pos);
				while (pos > 137){
					pos = pos/10;
				}
				//System.out.println ( i.getName() + " " +pos);
				if (table[pos].contains(i)){
					//System.out.println( "Duplicate! " + i);
					return i;
				}
				table[pos].add(i);
				return i;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public Item insert (Item i){
		
		try{
			if (i != null){
				int pos = 0;
				char[] chars = i.getName().toCharArray();
				for (int index=0; index<4 && index<chars.length;index++)
					pos = pos + chars[index];
				if (pos < 0)
					pos = pos*-1;
				//System.out.println ( i.getName() + pos);
				while (pos > 137){
					pos = pos/10;
				}
				//System.out.println ( i.getName() + " " +pos);
				if (table[pos].contains(i)){
					System.out.println( "Duplicate! " + i);
					return i;
				}
				table[pos].add(i);
				return i;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  Private method for testing.s
	 */
	public void printTable (){
		for (int i =0; i<137;i++){
			Iterator iter = table[i].iterator();
			while (iter.hasNext())
				System.out.println(((Item) iter.next()));
		}
	}
	
	public static void main(String[] args) {
		SymTab symbols = new SymTab();
		try {
			BufferedReader br = new BufferedReader ( new FileReader ("./InSymbols.txt"));
			String line = br.readLine();
			while (line != null ){
				symbols.insert(line);
				line=br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//symbols.printTable();
		System.out.println("Finished Loading Symbol Table from file ./InSymbols.txt");
		try {
			BufferedReader br = new BufferedReader ( new FileReader ("./OutSymbols.txt"));
			String line = br.readLine();
			while (line != null ){
				System.out.println(symbols.find(line) + " from " + line );
				line=br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
