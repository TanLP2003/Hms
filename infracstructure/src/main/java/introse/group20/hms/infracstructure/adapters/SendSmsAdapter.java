package introse.group20.hms.infracstructure.adapters;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.type.PhoneNumber;
import introse.group20.hms.application.adapters.ISendSmsAdapter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SendSmsAdapter implements ISendSmsAdapter {
    private final String TWILIO_ACCOUNT_SID = "ACdafb745a932c0febbe0e8297f40ee6a4";
    private final String TWILIO_AUTH_TOKEN = "b9cf25ca0a7daae2c9ccdd3ac32405b2";
    @Override
    public void sendSmsAdapter(String phoneNumber, String password) {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Verification verification = Verification.creator(
                "ZScf7a680836a160ee9de49d7a6a7c2f3e",
                "+84964790025",
                "sms"
        ).create();
        System.out.println(verification.getStatus());
//        Message.creator(new PhoneNumber(phoneNumber),
//                new PhoneNumber("+18064644033"), password).create();
    }
}
