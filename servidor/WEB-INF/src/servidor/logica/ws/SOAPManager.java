package servidor.logica.ws;

import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class SOAPManager {	
	private String urlSoapWS;
	SOAPConnectionFactory soapConnectionFactory;
			
	public SOAPManager(String url) throws UnsupportedOperationException, SOAPException{
		this.urlSoapWS = url;		
		this.soapConnectionFactory = SOAPConnectionFactory.newInstance();
		
	}
	
	private SOAPConnection createConnection() throws SOAPException {
		SOAPConnection soapConnection = this.soapConnectionFactory.createConnection();
		return soapConnection;
	}
	
	private String responseToString(SOAPMessage soapMesage){
		return "";
	}
	
	public String setGuardarPartida(String dataJuego) throws SOAPException, IOException{
		MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("wservicejuego", "http://logica.servidor");
                
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("setGuardarPartidaRequest", "wservicejuego");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("parameters", "wservicejuego"); 
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("dataJuego", "wservicejuego");        
        soapBodyElem2.addTextNode(dataJuego);
        
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", this.urlSoapWS  + "setGuardarPartidaRequest");
        soapMessage.saveChanges();
        soapMessage.writeTo(System.out);
        SOAPConnection connection = this.createConnection();
		SOAPMessage soapResponse = connection.call(soapMessage, this.urlSoapWS);		
		return this.responseToString(soapResponse);
	}	
	
	public String setCargarPartida(String dataJuego) throws SOAPException, IOException{
		MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("wservicejuego", "http://logica.servidor");
                
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("setCargarPartidaRequest", "wservicejuego");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("parameters", "wservicejuego"); 
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("dataJuego", "wservicejuego");        
        soapBodyElem2.addTextNode(dataJuego);
        
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", this.urlSoapWS  + "setCargarPartidaRequest");
        soapMessage.saveChanges();
        soapMessage.writeTo(System.out);
        SOAPConnection connection = this.createConnection();
		SOAPMessage soapResponse = connection.call(soapMessage, this.urlSoapWS);		
		return this.responseToString(soapResponse);
	}	

	public String getCargarPartida() throws SOAPException, IOException{
		
		MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("wservicejuego", this.urlSoapWS);

        SOAPBody soapBody = envelope.getBody();
        soapBody.addChildElement("getCargarPartidaRequest", "wservicejuego");                
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", this.urlSoapWS  + "getCargarPartida");
        soapMessage.saveChanges();
        SOAPConnection connection = this.createConnection();
		SOAPMessage soapResponse = connection.call(soapMessage, this.urlSoapWS);
		
		return this.responseToString(soapResponse);		
	}
	
	public String getUnirsePartida(String dataJuego) throws SOAPException, IOException{
		MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("wservicejuego", "http://logica.servidor");
                
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("getUnirsePartidaRequest", "wservicejuego");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("parameters", "wservicejuego"); 
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("dataJuego", "wservicejuego");        
        soapBodyElem2.addTextNode(dataJuego);
        
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", this.urlSoapWS  + "getUnirsePartidaRequest");
        soapMessage.saveChanges();
        soapMessage.writeTo(System.out);
        SOAPConnection connection = this.createConnection();
		SOAPMessage soapResponse = connection.call(soapMessage, this.urlSoapWS);		
		return this.responseToString(soapResponse);
	}
    
}
