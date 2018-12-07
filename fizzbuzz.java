//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// Rick's solution for the Ofgem challenge.
// Rick Turner 05 Dec 2018
//
// Comments:
// *) Turns out that this is cleaner to do in Java than Python, so this one is in Java. Also, I
//		can do this with Eclipse on Windows - installing that damn vendor code on my (real) dev
//		system (Linux / TensorFlow / Anaconda / GPU's) trashed it so thoroughly that it is going
// 		to take me a couple more days to recover it. So, I have done this on a laptop.
//
// *) The execution environment I have used / tested the code under is the Eclipse debug runtime
//		and if works fine in that environment. Being Java, it should be runnable as a jar file or
//		even - with a minimum of work - as a Java web applet, but I have not tried it with either.
//		All I/O is to the console and from the keyboard, so it will run on any machine.
//
// *) This solution contains nothing fancy - it is simply an implementation of what I
//		think is the most direct and/or concise way of doing what was asked, whilst avoiding
//      unnecessarily complex language constructs by doing things in noddy steps.
//
//		I offer no apology for this if you want to disagree with me as there are always multiple
//		ways of doing anything in a language like Java, but experience shows that this is the
//		best way of writing maintainable code.
//
// *) There is basically no error detection in this code, so it is not even remotely suitable
//		for any sort of production use. It the user enters something daft, then it will simply
//		misbehave or just plain crash. Caveat Receptor. There are not even try/catch statements.
//
// *) Likewise, there is no documentation, but I have put comments into the code that will tell
//		you what it is doing
//
// *) I have not followed any specific coding standards, but I have as usual used indentation
//		(and comments where needed) in such a way as to make the logic flow obvious
//
// *) I have tested this 'ad hoc' (it all works ok)
//		However, I have not done any sort of extensive or structured (e.g. branch, coverage, etc)
//		testing. It is quite possible therefore that there are still bugs in the code somewhere.
//		If you find one, I apologise in advance, and if it really matters to you let me
//		know and I will fix it
//
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

package ofgem;

import java.lang.String;
import java.util.Scanner;

public class fizzbuzz {

	// level indicator for debugging - set the value below to 'false' if you don't want to see the
	// inline status messages
	//private static final Boolean debug=true;
	private static final Boolean debug=false;

	// couple of class level variables, declared here for convenience. Declared as private
	// since I dont want anybody outside the fizzbuzz world being able to change them by
	// accident (or otherwise!)
	private static String c3="3", c5="5";

	//*********************************************************************************************
	// Internal helper methods. Declared as private since we dont (need to) expose them outside
	// of the methods in this class
	//*********************************************************************************************
	private static boolean isDivisbleBy3(int inum) {
		return (0==inum%3 ? true : false);
	}

	private static boolean isDivisbleBy5(int inum) {
		return (0==inum%5 ? true : false);
	}

	private static boolean containsChar(String s, String c) {
		return s.contains(c);			// will return true if found, false if not
	}

	private static boolean isAllSameChar(String s) {
		char c = s.charAt(0); 		// get first char in string

		for(int k=0; k<s.length(); k++ ) {
			if ( s.charAt(k) != c)
				return false;		// drop out as soon as first different char found
		}

		// else
		return true;		// if we get here, all the characters were the same
	}

	private static boolean isOdd(int inum) {
		if (inum%2 != 0)	//odd
			return true;
		else
			return false;
	}

	//*********************************************************************************************
	// shell main routine
	//*********************************************************************************************
	public static void main(String[] args) {
		if (debug) System.out.println("Debug ==> entered FBmain()");

		String result="a";	// placeholder for results

		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// get a number from the user via the console
		// comment - this next chunk of code is a candidate to become an independent method in its
		// own right, since we duplicate it for both the number choice and the 'round choice'.
		// However, I have not done so since this is (a) not asked for in the 'instructions' that have
		// been provided, (b) such a tiny bit of code that it does not matter particularly, and (c)
		// this is not a production application in any way, shape or form. If it were, I would assume
		// that a graphical UI or some sort would [have to] be created anyway.
		// So, I just duplicate this code ....
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Scanner s = new Scanner(System.in);
		System.out.println( "Enter the number to check: ");
		String snum = s.nextLine();
		int inum = Integer.parseInt(snum);			// convert the string to integer

		if (debug) System.out.println("Debug ==> num as String is:"+ snum);
		if (debug) System.out.println("Debug ==> num as integer is:" + inum);

		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// ...here. Get the method ('round' logic) to be used:
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//int Round = 3;					// for testing only
		System.out.println("Enter the Round (method) to use: ");
		String sr = s.nextLine();
		int Round = Integer.parseInt(sr);

		s.close();

		// call the method for the appropriate round15#
		switch (Round) {
			case (1):
				System.out.println("Info ==> Method 1 chosen: calling fb1() ");
				result=fb1 (inum);
				break;
			case (2):
				System.out.println("Info ==> Method 2 chosen: calling FBvar() ");
				result = fbvar(snum, inum, true);
				break; 			
			default:
				System.out.println("Error ==> invalid Round number entered - exiting");
				System.exit(-1);
				break;		// not strictly necessary here, but...
		}

		System.out.println(result);

		// print out the results....
		if (debug) System.out.println("Main: returned results are: " + result);

		// tidy up and exit
		if (debug) System.out.println("Debug ==> leaving FBmain()");

	} // main


	//*********************************************************************************************
	// Round 1 method. Print 'fizz' if divisible by 3, 'buzz' divisible by 5, and 'fizz buzz' if
	// both are true. Else print the number.
	//*********************************************************************************************
	public static String fb1 (int inum) {
		if (debug) System.out.println("Debug ==> entering fb1() - Round 1");

		String outstr =""; 			// initialise empty string
		boolean modnum = false;		// initialise as not divisible by 3 or 5

		if (isDivisbleBy3(inum)) {
			outstr += "fizz ";
			modnum = true;		// divisble by 3
		}

		if (isDivisbleBy5(inum)) {
			outstr += "buzz ";
			modnum = true;		// divisble by 5
		}

		if (!modnum)			// number is indivisible by 3 or 5
			outstr += inum;

		if (debug) System.out.println("Round 1 - fb1() - result is: " + outstr);    // print results
		if (debug) System.out.println("Debug ==> leaving fb1() - Round 1");

		return outstr;
	}   // fb()


	//*********************************************************************************************
	// Round 2 method. Print 'fizz' if divisible by 3, or contains the character 3, 'buzz' if
	// divisible by 5 or contains the character 5, and 'fizz buzz' if at least one of both (pairs)
	// is true.
	//
	// Nothing about printing the number if neither apply is specified, so we will give the user a
	// helpful warning message if none of the above are true, and if we have been called by some
	// main() routine that is expecting user output rather than a silent return with no indication that
	// that we have actually executed and not just died.
	//
	// However, if we have been called by another routine (fbdeluxe in Round 3 for example)
	// then we dont want to add it to the returned string as the calling routine would receive a
	// warning that it is not expecting and which may or may not be relevant to it anyway.
	//
	// Commment - we pass both the integer and string versions of the number entered to this
	// method as we will use both, and doing so avoids unnecessary type conversions
	//*********************************************************************************************
	public static String fbvar (String snum, int inum, boolean maincaller) {
		if (debug) System.out.println("Debug ==> entering fbvar - Round 2");
//		String c3="3", c5="5";
		String outstr =""; 			// initialise empty string
		boolean fbed=false;				// flag - did we find fizz or buzz?

		if( (isDivisbleBy3(inum)) || (containsChar(snum, c3)) ) {
			outstr += "fizz ";
			fbed = true;
		}

		if( (isDivisbleBy5(inum)) || (containsChar(snum, c5)) ) {
			outstr += "buzz ";
			fbed = true;
		}

		if (debug) System.out.println("Round 2 - fbvar() - result is: " + outstr);    // print results
		if (debug) System.out.println("Debug ==> leaving fbvar - Round 2");

		// if this routine has been called directly by a main routine, we ought to give the
		// user a warning if a number is neither fizz nor buzz - since we dont print out
		// the number here it would just return silently with no indication of what is
		// going on.

		// Print a warning if necessary
		if (maincaller && !fbed) {		// we have been called by a main() routine and are not simply
										// being used as a subroutine
			System.out.println("Warning ==> number is neither fizz nor buzz");
		}

		// exit to caller
		return(outstr);
	} // fbvar()


} // class fizzbuzz
