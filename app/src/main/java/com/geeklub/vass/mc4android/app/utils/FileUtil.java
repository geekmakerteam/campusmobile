package com.geeklub.vass.mc4android.app.utils;

import android.util.Log;
import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by justhacker on 9/05/14.
 */
public class FileUtil {

	static String host = "bcs.duapp.com";
	static String accessKey = "64FGI53auUzr0wYQn3czfWGR";
	static String secretKey = "YF4GW5uorp8wCRnC16ScFeXLx9z63Dc7";
	static String bucket = "simpnews";
	static String object="";


	private static List<ObjectSummary> listObject(BaiduBCS baiduBCS) {
		ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setStart(0);
		listObjectRequest.setLimit(20);
		BaiduBCSResponse<ObjectListing> response = baiduBCS.listObject(listObjectRequest);
		Log.i("----listObject---","we get [" + response.getResult().getObjectSummaries().size() + "] object record.");
	    List<ObjectSummary> osList=new ArrayList<ObjectSummary>();
		for (ObjectSummary os : response.getResult().getObjectSummaries()) {
			Log.i("----listObject---",os.toString());
            osList.add(os);
		}
		return osList;
	}
}
