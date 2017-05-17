package id.sch.smktelkom_mlg.privateassignment.xirpl527.movee;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {

    private final Context context;
    ArrayList<SavedItemList> wItem = new ArrayList<>();

    public SavedAdapter(ArrayList<SavedItemList> wishItem, Context context) {
        this.wItem = wishItem;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_item_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SavedItemList wishItem = wItem.get(position);
        holder.tvJudul.setText(wishItem.judul);
        holder.tvOverview.setText(wishItem.overview);

        Toast.makeText(context, "Press 'X' button to delete from wishlist ", Toast.LENGTH_LONG).show();

        Glide
                .with(context)
                .load("http://image.tmdb.org/t/p/w500" + wishItem.images)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgUrl);

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SavedItemList savedItemList1 = wItem.get(position);
                wItem.remove(position);
                savedItemList1.delete();
                SavedAdapter.this.notifyDataSetChanged();

                Snackbar.make(view, "DELETED", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return wItem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudul;
        public TextView tvJudulAsli;
        public TextView tvTahun;
        public TextView tvRating;
        public TextView tvOverview;
        public ImageView imgUrl;
        public Button buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            tvJudul = (TextView) itemView.findViewById(R.id.textViewJudul2);
            tvOverview = (TextView) itemView.findViewById(R.id.textViewDeskripsi2);
            imgUrl = (ImageView) itemView.findViewById(R.id.imageView2);
            buttonDelete = (Button) itemView.findViewById(R.id.btDelete);
        }
    }
}