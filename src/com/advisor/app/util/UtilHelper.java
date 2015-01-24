package com.advisor.app.util;

import java.math.BigDecimal;

/**
 * Created by Pawan on 1/24/2015.
 */
public class UtilHelper
{
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

}
