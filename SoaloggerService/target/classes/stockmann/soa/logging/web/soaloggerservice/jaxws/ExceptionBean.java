
package stockmann.soa.logging.web.soaloggerservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3-b02-
 * Generated source version: 2.1.3
 * 
 */
@XmlRootElement(name = "Exception", namespace = "http://soaloggerservice.web.logging.soa.stockmann/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Exception", namespace = "http://soaloggerservice.web.logging.soa.stockmann/")
public class ExceptionBean {

    private String message;

    /**
     * 
     * @return
     *     returns String
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 
     * @param message
     *     the value for the message property
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
