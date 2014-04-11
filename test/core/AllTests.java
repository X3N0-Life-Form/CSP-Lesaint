package core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestsConstraint.class, TestsCSP.class,
		TestsIntegerDomain.class, TestsVariable.class })
public class AllTests {

}
