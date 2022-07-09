package org.khasanof.handlers.callBack;

import org.khasanof.VideoDownloader;
import org.khasanof.enums.language.Language;
import org.khasanof.handlers.IBaseHandler;
import org.khasanof.keyboards.reply.ReplyKeyboard;
import org.khasanof.utils.baseUtils.BaseUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackHandler implements IBaseHandler {
    public static final CallbackHandler CALLBACK_HANDLER = new CallbackHandler();
    private static final VideoDownloader bot = VideoDownloader.getDownloader();

    @Override
    public void process(Update update) {
        Long chatID = update.getCallbackQuery().getMessage().getChatId();
        if (update.hasCallbackQuery()) {
            switch (update.getCallbackQuery().getData()) {
                case "UZ", "EN", "RU" -> {
                    DeleteMessage deleteMessage = new DeleteMessage(chatID.toString(), update.getCallbackQuery().getMessage().getMessageId());
                    SendMessage message = new SendMessage(chatID.toString(), "<b>" + BaseUtils.translate("EN", Language.WELCOME_TO) + "</b>");
                    SendMessage message1 = new SendMessage(chatID.toString(), "<b>" + BaseUtils.translate("EN", Language.CHOOSE_MENU) + "</b>");
                    message1.setReplyMarkup(ReplyKeyboard.enterMenu());
                    message1.setParseMode("html");
                    message.setParseMode("html");
                    bot.executeMessage(message);
                    bot.executeMessage(message1);
                    bot.executeMessage(deleteMessage);
                }
            }
        }
    }

    public static CallbackHandler getCallbackHandler() {
        return CALLBACK_HANDLER;
    }
}
