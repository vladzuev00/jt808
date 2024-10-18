package by.zuevvlad.jt808.model;

public final class JT808HeartbeatMessage extends JT808Message<Void> {

    public JT808HeartbeatMessage(short id,
                                 BodyProperties bodyProperties,
                                 String phoneNumber,
                                 short serialNumber,
                                 Void body,
                                 byte checkCode) {
        super(id, bodyProperties, phoneNumber, serialNumber, body, checkCode);
    }
}
