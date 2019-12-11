/**
 * Raul Barbosa 2014-11-07
 */
package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;
import hey.model.HeyBean;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null;
	private String palavras = null;

	@Override
	public String execute() throws RemoteException {
		// any username is accepted without confirmation (should check using RMI)
		if(this.username != null && !username.equals("")) {

			String aux =  this.getHeyBean().checkLogin(this.username,this.password);

			if (aux.equals("Servidor Multicast: type | logged ; resultado | success ;")) {
				this.getHeyBean().setUsername(this.username);
				this.getHeyBean().setPassword(this.password);
				session.put("username", username);
				session.put("loggedin", true);
				aux = this.getHeyBean().checkAdmin(username);
				if (aux.contains("success")) {
					session.put("admin",true);
					return "admin";
				}else{
					session.put("admin",false);
					return SUCCESS;
				}

			} else if (aux.contains("fail")) {
				session.put("ERROR_LOG", "wrong username/password");
				System.out.println("\nuser/password incorreto\n");
				return "fail";
			}

		}

		return "fail";
	}
	public String registar() throws RemoteException {
		if(this.username != null && !username.equals("")) {

			String aux = this.getHeyBean().getRegista(this.username,this.password);
			if(aux.startsWith("Servidor Multicast: type | registar ; resultado | success ;")){
				return SUCCESS;
			}
		}
		return "fail";
	}
	public String logout() throws RemoteException {
		this.getHeyBean().disconnect((String) session.get("username"));
		session.clear();

		return SUCCESS;
	}
	public String fail() {

		session.put("ERROR_LOG", "não pode aceder a essa página");

		return SUCCESS;
	}

	public String checkWords() throws RemoteException{
		String tmp = this.getHeyBean().checkWords(palavras);
		String[] tmp_split = tmp.split(":", 2);
		System.out.println("palavras:"+palavras);
		if (tmp.length() == 22) {
			System.out.println("\nNão existem urls com as palavras!");
			return "fail";
		} else {
			System.out.println("\nOs urls são: " + tmp_split[1]);
			session.put("checkWords",tmp_split[1]);
			return "success";

		}
	}

	public void setPalavras(String palavras) {
		this.palavras = palavras; // will you sanitize this input? maybe use a prepared statement?
	}

	public void setUsername(String username) {
		this.username = username; // will you sanitize this input? maybe use a prepared statement?
	}

	public void setPassword(String password) {
		this.password = password; // what about this input? 
	}
	
	public HeyBean getHeyBean() throws RemoteException {

		if(!session.containsKey("heyBean"))
			this.setHeyBean(new HeyBean());
		return (HeyBean) session.get("heyBean");
	}

	public void setHeyBean(HeyBean heyBean) {
		this.session.put("heyBean", heyBean);
	}


	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
