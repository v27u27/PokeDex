package xyz.vinayak.pokedex;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PokemonDetailActivity extends AppCompatActivity {

    public Typeface font, fontBold;

    TextView tvPokemonNameRank, tvTypesLabel, tvTypeValue1, tvTypeValue2, tvPokemonWeightLabel, tvPokemonWeight, tvPokemonHeightLabel, tvPokemonHeight, tvAttackLabel, tvAttackValue;
    TextView tvDefenseLabel, tvDefenseValue, tvSpeedLabel, tvSpeedValue, tvHPLabel, tvHPValue, tvXPLabel, tvXPValue, tvMovesLabel, tvPokemonStatsLabel;
    ImageView ivPokemonImage1;
    RecyclerView recyclerView;
    RoundCornerProgressBar progressAttack, progressDefense, progressSpeed, progressHP, progressXP;

    int currentPokemonRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        ivPokemonImage1 = findViewById(R.id.ivPokemonImage1);

        recyclerView = findViewById(R.id.recyclerView);

        progressAttack = findViewById(R.id.progressAttack);
        progressDefense = findViewById(R.id.progressDefense);
        progressSpeed = findViewById(R.id.progressSpeed);
        progressHP = findViewById(R.id.progressHP);
        progressXP = findViewById(R.id.progressXP);

        tvPokemonNameRank = findViewById(R.id.tvPokemonNameRank);
        tvTypesLabel = findViewById(R.id.tvTypesLabel);
        tvTypeValue1 = findViewById(R.id.tvTypeValue1);
        tvTypeValue2 = findViewById(R.id.tvTypeValue2);
        tvPokemonWeightLabel = findViewById(R.id.tvPokemonWeightLabel);
        tvPokemonWeight = findViewById(R.id.tvPokemonWeight);
        tvPokemonHeightLabel = findViewById(R.id.tvPokemonHeightLabel);
        tvPokemonHeight = findViewById(R.id.tvPokemonHeight);
        tvAttackLabel = findViewById(R.id.tvAttackLabel);
        tvAttackValue = findViewById(R.id.tvAttackValue);
        tvDefenseLabel = findViewById(R.id.tvDefenseLabel);
        tvDefenseValue = findViewById(R.id.tvDefenseValue);
        tvSpeedLabel = findViewById(R.id.tvSpeedLabel);
        tvSpeedValue = findViewById(R.id.tvSpeedValue);
        tvHPLabel = findViewById(R.id.tvHPLabel);
        tvHPValue = findViewById(R.id.tvHPValue);
        tvXPLabel = findViewById(R.id.tvXPLabel);
        tvXPValue = findViewById(R.id.tvXPValue);
        tvMovesLabel = findViewById(R.id.tvMovesLabel);
        tvPokemonStatsLabel = findViewById(R.id.tvPokemonStatsLabel);


        font = Typeface.createFromAsset(this.getAssets(), "fonts/Montserrat.otf");
        fontBold = Typeface.createFromAsset(this.getAssets(), "fonts/Montserrat-SemiBold.otf");

        tvPokemonNameRank.setTypeface(fontBold);
        tvPokemonHeightLabel.setTypeface(fontBold);
        tvPokemonWeightLabel.setTypeface(fontBold);
        tvMovesLabel.setTypeface(fontBold);
        tvTypesLabel.setTypeface(fontBold);
        tvPokemonStatsLabel.setTypeface(fontBold);

        tvTypeValue1.setTypeface(font);
        tvTypeValue2.setTypeface(font);
        tvPokemonWeight.setTypeface(font);
        tvPokemonHeight.setTypeface(font);
        tvAttackLabel.setTypeface(font);
        tvAttackValue.setTypeface(font);
        tvDefenseLabel.setTypeface(font);
        tvDefenseValue.setTypeface(font);
        tvSpeedLabel.setTypeface(font);
        tvSpeedValue.setTypeface(font);
        tvHPLabel.setTypeface(font);
        tvHPValue.setTypeface(font);
        tvXPLabel.setTypeface(font);
        tvXPValue.setTypeface(font);


        Intent i = getIntent();
        currentPokemonRank = Integer.parseInt(i.getStringExtra("currentPokemonRank"));
        fetchPokemonData("https://pokeapi.co/api/v2/pokemon/" + String.valueOf(currentPokemonRank));


    }

    public void fetchPokemonData(String s) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(s).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                final APIResponse apiResponse = gson.fromJson(result, APIResponse.class);

                (PokemonDetailActivity.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String name = apiResponse.getName();
                        int rank = apiResponse.getId();
                        String imgUrl = removeBackslash(apiResponse.getSprites().getFront_default());

                        tvPokemonNameRank.setText("#" + rank + " " + name);

                        Glide.with(getBaseContext()).load(imgUrl)
                                .fitCenter()
                                .thumbnail(Glide.with(getBaseContext()).load(R.drawable.pokeball_moving))
                                .crossFade()
                                .into(ivPokemonImage1);

                        ArrayList<APIResponse.Stats> stats = apiResponse.getStats();
                        for (int i = 0; i < stats.size(); i++) {
                            APIResponse.Stats.Stat currenstat = stats.get(i).getStat();
                            String nameee = currenstat.getName();
                            switch (nameee) {
                                case "attack":
                                    progressAttack.setProgress(stats.get(i).getBase_stat());
                                    tvAttackValue.setText(String.valueOf(stats.get(i).getBase_stat()));
                                    break;

                                case "defense":
                                    progressDefense.setProgress(stats.get(i).getBase_stat());
                                    tvDefenseValue.setText(String.valueOf(stats.get(i).getBase_stat()));
                                    break;

                                case "speed":
                                    progressSpeed.setProgress(stats.get(i).getBase_stat());
                                    tvSpeedValue.setText(String.valueOf(stats.get(i).getBase_stat()));
                                    break;

                                case "hp":
                                    progressHP.setProgress(stats.get(i).getBase_stat());
                                    tvHPValue.setText(String.valueOf(stats.get(i).getBase_stat()));
                                    break;
                                default:
                                    break;
                            }

                        }

                        progressXP.setProgress(apiResponse.getBase_experience());
                        tvXPValue.setText(String.valueOf(apiResponse.getBase_experience()));

                        ArrayList<APIResponse.Types> types = apiResponse.getTypes();
                        for (int i = 0; i < types.size(); i++) {
                            APIResponse.Types currentType = types.get(i);
                            APIResponse.Types.Type typee = currentType.getType();
                            switch (currentType.getSlot()) {
                                case 1:
                                    tvTypeValue1.setText(typee.getName());
                                    break;
                                case 2:
                                    tvTypeValue2.setText(typee.getName());
                                    break;
                                default:
                                    break;
                            }
                        }

                        tvPokemonWeight.setText(String.valueOf(apiResponse.getWeight()));
                        tvPokemonHeight.setText(String.valueOf(apiResponse.getHeight()));

                        ArrayList<APIResponse.Moves> moves = apiResponse.getMoves();
                        ArrayList<String> moveArrayList = new ArrayList<>();
                        for (int i = 0; i < moves.size(); i++) {
                            moveArrayList.add(moves.get(i).getMove().getName());
                        }

                        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
                        recyclerView.setAdapter(new MovesAdapter(getBaseContext(), moveArrayList));

                    }
                });
            }
        });
    }

    String removeBackslash(String url) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) != '\\') {
                stringBuilder.append(url.charAt(i));
            }
        }
        return stringBuilder.toString();
    }
}
