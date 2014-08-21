package org.idwfed.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ShareTo extends Activity {

    public static final String PREFS_NAME = "IDWF";

    private SharedPreferences settings;

    EditText txtView;
    EditText titleTxt;
    EditText bodyTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to);
        settings = getSharedPreferences(PREFS_NAME, 0);
        txtView = (EditText)findViewById(R.id.txt);
        titleTxt = (EditText)findViewById(R.id.titleTxt);
        bodyTxt = (EditText)findViewById(R.id.bodyTxt);
        Intent receivedIntent = getIntent();
        String receivedAction = receivedIntent.getAction();
        String receivedType = receivedIntent.getType();
        if(receivedAction!=null && receivedAction.equals(Intent.ACTION_SEND)){
            if(receivedType.startsWith("text/")){
                String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                if(receivedText!=null){
                    txtView.setText(receivedText);
                }
                String receivedSubject = receivedIntent.getStringExtra(Intent.EXTRA_SUBJECT);
                if(receivedSubject!=null){
                    titleTxt.setText(receivedSubject);
                }

                Bundle bundle = receivedIntent.getExtras();
                for (String key : bundle.keySet()) {
                    Object value = bundle.get(key);
                    Log.d("IDWF - intent", String.format("%s %s (%s)", key,
                            value.toString(), value.getClass().getName()));
                }

                //Log.d("IDWF - title",receivedIntent.getStringExtra(Intent.EXTRA_TITLE));
                //Log.d("IDWF - template",receivedIntent.getStringExtra(Intent.EXTRA_TEMPLATE));
                //Log.d("IDWF - html",receivedIntent.getStringExtra(Intent.EXTRA_HTML_TEXT));
                //Log.d("IDWF - ori uri",receivedIntent.getStringExtra(Intent.EXTRA_ORIGINATING_URI));
                //Log.d("IDWF - referrer",receivedIntent.getStringExtra(Intent.EXTRA_REFERRER));
                //Log.d("IDWF - subject",receivedIntent.getStringExtra(Intent.EXTRA_SUBJECT));
                //Log.d("IDWF - email",receivedIntent.getStringExtra(Intent.EXTRA_EMAIL));
            }
        }
        else if(receivedAction.equals(Intent.ACTION_MAIN)){
            txtView.setText("Nothing has been shared");
        }
        Button mShare = (Button) findViewById(R.id.sharebutton);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doShare();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share_to, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doShare() {
        // Create a new HttpClient and Post Header
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String status){
                Toast toast;
                toast = Toast.makeText(getApplicationContext(),
                        status, Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }

            @Override
            protected String doInBackground(String... params) {

                HttpClient httpclient = new DefaultHttpClient();


                String urlqueryid = settings.getString("serverurl", "") + "/@@API/plone/api/1.0/folders?q=idwfshared";
                Log.d("IDWF - urlqueryid",urlqueryid);
                HttpGet queryidpost = new HttpGet(urlqueryid);
                String uid = "";
                try {
                    HttpResponse idresponse = httpclient.execute(queryidpost);
                    String idresponsetext = null;
                    try {
                        idresponsetext = EntityUtils.toString(idresponse.getEntity());
                        Log.d("IDWF - idresponse",idresponsetext);
                        JSONObject idjson = new JSONObject(idresponsetext);
                        JSONArray juid = idjson.getJSONArray("items");
                        uid = juid.getJSONObject(0).getString("uid");
                    }catch (ParseException e) {
                        e.printStackTrace();
                        Log.i("Parse Exception", e + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                String serverurl = settings.getString("serverurl", "") + "/@@API/plone/api/1.0/documents/create/" + uid;
                //String serverurl = settings.getString("serverurl", "");
                Log.d("IDWF", "Login:" + serverurl);
                HttpPost httppost = new HttpPost(serverurl);


                String credentials = settings.getString("username", "") + ":" + settings.getString("password", "");
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                httppost.addHeader("Authorization", "Basic " + base64EncodedCredentials);
                try {

                    JSONObject jsonobj = new JSONObject();

                    jsonobj.put("title", titleTxt.getText());
                    jsonobj.put("description", txtView.getText());
                    jsonobj.put("effective","2001-05-11");
                    jsonobj.put("expires","2015-12-11");
                    jsonobj.put("subjects","[]");
                    jsonobj.put("document_type","Letter");
                    jsonobj.put("document_owner","admin");
                    jsonobj.put("text",bodyTxt.getText());

                    StringEntity se = new StringEntity(jsonobj.toString());
                    se.setContentType("application/json;charset=UTF-8");
                    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));

                    httppost.setEntity(se);

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);

                    Log.d("IDWF - response",response.toString());

                    String responseText = null;
                    try {
                        responseText = EntityUtils.toString(response.getEntity());
                    }catch (ParseException e) {
                        e.printStackTrace();
                        Log.i("Parse Exception", e + "");
                    }

                    Log.d("IDWF - response Text",responseText);

                    /* Toast toast;
                    toast = Toast.makeText(getApplicationContext(),
                            "Item Shared", Toast.LENGTH_SHORT);
                    toast.show(); */

                    /* String responseText = null;
                    try {
                        responseText = EntityUtils.toString(response.getEntity());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.i("Parse Exception", e + "");
                    }

                    JSONObject responsejson = new JSONObject(responseText); */

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "Successfully shared item";
            }
        }
        SendPostReqAsyncTask postreq = new SendPostReqAsyncTask();
        postreq.execute();
    }
}
