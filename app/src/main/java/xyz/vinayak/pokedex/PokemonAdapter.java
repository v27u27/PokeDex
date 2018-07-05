package xyz.vinayak.pokedex;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    Context ctx;
    ArrayList<Pokemon> pokemonArrayList;

    public PokemonAdapter(Context ctx, ArrayList<Pokemon> pokemonArrayList) {
        this.ctx = ctx;
        this.pokemonArrayList = pokemonArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(ctx);
        View view = li.inflate(R.layout.cardview_item_pokemon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon currentPokemon = pokemonArrayList.get(position);
        holder.tvPokemonName.setText(currentPokemon.getName());

        Glide.with(ctx).load(currentPokemon.getImageUrl())
                .centerCrop()
                .thumbnail(Glide.with(ctx).load(R.drawable.pokeball_moving).centerCrop())
                .fitCenter()
                .crossFade()
                .into(holder.ivPokemonImage);

//        Picasso.get().load(currentPokemon.getImageUrl()).fit().placeholder(R.drawable.pokeball_moving).fit().into(holder.ivPokemonImage);

    }

    @Override
    public int getItemCount() {
        return pokemonArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPokemonImage;
        TextView tvPokemonName;
        Typeface myfont;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPokemonImage = itemView.findViewById(R.id.pokemon_image_id);
            tvPokemonName = itemView.findViewById(R.id.pokemon_name_id);
            myfont = Typeface.createFromAsset(ctx.getAssets(),"fonts/Montserrat.otf");
            tvPokemonName.setTypeface(myfont);
        }
    }
}
