package org.idwfed.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ShareTo extends Activity {

    public static final String PREFS_NAME = "IDWF";

    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to);
        settings = getSharedPreferences(PREFS_NAME, 0);
        ImageView picView = (ImageView)findViewById(R.id.picture);
        TextView txtView = (TextView)findViewById(R.id.txt);
        Intent receivedIntent = getIntent();
        String receivedAction = receivedIntent.getAction();
        String receivedType = receivedIntent.getType();
        if(receivedAction.equals(Intent.ACTION_SEND)){
            if(receivedType.startsWith("text/")){
                picView.setVisibility(View.GONE);
                String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                if(receivedText!=null){
                    txtView.setText(receivedText);
                }
            }
            else if(receivedType.startsWith("image/")){
                txtView.setVisibility(View.GONE);
                Uri receivedUri = (Uri)receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
                if(receivedUri!=null){
                    picView.setImageURI(receivedUri);
                }
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
            protected String doInBackground(String... params) {

                String serverurl = settings.getString("serverurl", "") + "/@@API/plone/api/1.0/create/abdzatry";
                Log.d("IDWF", "Login:" + serverurl);
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(serverurl);

                String credentials = settings.getString("username", "") + ":" + settings.getString("password", "");
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                httppost.addHeader("Authorization", "Basic " + base64EncodedCredentials);

                try {
                    JSONObject jsonobj = new JSONObject();
                    jsonobj.put("title", "testing");
                    jsonobj.put("description", "whoah");

                    StringEntity se = new StringEntity(jsonobj.toString());
                    se.setContentType("application/json;charset=UTF-8");
                    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));

                    httppost.setEntity(se);

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);


                    String responseText = null;
                    try {
                        responseText = EntityUtils.toString(response.getEntity());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.i("Parse Exception", e + "");
                    }

                    JSONObject responsejson = new JSONObject(responseText);

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        SendPostReqAsyncTask postreq = new SendPostReqAsyncTask();
        postreq.execute();
    }
}
