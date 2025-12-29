package com.qa.opencart.utils;

public class StringUtil {

	public static String generateEmailId()
	{
		String email="UiAutomation"+Math.random()+"@test.com";
		return email;
	}
}
