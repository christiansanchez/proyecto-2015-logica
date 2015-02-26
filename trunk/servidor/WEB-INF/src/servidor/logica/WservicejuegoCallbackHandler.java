
/**
 * WservicejuegoCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package servidor.logica;

    /**
     *  WservicejuegoCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class WservicejuegoCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public WservicejuegoCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public WservicejuegoCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getCargarPartida method
            * override this method for handling normal response from getCargarPartida operation
            */
           public void receiveResultgetCargarPartida(
                    servidor.logica.WservicejuegoStub.GetCargarPartidaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCargarPartida operation
           */
            public void receiveErrorgetCargarPartida(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setPartida method
            * override this method for handling normal response from setPartida operation
            */
           public void receiveResultsetPartida(
                    servidor.logica.WservicejuegoStub.SetPartidaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setPartida operation
           */
            public void receiveErrorsetPartida(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUnirsePartida method
            * override this method for handling normal response from getUnirsePartida operation
            */
           public void receiveResultgetUnirsePartida(
                    servidor.logica.WservicejuegoStub.GetUnirsePartidaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUnirsePartida operation
           */
            public void receiveErrorgetUnirsePartida(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setCargarPartida method
            * override this method for handling normal response from setCargarPartida operation
            */
           public void receiveResultsetCargarPartida(
                    servidor.logica.WservicejuegoStub.SetCargarPartidaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setCargarPartida operation
           */
            public void receiveErrorsetCargarPartida(java.lang.Exception e) {
            }
                


    }
    