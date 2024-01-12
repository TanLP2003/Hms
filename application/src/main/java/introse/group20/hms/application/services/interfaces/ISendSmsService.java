package introse.group20.hms.application.services.interfaces;

import java.io.IOException;

public interface ISendSmsService {
    void sendSms(String phoneNumber, String message) throws IOException;

}
