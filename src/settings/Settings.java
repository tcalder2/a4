package settings;

/**
 * The class Settings.
 * 
 * @author James Baron
 * @version 1.0
 */
public class Settings {

	private static boolean fb_test = true;
	private static boolean testMode = false;
	
	public static boolean isTestMode() {
		return testMode;
	}

	public static void setTestMode(boolean testMode) {
		Settings.testMode = testMode;
	}

	public static boolean getFbTest()
	{
		return fb_test;
	}
	
}
