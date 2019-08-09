package app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/test")
  public void sendMessage() {
    System.out.println("Received a test message");
  }

}
