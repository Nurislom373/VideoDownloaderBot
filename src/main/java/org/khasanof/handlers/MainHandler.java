package org.khasanof.handlers;

import org.khasanof.handlers.callBack.CallbackHandler;
import org.khasanof.handlers.message.MessageHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MainHandler {
    public static final MainHandler HANDLER = new MainHandler();
    private static final MessageHandler MESSAGE_HANDLER = MessageHandler.getMessageHandler();
    private static final CallbackHandler CALL_BACK_HANDLER = CallbackHandler.getCallbackHandler();

    public void handler(Update update) {
        if (update.hasMessage()) {
            MESSAGE_HANDLER.process(update);
        } else if(update.hasCallbackQuery()) {
            CALL_BACK_HANDLER.process(update);
        }
    }

    public static MainHandler getHandler() {
        return HANDLER;
    }
}
