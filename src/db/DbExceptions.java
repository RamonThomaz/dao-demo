package db;

public class DbExceptions extends RuntimeException {
    private static final long serialVersiorUID = 1L;

    public DbExceptions(String message) {
        super(message);
    }
}
