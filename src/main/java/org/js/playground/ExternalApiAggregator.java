package org.js.playground;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.supplyAsync;

class ExternalApiAggregator {
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private final ExternalApi api;

    ExternalApiAggregator(ExternalApi api) {
        this.api = api;
    }

    public static void main(String[] args) {
        System.out.println("Starting External API Aggregation...");

        ExternalApi realApi = new RealExternalApi();
        ExternalApiAggregator aggregator = new ExternalApiAggregator(realApi);

        try {
            CombinedResponse result = aggregator.fetchData();

            System.out.println("\n--- Aggregation Complete ---");
            System.out.println("API 1 Data: " + result.r1.data);
            System.out.println("API 2 Data: " + result.r2.data);
            System.out.println("API 3 Data: " + result.r3.data);
            System.out.println("----------------------------");
        } finally {
            System.out.println("Shutting down ExecutorService...");
            aggregator.shutdown();
            System.out.println("Shutdown complete.");
        }
    }

    CombinedResponse fetchData() {
        CompletableFuture<ApiResponse1> api1Future = supplyAsync(api::callApi1, executor).exceptionally(_ -> new ApiResponse1("fallback1"));
        CompletableFuture<ApiResponse2> api2Future = supplyAsync(api::callApi2, executor).exceptionally(_ -> new ApiResponse2("fallback2"));
        CompletableFuture<ApiResponse3> api3Future = supplyAsync(api::callApi3, executor).exceptionally(_ -> new ApiResponse3("fallback3"));

        // Wait for all to complete
        CompletableFuture<Void> allDone = allOf(api1Future, api2Future, api3Future);

        // Aggregate results once all are done
        return allDone.thenApply(v -> {
            ApiResponse1 r1 = api1Future.join();
            ApiResponse2 r2 = api2Future.join();
            ApiResponse3 r3 = api3Future.join();
            return new CombinedResponse(r1, r2, r3);
        }).join(); // block to get final result
    }

    // --- Resource Management ---
    void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdown();
            }

            if(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("ExecutorService did not terminate");
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Simulated API calls
    private ApiResponse1 callApi1() {
        return new ApiResponse1("data1");
    }

    private ApiResponse2 callApi2() {
        return new ApiResponse2("data2");
    }

    private ApiResponse3 callApi3() {
        return new ApiResponse3("data3");
    }

    // Combined response wrapper
    static class CombinedResponse {
        ApiResponse1 r1;
        ApiResponse2 r2;
        ApiResponse3 r3;

        public CombinedResponse(ApiResponse1 r1, ApiResponse2 r2, ApiResponse3 r3) {
            this.r1 = r1;
            this.r2 = r2;
            this.r3 = r3;
        }
    }

    static class ApiResponse1 {
        String data;
        ApiResponse1(String data) {
            this.data = data;
        }
    }

    static class ApiResponse2 {
        String data;
        ApiResponse2(String data) {
            this.data = data;
        }
    }

    static class ApiResponse3 {
        String data;
        ApiResponse3(String data) {
            this.data = data;
        }
    }
}

class RealExternalApi implements ExternalApi {
    @Override
    public ExternalApiAggregator.ApiResponse1 callApi1() {
        return new ExternalApiAggregator.ApiResponse1("data1");
    }

    @Override
    public ExternalApiAggregator.ApiResponse2 callApi2() {
        return new ExternalApiAggregator.ApiResponse2("data2");
    }

    @Override
    public ExternalApiAggregator.ApiResponse3 callApi3() {
        return new ExternalApiAggregator.ApiResponse3("data3");
    }
}