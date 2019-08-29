package app;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

  private static final int DELAY_MULTIPLIER = 100;
  private JmsTemplate jmsTemplate;

  @Autowired
  public TestController(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @GetMapping("/test1")
  public ResponseEntity<Void> sendMessage() throws Exception {
    Thread.sleep(new Random().nextInt(2) * DELAY_MULTIPLIER);
    ResponseEntity<Void> response = response();
    log.info("Received a test message 1 {}", response.getStatusCode().toString());
    return response;
  }

  @GetMapping("/test2")
  public ResponseEntity<Void> sendMessage2() throws Exception {
    Thread.sleep((new Random().nextInt(5) + 15) * DELAY_MULTIPLIER);
    ResponseEntity<Void> response = response();
    log.info("Received a test message 2 {}", response.getStatusCode().toString());
    return response;
  }

  @GetMapping("/test3")
  public ResponseEntity<Void> sendMessage3() throws Exception {
    Thread.sleep((new Random().nextInt(5) + 5) * DELAY_MULTIPLIER);
    ResponseEntity<Void> response = response();
    log.info("Received a test message 3 {}", response.getStatusCode().toString());

    Map<String, String> map = new HashMap<>();
    map.put("hello", "world");
    jmsTemplate.convertAndSend("DEV.QUEUE.1", map);

    return response;
  }

  private ResponseEntity<Void> response() {
    int result = new Random().nextInt(10);
    if (result % 5 == 0) {
      return ResponseEntity.notFound().build();
    } else if (result % 7 == 0) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok().build();
  }

}
