
/**
 * FachadaExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package servidor.excepciones;


public class FachadaExceptionException0 extends java.lang.Exception{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private servidor.logica.WservicejuegoStub.FachadaExceptionE faultMessage;
    
    public FachadaExceptionException0() {
        super("FachadaExceptionException0");
    }
           
    public FachadaExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public FachadaExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(servidor.logica.WservicejuegoStub.FachadaExceptionE msg){
       faultMessage = msg;
    }
    
    public servidor.logica.WservicejuegoStub.FachadaExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    