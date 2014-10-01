package org.idwfed.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import org.idwfed.app.api.ApiFactory;
import org.idwfed.app.api.IDWFApi;
import org.idwfed.app.callback.CreateWccDocumentCallback;
import org.idwfed.app.domain.Item;
import org.idwfed.app.exception.CreateWccDocException;
import org.idwfed.app.response.CreateWccDocResponse;
import org.idwfed.app.util.Consts;
import org.idwfed.app.util.PrefUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class PublishDocumentActivity extends Activity {

    private static final int SELECT_PHOTO = 100;
    private static final int CAPTURE_PHOTO = 101;

    @InjectView(R.id.editText_description)
    EditText etDescription;

    @InjectView(R.id.editText_titleTxt)
    EditText etTitleTxt;

    @InjectView(R.id.editText_bodyTxt)
    EditText etBodyTxt;

    @InjectView(R.id.imageView)
    ImageView imageView;

    @InjectView(R.id.button_submit)
    Button submitBtn;

    List<String> countries = new ArrayList<String>();
    List<Integer> themesInt = new ArrayList<Integer>();
    List<Integer> countriesInt = new ArrayList<Integer>();
    private IDWFApi idwfApi;
    private String imageDataStr;
    private String[] relatedThemes;
    private String[] relatedThemesStr;
    private List<String> selectedCountries;
    private List<String> themes;
    private View.OnClickListener onSubmitClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (TextUtils.isEmpty(etTitleTxt.getText().toString())) {
                etTitleTxt.setError(getString(R.string.text_required_field));
            } else if (TextUtils.isEmpty(etBodyTxt.getText().toString())) {
                etBodyTxt.setError(getString(R.string.text_required_field));
            } else if (TextUtils.isEmpty(etDescription.getText().toString())) {
                etDescription.setError(getString(R.string.text_required_field));
            } else {
                final ProgressDialog progressDialog = ProgressDialog.show(PublishDocumentActivity.this, "", "Submitting...", false, false);

                String url;
                if (PrefUtils.getServerUrl(PublishDocumentActivity.this).isEmpty()) {
                    url = getString(R.string.server_url) + getString(R.string.createdoc_path);
                } else {
                    url = PrefUtils.getServerUrl(PublishDocumentActivity.this) + getString(R.string.createdoc_path);
                }

                idwfApi.createWccDoc(getApplicationContext(), etTitleTxt.getText().toString(), etDescription.getText().toString(), etBodyTxt.getText().toString(), countries, url, "", PrefUtils.getUsername(PublishDocumentActivity.this), PrefUtils.getPassword(PublishDocumentActivity.this), imageDataStr, "image/*", "", "", "", themes, new CreateWccDocumentCallback() {
                    @Override
                    public void onFail(CreateWccDocException ex) {
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onSuccess(CreateWccDocResponse response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.getItems() != null && response.getError() == null) {
                            AlertDialog dialog = new AlertDialog.Builder(PublishDocumentActivity.this)
                                    .setTitle("Status")
                                    .setMessage("Document Created!")
                                    .setPositiveButton("Back to Main", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            NavUtils.navigateUpTo(PublishDocumentActivity.this, new Intent(PublishDocumentActivity.this, SharedListActivity.class));
                                        }
                                    }).create();
                            dialog.show();
                        } else {
                            Toast.makeText(PublishDocumentActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    };
    private List<String> displayCountries;

    @OnClick(R.id.button_relatedThemes)
    void onRelatedThemesClick() {
        boolean[] booleanAry = new boolean[relatedThemesStr.length];

        //set values to true
        for (Integer integer : themesInt) {
            booleanAry[integer] = true;
        }
        AlertDialog multiSelectDialog = new AlertDialog.Builder(PublishDocumentActivity.this)
                .setTitle("Choose Related Themes")
                .setMultiChoiceItems(relatedThemesStr, booleanAry, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            themesInt.add(which);
                        }else if(themesInt.contains(which)){
                            themesInt.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        themes = new ArrayList<String>();
                        for (Integer integer : themesInt) {
                            themes.add(relatedThemes[integer]);
                        }
                    }
                })
                .create();
        multiSelectDialog.show();
    }

    @OnClick(R.id.button_countries)
    void onCountriesClick() {
        boolean[] booleanAry = new boolean[Locale.getISOCountries().length];

        for (Integer integer : countriesInt) {
            booleanAry[integer] = true;
        }

        CharSequence[] cs = displayCountries.toArray(new CharSequence[displayCountries.size()]);
        AlertDialog countriesDialog = new AlertDialog.Builder(PublishDocumentActivity.this)
                .setTitle("Select Countries")
                .setMultiChoiceItems(cs, booleanAry, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // If user select a item then add it in selected items
                            countriesInt.add(which);
                        } else if (countriesInt.contains(which)) {
                            // if the item is already selected then remove it
                            countriesInt.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedCountries = new ArrayList<String>();
                        for (Integer integer : countriesInt) {
                            selectedCountries.add(displayCountries.get(integer));
                        }
                    }
                })
                .create();
        countriesDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to);
        ButterKnife.inject(this);
        idwfApi = ApiFactory.INSTANCE.getIDWFApi();
        relatedThemes = getResources().getStringArray(R.array.related_themes);
        relatedThemesStr = getResources().getStringArray(R.array.related_themes_string);

         displayCountries = new ArrayList<String>();
        for (String countryCode : Locale.getISOCountries()) {
            Locale obj = new Locale("", countryCode);
            displayCountries.add(obj.getDisplayCountry());
        }

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    etTitleTxt.setText(sharedText);
                }
            }
        }

        submitBtn.setOnClickListener(onSubmitClick);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_send_now:
                //doShare();
                return true;
            case R.id.action_pick_gallery:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                return true;
            case R.id.action_camera:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, CAPTURE_PHOTO);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        imageDataStr = Base64.encodeToString(b, Base64.DEFAULT);
                        try {
                            imageView.setImageBitmap(decodeUri(selectedImage));
                        } catch (FileNotFoundException e) {
                            Log.e(Consts.LOGTAG, e.getMessage(), e);
                        }
                    } catch (IOException e) {
                        Log.e(Consts.LOGTAG, "IOException", e);
                    }

                }
                return;
            case CAPTURE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] fullBytes = byteArrayOutputStream.toByteArray();
                    imageDataStr = Base64.encodeToString(fullBytes, Base64.DEFAULT);
                    imageView.setImageBitmap(imageBitmap);
                }
                return;
        }
    }
}
