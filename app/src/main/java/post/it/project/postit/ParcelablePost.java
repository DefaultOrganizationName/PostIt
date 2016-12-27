package post.it.project.postit;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kirill Antonov on 27.12.2016.
 */

public class ParcelablePost extends Post implements Parcelable {
    public ParcelablePost(int[] networks, String post_text, Bitmap image_bitmap) {
        super(networks, post_text, image_bitmap);
    }

    public ParcelablePost(Post post) {
        super(post.networks, post.post_text, post.image_bitmap);
    }

    protected ParcelablePost(Parcel in) {
        super(in.createIntArray(),
                in.readString(),
                (Bitmap) in.readParcelable(Bitmap.class.getClassLoader()));
    }

    public static final Creator<ParcelablePost> CREATOR = new Creator<ParcelablePost>() {
        @Override
        public ParcelablePost createFromParcel(Parcel in) {
            return new ParcelablePost(in);
        }

        @Override
        public ParcelablePost[] newArray(int size) {
            return new ParcelablePost[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(networks);
        dest.writeString(post_text);
        dest.writeParcelable(image_bitmap, 0);
    }
}
