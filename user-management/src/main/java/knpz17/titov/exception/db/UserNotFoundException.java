package knpz17.titov.exception.db;

import static java.lang.String.format;

public class UserNotFoundException extends RuntimeException {

    private long id;

    public UserNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return format("User with Id = %d not found", id);
    }
}
