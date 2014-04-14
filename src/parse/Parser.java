package parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.CSP;
import core.Constraint;
import core.Domain;
import core.IntegerDomain;
import core.Variable;
import core.enums.ConstraintType;
import core.enums.VariableType;
import core.exceptions.VariableException;

public class Parser {
	
	public static final String $_CONSTRAINT = "$Constraint:";
	public static final String $_VARIABLE = "$Variable:";
	public static final String $_DOMAIN = "$Domain:";

	protected enum State {
		PARSING,
		DOMAIN,
		VARIABLE,
		CONSTRAINT
	}
	
	private static State state;
	private static List<Domain> domains;
	private static List<Variable> variables;
	private static List<Constraint> constraints;
	private static Map<Variable, String> var_dom;
	
	private static String bufferedLine = null;

	public static CSP parseCSP(String filename) throws IOException {
		System.out.println("### Begin parsing of " + filename + " ###");
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader reader = new BufferedReader(isr);
		
		domains = new ArrayList<Domain>();
		variables = new ArrayList<Variable>();
		constraints = new ArrayList<Constraint>();
		var_dom = new HashMap<Variable, String>();
		
		String line = "";
		state = State.PARSING;
		while ((line = reader.readLine()) != null) {
			line = removeComments(line);
			setState(line);
			
			switch (state) {
			case DOMAIN:
				if (line.trim().startsWith($_DOMAIN)) {
					Domain dom = parseDomain(reader, line);
					domains.add(dom);
				} else if (bufferedLine != null && bufferedLine.trim().startsWith($_DOMAIN)) {
					Domain dom = parseDomain(reader, bufferedLine);
					domains.add(dom);
				} else {
					continue;
				}
				break;
			case VARIABLE:
				if (line.trim().startsWith($_VARIABLE)) {
					variables.add(parseVariable(reader, line));
				} else if (bufferedLine != null && bufferedLine.trim().startsWith($_VARIABLE)) {
					variables.add(parseVariable(reader, bufferedLine));
				} else {
					continue;
				}
				break;
			case CONSTRAINT:
				if (line.trim().startsWith($_CONSTRAINT)) {
					constraints.add(parseConstraint(reader, line));
				} else if (bufferedLine != null && bufferedLine.trim().startsWith($_CONSTRAINT)) {
					constraints.add(parseConstraint(reader, bufferedLine));
				}  else {
					continue;
				}
				break;
			
			case PARSING:
				break;
			}
			
		}
		
		CSP csp = constructCSP();
		System.out.println("### Parsing complete ###");
		
		return csp;
	}

	protected static void setState(String line) {
		if (line == null) return;
		if (line.trim().startsWith("#Domains")) {
			state = State.DOMAIN;
		} else if (line.trim().startsWith("#Variables")) {
			state = State.VARIABLE;
		} else if (line.trim().startsWith("#Constraints")) {
			state = State.CONSTRAINT;
		}
	}

	protected static CSP constructCSP() {
		CSP csp = new CSP();
		System.out.println("Constructing CSP");
		for (Variable var : variables) {
			String dname = var_dom.get(var);
			Domain dom = getDom(dname);
			System.out.println(var.getName() + " => " + dom);
			csp.addVariable(var, dom);
		}
		
		for (Constraint con : constraints) {
			System.out.println(con);
			csp.addConstraint(con);
		}
		
		return csp;
	}

	private static Domain getDom(String name) {
		for (Domain dom : domains) {
			if (dom.getName().equals(name)) {
				return dom;
			}
		}
		return null;
	}

	private static ConstraintType getConstraintType(String line) {
		line = getName(line);
		switch (line) {
		case "EQUAL":
			return ConstraintType.EQUAL;
		case "DIFFERENT":
			return ConstraintType.DIFFERENT;
		case "INF":
			return ConstraintType.INF;
		case "INF_EQUAL":
			return ConstraintType.INF_EQUAL;
		case "SUP":
			return ConstraintType.SUP;
		case "SUP_EQUAL":
			return ConstraintType.SUP_EQUAL;
		}
		return null;
	}

	private static Variable getVar(String line) {
		String name = getName(line);
		for (Variable var : variables) {
			if (var.getName().equals(name)) {
				return var;
			}
		}
		return null;
	}

	/**
	 * Note: Domains must have been parsed.
	 * @param reader
	 * @param line
	 * @return
	 * @throws IOException
	 */
	protected static Variable parseVariable(BufferedReader reader, String line) throws IOException {
		String name = getName(line);
		Variable var = new Variable(name, VariableType.INTEGER);
		
		line = reader.readLine();
		do {
			if (line.trim().startsWith("+domain:")) {
				var_dom.put(var, getName(line));
			}
			if (exitLoop(line)) {
				break;
			}
		} while ((line = reader.readLine()) != null);
		
		setState(line);
		return var;
	}

	protected static Domain parseDomain(BufferedReader reader, String line) throws IOException {
		String name = getName(line);
		Domain domain = new IntegerDomain();
		
		domain.setName(name);
		line = reader.readLine();
		while (line != null && !exitLoop(line)) {
			if (line.trim().startsWith("+lowerBoundary:")) {
				int boundary = getInt(line);
				((IntegerDomain)domain).setLowerBoundary(boundary);
			} else if (line.trim().startsWith("+upperBoundary:")) {
				int boundary = getInt(line);
				((IntegerDomain)domain).setUpperBoundary(boundary);;
			}
			
			line = reader.readLine();
		}
		
		setState(line);
		return domain;
	}
	
	/**
	 * Note: Variables must have been parsed.
	 * @param reader
	 * @param line
	 * @return
	 * @throws IOException
	 */
	protected static Constraint parseConstraint(BufferedReader reader, String line) throws IOException {
		//String name = getName(line);
		Variable left = null;
		Variable right = null;
		ConstraintType type = null;
		int value = 0;
		//System.out.println(line);
		line = reader.readLine();
		//System.out.println(line);
		while (line != null && !exitLoop(line)) {
			if (line.trim().startsWith("+variable:")) {
				left = getVar(line);
			} else if (line.trim().startsWith("+type:")) {
				type = getConstraintType(line);
			} else if (line.trim().startsWith("+value:")) {
				value = getInt(line);
			} else if (line.trim().startsWith("+second:")) {
				right = getVar(line);
			}
			
			line = reader.readLine();
		}
		
		setState(line);
		try {
			if (right != null) {
				return new Constraint(left, type, right);
			} else {
				return new Constraint(left, type, value);
			}
		} catch (VariableException e) {
			return null;
		}
	}

	private static int getInt(String line) {
		int index = line.indexOf(':');
		int boundary = Integer.parseInt(line.substring(index + 1).trim());
		return boundary;
	}

	private static boolean exitLoop(String line) {
		if (line.trim().startsWith("$") || line.trim().startsWith("#")) {
			bufferedLine = line;
			return true;
		} else {
			bufferedLine = null;
			return false;
		}
	}

	public static String getName(String line) {
		return line.substring(line.indexOf(':') + 1).trim();
	}
	
	/**
	 * Removes comments from a line.
	 * @param line
	 * @return
	 */
	public static String removeComments(String line) {
		int index = line.indexOf(';');
		if (index >= 0) {
			return line.substring(0, index);
		} else {
			return line;
		}
	}
}
