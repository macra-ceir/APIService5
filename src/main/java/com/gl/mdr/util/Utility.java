package com.gl.mdr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

@Component
public class Utility {

	private final static String NUMERIC_STRING = "0123456789";
	public String newDate(int nextdateDay) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DAY_OF_MONTH, nextdateDay);  
		String newDate = sdf.format(cal.getTime());  

		return newDate;

	}


	public static String getTxnId() {

		DateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
		Date date = new Date();
		String transactionId = dateFormat.format(date)+randomNumericString(3);	
		return transactionId;
	}

	public static String randomNumericString(int length) {
		StringBuilder builder = new StringBuilder();
		while (length-- != 0) {
			int character = (int)(Math.random()*NUMERIC_STRING.length());
			builder.append(NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}


	public Date formatChanger(LocalDateTime localDateTime ) throws ParseException {

		/*SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
		java.util.Date date = format.parse(dateString);
		 */
		String dmyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDateTime);
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dmyFormat);
		
		return date;


	}
	

	public <T> void replaceNullsWithEmptyStrings(T myObject) {
	    Field[] fields = myObject.getClass().getDeclaredFields();
	    for (Field field : fields) {
	        field.setAccessible(true);
	        
	        try {
	            Object value = field.get(myObject);
	            if (field.getType().getName().equals("java.lang.String") && !Objects.nonNull(value)) {
	                field.set(myObject, "");
	            }
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        }
	    }
	}


	public static void main(String[] args) {
		String str = "org.hibernate.dialect.MySQL5InnoDBDialect";
		
		System.out.println(str.toLowerCase().contains("mysql"));
	} 


}
