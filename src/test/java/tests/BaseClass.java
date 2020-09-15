package tests;
//import Pages.NavigationPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.screenrecording.*;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
//import atu.testrecorder.ATUTestRecorder;
//import atu.testrecorder.exceptions.ATUTestRecorderException;
//import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Base64.*;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class BaseClass {
	
	 //ATUTestRecorder recorder;

	public static AndroidDriver<MobileElement> driver;
	

	@BeforeTest
	public void setup()  {

		try {
			final String URL_STRING = "http://127.0.0.1:4723/wd/hub";

			URL url = new URL(URL_STRING);
			

			//Use a empty DesiredCapabilities object
			DesiredCapabilities capabilities = new DesiredCapabilities();
			//capabilities.setCapability("language", "fr");
			//capabilities.setCapability("locale", "CA");
			capabilities.setCapability("platformName", "ANDROID");
			capabilities.setCapability("deviceName", "Pixel 3 API 28");
			capabilities.setCapability("app", "/Users/nidhiman/Downloads/Sample-projects/device-farm/aws-device-farm-sample-app-for-android-master/prebuilt/app-debug.apk");
			
			//capabilities.setCapability("appPackage", "io.appium.android.apis");
			//capabilities.setCapability("appActivity", "io.appium.android.apis.ApiDemos");

			
			driver = new AndroidDriver<MobileElement>(url, capabilities);
			
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			//driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
			((CanRecordScreen) driver).startRecordingScreen();
			
			
			 

		} catch(Exception exp){
			System.out.println("cause is"+ exp.getCause());
			System.out.println("message is" + exp.getMessage());
			exp.printStackTrace();

		}
	}
	
	

	@Test
	public void SampleTest() throws InterruptedException {
		System.out.println("I am inside Sample test");
		System.out.println("test");
		MobileElement el1 = (MobileElement) driver.findElementByAccessibilityId("ReferenceApp");
		el1.click();
		Thread.sleep(1000);
		MobileElement el2 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[8]");
		el2.click();
		Thread.sleep(1000);
	}


	@AfterTest
	public void teardown() throws IOException, InterruptedException {
		System.out.println("inside teardown");
		
		String media = ((CanRecordScreen) driver).stopRecordingScreen();
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		Date date = new Date();
		String dir = "videos" + File.separator + dateFormat.format(date);
		
		File videoDir = new File (dir);
		if(!videoDir.exists()){
			videoDir.mkdirs();
		}
	
		try {
			//String DFarm_Path = System.getenv("DEVICEFARM_LOG_DIR");
			FileOutputStream stream = new FileOutputStream ( videoDir + File.separator + "test" + ".mp4");
			stream.write(org.apache.commons.codec.binary.Base64.decodeBase64(media));
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		  // Stop appium server when test Is ended.
		  // Stop video recording.
		
		  driver.quit();
		
	}

}

