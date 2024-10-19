package by.zuevvlad.jt808.model;

import lombok.Value;

import java.time.Instant;

@Value
public class JT808LocationMessage {
    Instant dateTime;
    float latitude;
    float longitude;
    int altitude;
    int speed;
    int course;
}
