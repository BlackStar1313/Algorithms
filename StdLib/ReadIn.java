package StdLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public final class ReadIn {

	private String lineContent = "";
	private StringBuilder stringBuilder;
	private StringTokenizer token;


	/**
	 * Create an input stream from the standard input.
	 */
	public ReadIn() {
		stringBuilder = new StringBuilder();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			lineContent = br.readLine();
			stringBuilder.append(lineContent);
			token = new StringTokenizer(stringBuilder.toString());
		}catch(FileNotFoundException e) {
			System.out.println("File not found" + e);

		}catch (IOException ioe){
			System.out.println("Exception while reading file " + ioe);
		}
	}

	public ReadIn(File file){
		stringBuilder = new StringBuilder();

		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			while((lineContent = br.readLine()) != null){
				stringBuilder.append(lineContent);
			}
			token = new StringTokenizer(stringBuilder.toString());
		}catch(FileNotFoundException e) {
			System.out.println("File not found" + e);

		}catch (IOException ioe){
			System.out.println("Exception while reading file " + ioe);
		}
	}

	public ReadIn(String filename) {
		this(new File(filename));
	}

	public boolean isEmpty() {
		return token.hasMoreTokens();
	}
	
	public boolean hasNext() {
		return token.hasMoreTokens();
	}

	public boolean hasNextLine() {
		return token.hasMoreTokens();
	}

	/**
	 * Get the rest of input
	 * 
	 * @return	the rest of input 
	 */
	public String readAll() {
		return stringBuilder.toString();
	}

	/**
	 * Read and return the next line.
	 */
	public String readLine() {
		if(!hasNextLine()) {
			throw new NoSuchElementException("There aren't any lines left!!");
		}
		return token.nextToken("\n");
	}


	/**
     * Return the next string from the input stream.
     */
	public String readString() {
		return token.nextToken();
	}

	/**
     * Return the next int from the input stream.
     */
	public int readInt() {
		return Integer.parseInt(token.nextToken("\\s+"));
	}

	/**
     * Return the next double from the input stream.
     */
	public double readDouble() {
		return Double.parseDouble(token.nextToken("\\s+"));
	}

	/**
     * Return the next float from the input stream.
     */
	public float readFloat() {
		return Float.parseFloat(token.nextToken("\\s"));
	}
	
	/**
     * Return the next long from the input stream.
     */
	public long readLong() {
		return Long.parseLong(token.nextToken("\\s+"));
	}
	
	/**
     * Return the next byte from the input stream.
     */
	public Byte readByte() {
		return Byte.parseByte(token.nextToken("\\s+"));
	}

	/**
	 * Read integer from a given file.
	 * @param filename
	 * @return
	 */
	public static int[] readInts(String filename) {
		ReadIn in = new ReadIn(filename);
		String[] strings = in.readAll().split("\\s+");
		int[] ints = new int[strings.length];
		for(int i = 0; i < strings.length; i++) {
			ints[i] = Integer.parseInt(strings[i]);
		}
		return ints;
	}

	/**
	 * Read doubles from file
	 */
	public static double[] readDoubles(String filename) {
		ReadIn in = new ReadIn(filename);
		String[] fields = in.readAll().split("\\s+");
		double[] vals = new double[fields.length];
		for (int i = 0; i < fields.length; i++)
			vals[i] = Double.parseDouble(fields[i]);
		return vals;
	}
	
	/**
     * Return the next boolean from the input stream, allowing "true" or "1"
     * for true and "false" or "0" for false.
     */
    public boolean readBoolean() {
        String s = readString();
        if (s.equalsIgnoreCase("true"))  return true;
        if (s.equalsIgnoreCase("false")) return false;
        if (s.equals("1"))               return true;
        if (s.equals("0"))               return false;
        throw new java.util.InputMismatchException();
    }

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static String[] readStrings(String filename) {
		ReadIn in = new ReadIn(filename);
		String all = in.readAll().toLowerCase();
		String strings[] = all.split("\\s+");
		in = null;
		return strings;
	}

	/**
	 * Read ints from standard input
	 */
	public static int[] readInts() {
		ReadIn in = new ReadIn();
		String[] fields = in.readAll().split("\\s+");
		int[] vals = new int[fields.length];
		for (int i = 0; i < fields.length; i++)
			vals[i] = Integer.parseInt(fields[i]);
		return vals;
	}

	/**
	 * Read doubles from standard input
	 */
	public static double[] readDoubles() {
		ReadIn in = new ReadIn();
		String[] fields = in.readAll().split("\\s+");
		double[] vals = new double[fields.length];
		for (int i = 0; i < fields.length; i++)
			vals[i] = Double.parseDouble(fields[i]);
		return vals;
	}

	/**
	 * Read strings from standard input
	 */
	public static String[] readStrings() {
		ReadIn in = new ReadIn();
		String all = in.readAll().toLowerCase();
		String[] fields = all.split("\\s+");
		return fields;
	}
}
