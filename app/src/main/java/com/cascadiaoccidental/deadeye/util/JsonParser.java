package com.cascadiaoccidental.deadeye.util;
import android.util.Base64;
import com.cascadiaoccidental.deadeye.data.AmazonResultData;
import org.json.JSONException;
import org.json.JSONObject;
public class JsonParser {
	public static AmazonResultData parse(String json) throws JSONException {
		AmazonResultData data = new AmazonResultData();
		JSONObject obj = new JSONObject(json);
		data.amazonPrice = obj.getDouble("amazonPrice");
		data.lowestPrice = obj.getDouble("lowestPrice");
		data.isbn = obj.getString("isbn");
		data.title = obj.getString("title");
		data.type = obj.getString("type");
		data.image = Base64.decode(obj.getString("image"), 0);
        data.errorFlag = obj.getBoolean("errorFlag");
        data.errorText = obj.getString("errorText");
		data.listingsUrl = null;
		return data;
	}
}
