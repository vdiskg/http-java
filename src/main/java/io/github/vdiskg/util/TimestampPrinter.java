package io.github.vdiskg.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.util.Assert;

/**
 * @author vdisk
 * @version 1.0
 * @since 2024-12-16 10:06
 */
public class TimestampPrinter {

    /**
     * timestamp
     */
    private final Instant instant;

    private TimestampPrinter(Instant instant) {
        Assert.notNull(instant, "instant");
        this.instant = instant;
    }

    public static TimestampPrinter ofInstant(Instant instant) {
        return new TimestampPrinter(instant);
    }

    public static TimestampPrinter ofEpochMilli(long epochMilli) {
        return TimestampPrinter.ofInstant(Instant.ofEpochMilli(epochMilli));
    }

    public static TimestampPrinter now() {
        return TimestampPrinter.ofInstant(Instant.now());
    }

    public Instant getInstant() {
        return this.instant;
    }

    @Override
    public String toString() {
        long epochMilli = this.instant.toEpochMilli();
        LocalDateTime dateTime = LocalDateTime.ofInstant(this.instant, ZoneId.systemDefault());
        // epochMilli(ISO8601)
        return epochMilli + " (" + dateTime + ")";
    }
}
