package com.dmart.ecommerce.Service.ServiceImpls;

import com.dmart.ecommerce.DTO.ResponseDTO.ApiResponseWithMessage;
import com.dmart.ecommerce.Service.OtpService;
import com.dmart.ecommerce.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService
{
    private final TwilioConfig twilioConfig;

    @Autowired
    public OtpServiceImpl(TwilioConfig twilioConfig)
    {
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
    }

    // send otp on mobile.
    public ApiResponseWithMessage sendOtp(String mobileNumber)
    {
        String otp = String.valueOf(new Random().nextInt(900000)+100000);

        Message.creator(
                new PhoneNumber(mobileNumber),
                new PhoneNumber(twilioConfig.getTrialNumber()),
                "Your OTP is:" + otp + "(Valid for 5 minutes)"
        ).create();
        return new ApiResponseWithMessage(true, HttpStatus.OK,"OTP Received :" + otp);
    }
}
