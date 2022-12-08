package com.example.law_ita;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class rateAdapter extends FirestoreRecyclerAdapter<rating, rateAdapter.ViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    RecyclerView recView;
    rateAdapter adapter;
    public rateAdapter(@NonNull FirestoreRecyclerOptions<rating> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull rating rating) {
        holder.txtName.setText(rating.getName() + " " + rating.getLastname());
        holder.txtComment.setText("Comentario: \n" + rating.getComment());
        holder.txtRate.setText("Calificación: " + rating.getRate());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_comentarios, viewGroup, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtLastname, txtComment, txtRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNameComment);
            txtComment = itemView.findViewById(R.id.txtComment);
            txtRate = itemView.findViewById(R.id.calif);

        }
    }
}

