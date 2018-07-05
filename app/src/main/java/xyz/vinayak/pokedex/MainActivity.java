package xyz.vinayak.pokedex;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<Pokemon> pokemonArrayList;
    RecyclerView recyclerView;
    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer = MediaPlayer.create(this, R.raw.pokedex_sound);
        mPlayer.start();

        recyclerView = findViewById(R.id.recycler_view_id);

        RoundCornerProgressBar progress1 = findViewById(R.id.progress_1);
        progress1.setProgress(25);


        pokemonArrayList = new ArrayList<>();

    }

    public void fetchPokemonData(String s) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(s).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                final APIResponse apiResponse = gson.fromJson(result,APIResponse.class);

                (MainActivity.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                        recyclerView.setAdapter(new PokemonAdapter(getBaseContext(),pokemonArrayList));
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//                        recyclerView.setAdapter(new UserNameAdapter(getBaseContext(),apiResponse.getItems()));
                    }
                });

            }
        });
    }

    String removeBackslash(String url){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i < url.length(); i++){
            if(url.charAt(i) != '\\'){
                stringBuilder.append(url.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onDestroy() {
        mPlayer.stop();
        super.onDestroy();
    }
}
