package model;

import org.junit.Before;

public class testProcessor {
	Processor ryzen5;
	Process A;
	
	@Before
	void setUp() {
		A = new Process("A", 0, 200, 512);
		ryzen5 = new Processor(2000);
	}
	
	
}
