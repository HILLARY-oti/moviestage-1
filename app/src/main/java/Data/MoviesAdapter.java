package Data;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.hillary.moviestage1.MainActivity;
import com.hillary.moviestage1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private ArrayList<MoviesData> mMoviesArrayList;
    private Context mContext;
    private MovieAdapterOnClickHandler mClickHandler;

    public MoviesAdapter(MainActivity mainActivity) {
        mContext = mainActivity.getApplicationContext();
        mClickHandler = mainActivity;
    }
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView posterImageView;
        private MovieViewHolder(View view) {
            super(view);
            posterImageView = (ImageView) view.findViewById(R.id.movies_images);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MoviesData movies = mMoviesArrayList.get(adapterPosition);
            mClickHandler.onClickItem(movies);
        }
    }
    public interface MovieAdapterOnClickHandler {
        void onClickItem(MoviesData movie);
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.activity_grid_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForGridItem, parent, false);
        return new MovieViewHolder(view);
    }
    @Override
    public int getItemCount() {
        if (mMoviesArrayList == null) return 0;
        return mMoviesArrayList.size();
    }

    public void setMovieArrayList(ArrayList<MoviesData> moviesArrayList) {
        mMoviesArrayList = moviesArrayList;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MoviesData movie = mMoviesArrayList.get(position);
        String posterUrl = movie.getPosterURL();
        Picasso.with(mContext)
                .load(Uri.parse(posterUrl))
                .fit()
                .centerInside()
                .into(holder.posterImageView);
    }
}