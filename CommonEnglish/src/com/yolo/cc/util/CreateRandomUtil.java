package com.yolo.cc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateRandomUtil {

	/**
	 * 将n个数随机排列
	 * 
	 * @return
	 */
	public static List<Integer> getNumberList(int rang) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < rang; i++) {
			list.add(i);
		}

		for (int i = (rang - 1); i > 0; i--) {
			int number = new Random().nextInt(i);
			int temp = list.get(i);
			list.set(i, list.get(number));
			list.set(number, temp);
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + ": " + list.get(i));
		}
		return list;
	}
}
