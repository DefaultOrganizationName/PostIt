package post.it.project.exceptions;

/**
 * Created by Михаил on 14.12.2016.
 */

public class PostItDatabaseException extends Exception {
    public PostItDatabaseException() {};

    public PostItDatabaseException(String message) {
        super(message);
    }

    public PostItDatabaseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    private static final String TAG = "PostItDatabaseException";
}
