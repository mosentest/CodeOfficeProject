package mu.codeoffice.utility;

import mu.codeoffice.common.InformationException;

public class ColorValidator {

	private static final String[] COLOR_CLASS = {
		"color-palette-primary-blue",
		"color-palette-primary-brightblue", 
		"color-palette-primary-paleblue", 
		"color-palette-primary-green", 
		"color-palette-primary-yellow", 
		"color-palette-primary-red", 
		"color-palette-primary-charcoal", 
		"color-palette-primary-mediumgray", 
		"color-palette-primary-lightgray", 
		"color-palette-secondary-gray", 
		"color-palette-secondary-ashgray", 
		"color-palette-secondary-silver", 
		"color-palette-secondary-brown", 
		"color-palette-secondary-orange", 
		"color-palette-secondary-tan", 
		"color-palette-secondary-lightbrown", 
		"color-palette-secondary-lighterblue", 
		"color-palette-secondary-slate", 
		"color-palette-secondary-limegreen", 
		"color-palette-secondary-emerald", 
		"color-palette-secondary-violet", 
		"color-palette-secondary-mauve", 
		"color-palette-secondary-brightpink", 
		"color-palette-secondary-pink"
	};
	
	public static boolean validateColor(String cssClass) throws InformationException {
		for (String css : COLOR_CLASS) {
			if (css.equals(cssClass)) {
				return true;
			}
		}
		throw new InformationException("Color is invalid");
	}
}
