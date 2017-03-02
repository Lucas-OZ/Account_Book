package com.per.note.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppUtils {

	public static String getVersion(Context context) {
		String version = "null";
		try{
			PackageManager manager = context.getPackageManager();
		    PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
		    version = info.versionName;
		}catch(Exception e){
			e.printStackTrace();
		}
		return version;
	}
	
	
	public static int getVersionCode(Context context) {
		int version = 0;
		try{
			PackageManager manager = context.getPackageManager();
		    PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
		    version = info.versionCode;
		}catch(Exception e){
			e.printStackTrace();
		}
		return version;
	}
}
