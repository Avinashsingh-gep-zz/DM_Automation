package dm.testtier.utils;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Assertion {
	static SoftAssert softAssertion = new SoftAssert();
	static ExtentReports oExtent;
	static ExtentTest oTest;

	public static boolean isEqual(String oActual, String OExpected,String TestStep) {
		String sMsg;
		boolean flag = false;
		try {
			if (oActual.equals(OExpected)) {
				sMsg = "Pass : Actual Value :- " + oActual + " Matches with the expected value - " + OExpected;
				oTest.log(Status.PASS, TestStep + "<br/>" + sMsg);
				flag = true;
				Assert.assertEquals(oActual, OExpected);
			} else {
				sMsg = "Fail : Actual Value :- " + oActual + " does not match with expected value - " + OExpected;
				oTest.log(Status.FAIL, TestStep + "<br/>" + sMsg);
				flag = false;
				Assert.assertEquals(oActual, OExpected);
			}
		} catch (Exception e) {
			e.printStackTrace();
			oTest.log(Status.FAIL, TestStep + "<br/>" + " The Error happen while comparing" + e);
		} finally {
			oExtent.flush();
		}
		return flag;
	}

	public static boolean isEqual_Soft(String oActual, String OExpected,String TestStep) {
		String sMsg;
		boolean flag = false;
		try {
			if (oActual.equals(OExpected)) {
				sMsg = "Pass : Actual Value :- " + oActual + " Matches with the expected value - " + OExpected;
				oTest.log(Status.PASS, TestStep + "<br/>" + sMsg);
				flag = true;
				softAssertion.assertEquals(oActual, OExpected);
			} else {
				sMsg = "Fail : Actual Value :- " + oActual + " does not match with expected value - " + OExpected;
				oTest.log(Status.FAIL, TestStep + "<br/>" + sMsg);
				flag = false;
				softAssertion.assertEquals(oActual, OExpected);
			}
		} catch (Exception e) {
			e.printStackTrace();
			oTest.log(Status.FAIL, TestStep + "<br/>" + " The Error happen while comparing" + e);
		} finally {
			softAssertion.assertAll();
			oExtent.flush();
		}
		return flag;
	}

	public static boolean isTrue_Soft(Boolean Value, String TestStep) {
		String sMsg;
		boolean flag = false;
		try {
			if (Value) {
				sMsg = "Pass : Boolean Value is true" + Value;
				oTest.log(Status.PASS, TestStep + "<br/>" + sMsg);
				flag = true;
				softAssertion.assertTrue(Value);
			} else {
				sMsg = "Pass : Boolean Value is false" + Value;
				oTest.log(Status.FAIL, TestStep + "<br/>" + sMsg);
				flag = false;
				softAssertion.assertTrue(Value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			oTest.log(Status.FAIL, TestStep + "<br/>" + " The Error happen while comparing" + e);
		} finally {
			softAssertion.assertAll();
			oExtent.flush();
		}
		return flag;
	}

	public static boolean isfalse_Soft(Boolean Value, String TestStep) {
		String sMsg;
		boolean flag = false;
		try {
			if (!Value) {
				sMsg = "Pass : Boolean Value is false" + Value;
				oTest.log(Status.PASS, TestStep + "<br/>" + sMsg);
				flag = true;
				softAssertion.assertFalse(Value);
			} else {
				sMsg = "Pass : Boolean Value is true" + Value;
				oTest.log(Status.FAIL, TestStep + "<br/>" + sMsg);
				flag = false;
				softAssertion.assertFalse(Value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			oTest.log(Status.FAIL, TestStep + "<br/>" + " The Error happen while comparing" + e);
		} finally {
			softAssertion.assertAll();
			oExtent.flush();
		}
		return flag;
	}

}
