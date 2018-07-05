package xyz.vinayak.pokedex;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    ImageView ivPokemonImage;
    TextView tvPokemonName;
    Typeface myfont;
    Button btnNext, btnPrevious;
    int currentPokemonRank = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer = MediaPlayer.create(this, R.raw.pokedex_sound);
        mPlayer.start();

        final MediaPlayer mpBtnClick = MediaPlayer.create(this, R.raw.button_click);


        ivPokemonImage = findViewById(R.id.ivPokemonImage);
        tvPokemonName = findViewById(R.id.tvPokemonName);
        btnNext = findViewById(R.id.buttonNext);
        btnPrevious = findViewById(R.id.buttonPrevious);
        myfont = Typeface.createFromAsset(this.getAssets(),"fonts/pokemon_gb.ttf");
        tvPokemonName.setTypeface(myfont);

        Glide.with(getBaseContext()).load(R.drawable.pokeball_moving)
                .crossFade()
                .into(ivPokemonImage);

        fetchPokemonData("https://pokeapi.co/api/v2/pokemon/"+String.valueOf(currentPokemonRank));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpBtnClick.start();
                tvPokemonName.setText("catching Pokemon...");

                Glide.with(getBaseContext()).load(R.drawable.pokeball_moving)
                        .crossFade()
                        .into(ivPokemonImage);
                currentPokemonRank++;
                fetchPokemonData("https://pokeapi.co/api/v2/pokemon/"+String.valueOf(currentPokemonRank));
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpBtnClick.start();
                if(currentPokemonRank > 1){
                    tvPokemonName.setText("catching Pokemon...");

                    Glide.with(getBaseContext()).load(R.drawable.pokeball_moving)
                            .crossFade()
                            .into(ivPokemonImage);
                    currentPokemonRank--;
                    fetchPokemonData("https://pokeapi.co/api/v2/pokemon/"+String.valueOf(currentPokemonRank));
                }
            }
        });

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

                        String name = apiResponse.getName();
                        int rank = apiResponse.getId();
                        String imgUrl = removeBackslash(apiResponse.getSprites().getFront_default());

                        tvPokemonName.setText("#"+rank+" "+ name);

                        Glide.with(getBaseContext()).load(imgUrl)
                        .fitCenter()
                        .thumbnail(Glide.with(getBaseContext()).load(R.drawable.pokeball_moving))
                        .crossFade()
                        .into(ivPokemonImage);
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
