package utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author nguyenlm Contains helper functions
 */
public class Utils {

	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static Logger LOGGER = getLogger(Utils.class.getName());
	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-4s] [%1$tF %1$tT] [%2$-7s] %5$s %n");
	}

	public static Logger getLogger(String className) {
		return Logger.getLogger(className);
	}

	public static String getCurrencyFormat(int num) {
		Locale vietname = new Locale("vi", "VN");
		NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(vietname);
		return defaultFormat.format(num);
	}

	/**
	 * Return a {@link java.lang.String String} that represents the current time in the format of yyyy-MM-dd HH:mm:ss.
	 *
	 * @author hieudm
	 * @return the current time as {@link java.lang.String String}.
	 */
	public static String getToday() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * Return a {@link java.lang.String String} that represents the cipher text
	 * encrypted by md5 algorithm.
	 *
	 * @author hieudm vnpay
	 * @param message - plain text as {@link java.lang.String String}.
	 * @return cipher text as {@link java.lang.String String}.
	 */
	public static String md5(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));
			// converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
			Utils.getLogger(Utils.class.getName());
			digest = "";
		}
		return digest;
	}


	public static String Sha256(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(message.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) {
			digest = "";
		} catch (NoSuchAlgorithmException ex) {
			digest = "";
		}
		return digest;
	}

	//Util for VNPAY
	public static String hashAllFields(Map fields) {
		List fieldNames = new ArrayList(fields.keySet());
		Collections.sort(fieldNames);
		StringBuilder sb = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) fields.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				sb.append(fieldName);
				sb.append("=");
				sb.append(fieldValue);
			}
			if (itr.hasNext()) {
				sb.append("&");
			}
		}
		return hmacSHA512(Configs.secretKey,sb.toString());
	}

	public static String hmacSHA512(final String key, final String data) {
		try {

			if (key == null || data == null) {
				throw new NullPointerException();
			}
			final Mac hmac512 = Mac.getInstance("HmacSHA512");
			byte[] hmacKeyBytes = key.getBytes();
			final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
			hmac512.init(secretKey);
			byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
			byte[] result = hmac512.doFinal(dataBytes);
			StringBuilder sb = new StringBuilder(2 * result.length);
			for (byte b : result) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();

		} catch (Exception ex) {
			System.out.println(ex);
			return "nothing";
		}
	}

//    public static String getIpAddress(HttpServletRequest request) {
//        String ipAdress;
//        try {
//            ipAdress = request.getHeader("X-FORWARDED-FOR");
//            if (ipAdress == null) {
//                ipAdress = request.getRemoteAddr();
//            }
//        } catch (Exception e) {
//            ipAdress = "Invalid IP:" + e.getMessage();
//        }
//        return ipAdress;
//    }

	public static String getRandomNumber(int len) {
		Random rnd = new Random();
		String chars = "0123456789";
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}

}