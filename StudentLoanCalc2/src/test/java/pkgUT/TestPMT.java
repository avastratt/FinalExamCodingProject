package pkgUT;

import static org.junit.Assert.*;
import org.apache.poi.ss.formula.functions.*;
import org.junit.Test;


public class TestPMT {

	@Test
	public void test() {
		double PMT;
		double r = 0.07 / 12;
		double n = 20 * 12;
		double p = 150000;
		double f = 0;
		boolean t = false;
		PMT = Math.abs(FinanceLib.pmt(r, n, p, f, t));
		
		double PMTExpected = 1162.95;
		
		assertEquals(PMTExpected, PMT, 0.01);
		}
		

		@Test
		public void test2() {
			double PMT;
			double r = 0.20 / 12;
			double n = 20 * 12;
			double p = 27000;
			double f = 200;
			boolean t = false;
			PMT = Math.abs(FinanceLib.pmt(r, n, p, f, t));
			
			
			double PMTExpected = 458.74;
			
			assertEquals(PMTExpected, PMT, 0.01);
			}
			
		@Test
		public void test3() {
			double PMT;
			double r = 0.10 / 12;
			double n = 10 * 12;
			double p = 30000;
			double f = 1500;
			boolean t = false;
			PMT = Math.abs(FinanceLib.pmt(r, n, p, f, t));
			
			
			double PMTExpected = 403.77;
			
			assertEquals(PMTExpected, PMT, 0.01);
			}
	}