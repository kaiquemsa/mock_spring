package br.com.valueprojects.mock_spring.model;

public class Sms {
    public String Mensagem;

    public Sms(String mensagem) {
        this.Mensagem = mensagem;
    }

    public void enviarSmsParaVencedor(Sms smsVencedor, Participante vencedor) {
        throw new UnsupportedOperationException("Unimplemented method 'enviarSmsParaVencedor'");
    }
}