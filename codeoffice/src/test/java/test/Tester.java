package test;

import mu.codeoffice.enums.ProjectRoleType;

public class Tester {

	public static void main(String[] args) {
		ProjectRoleType.getRoles(16 + 8 + 4 + 2 + 1).forEach(System.out::println);
	}

}
