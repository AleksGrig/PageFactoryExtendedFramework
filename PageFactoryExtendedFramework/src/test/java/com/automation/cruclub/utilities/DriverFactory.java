package com.automation.cruclub.utilities;

public class DriverFactory {

	private static String cromeDriverExePath;
	private static String firefoxDriverExePath;
	private static String configPropertyFile;
	private static String gridPath;
	private static boolean isRemote;

	public static boolean getIsRemote() {
		return isRemote;
	}

	public static void setIsRemote(boolean isRemote) {
		DriverFactory.isRemote = isRemote;
	}

	public static String getCromeDriverExePath() {
		return cromeDriverExePath;
	}

	public static void setCromeDriverExePath(String cromeDriverExePath) {
		DriverFactory.cromeDriverExePath = cromeDriverExePath;
	}

	public static String getFirefoxDriverExePath() {
		return firefoxDriverExePath;
	}

	public static void setFirefoxDriverExePath(String firefoxDriverExePath) {
		DriverFactory.firefoxDriverExePath = firefoxDriverExePath;
	}

	public static String getConfigPropertyFile() {
		return configPropertyFile;
	}

	public static void setConfigPropertyFile(String configPropertyFile) {
		DriverFactory.configPropertyFile = configPropertyFile;
	}

	public static String getGridPath() {
		return gridPath;
	}

	public static void setGridPath(String gridPath) {
		DriverFactory.gridPath = gridPath;
	}
}
