package org.idwfed.app;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SharedListActivity extends Activity {

    public static final String PREFS_NAME = "IDWF";

    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_list);
        settings = getSharedPreferences(PREFS_NAME, 0);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new SharedListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shared_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingactivity = new Intent(this,LoginActivity.class);
            startActivity(settingactivity);
            return true;
        }
        else if (id == R.id.action_new) {
            Intent newshare = new Intent(this,ShareTo.class);
            startActivity(newshare);
            return true;
        }
        else if(id == R.id.action_refresh) {
            FragmentManager fmanager = getFragmentManager();
            SharedListFragment slf = (SharedListFragment) fmanager.findFragmentById(R.id.container);
            slf.updateList();
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) slf.getListAdapter();
            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class SharedListFragment extends ListFragment {

        List<String> values = new ArrayList<String>();

        public SharedListFragment() {
        }

        public void updateList() {
                // Create a new HttpClient and Post Header
                class SendPostReqAsyncTask extends AsyncTask<String, Void, Boolean> {

                    protected void onPostExecute(Boolean status){
                        String message = "";
                        if(status){
                            message = "Shared List Updated";
                        }
                        else{
                            message = "Failed to update shared list";
                        }
                        Toast toast;
                        toast = Toast.makeText(getActivity(),
                                message, Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    protected Boolean doInBackground(String... params) {

                        HttpClient httpclient = new DefaultHttpClient();
                        Boolean success = true;

                        String urlqueryid = settings.getString("serverurl", "") + "/@@API/plone/api/1.0/documents?creator=" + settings.getString("username","");
                        Log.d("IDWF - listurl ", urlqueryid);
                        HttpGet queryidpost = new HttpGet(urlqueryid);
                        String uid = "";
                        try {
                            HttpResponse idresponse = httpclient.execute(queryidpost);
                            String idresponsetext = null;
                            try {
                                idresponsetext = EntityUtils.toString(idresponse.getEntity());
                                Log.d("IDWF - idresponse",idresponsetext);
                                JSONObject idjson = new JSONObject(idresponsetext);
                                int count = idjson.getInt("count");
                                JSONArray items = idjson.getJSONArray("items");
                                values.clear();
                                for(int i=0;i<count;i++){
                                    values.add(items.getJSONObject(i).getString("title"));
                                }
                            }catch (ParseException e) {
                                e.printStackTrace();
                                Log.i("Parse Exception", e + "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return success;
                    }
                }
                SendPostReqAsyncTask postreq = new SendPostReqAsyncTask();
                postreq.execute();

            }


        public void onListItemClick(ListView l, View v, int position, long id) {
            Toast toast;
            toast = Toast.makeText(getApplicationContext(),
                    "hi", Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            //updateList();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    inflater.getContext(), android.R.layout.simple_list_item_1,
                    values);
            setListAdapter(adapter);

            adapter.notifyDataSetChanged();

            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
}
