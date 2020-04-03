package Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Data.MoviesData;

public class MoviesJson {
    public static ArrayList<MoviesData> getMovieArrayListFromJson(String movieJson) throws Exception {
        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

        JSONObject movieJsonObject = new JSONObject(movieJson);

        JSONArray resultsJsonArray = movieJsonObject.getJSONArray("results");

        ArrayList<MoviesData> moviesArrayList = new ArrayList<>(resultsJsonArray.length());

        for (int pos = 0; pos < resultsJsonArray.length(); pos++) {

            JSONObject jsonObject = resultsJsonArray.getJSONObject(pos);

            String title = jsonObject.getString("title");

            String synopsis = jsonObject.getString("overview");

            String vote = jsonObject.getString("vote_average");

            String posterURL = IMAGE_BASE_URL + jsonObject.getString("poster_path");

            String releaseDate = jsonObject.getString("release_date");

            MoviesData moviesData = new MoviesData(title, releaseDate, posterURL, synopsis, vote);
            moviesArrayList.add(pos, moviesData);


        }
        return moviesArrayList;
    }
}
