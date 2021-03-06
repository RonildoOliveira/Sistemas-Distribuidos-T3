package ufc.sd.cind1;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import ufc.sd.cind.config.Config;

public class NewTask {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv)
                      throws java.io.IOException, TimeoutException {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(Config.IP);
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

    String [] argumentos = {"1", "ronildo", "4"};
    String message = getMessage(argumentos);
    
    channel.basicPublish( "", TASK_QUEUE_NAME,
            MessageProperties.PERSISTENT_TEXT_PLAIN,
            message.getBytes());
    System.out.println(" [x] Enviado '" + message + "'");

    channel.close();
    connection.close();
  }      
  
  private static String getMessage(String[] strings){
	    if (strings.length < 1)
	        return "[...]";
	    return joinStrings(strings, " ");
	}
  
  private static String joinStrings(String[] strings, String delimiter) {
	    int length = strings.length;
	    if (length == 0) return "";
	    StringBuilder words = new StringBuilder(strings[0]);
	    for (int i = 1; i < length; i++) {
	        words.append(delimiter).append(strings[i]);
	    }
	    return words.toString();
	}
}

