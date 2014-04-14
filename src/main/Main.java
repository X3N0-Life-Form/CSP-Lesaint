package main;

import java.io.IOException;

import parse.Parser;
import algo.AlgorithmException;
import algo.ArcConsistency_1;
import algo.Backtrack;
import core.CSP;

public class Main {
	
	private static CSP csp;
	private static Backtrack backtrack;

	public static void main(String[] args) throws AlgorithmException, IOException {
		dealsWithArgs(args);
		printRecap();
		backtrack.start();
		System.out.println("### Final Result ###");
		System.out.println(csp);
	}

	private static void dealsWithArgs(String[] args) throws IOException {
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-file":
				csp = Parser.parseCSP(args[++i]);
				backtrack = new Backtrack(csp);
				break;
			case "-ac1":
				backtrack.addBeforeAlgorithm(new ArcConsistency_1(csp));
				break;
			case "-ac3":
			case "-fc":
				System.out.println(args[i] + " not implemented.");
				break;
			}
		}
	}
	
	private static void printRecap() {
		System.out.println(backtrack);
	}

}
