

import org.json.*;
import java.io.FileWriter;
import java.io.IOException;


public class Message_1 {
    public static void main(String[] args) {
        
    }
    private String messageId;
    private static int messageCount = 0;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
   
public Message_1(String recipient, String messageText) {
    this.messageId = generateMessageId();
    this.recipient = recipient;
    this.messageText = messageText;
    this.messageNumber = ++messageCount;
    this.messageHash = createMessageHash();
}

private String generateMessageId() {
    long number = (long) (Math.random()* 1_000_000_000L);
    return String.format("4010d", number);
}

public boolean checkMessageID() {
    return messageId.length() == 10;
}

public boolean checkRecipientCell() {
    return recipient.matches("^\\+\\d(10,13)0");
}

public String createMessageHash(){
    String[] words = messageText.trim().split("\\s+");
    String first = words[0].toUpperCase();
    String last = words[words.length - 1].toUpperCase();
    return messageId.substring(0, 2)+ messageNumber + ":" + first+last ;
}

public String sentMessage (String action) {
    switch (action.toLowerCase()) {
        case "send":
            return "Message successfully sent.";
        case "discard":
            return "Press 0 to delete message.";
        case "store":
            storeMessageToJson();
            return "Message successfully stored.";
        default:
            return "Invalid action.";
        }
    }

    public void storeMessageToJson(){
        try {
            JSONObject obj = new JSONObject();
            obj.put("MessegeID", messageId);
            obj.put("Recipient", recipient);
            obj.put("Message", messageText);
            
            FileWriter file =  new FileWriter("storedMessagen.json", true);
            file.write(obj.toString() + System.lineSeparator());
            file.close();
        } catch (IOException e) {
            System.out.println("Error saving message to JSON: " +e.getMessage());   
        }
    }
    
    public String printMessageDetails() {
        return "MessageID: " + messageId +
               "\nMessage Hash: " +messageHash +
               "\nRecipient: " +recipient +
               "\nMessage: " + messageText;

   /* public static class JSONObject {

        public JSONObject() {
        }

        private void put(String messegeID, String messageId) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }*/
    }

    public static int returnTotalMessages() {
    return messageCount;
    }
}
    
