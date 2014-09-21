package org.idwfed.app.fragment;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.idwfed.app.R;
import org.idwfed.app.api.ApiFactory;
import org.idwfed.app.api.IDWFApi;
import org.idwfed.app.callback.PublicDocumentsCallback;
import org.idwfed.app.domain.Item;
import org.idwfed.app.exception.PublicDocumentsException;
import org.idwfed.app.response.LoginResponse;
import org.idwfed.app.response.PublicDocumentsResponse;
import org.idwfed.app.util.LoginSuccessEvent;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * A placeholder fragment containing a simple view.
 */
public class SharedListFragment extends ListFragment {

    private IDWFApi mIdwfApi;

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    };

    public SharedListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIdwfApi = ApiFactory.INSTANCE.getIDWFApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared_list, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIdwfApi.getPublicDocuments(getActivity().getApplicationContext(), new PublicDocumentsCallback() {
            @Override
            public void onFail(PublicDocumentsException exception) {
                Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(PublicDocumentsResponse response) {
                if (response != null) {
                    ItemsAdapter itemsAdapter = new ItemsAdapter(getActivity().getApplicationContext(), R.layout.layout_doc_row, response.getItems());
                    LoginSuccessEvent loginSuccessEvent = EventBus.getDefault().getStickyEvent(LoginSuccessEvent.class);
                    if (loginSuccessEvent != null) {

                    }
                    getListView().setAdapter(itemsAdapter);
                    getListView().setOnItemClickListener(onItemClick);
                }
            }
        });
    }

    class ItemsAdapter extends ArrayAdapter<Item> {

        private int resource;

        public ItemsAdapter(Context context, int resource, List<Item> objects) {
            super(context, resource, objects);
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder viewHolder = null;
            if (view == null) {
                view = getActivity().getLayoutInflater().inflate(resource, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.tvTitle.setText(getItem(position).getTitle());
            viewHolder.tvDateCreated.setText(getItem(position).getCreated().toString());
            return view;
        }
    }

    class ViewHolder {

        @InjectView(R.id.textView_title)
        TextView tvTitle;

        @InjectView(R.id.textView_date)
        TextView tvDateCreated;

        public ViewHolder(final View view) {
            ButterKnife.inject(this, view);
        }
    }

}

