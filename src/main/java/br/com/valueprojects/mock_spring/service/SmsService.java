package br.com.valueprojects.mock_spring.service;

import com.twilio.Twilio;

import br.com.valueprojects.mock_spring.model.Participante;
import br.com.valueprojects.mock_spring.model.Sms;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsService {

    private static final String ACCOUNT_SID = "ACd41acbf6451d81b90b4b220c8c71a178";
    private static final String AUTH_TOKEN = "356b89e5d9acc006da37e017efd4f0f6";
    private static final String TWILIO_PHONE_NUMBER = "+[+1 434 381 4642]";

    public SmsService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void enviarSms(Participante participante, String mensagem) {
        String numeroParticipante = participante.getNumeroDeTelefone();
        if (numeroParticipante != null) {
            Message.creator(
                    new PhoneNumber(numeroParticipante),
                    new PhoneNumber(TWILIO_PHONE_NUMBER),
                    mensagem).create();
        }
    }
}