package by.zuevvlad.jt808.model;

public final class JT808HeartbeatMessage extends JT808Message<Void> {
    public static final short MESSAGE_ID = 2;

    public JT808HeartbeatMessage(BodyProperties bodyProperties,
                                 String phoneNumber,
                                 short serialNumber,
                                 Void body,
                                 byte checkCode) {
        super(MESSAGE_ID, bodyProperties, phoneNumber, serialNumber, body, checkCode);
    }
}
