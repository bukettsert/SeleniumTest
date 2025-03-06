package utils;

import base.BaseTest;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends BaseTest implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        captureScreenshot(result.getName());
    }
}
