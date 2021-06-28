package Chandra.TI18D5.JSON.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import Chandra.TI18D5.JSON.R;
import Chandra.TI18D5.JSON.adapter.AdapterBerita;
import Chandra.TI18D5.JSON.model.topheadline.ArticlesItem;
import Chandra.TI18D5.JSON.model.topheadline.ResponseTopHeadlines;
import Chandra.TI18D5.JSON.network.ApiServices;
import Chandra.TI18D5.JSON.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rvListBerita;

    String API_KEY = "a8dc3c4cb3314165970e685bfd9fab70";
    String COUNTRY = "id";
    String CATEGORY = "health";
    String QUERY = "";

    androidx.appcompat.widget.Toolbar toolbar;
    TextView toolbarTittle;

    TextView pesanTidakada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.custome_toolbar);
        toolbarTittle = (TextView)findViewById(R.id.toolbar_custome_title);
        pesanTidakada = (TextView)findViewById(R.id.txtDataTidakAda);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_news);

        toolbarTittle.setText("Berita Terkini");
        toolbarTittle.setTextColor(Color.WHITE);

        rvListBerita = (RecyclerView)findViewById(R.id.rvListBerita);
        rvListBerita.setHasFixedSize(true);
        rvListBerita.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        fetchingBerita(API_KEY, COUNTRY, QUERY);

    }

    private void fetchingBerita(String apiKey, String country, String query) {
        ApiServices apiServices = RetrofitClient.getInstance();
        Call<ResponseTopHeadlines> topHeadline = apiServices.getListNews(apiKey, country, query);

        topHeadline.enqueue(new Callback<ResponseTopHeadlines>() {
            @Override
            public void onResponse(Call<ResponseTopHeadlines> call, Response<ResponseTopHeadlines> response) {
                if (response.isSuccessful()) {
                    int totalResult = response.body().getTotalResults();

                    if (totalResult != 0) {
                        pesanTidakada.setVisibility(View.GONE);
                        rvListBerita.setVisibility(View.VISIBLE);
                        List<ArticlesItem> listArticle = response.body().getArticles();
                        AdapterBerita adapterBerita = new AdapterBerita(HomeActivity.this, listArticle);
                        rvListBerita.setAdapter(adapterBerita);
                    } else {
                        pesanTidakada.setVisibility(View.VISIBLE);
                        rvListBerita.setVisibility(View.GONE);
                        pesanTidakada.setText("Data tidak ditemukan, cari keyword yang sesuai");
                      }
                }
            }

            @Override
            public void onFailure(Call<ResponseTopHeadlines> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnSearch) {
            // Toast.makeText(this, "Cari Berita", Toast.LENGTH_SHORT).show();
            showDialogSearch();
        } else if (item.getItemId() == android.R.id.home) {
            fetchingBerita(API_KEY, COUNTRY, "");
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogSearch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cari Berita");

        final View customLayout = getLayoutInflater().inflate(R.layout.custome_search, null);
        builder.setView(customLayout);

            builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = customLayout.findViewById(R.id.editText);
                            QUERY = editText.getText().toString();
                            fetchingBerita(API_KEY, COUNTRY, QUERY);
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}