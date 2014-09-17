package org.idwfed.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.idwfed.app.api.ApiFactory;
import org.idwfed.app.api.IDWFApi;
import org.idwfed.app.callback.PublicDocumentsCallback;
import org.idwfed.app.domain.Item;
import org.idwfed.app.exception.PublicDocumentsException;
import org.idwfed.app.fragment.SharedListFragment;
import org.idwfed.app.response.PublicDocumentsResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


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
            Intent newshare = new Intent(this, ShareTo.class);
            startActivity(newshare);
            return true;
        } else if (id == R.id.action_refresh) {

        }
        return super.onOptionsItemSelected(item);
    }
}
