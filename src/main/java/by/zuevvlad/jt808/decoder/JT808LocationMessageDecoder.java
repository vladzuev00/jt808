package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808LocationMessage;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static by.zuevvlad.jt808.util.JT808Util.*;

@Component
public final class JT808LocationMessageDecoder extends JT808MessageDecoder<JT808LocationMessage> {
    private static final short MESSAGE_ID = 512;

    public JT808LocationMessageDecoder() {
        super(MESSAGE_ID);
    }

    @Override
    protected JT808LocationMessage decodeInternal(ByteBuf buffer, String phoneNumber) {
        skipAlarmSign(buffer);
        skipStatus(buffer);
        float latitude = decodeLatitude(buffer);
        float longitude = decodeLongitude(buffer);
        short altitude = decodeAltitude(buffer);
        short speed = decodeSpeed(buffer);
        short course = decodeCourse(buffer);
        Instant dateTime = decodeDateTime(buffer);
        return new JT808LocationMessage(dateTime, latitude, longitude, altitude, speed, course);
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
