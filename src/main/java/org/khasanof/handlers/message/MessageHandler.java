package org.khasanof.handlers.message;

import lombok.RequiredArgsConstructor;
import org.khasanof.VideoDownloader;
import org.khasanof.entity.instagram.InstagramEntity;
import org.khasanof.handlers.IBaseHandler;
import org.khasanof.keyboards.inline.InlineKeyboard;
import org.khasanof.service.instagram.InstagramService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


public class MessageHandler implements IBaseHandler {
    private static final InstagramService instagramService = InstagramService.getService();
    public static final MessageHandler MESSAGE_HANDLER = new MessageHandler();
    private static final VideoDownloader bot = VideoDownloader.getDownloader();

    @Override
    public void process(Update update) {
        Long chatID = update.getMessage().getChatId();
        if (update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start")) {
                String text = "Choose bot language:";
                SendMessage sendMessage = new SendMessage(chatID.toString(), "<b>" + text + "</b>");
                sendMessage.setParseMode("html");
                sendMessage.setReplyMarkup(InlineKeyboard.language());
                bot.executeMessage(sendMessage);
            }
            else if(update.getMessage().getText().equals("Instagram")) {
                String text = "enter link";
                SendMessage sendMessage = new SendMessage(chatID.toString(), "<b>" + text + "</b>");
                sendMessage.setParseMode("html");
                bot.executeMessage(sendMessage);
            }
            else if(update.getMessage().getText().contains("www.instagram.com")) {
                try {
                    SendMessage sendMessage = new SendMessage(chatID.toString(), "processing...");
                    bot.executeMessage(sendMessage);
                    InstagramEntity instagramEntity = InstagramService.load(update.getMessage().getText());
                    String title = instagramEntity.getResponse().getTitle();
                    String url = instagramEntity.getResponse().getLinks().get(0).getUrl();
                    if (instagramEntity.getResponse().getLinks().get(0).getExt().equals("mp4")) {
                        SendVideo video = new SendVideo(chatID.toString(), new InputFile(URI.create(url).toURL().openStream(), title));
                        video.setCaption(title);
                        bot.executeVideo(video);
                    } else {
                        SendPhoto photo = new SendPhoto(chatID.toString(), new InputFile(URI.create(url).toURL().openStream(), title));
                        photo.setCaption(title);
                        bot.executePhoto(photo);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static MessageHandler getMessageHandler() {
        return MESSAGE_HANDLER;
    }
}
