package dirv.chat.client;

import java.io.IOException;

import dirv.chat.Message;

public class ServerListener implements Runnable {

    private final MessageSender messageSender;
    private final Display display;
    private long lastTimestampSeen = 0;
    
    public ServerListener(MessageSender messageSender, Display display) {
        this.messageSender = messageSender;
        this.display = display;
    }

    @Override
    public void run() {
        try {
            messageSender.retrieveMessagesSince(lastTimestampSeen)
            .stream()
            .forEach(this::handleMessage);
        } catch (IOException e) {
            display.exception(e);
        }
    }
    
    private void handleMessage(Message message) {
        updateTimestamp(message);
        if (message.getMessage() == "hangman"){
        	try {
				messageSender.sendMessage("Welcome to hangman");
			} catch (IOException e) {
				e.printStackTrace();
			}
//        	MessageSender sendReply = new MessageSender(null, null, 0, null);
        }
        display.message(message);
    }
    
    private void updateTimestamp(Message message) {
        this.lastTimestampSeen = message.getTimestamp();
    }

}
