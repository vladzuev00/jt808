package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808LocationMessage;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static by.zuevvlad.jt808.util.JT808Util.decodeDateTime;
import static by.zuevvlad.jt808.util.JT808Util.decodeGpsCoordinate;

@Component
public final class JT808LocationMessageDecoder extends JT808MessageDecoder<JT808LocationMessage> {
    private static final short MESSAGE_ID = 512;
    private static final int ALARM_SIGN_BYTE_COUNT = 4;
    private static final int STATUS_BYTE_COUNT = 16;

    public JT808LocationMessageDecoder() {
        super(MESSAGE_ID);
    }

    @Override
    protected JT808LocationMessage decodeInternal(ByteBuf buffer, String phoneNumber) {
        skipAlarmSign(buffer);
        skipStatus(buffer);
        float latitude = decodeGpsCoordinate(buffer);
        float longitude = decodeGpsCoordinate(buffer);
        short altitude = decodeAltitude(buffer);
        short speed = decodeSpeed(buffer);
        short course = decodeCourse(buffer);
        Instant dateTime = decodeDateTime(buffer);
        return new JT808LocationMessage(dateTime, latitude, longitude, altitude, speed, course);
    }

    private void skipAlarmSign(ByteBuf buffer) {
        buffer.skipBytes(ALARM_SIGN_BYTE_COUNT);
    }

    private void skipStatus(ByteBuf buffer) {
        buffer.skipBytes(STATUS_BYTE_COUNT);
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
