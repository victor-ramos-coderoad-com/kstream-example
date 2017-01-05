package com.tierconnect.riot.bridges;

import com.mojix.examples.KCacheService;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import javax.ws.rs.NotFoundException;

/**
 * Created by vramos on 1/5/17.
 */
public class CacheService {

    private final KafkaStreams streams;

    public CacheService(final KafkaStreams streams){
        this.streams = streams;
    }

    public String getAllZones(){
        final ReadOnlyKeyValueStore<String, String> cacheZones = streams.store(
                KCacheService.CACHE_ZONE,
                QueryableStoreTypes.<String,String> keyValueStore());

        KeyValueIterator<String, String> zones = cacheZones.all();
        String response = "";
        while (zones.hasNext()) {
            KeyValue<String, String> zone = zones.next();
            response += String.format("ZONE code=%s value=%s\n", zone.key, zone.value);
        }
        return response;
    }

    public String getZoneByCode(String code){
        final ReadOnlyKeyValueStore<String, String> songStore = streams.store(
                KCacheService.CACHE_ZONE,
                QueryableStoreTypes.<String,String> keyValueStore());
        final String zone = songStore.get(code);
        if (zone == null) {
            System.out.println(String.format("Zone with code %s was not found", code));
        }
        return zone;
    }
}
