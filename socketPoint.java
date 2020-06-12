/**
 * socketPoint.java client program
 * @author Thomas Johnson III
 * 9/25/2019
 * Dr. Dcosta
 * Net-Centric
 * Reference: http://www.buyya.com/java/Chapter13.pdf
 * Reference: https://cs.lmu.edu/~ray/notes/javanetexamples/
 * Reference: https://www.w3schools.com/java/java_user_input.asp
 * Reference: https://way2java.com/networking/chat-program-two-way-communication/
 * Reference: https://www.geeksforgeeks.org/multi-threaded-chat-application-set-1/
 * Reference: https://www.geeksforgeeks.org/multi-threaded-chat-application-set-2/
 * Reference: https://stackoverflow.com/questions/29057774/java-error-send-is-not-abstract-and-does-not-override-abstract-method-run-in
 * Reference: https://stackoverflow.com/questions/32707583/local-variables-referenced-from-an-inner-class-must-be-final-or-effectively-fina
 * Reference: https://www.geeksforgeeks.org/runnable-interface-in-java/
 * Reference: http://forums.devx.com/showthread.php?174141-ERROR-Class-is-not-abstract-and-does-not-override-abstract-method
 * 
 */
//Add thread for receiving only.
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class socketPoint
{
    public static Socket creationForSocket() throws IOException
    {
        Socket chatMeUp = new Socket("localhost", 8000);
        return chatMeUp;
    }

    public static String usernameInput()
    {
	System.out.printf("Please input the name of the user... \n");
	Scanner usernameInputValueTool = new Scanner(System.in);
	String usernameInputValue = usernameInputValueTool.nextLine();
	System.out.printf("The user's name is %s. \n", usernameInputValue);
	return usernameInputValue;
    }

    public static BufferedReader generateReaderobject() throws IOException
    {
        BufferedReader readerObjectConstruct = new BufferedReader(new InputStreamReader(System.in));
        return readerObjectConstruct;
    }

    public static BufferedReader generateRecReaderObject(InputStream receivedData) throws IOException
    {
        BufferedReader readerObjectConstruct = new BufferedReader(new InputStreamReader(receivedData));
        return readerObjectConstruct;
    }

    public static OutputStream generateOutputObject(Socket chatAppClient) throws IOException
    {
        OutputStream outputdata = chatAppClient.getOutputStream();
        return outputdata;
    }

    public static InputStream generateInputObject(Socket chatAppClient) throws IOException
    {
        InputStream inputdata = chatAppClient.getInputStream();
        return inputdata;
    }

    public static PrintWriter generateMessagePrintObject(OutputStream outgoingData) throws IOException
    {
        PrintWriter printWriteObject = new PrintWriter(outgoingData, true);
        return printWriteObject;
    }

    public static void activateClientToChat(BufferedReader messageStoreObject, PrintWriter messageData, BufferedReader retrievedData, String currentUsername) throws IOException
    {
        //String serverResponse;
	String clientResponse;
        messageData.println("\nYou have intiated a chat session with: " + currentUsername +"\n");
        messageData.flush();
        /*if((serverResponse = retrievedData.readLine()) != null)
	    {
		System.out.printf(serverResponse + "\n");
	    }
        */
        Thread captureMessage = new Thread(new Runnable() //Creates a thread with the singular purpose of receiving messages constantly. In one thread, you may only send or receive. Two threads allow for sending as well as receiving.
    {
    @Override
    public void run()
    {
        while(true)
        {
             try 
             {
                 String serverResponse;
                 if((serverResponse = retrievedData.readLine()) != null)
	         {
		     System.out.printf(serverResponse + "\n");
	         }
             }
             catch (IOException error)
             {
                  
             }
        }
    }
    });
        captureMessage.start();
        System.out.println("\nThe introduction has been concluded.\n");

        
        while(true)
        {
            System.out.printf(currentUsername + ": ");
	    clientResponse = currentUsername +": " + messageStoreObject.readLine();
	    messageData.println(clientResponse);
	    messageData.flush();
	    //if((serverResponse = retrievedData.readLine()) != null)
	    //{
		//System.out.printf(serverResponse + "\n");
	    //}
	}
    }
    
    
    public static void main(String Args[]) throws IOException
    {
        Socket chatAppClient = creationForSocket();
        BufferedReader messageStoreObject = generateReaderobject();

        OutputStream outgoingData = generateOutputObject(chatAppClient);
        PrintWriter messageData = generateMessagePrintObject(outgoingData);

        InputStream receivedData = generateInputObject(chatAppClient);
        BufferedReader retrievedData = generateRecReaderObject(receivedData);
	System.out.printf("Now the client of the chat app is booted. Please proceed to enter your name. \n");
        
        String currentUsername = usernameInput();
        
        activateClientToChat(messageStoreObject, messageData, retrievedData, currentUsername);
    }
}
/**
public class socketPointListen implements Runnable
{
    Socket clientConnection;
    BufferedReader retrievedDataFromServer;
    String clientUsername;

    public socketPointListen(Socket clientConnection, BufferedReader retrievedDataFromServer, String clientUsername)
    {
         this.clientConnection = clientConnection;
         this.retrievedDataFromServer = retrievedDataFromServer;
         this.clientUsername = clientUsername;
    }
    public void runClientListener()
    {
        while(true)
        {
             try 
             {
                 if((serverResponse = retrievedData.readLine()) != null)
	         {
		     System.out.printf(serverResponse + "\n");
	         }
             }
             catch (IOException error)
             {
                  
             }
        }
    }
}
**/
