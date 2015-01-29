package com.advisor.app.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pawan on 1/24/2015.
 */
public class UtilHelper
{
	private static Pattern pattern;
	private static Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String NAME_PATTERN ="^[A-Za-z]*$";

	public static int getMinutesRemaining( BigDecimal amount, String rates )
	{
		BigDecimal rate = new BigDecimal( rates ).setScale( 5, BigDecimal.ROUND_FLOOR );
		BigDecimal minutes = amount.setScale( 5, BigDecimal.ROUND_FLOOR );
		int count = 0;
		while( minutes.compareTo( rate ) >= 0 )
		{
			count++;
			minutes = minutes.subtract( rate ).setScale( 5, BigDecimal.ROUND_FLOOR );
		}
		return count;
	}

	public static boolean isNotNull( String txt )
	{
		return txt != null && txt.trim().length() > 0 ? true : false;
	}

	public static boolean validateName(String name)
	{
		pattern = Pattern.compile( NAME_PATTERN );
		matcher = pattern.matcher( name );
		return matcher.matches();
	}
	
	public static boolean validate( String email )
	{
		pattern = Pattern.compile( EMAIL_PATTERN );
		matcher = pattern.matcher( email );
		return matcher.matches();

	}

}
