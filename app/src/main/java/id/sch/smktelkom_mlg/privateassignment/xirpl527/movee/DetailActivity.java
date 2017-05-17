package id.sch.smktelkom_mlg.privateassignment.xirpl527.movee;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private static final String URL_DATA = "https://api.themoviedb.org/3/movie/top_rated?api_key=2cbf9519af8b992118229bfbb1fb2dd3";
    public TextView tvJudul;
    public TextView tvJudulAsli;
    public TextView tvTahun;
    public TextView tvRating;
    public TextView overview;
    public ImageView imageViewDetail;
    public ImageView bg;
    public String url;
    public String urlgambar;
    SavedItemList savedItemList;
    boolean isPressed = true;
    ArrayList<SavedItemList> fItem;
    ArrayList<SavedItemList> fList;
    SavedAdapter mAdapter;
    private Integer mPostkey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mPostkey = getIntent().getExtras().getInt("blog_id");
        loadRecyclerViewData();

        tvJudul = (TextView) findViewById(R.id.tvJudul);
        tvJudulAsli = (TextView) findViewById(R.id.tvJudulAsli);
        tvTahun = (TextView) findViewById(R.id.tahunRilis);
        tvRating = (TextView) findViewById(R.id.voteAv);
        overview = (TextView) findViewById(R.id.overview);
        imageViewDetail = (ImageView) findViewById(R.id.imageBack);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSimpan();
                Snackbar.make(view, "Added to wishlist", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void doSimpan() {
        String judul = tvJudul.getText().toString();
        String judulasli = tvJudulAsli.getText().toString();
        String tahun = tvTahun.getText().toString();
        String rating = tvRating.getText().toString();
        String overviewview = overview.getText().toString();
        String images = url;

        savedItemList = new SavedItemList(judul, judulasli, tahun, rating, overviewview, images);
        savedItemList.save();
        SharedPreferences.Editor editor = getSharedPreferences(judul, MODE_PRIVATE).edit();

        editor.putBoolean("isNew", true);

        editor.commit();

    }


    private void loadRecyclerViewData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data..");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("results");
                            JSONObject o = array.getJSONObject(mPostkey);

                            String urll = "http://image.tmdb.org/t/p/w500";

                            setTitle(" ");

                            tvJudul.setText(o.getString("title"));
                            tvJudulAsli.setText(o.getString("original_title"));
                            tvRating.setText(o.getString("vote_average"));
                            tvTahun.setText(o.getString("release_date"));
                            overview.setText(o.getString("overview"));
                            url = o.getString("backdrop_path");

                            Glide
                                    .with(DetailActivity.this)
                                    .load("http://image.tmdb.org/t/p/w500" + (o.getString("backdrop_path")))
                                    .into(imageViewDetail);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
