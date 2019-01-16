package com.pawpaw.framework.common.util;

import com.pawpaw.common.util.AssertUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 钱币相关的工具类
 * 
 * @author liujixin 上午11:08:50
 */
public class MoneyUtil {
	private static final String chineseMoneySymbol = "¥";

	/**
	 * 分格式化成元，保留两位小数点
	 * 
	 * @param number
	 * @return
	 */
	public static float cent2yuanTwoDecimalPlaces(Integer cent) {
		cent = cent == null ? 0 : cent;
		AssertUtil.assertTrue(cent >= 0, "金额不能为负数");
		BigDecimal bd = new BigDecimal(cent);
		bd = bd.divide(new BigDecimal(100));
		String s = new DecimalFormat("0.00").format(bd);
		return Float.parseFloat(s);
	}

	/**
	 * 分格式化成元，两位小数点,带上人民币符号
	 * 
	 * @param number
	 * @return
	 */
	public static String cent2yuanTwoDecimalPlacesChineseSymbol(Integer cent) {
		cent = cent == null ? 0 : cent;
		float yuan = cent2yuanTwoDecimalPlaces(cent);
		String s = new DecimalFormat("0.00").format(yuan);
		return chineseMoneySymbol + s;
	}

	/**
	 * 
	 * 分加上打折，转换成元。如果不足1分，最小单位是1分，不足1分的部分给去掉
	 * 
	 * @param discount 10是不打折，1是打一折 ，7.5是七五折
	 * @return
	 */
	public static float cent2yuanTwoDecimalPlaces(Integer cent, Float discount) {
		if (cent == null) {
			cent = 0;
		}
		if (discount == null) {
			// 设置成不打折
			discount = 10F;
		}
		AssertUtil.assertTrue(discount <= 10, "discount不能超过10");
		AssertUtil.assertTrue(discount >= 0, "discount不能为负数");
		BigDecimal realPrice = new BigDecimal(cent).multiply(new BigDecimal(discount)).divide(new BigDecimal(10));
		// 最小的单位就是分了，所以这里使用intValue，把不足1分钱的都去掉
		return cent2yuanTwoDecimalPlaces(realPrice.intValue());

	}

	/**
	 * 
	 * 
	 * @param number
	 * @param discount 10是不打折，1是打一折 ，7.5是七五折
	 * @return
	 */
	public static String cent2yuanTwoDecimalPlacesChineseSymbol(Integer cent, Float discount) {
		float yuan = cent2yuanTwoDecimalPlaces(cent, discount);
		String s = new DecimalFormat("0.00").format(yuan);
		return chineseMoneySymbol + s;
	}

}
