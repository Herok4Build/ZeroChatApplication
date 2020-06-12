/**
 * socketSource.java client program
 * @author Thomas Johnson III
 * 9/25/2019
 * Dr. Dcosta
 * Net-Centric
 * Reference: http://www.buyya.com/java/Chapter13.pdf
 * Reference: https://cs.lmu.edu/~ray/notes/javanetexamples/
 * Reference: https://www.w3schools.com/java/java_user_input.asp
 * Reference: https://way2java.com/networking/chat-program-two-way-communication/
 * Reference: https://stackoverflow.com/questions/29057774/java-error-send-is-not-abstract-and-does-not-override-abstract-method-run-in
 * Reference: https://stackoverflow.com/questions/32707583/local-variables-referenced-from-an-inner-class-must-be-final-or-effectively-fina
 * Reference: https://www.geeksforgeeks.org/runnable-interface-in-java/
 * Reference: http://forums.devx.com/showthread.php?174141-ERROR-Class-is-not-abstract-and-does-not-override-abstract-method
 */
//Add thread for receiving only
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class socketSource
{
    public static ServerSocket creationForSocket() throws IOException
    {
        ServerSocket chatMeUp = new ServerSocket(8000);
        return chatMeUp;
    }

    public static Socket activationOfSocket(ServerSocket chatAppServer) throws IOException
    {
        Socket connectionAcceptance = chatAppServer.accept();
        return connectionAcceptance;
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
        String serverResponse;
        messageData.println("You have intiated a chat session with: " + currentUsername+ "\n");
        messageData.flush();
        //if((clientResponse = retrievedData.readLine()) != null)
	    //{
		//System.out.printf(clientResponse + "\n");
	    //}
        Thread captureMessage = new Thread(new Runnable()
    {
    @Override
    public void run()
    {
        while(true)
        {
             try 
             {
                 String clientResponse;
                 if((clientResponse = retrievedData.readLine()) != null)
	         {
		     System.out.printf(clientResponse + "\n");
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
	    //if((clientResponse = retrievedData.readLine()) != null)
	    //{
		//System.out.printf(clientResponse + "\n");
	    //}
            System.out.printf(currentUsername + ": ");
	    serverResponse = currentUsername +": " + messageStoreObject.readLine();
	    messageData.println(serverResponse);
	    messageData.flush();
	    
	}
    }
    public static void main(String Args[]) throws IOException
    {
        ServerSocket chatAppServer = creationForSocket();
        Socket chatAppServerAcceptance = activationOfSocket(chatAppServer);
        System.out.printf("The socket has been initiated. \n");
        BufferedReader messageStoreObject = generateReaderobject();

        OutputStream outgoingData = generateOutputObject(chatAppServerAcceptance);
        PrintWriter messageData = generateMessagePrintObject(outgoingData);

        InputStream receivedData = generateInputObject(chatAppServerAcceptance);
        BufferedReader retrievedData = generateRecReaderObject(receivedData);
	System.out.printf("Now the client of the chat app is booted. Please proceed to enter your name. \n");
        
        String currentUsername = usernameInput();
        
        activateClientToChat(messageStoreObject, messageData, retrievedData, currentUsername);
    }
}
