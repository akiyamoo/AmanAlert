package kg.iaau.amanalert.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import kg.iaau.amanalert.service.SmsSenderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SmsSenderServiceImpl implements SmsSenderService {

    String accountSid;
    String authToken;
    String twilioPhone;

    public SmsSenderServiceImpl(
            @Value("${aman.alert.twilio.account-sid}") String accountSid,
            @Value("${aman.alert.twilio.auth-token}") String authToken,
            @Value("${aman.alert.twilio.twilio-number}") String twilioPhone
    ) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.twilioPhone = twilioPhone;
    }

    @Override
    public boolean sendMessage(String phone, String message) {
        Message msg;
        Twilio.init(accountSid, authToken);
        try {
            msg = Message.creator(
                    new PhoneNumber(phone),
                    new PhoneNumber(twilioPhone),
                    message
            ).create();
        } catch (Exception e) {
            log.error("{}", e);
            return false;
        }

        log.info("Send SMS: sid: {}, phone: {}, message: {}", msg.getSid(), phone, message);

        return true;
    }
}
