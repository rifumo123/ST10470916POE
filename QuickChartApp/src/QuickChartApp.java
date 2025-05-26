import javax.swing.JOptionPane;

public class QuickChartApp {
    
   public static void main(String[] args) {
       boolean loggedIn = login();

       if (loggedIn) {
           JOptionPane.showMessageDialog(null, "Login failed. Exiting.");
            return;
       }
       
       JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        int messageLimit = Integer.parseInt(JOptionPane.showInputDialog("how many messages would you like to enter"));
        int sentCount = 0;
        
        while (true) {
            String input = JOptionPane.showInputDialog(null, "Message limit reached."+
                "choose an optoin:\nl) Send Message\n2) Show recently sent message\n3 Quit");
            if (input == null) break;
            int option = Integer.parseInt(input);
    
            switch (option) {
                case 1:
                    if (sentCount >= messageLimit) {
                        JOptionPane.showConfirmDialog(null, "messageLimit reached.");
                         break;
                    }
                    
                    String recipient =JOptionPane.showInputDialog("Enter recipient phone number (+countrycode...): ");
                    String message = JOptionPane.showInputDialog("Enter message (max 250 chara)");
            
                    if (message.length()>250) {
                        JOptionPane.showMessageDialog(null,
                           "message exceed 250 characters by " +(message.length() - 250) + " plealse redue site.");
                        break;
                    }
            
                    Message_1 msg = new Message_1(recipient, message);
            
                    if (msg.checkRecipientCell()) {
                       JOptionPane.showConfirmDialog(null,
                          "Cellphone number is correct is incoorectly formatted or missig internatiom");
                     break;
                    }
            
                    String[] options = {"Send message", "Discard Message" , "Store Message"};
                    int choice = JOptionPane.showOptionDialog(null,"Choose an action :" ,"Send Message",
                          JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null, options, options[0]);
                 
                    String actionResult = (" ");
                    switch (choice) {
                        case 0:
                             actionResult = msg.sentMessage("send");
                            sentCount++;
                        break;
                        case 1:
                        actionResult = msg.sentMessage("discard");
                        break;
                        case 2:
                        actionResult = msg.sentMessage("stoe");
                        break;
                        default:
                        actionResult = "No action taken. ";
                   
                    }    
           
                    JOptionPane.showMessageDialog(null,
                      msg.printMessageDetails() + "\n\nAction: " + actionResult);
                    break;
            
                case 2:
                    JOptionPane.showMessageDialog(null, "Coming soon. ");
                    break;
            
                case 3:
                    JOptionPane.showMessageDialog(null,
                       "Total Message Sent: " + Message_1.returnTotalMessages()+ "\nExiting QuickChart)");
                    System.exit(0);
                    break;
            
                default:
                     JOptionPane.showMessageDialog(null, "Invalid option.Please choose 1, 2, 3.");
            }
        }   
    }     
    

    private static boolean login() {
        String username = JOptionPane.showInputDialog("Enter usernsame:");
        String password = JOptionPane.showInputDialog("Enter password:");
       Object unsername = null;
       Object pswd = null;
         
         return unsername != null && password != null && username.equals("admin") && password.equals("pass123");
    }  

    private static int length() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class password {

        public password() {
        }
    }
}