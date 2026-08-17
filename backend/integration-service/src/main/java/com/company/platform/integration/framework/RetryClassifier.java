package com.company.platform.integration.framework;

public final class RetryClassifier {
    private RetryClassifier() {}
    public static boolean transientFailure(int status) { return status == 408 || status == 429 || status == 502 || status == 503 || status == 504; }
    public static boolean retryable(Throwable failure) { return failure instanceof java.net.SocketTimeoutException || failure instanceof java.net.ConnectException || failure instanceof java.io.IOException; }
    public static long backoffMillis(int attempt) { return Math.min(30_000L, 500L * (1L << Math.min(Math.max(attempt - 1, 0), 6))); }
}
