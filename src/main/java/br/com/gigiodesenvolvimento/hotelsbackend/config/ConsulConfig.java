package br.com.gigiodesenvolvimento.hotelsbackend.config;

import org.microprofileext.config.source.base.EnabledConfigSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ConsulConfig extends EnabledConfigSource {

    private static final String NAME = "ConsulConfig";

    private final Map<String, TimedEntry> cache = new ConcurrentHashMap<>();

    private Long validity = null;
    private String prefix = null;

    @Override
    protected Map<String, String> getPropertiesIfEnabled() {
        return cache.entrySet()
                .stream()
                .filter(e -> e.getValue().getValue() != null)
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().getValue()));
    }

    @Override
    public String getValue(String propertyName) {
        if (key.startsWith("consul")) {
            // in case we are about to configure ourselves we simply ignore that key
            return null;
        }

        TimedEntry entry = cache.get(key);
        if (entry == null || entry.isExpired()) {

            GetValue value = null;
            try {
                value = getClient().getKVValue(getPrefix() + key).getValue();
            } catch (Exception e) {
                log.log(Level.WARNING, "consul getKVValue failed: {0}", e.getMessage());
                if (entry != null) {
                    return entry.getValue();
                }
            }
            if (value == null) {
                cache.put(key, new TimedEntry(null));
                return null;
            }
            String decodedValue = value.getDecodedValue();
            cache.put(key, new TimedEntry(decodedValue));
            return decodedValue;
        }
        return entry.getValue();
    }

    @Override
    public String getName() {
        return NAME;
    }

    private Long getValidity() {
        if (validity == null) {
            validity = getConfig().getOptionalValue("consul.validity", Long.class).orElse(120L) * 1000L;
        }
        return validity;
    }

    class TimedEntry {
        private final String value;
        private final long timestamp;

        public TimedEntry(String value) {
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }

        public String getValue() {
            return value;
        }

        public boolean isExpired() {
            return (timestamp + getValidity()) < System.currentTimeMillis();
        }
    }

}
