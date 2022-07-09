package org.khasanof.handlers.message;

import lombok.Getter;
import lombok.Setter;
import org.khasanof.VideoDownloader;
import org.khasanof.entity.instagram.InstagramEntity;
import org.khasanof.entity.tiktok.TikTokEntity;
import org.khasanof.handlers.IBaseHandler;
import org.khasanof.keyboards.reply.ReplyKeyboard;
import org.khasanof.service.instagram.InstagramService;
import org.khasanof.service.tiktok.TikTokService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.io.IOException;
import java.net.URI;


@Getter
@Setter
public class MessageHandler implements IBaseHandler {
    @Override
    public void process(Update update, VideoDownloader bot) {
        Long chatID = update.getMessage().getChatId();
        if (update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start")) {
                String text = "Choose Download one:";
                SendMessage sendMessage = new SendMessage(chatID.toString(), "<b>" + text + "</b>");
                sendMessage.setParseMode("html");
                sendMessage.setReplyMarkup(ReplyKeyboard.enterMenu());
                bot.executeMessage(sendMessage);
            } else if (update.getMessage().getText().equals("Instagram")) {
                String text = "enter link";
                SendMessage sendMessage = new SendMessage(chatID.toString(), "<b>" + text + "</b>");
                sendMessage.setParseMode("html");
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
                bot.executeMessage(sendMessage);
            } else if (update.getMessage().getText().equals("TikTok")) {
                String text = "enter link";
                SendMessage sendMessage = new SendMessage(chatID.toString(), "<b>" + text + "</b>");
                sendMessage.setParseMode("html");
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
                bot.executeMessage(sendMessage);
            } else if (update.getMessage().getText().contains("www.instagram.com")) {
                try {
                    SendMessage sendMessage = new SendMessage(chatID.toString(), "<b>processing...</b>");
                    sendMessage.setParseMode("html");
                    bot.executeMessage(sendMessage);
                    InstagramEntity instagramEntity = InstagramService.load(update.getMessage().getText());
                    String title = instagramEntity.getResponse().getTitle();
                    String url = instagramEntity.getResponse().getLinks().get(0).getUrl();
                    if (instagramEntity.getResponse().getLinks().get(0).getExt().equals("mp4")) {
                        SendVideo video = new SendVideo(chatID.toString(), new InputFile(URI.create(url).toURL().openStream(), title));
                        video.setCaption(title);
                        video.setReplyMarkup(ReplyKeyboard.enterMenu());
                        bot.executeVideo(video);
                    } else {
                        SendPhoto photo = new SendPhoto(chatID.toString(), new InputFile(URI.create(url).toURL().openStream(), title));
                        photo.setCaption(title);
                        photo.setReplyMarkup(ReplyKeyboard.enterMenu());
                        bot.executePhoto(photo);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().getText().contains("tiktok.com")) {
                try {
                    SendMessage sendMessage = new SendMessage(chatID.toString(), "<b>processing...</b>");
                    sendMessage.setParseMode("html");
                    bot.executeMessage(sendMessage);
                    TikTokEntity tikTokEntity = TikTokService.load(update.getMessage().getText());
                    String url = tikTokEntity.getVideo().get(0);
                    SendVideo video = new SendVideo(chatID.toString(), new InputFile(URI.create(url).toURL().openStream(), String.valueOf(System.currentTimeMillis())));
                    video.setReplyMarkup(ReplyKeyboard.enterMenu());
                    bot.executeVideo(video);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
