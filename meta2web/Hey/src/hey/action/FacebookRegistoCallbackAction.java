package hey.action;


import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.rmi.RemoteException;
import java.util.Map;


import hey.model.FacebookBean;
import hey.model.HeyBean;
import uc.sd.apis.FacebookApi2;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FacebookRegistoCallbackAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	
	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
	private static final Token EMPTY_TOKEN = null;

	private static final String API_APP_KEY = "574377749961850";
	private static final String API_APP_SECRET = "a462bcb8260e08f74fc485610bc80f25";
    
    public String authorizationUrl;
    private Token accessToken;
    

	private String username;
    private String facebook_id;

	@Override
	public String execute() throws RemoteException {
	    OAuthService service = new ServiceBuilder()
                .provider(FacebookApi2.class)
                .apiKey(API_APP_KEY)
                .apiSecret(API_APP_SECRET)
                .callback("http://localhost:8080/hey/callbackregisto") // Do not change this.
				.scope("public_profile,publish_pages")
                .build();
	    
	    
	    HttpServletRequest servletRequest = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	    
        String code = servletRequest.getParameter("code");
        
        System.out.println(code);
        
        Verifier verifier = new Verifier(code);
         
        accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
        
        
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);
        
       
        service.signRequest(accessToken, request);
        
        Response response = request.send();
        
        
        
        if (response.getCode() == 200)
        {
        		this.setFacebook_id(response.getBody());
        		this.setUsername(response.getBody());
        		
        		/* Verificar se é um metohod o de associação de conta ou método de login */


				String aux = this.getFaceBean().registoFace(username);

			if (aux.startsWith("Servidor Multicast: type | registar ; resultado | success ;"))
			{
				return SUCCESS;
			}
			else
				return ERROR;
		}
        		
        return LOGIN;
	}
	
	
	public HeyBean getHeyBean() throws RemoteException {
		if(!session.containsKey("heyBean"))
			this.setHeyBean(new HeyBean());
		return (HeyBean) session.get("heyBean");
	}
	
	public void setHeyBean(HeyBean heyBean) {
		this.session.put("heyBean", heyBean);
	}
	
	public FacebookBean getFaceBean() {
		return (FacebookBean) session.get("faceBean");
	}


	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) 
	{
		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(username);
			this.username = (String) json.get("name");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public String getFacebook_id() {
		return facebook_id;
	}

	public void setFacebook_id(String facebook_id) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(facebook_id);
			this.facebook_id = (String) json.get("id");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Token getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(Token accessToken) {
		this.accessToken = accessToken;
	}
	
	
	
}