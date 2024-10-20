package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808LocationMessage;
import by.zuevvlad.jt808.model.Location;
import io.netty.buffer.ByteBuf;

import java.time.Instant;

import static by.zuevvlad.jt808.util.JT808Util.*;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public abstract class JT808LocationMessageDecoder extends JT808MessageDecoder<JT808LocationMessage> {

    public JT808LocationMessageDecoder(short messageId) {
        super(messageId);
    }

    @Override
    protected final JT808LocationMessage decodeInternal(ByteBuf buffer, String phoneNumber) {
        int locationCount = decodeLocationCount(buffer);
        skipToFirstLocation(buffer);
        return range(0, locationCount)
                .mapToObj(i -> decodeLocation(buffer))
                .collect(collectingAndThen(toList(), JT808LocationMessage::new));
    }

    protected abstract int decodeLocationCount(ByteBuf buffer);

    protected abstract void skipToFirstLocation(ByteBuf buffer);

    private Location decodeLocation(ByteBuf buffer) {
        skipAlarmSign(buffer);
        skipStatus(buffer);
        float latitude = decodeLatitude(buffer);
        float longitude = decodeLongitude(buffer);
        short altitude = decodeAltitude(buffer);
        short speed = decodeSpeed(buffer);
        short course = decodeCourse(buffer);
        Instant dateTime = decodeDateTime(buffer);
        return new Location(dateTime, latitude, longitude, altitude, speed, course);
    }

    private void skipAlarmSign(ByteBuf buffer) {
        buffer.skipBytes(Integer.BYTES);
    }

    private void skipStatus(ByteBuf buffer) {
        buffer.skipBytes(Integer.BYTES);
    }

    private short decodeAltitude(ByteBuf buffer) {
        return buffer.readShort();
    }

    private short decodeSpeed(ByteBuf buffer) {
        return buffer.readShort();
    }

    private short decodeCourse(ByteBuf buffer) {
        return buffer.readShort();
    }
}
