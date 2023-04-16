package kg.iaau.amanalert.service;

public interface SmsSenderService {
    boolean sendMessage(String phone, String message);
}
