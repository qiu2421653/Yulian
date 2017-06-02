package com.naga.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 批量导入验证方法
 * 
 * @author 江风成
 */
public class ExcelCheakUtil {
	// 验证电话号码手机号11位固话11位带横线
	private static final String phone = "^(1\\d{10})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
	// 验证身份证
	private static final String idcard = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";

	// 验证电话号码
	public static String reGexMobileNumber(String cellName, String str, int indexRow) {
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile(phone);
			Matcher matcher = regex.matcher(str);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		if (!flag) {
			return cellName + "列：第" + indexRow + "行，数据的电话号码格式错误！\r\n";
		}
		return "";
	}

	// 验证身份证号码
	public static String reGexIdcardNumber(String cellName, String str, int indexRow) {
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile(idcard);
			Matcher matcher = regex.matcher(str);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		if (!flag) {
			return cellName + "列：第" + indexRow + "行，数据的身份证格式错误！\r\n";
		}
		return "";
	}

	// 验证字符的最大长度
	public static String StrMaxLength(String cellName, String str, int indexRow, int lengths) {
		if (str.length() >= lengths) {
			return cellName + "列：第" + indexRow + "行，数据最大长度不能超过" + lengths + "个字符！\r\n";
		}
		return "";
	}

	// 验证字符的最小长度
	public static boolean StrMinLength(String str, int lengths) {
		return str.length() >= lengths;
	}

	// 是否为空
	public static String isNull(Cell cell, String cellName, int indexRow) {
		if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
			return cellName + "列：第" + indexRow + "行，数据不能为空！\r\n";
		}
		return "";
	}

	// 校验值
	public static String getValue(Cell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue().trim());
		}
	}
	// 说明：parseTimestamp是一个转化时间的方法，因为考虑到日期的格式可能为2010/12/14，代码如下：
	public static String parseTimestamp(Cell cell,String cellName, int indexRow) {
		if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
		    short format = cell.getCellStyle().getDataFormat();  
		    SimpleDateFormat sdf = null;  
		    if(format == 14 || format == 31 || format == 57 || format == 58){  
		        //日期  
		        sdf = new SimpleDateFormat("yyyy-MM-dd");  
		    }else if (format == 20 || format == 32) {  
		        //时间  
		        sdf = new SimpleDateFormat("HH:mm");  
		    }  
		    double value = cell.getNumericCellValue();  
		    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
			return "";
		}else{  
			return cellName + "列：第" + indexRow + "行，数据时间格式有误！\r\n";
		}
	
	}
}
