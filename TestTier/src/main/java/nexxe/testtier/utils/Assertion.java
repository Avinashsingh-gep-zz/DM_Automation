package nexxe.testtier.utils;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.vimalselvam.cucumber.listener.Reporter;

public class Assertion  {
	static SoftAssert softAssertion = new SoftAssert();
	static ExtentReports oExtent;
	static ExtentTest oTest;
	Report report = (Report)ScenarioContext.getContext(Keys.REPORT);

	public static boolean isEqual(String oActual, String OExpected,String TestStep) {
		String sMsg;
		boolean flag = false;
		try {
			if (oActual.equals(OExpected)) {
				sMsg = "Pass : Actual Value :- " + oActual + " Matches with the expected value - " + OExpected;									
				flag = true;
				Assert.assertEquals(oActual, OExpected);
				Reporter.addStepLog(sMsg);
			} else {
				sMsg = "Fail : Actual Value :- " + oActual + " does not match with expected value - " + OExpected;
				flag = false;
				Assert.assertEquals(oActual, OExpected);
				Reporter.addStepLog(sMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("The Error Occured == "+e.getLocalizedMessage());
		} 
		return flag;
	}

	public static boolean isEqual_Soft(String oActual, String OExpected,String TestStep) {
		String sMsg;
		boolean flag = false;
		try {
			if (oActual.equals(OExpected)) {
				sMsg = "Pass : Actual Value :- " + oActual + " Matches with the expected value - " + OExpected;
				flag = true;
				softAssertion.assertEquals(oActual, OExpected);
				Reporter.addStepLog(sMsg);
			} else {
				sMsg = "Fail : Actual Value :- " + oActual + " does not match with expected value - " + OExpected;
				flag = false;
				softAssertion.assertEquals(oActual, OExpected);
				Reporter.addStepLog(sMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("The Error Occured == "+e.getLocalizedMessage());
		
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
			Reporter.addStepLog("The Error Occured == "+e.getLocalizedMessage());
		} finally {
			softAssertion.assertAll();
			
		}
		return flag;
	}

	public static boolean isfalse_Soft(Boolean Value, String TestStep) {
		String sMsg;
		boolean flag = false;
		try {
			if (!Value) {
				sMsg = "Pass : Boolean Value is false" + Value;
				flag = true;
				softAssertion.assertFalse(Value);
				Reporter.addStepLog(sMsg);
			} else {
				sMsg = "Pass : Boolean Value is true" + Value;
				flag = false;
				softAssertion.assertFalse(Value);
				Reporter.addStepLog(sMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("The Error Occured == "+e.getLocalizedMessage());
		} finally {
			softAssertion.assertAll();
			
		}
		return flag;
	}

}
