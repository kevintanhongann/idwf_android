package org.idwfed.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.progressfragment.ProgressFragment;

import org.idwfed.app.R;
import org.idwfed.app.api.ApiFactory;
import org.idwfed.app.api.IDWFApi;
import org.idwfed.app.callback.PublicDocumentsCallback;
import org.idwfed.app.domain.Item;
import org.idwfed.app.exception.PublicDocumentsException;
import org.idwfed.app.response.PublicDocumentsResponse;
import org.idwfed.app.util.PrefUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class SharedListFragment extends ProgressFragment {

    private IDWFApi mIdwfApi;

    @InjectView(R.id.listView_docs)
    ListView docsListView;

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Item item = (Item) parent.getAdapter().getItem(position);
            intent.setData(Uri.parse(item.getUrl()));
            startActivity(intent);
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

        String url = getString(R.string.server_url) + getString(R.string.publicdocs_path);

        if(!PrefUtils.getUsername(getActivity().getApplicationContext()).isEmpty()){
            url = url + "?creator="+PrefUtils.getUsername(getActivity().getApplicationContext());
        }

        mIdwfApi.getPublicDocuments(getActivity().getApplicationContext(),url,  new PublicDocumentsCallback() {
            @Override
            public void onFail(PublicDocumentsException exception) {
                if (getView() != null) {
                    Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                    setContentShown(true);
                }
            }

            @Override
            public void onSuccess(PublicDocumentsResponse response) {
                if (response != null && getView() != null) {
                    ItemsAdapter itemsAdapter = new ItemsAdapter(getActivity().getApplicationContext(), R.layout.layout_doc_row, response.getItems());
                    if (PrefUtils.getUserDocs(getActivity()) != null) {
                        itemsAdapter.addAll(PrefUtils.getUserDocs(getActivity()));
                    }
                    docsListView.setAdapter(itemsAdapter);
                    setContentShown(true);
                    docsListView.setOnItemClickListener(onItemClick);
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

