package io.github.vdiskg.util;

import java.text.NumberFormat;
import java.time.Duration;

import org.springframework.util.Assert;

/**
 * @author vdisk
 * @version 1.0
 * @since 2024-12-16 10:06
 */
public class DurationPrinter {

    /**
     * the duration
     */
    private final Duration duration;

    DurationPrinter(Duration duration) {
        Assert.notNull(duration, "duration 不允许为 null");
        this.duration = duration;
    }

    public static DurationPrinter ofDuration(Duration duration) {
        return duration != null ? ofDurationInternal(duration) : null;
    }

    private static DurationPrinter ofDurationInternal(Duration duration) {
        return duration != null ? new DurationPrinter(duration) : null;
    }

    public static DurationPrinter ofMillis(Long millis) {
        return millis != null ? ofDurationInternal(Duration.ofMillis(millis)) : null;
    }

    @Override
    public String toString() {
        long nanos;
        try {
            nanos = this.duration.toNanos();
        } catch (ArithmeticException e) {
            return this.duration.toString();
        }
        double millis = nanosToMillis(nanos);
        NumberFormat millisFormat = millisFormat();
        return millisFormat.format(millis) + "ms (" + this.duration + ")";
    }

    private static double nanosToMillis(long nanos) {
        return (double) nanos / (double) 1000_000;
    }

    private static NumberFormat millisFormat() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumFractionDigits(6);
        numberFormat.setMaximumFractionDigits(6);
        return numberFormat;
    }
}
