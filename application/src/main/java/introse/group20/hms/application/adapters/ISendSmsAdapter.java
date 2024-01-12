package introse.group20.hms.application.adapters;

import java.io.IOException;

public interface ISendSmsAdapter {
    void sendSmsAdapter(String phoneNumber, String password) throws IOException;
}
