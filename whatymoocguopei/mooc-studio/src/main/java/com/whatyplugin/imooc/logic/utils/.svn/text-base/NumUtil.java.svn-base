package com.whatyplugin.imooc.logic.utils;

public class NumUtil {
	public static String num2Hanzi(int aaaa) {
		String num = "";
		int[] Wei = new int[4];
		int a = 0;// 定义a是为了判断输入的数字是几位数；
		String[] ss = { "千", "百", "十", "" };
		for (int i = 0; i < Wei.length; i++) {
			Wei[i] = aaaa % 10;
			aaaa = aaaa / 10;
			if (aaaa == 0)
				break;
			a++;
		}
		a = a + 1;

		if (1 == a) {
			System.out.print(num2String(Wei[0]));
			num = num2String(Wei[0]);
		}
		if (2 == a) {
			
			if (Wei[0] != 0){
				System.out.print(num2String(Wei[1]) + ss[2]
						+ num2String(Wei[0]));
				if(Wei[1] == 1)
					num = ss[2]
							+ num2String(Wei[0]);
				else
					num = num2String(Wei[1]) + ss[2]
							+ num2String(Wei[0]);
			}
			else {
				System.out.print(num2String(Wei[1]) + ss[2]);
				if(Wei[1] == 1){
					num = ss[2];
				}else
					num = num2String(Wei[1]) + ss[2];
			}
		}
		if (3 == a) {
			if (Wei[0] != 0 && Wei[1] != 0) {
				System.out.print(num2String(Wei[2]) + ss[1]
						+ num2String(Wei[1]) + ss[2] + num2String(Wei[0]));
				num = num2String(Wei[2]) + ss[1]
						+ num2String(Wei[1]) + ss[2] + num2String(Wei[0]);
			} else if (Wei[0] != 0 && Wei[1] == 0) {
				System.out.print(num2String(Wei[2]) + ss[1] + "零"
						+ num2String(Wei[0]));
				num = num2String(Wei[2]) + ss[1] + "零"
						+ num2String(Wei[0]);
			} else if (Wei[0] == 0 && Wei[1] == 0) {
				System.out.print(num2String(Wei[2]) + ss[1]);
				num = num2String(Wei[2]) + ss[1];
			}
		}
		if (4 == a) {
			// 百、十、个位均为0，如1000；
			if (Wei[0] == 0 && Wei[1] == 0 && Wei[2] == 0) {
				System.out.print(num2String(Wei[3]) + ss[0]);
				num = num2String(Wei[3]) + ss[0];
			}
			// 百、十、个位仅仅个位不为0，如1001；
			else if (Wei[0] != 0 && Wei[1] == 0 && Wei[2] == 0) {
				System.out.print(num2String(Wei[3]) + ss[0] + "零"
						+ num2String(Wei[0]));
				num = num2String(Wei[3]) + ss[0] + "零"
						+ num2String(Wei[0]);
			}
			// 百、十、个位仅仅十位不为0，如1010；
			else if (Wei[0] == 0 && Wei[1] != 0 && Wei[2] == 0) {
				System.out.print(num2String(Wei[3]) + ss[0] + "零"
						+ num2String(Wei[1]) + ss[2]);
				num = num2String(Wei[3]) + ss[0] + "零"
						+ num2String(Wei[1]) + ss[2];
			}
			// 百、十、个位仅仅百位为0，如1011；
			else if (Wei[0] != 0 && Wei[1] != 0 && Wei[2] == 0) {
				System.out.print(num2String(Wei[3]) + ss[0] + "零"
						+ num2String(Wei[1]) + ss[2] + num2String(Wei[0]));
				num = num2String(Wei[3]) + ss[0] + "零"
						+ num2String(Wei[1]) + ss[2] + num2String(Wei[0]);
			}
			// 百、十、个位仅仅百位不为0，如1100；
			else if (Wei[0] == 0 && Wei[1] == 0 && Wei[2] != 0) {
				System.out.print(num2String(Wei[3]) + ss[0]
						+ num2String(Wei[2]) + ss[1]);
				num = num2String(Wei[3]) + ss[0]
						+ num2String(Wei[2]) + ss[1];
			}
			// 百、十、个位仅仅十位为0，如1101；
			else if (Wei[0] != 0 && Wei[1] == 0 && Wei[2] != 0) {
				System.out
						.print(num2String(Wei[3]) + ss[0] + num2String(Wei[2])
								+ ss[1] + "零" + num2String(Wei[0]));
				num = num2String(Wei[3]) + ss[0] + num2String(Wei[2])
						+ ss[1] + "零" + num2String(Wei[0]);
			}
			// 百、十、个位仅仅个位为0，如1110；
			else if (Wei[0] == 0 && Wei[1] != 0 && Wei[2] != 0) {
				System.out.print(num2String(Wei[3]) + ss[0]
						+ num2String(Wei[2]) + ss[1] + num2String(Wei[1])
						+ ss[2]);
				num = num2String(Wei[3]) + ss[0]
						+ num2String(Wei[2]) + ss[1] + num2String(Wei[1])
						+ ss[2];
			}
			// 百、十、个位都不为0，如1111；
			else if (Wei[0] != 0 && Wei[1] != 0 && Wei[2] != 0) {
				System.out.print(num2String(Wei[3]) + ss[0]
						+ num2String(Wei[2]) + ss[1] + num2String(Wei[1])
						+ ss[2] + num2String(Wei[0]));
				num = num2String(Wei[3]) + ss[0]
						+ num2String(Wei[2]) + ss[1] + num2String(Wei[1])
						+ ss[2] + num2String(Wei[0]);
			}
		}
		return num;
	}

	public static String num2String(int nnn) {
		String bb = null;
		switch (nnn) {
		case 0:
			bb = "零";
			break;
		case 1:
			bb = "一";
			break;
		case 2:
			bb = "二";
			break;
		case 3:
			bb = "三";
			break;
		case 4:
			bb = "四";
			break;
		case 5:
			bb = "五";
			break;
		case 6:
			bb = "六";
			break;
		case 7:
			bb = "七";
			break;
		case 8:
			bb = "八";
			break;
		case 9:
			bb = "九";
			break;
		}
		return bb;
	}
	
	public static String createChapterName(int num){
		
		return "第" + num2Hanzi(num) + "章";
	}
	
	public static String createSectionName(int num){
		
		return "第" + num2Hanzi(num) + "讲";
	}
}
