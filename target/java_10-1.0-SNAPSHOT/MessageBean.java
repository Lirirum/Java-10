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
import jakarta.jms.Queue;

@Named
@RequestScoped
public class MessageBean {
    private String filter;
    private String text;

    @Resource(lookup = "java:/jms/queue/myQueue") // Замініть на реальну назву черги
    private Queue queue;

    @Resource(lookup = "java:/ConnectionFactory") // Замініть на реальний ConnectionFactory
    private ConnectionFactory connectionFactory;

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
            context.createProducer()
                   .setProperty("filter", filter) // Додаємо фільтр
                   .send(queue, text); // Відправляємо текст повідомлення
        }
    }
}
