package com.geeklub.vass.mc4android.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.geeklub.vass.mc4android.app.beans.UserPassword;


public class SharedPreferencesUtils{
	public static UserPassword readSharedPreferences(Context context) {
		String strName, strPassword;
		SharedPreferences user = context.getSharedPreferences("user_info", 0);
		strName = user.getString("NAME", "");
		strPassword = user.getString("PASSWORD", "");
		if (StringUtils.isBlank(strPassword) && StringUtils.isBlank(strName)) {
			return null;
		}
		UserPassword userPassword = new UserPassword();
		userPassword.setPassword(strPassword);
		userPassword.setUserName(strName);
		return userPassword;
	}

	public static void writeSharedPreferences(Context context, String strName,
			String strPassword) {
		SharedPreferences user = context.getSharedPreferences("user_info", 0);
		Editor userEditor = user.edit();
		userEditor.putString("NAME", strName);
		userEditor.putString("PASSWORD", strPassword);
		userEditor.commit();
	}

	public static void removeSharedPreferences(Context context) {
		SharedPreferences user = context.getSharedPreferences("user_info", 0);
		Editor userEditor = user.edit();
		userEditor.clear().commit();
	}
}
