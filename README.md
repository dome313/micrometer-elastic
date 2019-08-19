### Micormeter does not return anything from /actuator/metrics endpoint for interval 120 seconds

Script to send random requests to service endpoints
```
#!
while true; do
  sleep=$(shuf -i 100-1000 -n 1)
  endpoint=$(shuf -i 1-2 -n 1)
  curl -s "http://localhost:8080/test$endpoint"
  sleep "0.$sleep"
done
```
Script to poll /actuator endpoint every X seconds 
```
#!
sleepTime=${1:-60}
while true; do
  date
  data=$(curl -s "http://localhost:8080/actuator/metrics/http.server.requests")
  stat1=$(echo "$data" | jq '.measurements[0].statistic')
  val1=$(echo "$data" | jq '.measurements[0].value')
  echo "$stat1: $val1" ;
  stat2=$(echo "$data" | jq '.measurements[1].statistic')
  val2=$(echo "$data" | jq '.measurements[1].value')
  echo "$stat2: $val2" ;
  stat3=$(echo "$data" | jq '.measurements[2].statistic')
  val3=$(echo "$data" | jq '.measurements[2].value')
  echo "$stat3: $val3" ;
  echo "------------------------------------------------------------------------"
  sleep "$sleepTime"
done
```

RUn it as
```
 ./poll-actuator-metrics.sh 120 8080
```
