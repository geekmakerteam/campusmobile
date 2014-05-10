package com.geeklub.vass.mc4android.app.utils;

import android.os.Environment;
import android.util.Log;
import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSSignCondition;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.policy.Policy;
import com.baidu.inf.iis.bcs.policy.PolicyAction;
import com.baidu.inf.iis.bcs.policy.PolicyEffect;
import com.baidu.inf.iis.bcs.policy.Statement;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by justhacker on 9/05/14.
 */
public class FileUtil {

	public static String host = "bcs.duapp.com";
	public static String accessKey = "64FGI53auUzr0wYQn3czfWGR";
	public static String secretKey = "YF4GW5uorp8wCRnC16ScFeXLx9z63Dc7";
	public static String bucket = "simpnews";
	public static String object="";
	public static String weburl="http://officeweb365.com/o/?i=44&furl=";


	public static List<ObjectSummary> listObject(BaiduBCS baiduBCS) {
		ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setStart(0);
		listObjectRequest.setLimit(20);
		BaiduBCSResponse<ObjectListing> response = baiduBCS.listObject(listObjectRequest);
		Log.i("----listObject---","we get [" + response.getResult().getObjectSummaries().size() + "] object record.");
	    List<ObjectSummary> osList=new ArrayList<ObjectSummary>();
		for (ObjectSummary os : response.getResult().getObjectSummaries()) {
			Log.i("----listObject---",os.toString());
			Log.i("----(2)-------",os.getParentDir().split("/",0)[1]);
			Log.i("-----(3)------",object);
           if(os.getParentDir().split("/",0)[1].equals(object))
           {
	           osList.add(os);
	           Log.i("---(4)----","ss");
           }
		}
		return osList;
	}

	public static void getObjectWithDestFile(BaiduBCS baiduBCS,String url) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, object);
		try
		{
			File file=new File(Environment.getExternalStorageDirectory().getPath()+"/campusmobile/"+url);
			File parentFile = file.getParentFile();
			if(!parentFile.exists())
			{
				parentFile.mkdirs();
			}
			file.createNewFile();
			baiduBCS.getObject(getObjectRequest,file);
		}
		catch(Exception e)
		{
            e.printStackTrace();
		}

	}


	public static void putObjectByFile(BaiduBCS baiduBCS,String url,String username)
	{
		object="/"+object+"/"+url.split("/",0)[url.split("/",0).length-1];
		Log.i("---object---",object);
		PutObjectRequest request = new PutObjectRequest(bucket, object, createSampleFile(url));
		ObjectMetadata metadata = new ObjectMetadata();
		// metadata.setContentType("text/html");
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		ObjectMetadata objectMetadata = response.getResult();
		putObjectPolicyByPolicy(baiduBCS,username);
		getObjectPolicy(baiduBCS);
	}

	public static void putObjectPolicyByPolicy(BaiduBCS baiduBCS,String username) {
		Policy policy = new Policy();
		Statement st1 = new Statement();
		st1.addAction(PolicyAction.all).addAction(PolicyAction.all);
		st1.addUser("hangzhoushoot").addUser(username);
		st1.addResource(bucket + object).addResource(bucket + object);
		st1.setEffect(PolicyEffect.allow);
		policy.addStatements(st1);
		baiduBCS.putObjectPolicy(bucket, object, policy);
	}

	public static Policy getObjectPolicy(BaiduBCS baiduBCS) {
		BaiduBCSResponse<Policy> response = baiduBCS.getObjectPolicy(bucket, object);
		Log.i("After analyze: ",response.getResult().toJson());
		Log.i("Origianal str: " ,response.getResult().getOriginalJsonStr());
		return response.getResult();
	}


	public static String generateUrl(BaiduBCS baiduBCS) {
		GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(HttpMethodName.GET, bucket, object);
		generateUrlRequest.setBcsSignCondition(new BCSSignCondition());
		System.out.println(baiduBCS.generateUrl(generateUrlRequest));
		return baiduBCS.generateUrl(generateUrlRequest);
	}


	private static File createSampleFile(String url)
	{
		File file=new File(url);
		return file;
	}
}
