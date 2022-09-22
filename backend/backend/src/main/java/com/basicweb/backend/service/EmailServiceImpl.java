package com.basicweb.backend.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailServiceImpl {
	
	public Response sendTextEmail(String email, String password) throws IOException {
		// the sender email should be the same as we used to Create a Single Sender Verification
		    Email from = new Email("vijaykmahesh98@gmail.com");
		    String subject = "Login Credentials";
		    Email to = new Email(email);
		     
		    Content content = new Content("text/plain", " Your EmailId is "+ email +"  Your Password is " + password + " Please login using this credentials ");
		    Mail mail = new Mail(from, subject, to, content);
		
		    SendGrid sg = new SendGrid("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");
		    Request request = new Request();
		    try {
		      request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);	
		      return response;
		    } catch (IOException ex) {
		      throw ex;
		    }	
		    
		    
		    
	}

}
