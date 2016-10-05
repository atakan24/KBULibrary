package com.example.atakan_pc.kbulibrary;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListResult extends ListActivity {
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> kitapList;

    private static String url_search = "http://192.168.1.12/tez/arama.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ISBN = "ktp_isbn";
    private static final String TAG_KITAP_ARRAY = "kitap"; //Json array ismi objeleri getirmek için.
    private static final String TAG_KITAP_ADI1 = "ktp_adi";
    private static final String TAG_YAZAR_ADI = "ktp_yazaradi";
    private static final String TAG_YAYIN_TARIH = "ktp_yayin_trh";

    JSONArray ktp_adi = null;

    public String searchkey;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        Intent myIntent = getIntent();


        searchkey = myIntent.getStringExtra("keyword");

        kitapList = new ArrayList<HashMap <String, String> >();

        new LoadKitap().execute();

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String iid = ((TextView) view.findViewById(R.id.id)).getText().toString();
            }
        });
    }
    class LoadKitap extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListResult.this);
            pDialog.setMessage("Kitaplar yükleniyor. Lütfen bekleyiniz...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List <NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("keyword", searchkey));
            JSONObject json = jParser.makeHttpRequest (url_search, "GET", params);
            Log.d("Search kitap: ", json.toString());
            try {
                //Log.d("try içi :", json.toString());

                int success = json.getInt(TAG_SUCCESS);

                if (success == 1)
                {
                    ktp_adi = json.getJSONArray(TAG_KITAP_ARRAY);
                   // Log.d("Secilen kitap:", json.toString());

                    for (int i= 0; i<ktp_adi.length(); i++)
                    {
                        JSONObject c = ktp_adi.getJSONObject(i);
                        //Log.d("bilgiler:", c.toString());

                        //Json nesnelerinin değerleri
                        String kitapAdi = c.getString(TAG_KITAP_ADI1);
                        kitapAdi = "Kitap Adı : " + kitapAdi;
                        String isbn = c.getString(TAG_ISBN);
                        isbn = "Kitap ISBN No : " + isbn;
                        String yazar_adi = c.getString(TAG_YAZAR_ADI);
                        yazar_adi = "Yazar Adı : " + yazar_adi;
                        String yayin_tarih = c.getString(TAG_YAYIN_TARIH);
                        yayin_tarih = "Kitap Yayın Tarihi : " + yayin_tarih;

                          //HashMap oluşturuldu.
                        HashMap<String, String> map = new HashMap<String, String>();

                        //  HashMap key => value
                        map.put(TAG_ISBN, isbn);
                        map.put(TAG_KITAP_ADI1,kitapAdi);
                        map.put(TAG_YAZAR_ADI,yazar_adi);
                        map.put(TAG_YAYIN_TARIH,yayin_tarih);

                        // adding HashList to ArrayList
                        kitapList.add(map);

                    }
                } else {
                    //kitap bulunamadı.
                    //yeniden ara.
                }
            } catch (JSONException e) {
                //Log.d("catch:", e.toString());

                e.printStackTrace();
            }
            //return "success";
            return null;
        }
        protected void onPostExecute(String file_url)
        {
            pDialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            ListResult.this, kitapList,
                            R.layout.list_view, new String[]{
                            TAG_KITAP_ADI1,TAG_ISBN,TAG_YAZAR_ADI, TAG_YAYIN_TARIH},
                            new int[]{R.id.kitapAdi,R.id.id, R.id.yazar_adi, R.id.yayin_tarih});
                    setListAdapter(adapter);
                }
            });

        }
    }



}
