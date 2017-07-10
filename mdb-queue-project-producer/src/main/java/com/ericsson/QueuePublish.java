package com.ericsson;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QueuePublish
 */
public class QueuePublish extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueuePublish() {
        super();
        // TODO Auto-generated constructor stub
    }

   //http://localhost:8080/mdb-queue-project/qpublish
    
    @Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/queue/HELLOWORLDMDBQueue")
	private Queue queue;

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
		Connection connection = null;
		Session session = null;
		Destination dest = queue;
		out.write("<p>Sending messages to <em>" + dest + "</em></p>");

		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(dest);
			connection.start();
			TextMessage message = session.createTextMessage();
			java.util.Date date = new java.util.Date();
			message.setText("Message has been produce at: " + date);
			messageProducer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
