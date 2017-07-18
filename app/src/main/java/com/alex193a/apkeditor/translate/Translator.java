package com.alex193a.apkeditor.translate;

import android.net.Uri;
import android.util.Log;

import com.gmail.heagoo.apkeditor.translate.TranslateItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;

public class Translator {

    private String targetLangCode;
    private JSONArray array;

    public Translator(String _target) {

        this.targetLangCode = _target;

    }

    public void translate(List<TranslateItem> items) {

        // Concat the string
        StringBuilder queryBuf = new StringBuilder();
        for (TranslateItem item : items) {
            queryBuf.append("&text=" + Uri.encode(item.originValue));
        }
        queryBuf.deleteCharAt(queryBuf.length() - 1);
        String q = queryBuf.toString();

        String YANDEX_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
        String YANDEX_FULL_URL = YANDEX_BASE_URL + BuildConfig.API_KEY + q + "&lang=" + targetLangCode + "&format=plain";

        try {

            String json = Jsoup.connect(YANDEX_FULL_URL).ignoreContentType(true).get().body().text();
            JSONObject mainObject = new JSONObject(json);

            array = mainObject.getJSONArray("text");
            Log.e("JSONArray", array.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        parseContent(array, items);
    }

    // Parse the content and save result to items
    private void parseContent(JSONArray jArray, List<TranslateItem> items) {

        try {
            if (items.size() == 1) {
                items.get(0).translatedValue = jArray.get(0).toString();
//				Log.d("DEBUG",
//						items.get(0).originValue + " ---> "
//								+ items.get(0).translatedValue);
            } else {
                int srcNum = items.size();
                for (int i = 0; i < srcNum; i++) {
                    TranslateItem item = items.get(i);
                    item.translatedValue = jArray.get(i).toString();
//					Log.d("DEBUG", item.originValue + " ---> "
//							+ item.translatedValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
