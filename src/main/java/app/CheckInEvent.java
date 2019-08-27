package app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Random;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CheckInEvent {

  private static Random RANDOM = new Random();

  private static String[] IATA_CITY_CODES = {
      "AUH",
      "DUB",
      "BEG",
      "KBP",
      "MSQ",
      "GIG"
  };

  private String from;
  private String to;
  @JsonIgnore
  private String mobileNumber;
  private String result;

  public static CheckInEvent randomSuccessfulEvent() {
    CheckInEvent checkInEvent = randomEvent();
    checkInEvent.setResult("SUCCESS");
    return checkInEvent;
  }

  public static CheckInEvent randomFailureEvent() {
    CheckInEvent checkInEvent = randomEvent();
    checkInEvent.setResult("FAILURE");
    return checkInEvent;
  }

  private static CheckInEvent randomEvent() {
    String from = IATA_CITY_CODES[RANDOM.nextInt(IATA_CITY_CODES.length - 1)];
    String to;
    do {
      to = IATA_CITY_CODES[RANDOM.nextInt(IATA_CITY_CODES.length - 1)];
    } while (to.equals(from));
    CheckInEvent checkInEvent = new CheckInEvent();
    checkInEvent.setFrom(from);
    checkInEvent.setTo(to);
    checkInEvent.setMobileNumber(String.valueOf(100000000 + RANDOM.nextInt(900000000)));
    return checkInEvent;
  }


  @ToString.Include(name = "mobileNumber")
  private String mobileNumberToString() {
    return mobileNumber.replaceAll("\\d(?=(?:\\D*\\d){3})", "*");
  }


}
