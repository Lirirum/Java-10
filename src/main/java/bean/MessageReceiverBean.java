/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author lirir
 */
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;

@Named
@RequestScoped
public class MessageReceiverBean {
    @Resource(lookup = "jms/MyQueue")   
    private Queue queue;

    @Resource(lookup = "jms/MyConnectionFactory")
    private ConnectionFactory connectionFactory;

    private String lastMessage;
     
     public String getLastMessage() {
        return lastMessage;
    }

    public void receiveMessage() {
        try (JMSContext context = connectionFactory.createContext()) {
            JMSConsumer consumer = context.createConsumer(queue);
            Message message = consumer.receive(5000);
            if (message instanceof TextMessage) {
                lastMessage = ((TextMessage) message).getText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
