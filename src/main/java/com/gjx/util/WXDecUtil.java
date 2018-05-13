package com.gjx.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class WXDecUtil {

	public static Map<String, Object> dec(String encryptedData, String session_key, String iv) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			byte[] resultByte = AES.decrypt(Base64.decodeBase64(encryptedData),
					Base64.decodeBase64(session_key), Base64.decodeBase64(iv));
			if (null != resultByte && resultByte.length > 0) {
				String userInfo = new String(resultByte, "UTF-8");
				map.put("status", "1");
				map.put("msg", "解密成功");
				map.put("userInfo", userInfo);
			} else {
				map.put("status", "0");
				map.put("msg", "解密失败");
			}
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return map;
	}
}
