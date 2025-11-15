package org.js.playground;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ExternalApiAggregatorTest {

    private ExternalApiAggregator aggregator;
    // Mocked dependency
    private ExternalApi mockApi;

    @BeforeEach
    void setUp() {
        mockApi = Mockito.mock(ExternalApi.class);
        aggregator = new ExternalApiAggregator(mockApi);
    }

    @AfterEach
    void tearDown() {
        aggregator.shutdown();
    }

    @Test
    void testSuccessfulDataAggregation() {
        // ARRANGE: Configure the mock to return successful data
        when(mockApi.callApi1()).thenReturn(new ExternalApiAggregator.ApiResponse1("data1"));
        when(mockApi.callApi2()).thenReturn(new ExternalApiAggregator.ApiResponse2("data2"));
        when(mockApi.callApi3()).thenReturn(new ExternalApiAggregator.ApiResponse3("data3"));

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
        when(mockApi.callApi1()).thenThrow(new RuntimeException("API 1 Timeout"));

        // 2. Simulate success for API 2
        when(mockApi.callApi2()).thenReturn(new ExternalApiAggregator.ApiResponse2("real_data_2"));

        // 3. Simulate failure for API 3
        when(mockApi.callApi3()).thenThrow(new IllegalStateException("API 3 500 Error"));

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