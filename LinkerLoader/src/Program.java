
public class Program {
	
	public String programName;
	public int length;
	public int starting;
	public Program(String arg0, int arg1, int arg2){
		programName = arg0;
		length = arg1;
		starting = arg2;
	}
	
	public String toString(){
		return String.format("%s , %s, %s ", programName, Integer.toHexString(length), Integer.toHexString(starting) );
	}
}
