package org.js.playground;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExternalApiAggregatorTest {

    private ExternalApiAggregator aggregator;

    // Test stub for ExternalApi - works with all Java versions
    private static class TestExternalApi implements ExternalApi {
        private ExternalApiAggregator.ApiResponse1 api1Response;
        private ExternalApiAggregator.ApiResponse2 api2Response;
        private ExternalApiAggregator.ApiResponse3 api3Response;
        private RuntimeException api1Exception;
        private RuntimeException api2Exception;
        private RuntimeException api3Exception;

        void setApi1Response(ExternalApiAggregator.ApiResponse1 response) {
            this.api1Response = response;
        }

        void setApi2Response(ExternalApiAggregator.ApiResponse2 response) {
            this.api2Response = response;
        }

        void setApi3Response(ExternalApiAggregator.ApiResponse3 response) {
            this.api3Response = response;
        }

        void setApi1Exception(RuntimeException exception) {
            this.api1Exception = exception;
        }

        void setApi2Exception(RuntimeException exception) {
            this.api2Exception = exception;
        }

        void setApi3Exception(RuntimeException exception) {
            this.api3Exception = exception;
        }

        @Override
        public ExternalApiAggregator.ApiResponse1 callApi1() {
            if (api1Exception != null) throw api1Exception;
            return api1Response;
        }

        @Override
        public ExternalApiAggregator.ApiResponse2 callApi2() {
            if (api2Exception != null) throw api2Exception;
            return api2Response;
        }

        @Override
        public ExternalApiAggregator.ApiResponse3 callApi3() {
            if (api3Exception != null) throw api3Exception;
            return api3Response;
        }
    }

    private TestExternalApi testApi;

    @BeforeEach
    void setUp() {
        testApi = new TestExternalApi();
        aggregator = new ExternalApiAggregator(testApi);
    }

    @AfterEach
    void tearDown() {
        aggregator.shutdown();
    }

    @Test
    void testSuccessfulDataAggregation() {
        // ARRANGE: Configure the test stub to return successful data
        testApi.setApi1Response(new ExternalApiAggregator.ApiResponse1("data1"));
        testApi.setApi2Response(new ExternalApiAggregator.ApiResponse2("data2"));
        testApi.setApi3Response(new ExternalApiAggregator.ApiResponse3("data3"));

        // ACT
        ExternalApiAggregator.CombinedResponse response = aggregator.fetchData();

        // ASSERT
        assertEquals("data1", response.r1.data);
        assertEquals("data2", response.r2.data);
        assertEquals("data3", response.r3.data);
    }

    @Test
    void testPartialFailureWithFallback() {
        // 1. Simulate failure for API 1 (e.g., a TimeoutException)
        testApi.setApi1Exception(new RuntimeException("API 1 Timeout"));

        // 2. Simulate success for API 2
        testApi.setApi2Response(new ExternalApiAggregator.ApiResponse2("real_data_2"));

        // 3. Simulate failure for API 3
        testApi.setApi3Exception(new IllegalStateException("API 3 500 Error"));

        // ACT
        ExternalApiAggregator.CombinedResponse response = aggregator.fetchData();

        // ASSERT: Verify that fallbacks were used for failed APIs

        // API 1 failed -> Fallback used
        assertEquals("fallback1", response.r1.data, "API 1 should use its fallback value.");

        // API 2 succeeded -> Real data used
        assertEquals("real_data_2", response.r2.data, "API 2 should return real data.");

        // API 3 failed -> Fallback used
        assertEquals("fallback3", response.r3.data, "API 3 should use its fallback value.");
    }
}