package test;

import java.util.Locale;
import java.util.TimeZone;

public class Tester {

	public static void main(String[] args) {
		System.out.println("abcSASw-a".matches("[a-zA-Z]+((-)?[a-zA-Z])+"));
		System.out.println("abcSASw-a-a-sdf".matches("[a-zA-Z]+((-)?[a-zA-Z])+"));
		System.out.println("abcSASw-a-".matches("[a-zA-Z]+((-)?[a-zA-Z])+"));
		System.out.println("abcSASw".matches("[a-zA-Z]+((-)?[a-zA-Z])+"));
	}

}
