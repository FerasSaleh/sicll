import java.math.BigInteger;


public class Program {
	
	public String programName;
	public BigInteger length;
	public BigInteger starting;
	public Program(String arg0, BigInteger arg1, BigInteger arg2){
		programName = arg0;
		length = arg1;
		starting = arg2;
	}
	
	public String toString(){
		return String.format("%s , %s, %s ", programName, LinkerLoader.bigIntToString(length), LinkerLoader.bigIntToString(starting) );
	}
}
