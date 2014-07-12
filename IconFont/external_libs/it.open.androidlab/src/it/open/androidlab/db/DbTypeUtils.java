package it.open.androidlab.db;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 
 * @author c.luchessa
 *
 */
public class DbTypeUtils {

	public static long convertTimestampToLong(Timestamp timestamp) {
		return timestamp==null ? 0 : timestamp.getTime();
	}
	
	public static Timestamp convertLongToTimestamp(long time) {
		return time==0 ? null : new Timestamp(time);
	}
	
	public static long convertDateToLong(Date date) {
		return date==null ? 0 : date.getTime();
	}
	
	public static Date convertLongToDate(long time) {
		return time==0 ? null : new Date(time);
	}
	
	public static int convertBooleanToInt(boolean bool) {
		return bool ? 1 : 0;
	}
	
	public static boolean convertIntToBoolean(int boolInt) {
		return boolInt==1;
	}
	
	public static String convertBigDecimalToString(BigDecimal bigDecimal) {
		return bigDecimal==null ? "0" : bigDecimal.toString();
	}
	
	public static BigDecimal convertStringToBigDecimal(String bigDecimalString) {
		return (bigDecimalString==null || bigDecimalString.equals("0")) ? null : new BigDecimal(bigDecimalString);
	}
}
