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
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;

@Named
@RequestScoped
public class MessageProducerBean {
  
    @Resource(lookup = "jms/MyQueue") 
    private Queue queue;

    @Resource(lookup = "jms/MyConnectionFactory") 
    private ConnectionFactory connectionFactory;

    private String filter;    
    private String text; 

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getText() { 
        return text;
    }

    public void setText(String text) { 
        this.text = text;
    }

    public void sendMessage() {
        try (JMSContext context = connectionFactory.createContext()) {
            JMSProducer producer = context.createProducer();
            producer.setProperty("filter", filter);
            producer.send(queue, text); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

