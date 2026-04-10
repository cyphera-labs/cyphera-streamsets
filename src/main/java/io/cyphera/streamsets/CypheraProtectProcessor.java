package io.cyphera.streamsets;

import io.cyphera.Cyphera;

import java.util.Map;

/**
 * StreamSets Data Collector Processor: Cyphera Protect
 *
 * Protects a specified field using format-preserving encryption.
 * Deploy as a stage library JAR in $SDC_HOME/streamsets-libs/.
 *
 * Note: StreamSets SDK requires additional build configuration.
 * This implementation uses the core Cyphera SDK and can be adapted
 * to the StreamSets Processor API (BaseProcessor) when the SDK is available.
 *
 * For now, this serves as a reference implementation showing how to
 * integrate Cyphera into StreamSets pipelines.
 */
public class CypheraProtectProcessor {

    private final Cyphera client;
    private final String policyName;
    private final String fieldPath;

    public CypheraProtectProcessor(String policyName, String fieldPath) {
        this.client = CypheraLoader.getInstance();
        this.policyName = policyName;
        this.fieldPath = fieldPath;
    }

    /**
     * Process a record — protect the specified field.
     * In a full StreamSets integration, this would be called from
     * BaseProcessor.process() on each record batch.
     */
    public String protect(String value) {
        if (value == null) return null;
        try {
            return client.protect(value, policyName);
        } catch (Exception e) {
            return "[error: " + e.getMessage() + "]";
        }
    }

    /**
     * Access (decrypt) a protected value using the embedded tag.
     */
    public static String access(String protectedValue) {
        if (protectedValue == null) return null;
        try {
            return CypheraLoader.getInstance().access(protectedValue);
        } catch (Exception e) {
            return "[error: " + e.getMessage() + "]";
        }
    }
}
