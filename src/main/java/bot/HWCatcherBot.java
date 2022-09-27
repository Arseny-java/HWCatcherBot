package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HWCatcherBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "HWCatcher_bot";
    }

    @Override
    public String getBotToken() {
        return "5725928588:AAFoKIKYhaZRMwLtvABIU83r4d6qKifh_B8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/stop")) {
            String messageText = "Бот отключен!";
            SendMessage message = new SendMessage();
            message.setChatId("1651191402");
            message.setText(messageText);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }

    }

    public void sendAlert(String hwName) {
        String messageText = "Сенечка, '" + hwName + "' взято в работу";
        SendMessage message = new SendMessage();
        message.setChatId("1651191402");
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendStart() {
        String messageText = "Бот включен!";
        SendMessage message = new SendMessage();
        message.setChatId("1651191402");
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
