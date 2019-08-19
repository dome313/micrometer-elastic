package app;

import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

//  @Autowired
//  JmsTemplate jmsTemplate;

  @GetMapping("/test1")
  public ResponseEntity<Void> sendMessage() throws Exception {
    Thread.sleep(new Random().nextInt(2) * 100);
    ResponseEntity<Void> response = response();
    System.out.println("Received a test message 1 " + response.getStatusCode().toString());
    return response;
  }

  @GetMapping("/test2")
  public ResponseEntity<Void> sendMessage2() throws Exception {
    Thread.sleep(new Random().nextInt(2) * 1000);
    ResponseEntity<Void> response = response();
    System.out.println("Received a test message 2 " + response.getStatusCode().toString());
    return response;
  }

//  @GetMapping("/test3")
//  public ResponseEntity<Void> sendMessage3() throws Exception {
//    Thread.sleep(new Random().nextInt(2) * 1000);
//    ResponseEntity<Void> response = response();
//    System.out.println("Received a test message 3 " + response.getStatusCode().toString());
//
//    Map<String, String> map = new HashMap<>();
//    map.put("hello", "world");
//    jmsTemplate.convertAndSend("DEV.QUEUE.1", map);
//
//    return response;
//  }


  private ResponseEntity<Void> response() {
    if (new Random().nextInt(5) % 5 == 0) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }

}
