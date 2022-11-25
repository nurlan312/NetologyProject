package org.nurlan;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "$BOT_TOKEN";
    private static final String BOT_USERNAME = "secondSecond_bot";
    private static final String URI = "https://api.nasa.gov/planetary/apod?api_key=VcFLMRZFSwkyYplpTX4Farqq5zQ9zXyApQo9OK65";

    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            switch (update.getMessage().getText()) {
                case "/help":
                    sendMassage("Привет, я бот NASA! Я высылаю ссылки на картинки по запросу. " +
                            "Напоминаю, что картинки на сайте NASA обновляются раз в сутки");
                    break;
                case "/give":
                    try {
                        sendMassage(Utilss.getUrl(URI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }

    private void sendMassage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId("$CHAT_ID");
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
