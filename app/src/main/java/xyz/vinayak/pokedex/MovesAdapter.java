package xyz.vinayak.pokedex;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MovesAdapter extends RecyclerView.Adapter<MovesAdapter.ViewHolder> {
    Context ctx;
    ArrayList<String> movesArrayList;

    public MovesAdapter(Context ctx, ArrayList<String> movesArrayList) {
        this.ctx = ctx;
        this.movesArrayList = movesArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(ctx);
        View view = li.inflate(R.layout.moves_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(movesArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return movesArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvMoveName);
            Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/Montserrat.otf");
            tv.setTypeface(font);
        }
    }
}
