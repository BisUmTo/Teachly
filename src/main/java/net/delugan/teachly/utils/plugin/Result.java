package net.delugan.teachly.utils.plugin;

public class Result<T> {
    private final boolean success;
    private final T result;
    private final String error;

    private Result(T result, String error) {
        this.success = error == null;
        this.result = result;
        this.error = error;
    }

    public static <T> Result<T> success(T result) {
        return new Result<>(result, null);
    }

    public static <T> Result<T> success() {
        return new Result<>(null, null);
    }

    public static <T> Result<T> failure(String error) {
        return new Result<>(null, error);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return error != null;
    }

    public T getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
}
