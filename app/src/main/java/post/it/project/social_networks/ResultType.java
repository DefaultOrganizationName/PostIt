package post.it.project.social_networks;

/**
 * Created by Kirill Antonov on 27.12.2016.
 */

public enum ResultType {
    /**
     * Данные успешно загружены.
     */
    OK,

    /**
     * Данные не загружены из-за отсутствия интернета.
     */
    NO_INTERNET,

    /**
     * Данные не загружены по другой причине.
     */
    ERROR
}
