package net.kubasik;
/**
 * @author kjk38
 * Item - Items are stored in the SymTab, they are comparable.
 */
public class Item implements Comparable {

	private String name;
	private String hexValue;
	private String numBytes;
	private boolean conditionCode;
	
	/**
	 * @param arg
	 * A Constructor that creates an Item with just an opcode
	 */
	public Item (String arg){
		name = arg.trim();
	}
	/**
	 * @param opcode
	 * @param hex
	 * @param bytes
	 * @param cc
	 * @throws Exception
	 * 
	 * A Constructor that creates an Item with all needed information
	 */
	public Item (String opcode, String hex, String bytes, String cc) throws Exception{
		name = opcode.trim();
		hexValue = hex.trim();
		numBytes = bytes.trim();
		if (cc.equalsIgnoreCase("NoCC"))
			conditionCode = false;
		else
			conditionCode = true;
	}
	public Item (String opcode, String hex) throws Exception{
		name = opcode.trim();
		hexValue = hex.trim();
		numBytes = "";
		conditionCode=false;
	}
	/**
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return conditionCode
	 */
	public boolean isConditionCode() {
		return conditionCode;
	}

	/**
	 * @return hexValue
	 */
	public String getHexValue() {
		return hexValue;
	}

	/**
	 * @return numBytes
	 */
	public String getNumBytes() {
		return numBytes;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name + " " + hexValue + " " + numBytes + " " + conditionCode;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo (Object o){
		if ( o instanceof Item){
			Item i = (Item) o;
			return this.getName().compareToIgnoreCase(i.getName());
		}
		return 0;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object o){
		if ( o instanceof Item){
			Item i = (Item) o;
			return this.getName().equalsIgnoreCase(i.getName());
		}
		return false;
	}
	
	public static void main(String[] args) {
		

	}

}
