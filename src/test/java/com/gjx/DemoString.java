package com.gjx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 面试测试类
 * @author Guo
 *
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DemoString {

	public static void main(String[] args) {
		String str = "qesdfsd125q3asdas454asdf1adsf56";
		
//		字符串反向输出
//		String reverse = StringUtils.reverse(str);
//		System.out.println(reverse);
		
//		字符串提取数字到数组中
		Pattern p = Pattern.compile("[^0-9]");
		Matcher m = p.matcher(str);
		String result1 = m.replaceAll(" ");
		String[] split = StringUtils.split(result1);
		
//		字符串数组转int数组
		List<Integer> arrayList = new ArrayList<Integer>();
		for (String string : split) {
			arrayList.add(Integer.valueOf(string));
		}
		
//		int数组排序
		Collections.sort(arrayList);
//		Collections.reverse(arrayList);
		System.out.println(arrayList);
		
		List<String> list = Arrays.asList(split);
		
		List<String> asList = Arrays.asList(split);
		System.out.println(asList.toString());
		System.out.println(Arrays.toString(split));
	}
	
	
}
