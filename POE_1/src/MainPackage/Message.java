/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;

import javax.swing.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Random;


public class Message  {
    
    
    
    private static int totalMessages = 0;
    private static final ArrayList<String> sentMessages = new ArrayList<>();
    private String messageID;
    private String recipient;
    private String message ;
    
    
    public Message(String messageID, String recipient, String message, String sender, String timestamp) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
    }
    
    
    
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }
    
    public int checkRecipientCell() {
        return (recipient.length() <= 10 && recipient.matches("\\+?[0-9]+")) ? 1 : 0;
    }
    
    public String createMessageHash() {
        return messageID.substring(0, 2) + ":" + message.length() + ":" + message.split(" ")[0] + message.split(" ")[message.split(" ").length - 1];
    }
    
    public String sentMessage() {
        
    while (true) { 
        String optionPicker = JOptionPane.showInputDialog(
            null,
            "Welcome to QuickChat\n" +
            "1. Send Messages\n" +
            "2. Show Recently Sent Messages\n" +
            "3. Quit",
            "QuickChat Menu",
            JOptionPane.QUESTION_MESSAGE
        );

        if (optionPicker == null) return "Exit"; 

        switch (optionPicker) {
            case "1": // Send Messages
                try {
                    int messageCount = Integer.parseInt(JOptionPane.showInputDialog(
                        null, "Enter number of messages to send:",JOptionPane.QUESTION_MESSAGE));
                    
                    for (int i = 0; i < messageCount; i++) {
                        
                        String content = JOptionPane.showInputDialog(null, "Enter message content for message (max of 250 charecters" + (i+1) + ":" , JOptionPane.QUESTION_MESSAGE);
                        String recipient = JOptionPane.showInputDialog(null, "Enter recipient cellphone number" + (i+1) + ":", JOptionPane.QUESTION_MESSAGE);
                        String sender = "User";
                        String messageID = String.valueOf(System.currentTimeMillis());
                        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                        // Create message object
                        Message currentMessage = new Message(messageID,Register_Form.phoneNumber, content, sender, timestamp);

                        // Handle message disposition
                        String choice = JOptionPane.showInputDialog(null,
                            "Message Options for message " + (i+1) + ":\n" +
                            "0. Send Message\n" +
                            "1. Disregard Message\n" +
                            "2. Store Message", JOptionPane.QUESTION_MESSAGE);

                        switch (choice) {
                            case "0": // Send
                                String details = "Message Sent Successfully!\n" +
                                    "Message ID: " + currentMessage.messageID + "\n" +
                                    "Message Hash: " + currentMessage.createMessageHash() + "\n" +
                                    "RecipientID: " + currentMessage.recipient + "\n" +
                                    "Content: " + currentMessage.message;
                                
                                JOptionPane.showMessageDialog(null, details);
                                sentMessages.add(details);
                                totalMessages++;
                                break;
                            
                            case "1": // 
                                JOptionPane.showMessageDialog(null, "Message disregarded", "Disregard",JOptionPane.INFORMATION_MESSAGE);
                                break;
                            
                            case "2": // Store
                                currentMessage.storeMessage();
                                JOptionPane.showMessageDialog(null, "Message stored successfully!", "Store",JOptionPane.INFORMATION_MESSAGE);
                                break;
                            
                            default:
                                JOptionPane.showMessageDialog(null, "Invalid choice - message disregarded","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid number input!","Error",JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "2": 
                   MessageReports();
               
                break;

            case "3": // Quit
                return "Exit";

            default:
                JOptionPane.showMessageDialog(null, "Invalid option selected!","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
    private void displaySenderAndRecipient() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder output = new StringBuilder("Senders and Recipients of Sent Messages:\n");
        for (String msg : sentMessages) {
            
            String[] lines = msg.split("\n");
            String recipientLine = lines[2]; 
            String recipient = recipientLine.split(": ")[1];
            String sender = "User"; // 
            output.append("Sender: ").append(sender).append(", Recipient: ").append(recipient).append("\n");
        }

        JOptionPane.showMessageDialog(null, output.toString(), "Sender & Recipient", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayLongestSentMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages to analyze.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String longest = "";
        for (String msg : sentMessages) {
            String[] lines = msg.split("\n");
            String contentLine = lines[3]; // Content: ...
            String content = contentLine.substring(9);
            if (content.length() > longest.length()) {
                longest = content;
            }
        }

        JOptionPane.showMessageDialog(null, "Longest Sent Message:\n" + longest, "Longest Message", JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchMessageByMessageID() {
        String searchID = JOptionPane.showInputDialog(null, "Enter the Message ID to search:", JOptionPane.QUESTION_MESSAGE);
        if (searchID == null || searchID.isEmpty()) return;

        for (String msg : sentMessages) {
            if (msg.contains("Message ID: " + searchID)) {
                JOptionPane.showMessageDialog(null, msg, "Message Found", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Message ID not found.", "Not Found", JOptionPane.WARNING_MESSAGE);
    }

    
    private void searchMessageByRecipiebt() {
        String searchRecipient = JOptionPane.showInputDialog(null, "Enter the Recipient number to search:", JOptionPane.QUESTION_MESSAGE);
        if (searchRecipient == null || searchRecipient.isEmpty()) return;

        StringBuilder result = new StringBuilder();
        for (String msg : sentMessages) {
            if (msg.contains("RecipientID: " + searchRecipient)) {
                result.append(msg).append("\n\n");
            }
        }

        if (result.length() == 0) {
            JOptionPane.showMessageDialog(null, "No messages found for this recipient.", "Not Found", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, result.toString(), "Messages Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteMessageUsingHash() {
        String hash = JOptionPane.showInputDialog(null, "Enter the Message Hash to delete:", JOptionPane.QUESTION_MESSAGE);
        if (hash == null || hash.isEmpty()) return;

        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).contains("Message Hash: " + hash)) {
                sentMessages.remove(i);
                totalMessages--;
                JOptionPane.showMessageDialog(null, "Message deleted successfully.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                return;
             }
          }

       JOptionPane.showMessageDialog(null, "No message found with the given hash.", "Not Found", JOptionPane.WARNING_MESSAGE);
   }

    
    private void displayFullDetailsal() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages to display.", "Info", JOptionPane.INFORMATION_MESSAGE);
        return;
        }

        StringBuilder allDetails = new StringBuilder("Full Message Details:\n\n");
           for (String msg : sentMessages) {
           allDetails.append(msg).append("\n------------------------\n");
        }

       JOptionPane.showMessageDialog(null, allDetails.toString(), "All Sent Messages", JOptionPane.INFORMATION_MESSAGE);
    }
  
    private void MessageReports(){
    
       String reportsOption =  JOptionPane.showInputDialog(null, "Message Reports \n"
            + "a. Display the sender and recipient of all sent messages \n"
            + "b. Display the longest sent message \n"
            + "c. Search for a message by message ID \n"
            + "d. Search for all messages by recipient \n"
            + "e. Delete a message using the message hash \n"
            + "f. Display a report that lists the full detailos of all the sent messages",JOptionPane.QUESTION_MESSAGE);    
       
        switch(reportsOption){
            
            case "a":
                 displaySenderAndRecipient();
                break;
                
            case "b":
                displayLongestSentMessage();
                break;
                
            case "c":
                 searchMessageByMessageID();
                break;
                
            case "d": 
                searchMessageByRecipiebt();
                break;
                
            case "e":
                 deleteMessageUsingHash();
                break;
                
            case "f":
                displayFullDetailsal();
                break;
             
            default:
                JOptionPane.showMessageDialog(null, "Wrong input entered","ERROR" ,JOptionPane.ERROR_MESSAGE);
                break;
                
        
        }
    }

    
    public String printMessages() {
        return String.join("\n", sentMessages);
    }
    
    public int returnTotalMessages() {
        return totalMessages;
    }
    
    public void storeMessage() {
        JSONArray messagesArray = new JSONArray();
        JSONObject messageObject = new JSONObject();
        
        messageObject.put("MessageID", messageID);
        messageObject.put("MessageHash", createMessageHash());
        messageObject.put("Recipient", recipient);
        messageObject.put("Message", message);
        messagesArray.add(messageObject);
        
        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(messagesArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + createMessageHash() + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + message;
    }
    


}
