package ke.co.nectar.api.domain.extractor;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Meter;
import ke.co.nectar.api.domain.MeterType;
import ke.co.nectar.api.domain.Subscriber;
import ke.co.nectar.api.domain.configuration.exceptions.InvalidConfigurationTypeException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface Extract<T> {

    T extractFrom(ApiResponse response) throws InvalidConfigurationTypeException;

    default List<Meter> extractMeters(List<LinkedHashMap> meters) {
        List<Meter> extractedMeters  = new ArrayList<>();
        meters.forEach(meter -> {
            extractedMeters.add(extractMeter(meter));
        });
        return extractedMeters;
    }

    default Meter extractMeter(LinkedHashMap meter) {
        return new Meter(
                (String) meter.get("ref"),
                BigDecimal.valueOf((Long) meter.get("no")),
                (Boolean) meter.get("activated"),
                extractMeterType((LinkedHashMap) meter.get("meter_type")),
                extractSubscriber((LinkedHashMap) meter.get("subscriber")),
                Instant.parse((String) meter.get("created_at")),
                Instant.parse((String) meter.get("updated_at")));
    }

    default MeterType extractMeterType(LinkedHashMap meterType) {
        if (meterType != null) {
            return new MeterType((String) meterType.get("name"),
                    (String) meterType.get("ref"),
                    Instant.parse((String) meterType.get("created_at")),
                    Instant.parse((String) meterType.get("updated_at")));
        }
        return null;
    }

    default Subscriber extractSubscriber(LinkedHashMap subscriber) {
        if (subscriber != null) {
            return new Subscriber((String) subscriber.get("name"),
                    (String) subscriber.get("phone_no"),
                    (String) subscriber.get("ref"),
                    (Boolean) subscriber.get("activated"),
                    Instant.parse((String) subscriber.get("created_at")),
                    Instant.parse((String) subscriber.get("updated_at")));
        }
        return null;
    }
}
