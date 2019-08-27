package app;

import static net.logstash.logback.argument.StructuredArguments.value;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Timer.Sample;
import java.util.HashMap;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestJmsListener {

  @Autowired
  private MeterRegistry meterRegistry;

  @JmsListener(destination = "DEV.QUEUE.1")
  public void receiveMessage(HashMap map) {

    Sample sample = Timer.start(this.meterRegistry);

    try {
      if (new Random().nextInt(5) % 5 == 0) {

        meterRegistry.counter("check.in", Tags.of("result", "FAILURE")).increment();

        CheckInEvent checkInEvent = CheckInEvent.randomFailureEvent();

        log.error("FAILURE check-in {}", value("checkInEvent", checkInEvent));

      } else {

        meterRegistry.counter("check.in", Tags.of("result", "SUCCESS")).increment();

        CheckInEvent checkInEvent = CheckInEvent.randomSuccessfulEvent();
        log.info("SUCCESS check-in {}", value("checkInEvent", checkInEvent));

      }
    } finally {

      sample.stop(Timer.builder("jms")
          .tags(Tags.of(
              Tag.of("queue", "DEV.QUEUE.1")
          ))
          .register(this.meterRegistry));
    }
  }
}
