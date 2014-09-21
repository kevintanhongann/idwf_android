package org.idwfed.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.idwfed.app.fragment.SharedListFragment;
import org.idwfed.app.util.PrefUtils;


public class SharedListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_list);
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
            Intent settingactivity = new Intent(this, LoginActivity.class);
            startActivity(settingactivity);
            return true;
        } else if (id == R.id.action_new) {
            if (!PrefUtils.getUsername(this).isEmpty() && !PrefUtils.getPassword(this).isEmpty()) {
                Intent newshare = new Intent(this, PublishDocumentActivity.class);
                startActivity(newshare);
                return true;
            } else {
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
            }
        } else if (id == R.id.action_refresh) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new SharedListFragment())
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
