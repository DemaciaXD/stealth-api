package com.gjx;

import java.util.Calendar;

public class CalendarDemo {

	public static void main(String[] args) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.set(2018, 4, 12);
		cal2.set(2018, 4, 12);
		cal2.set(Calendar.MILLISECOND, 0);
		System.out.println(cal1.getTime().getTime());
		System.out.println(cal2.getTime().getTime());
	}
}
