package Data;

import android.os.Parcel;
import android.os.Parcelable;

public class MoviesData implements Parcelable {
    private final String mTitle;
    private final String mPosterUrl;
    private final String mSynopsis;
    private final String mVoteAverage;
    private final String mReleaseDate;

    public MoviesData(String title, String releaseDate, String posterURL, String synopsis, String voteAverage) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterUrl = posterURL;
        mSynopsis = synopsis;
        mVoteAverage = voteAverage;
    }

    private MoviesData(Parcel parcel) {
        mTitle = parcel.readString();
        mReleaseDate = parcel.readString();
        mPosterUrl = parcel.readString();
        mSynopsis = parcel.readString();
        mVoteAverage = parcel.readString();
    }

    public String getPosterURL() {
        return mPosterUrl;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mPosterUrl);
        parcel.writeString(mSynopsis);
        parcel.writeString(mVoteAverage);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Parcelable.Creator<MoviesData> CREATOR = new Parcelable.Creator<MoviesData>() {
        @Override
        public MoviesData createFromParcel(Parcel parcel) {
            return new MoviesData(parcel);
        }

        @Override
        public MoviesData[] newArray(int i) {
            return new MoviesData[i];
        }
    };
}
