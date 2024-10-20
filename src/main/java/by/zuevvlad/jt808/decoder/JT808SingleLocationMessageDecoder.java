package by.zuevvlad.jt808.decoder;

import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public final class JT808SingleLocationMessageDecoder extends JT808LocationMessageDecoder {
    private static final short MESSAGE_ID = 512;

    public JT808SingleLocationMessageDecoder() {
        super(MESSAGE_ID);
    }

    @Override
    protected int decodeLocationCount(ByteBuf buffer) {
        return 1;
    }

    @Override
    protected void skipToFirstLocation(ByteBuf buffer) {

    }
}
