package post.it.project.social_networks;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kirill Antonov on 27.12.2016.
 */

public class Response implements Parcelable {
    public ResultType resultType;
    public  String message;
    public  int nameOfNetwork;

    public Response(ResultType resultType, String message, int nameOfNetwork) {
        this.resultType = resultType;
        this.message = message;
        this.nameOfNetwork = nameOfNetwork;
    }

    protected Response(Parcel in) {
        message = in.readString();
        nameOfNetwork = in.readInt();
        try {
            resultType = ResultType.valueOf(in.readString());
        } catch (IllegalArgumentException e) {
            resultType = null;
        }
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeInt(nameOfNetwork);
        dest.writeString((resultType == null) ? "" : resultType.name());
    }
}
