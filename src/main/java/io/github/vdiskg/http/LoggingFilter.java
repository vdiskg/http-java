package io.github.vdiskg.http;

import java.io.IOException;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.vdiskg.util.DurationPrinter;
import io.github.vdiskg.util.TimestampPrinter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author vdisk
 * @version 1.0
 * @since 2024-12-16 10:06
 */
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long requestReceiveTimeMillis = System.currentTimeMillis();
        String requestUri = request.getRequestURI();
        boolean logEnabled = log.isDebugEnabled();
        long start;
        if (logEnabled) {
            start = System.nanoTime();
            log.debug("request-start [uri:{}][receive:{}]", requestUri, TimestampPrinter.ofEpochMilli(requestReceiveTimeMillis));
        } else {
            start = 0;
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            if (logEnabled) {
                long end = System.nanoTime();
                Duration cost = Duration.ofNanos(end - start);
                log.debug("request-end [uri:{}][cost:{}]", requestUri, DurationPrinter.ofDuration(cost));
            }
        }
    }
}
