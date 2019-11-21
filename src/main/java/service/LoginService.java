package service;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class LoginService {

    public static WebClient autoLogin(String url, String password) throws FailingHttpStatusCodeException, IOException {

        System.out.println("Starting autoLogin on " + url);

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        HtmlPage page = client.getPage(url);
        HtmlInput inputPassword = page.getFirstByXPath("//input[@type='password']");
        inputPassword.setValueAttribute(password);

        //get the enclosing form
        HtmlForm loginForm = inputPassword.getEnclosingForm();

        //submit the form
        client.getPage(loginForm.getWebRequest(null));

        //returns the cookies filled client :)
        return client;
    }
}
