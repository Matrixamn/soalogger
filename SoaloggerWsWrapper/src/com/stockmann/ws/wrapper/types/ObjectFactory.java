
package com.stockmann.ws.wrapper.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.stockmann.ws.wrapper.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddLogEntryValues_QNAME = new QName("http://soaloggerservice.web.logging.soa.stockmann/", "addLogEntryValues");
    private final static QName _AddLogMessageResponse_QNAME = new QName("http://soaloggerservice.web.logging.soa.stockmann/", "addLogMessageResponse");
    private final static QName _AddLogEntryValuesResponse_QNAME = new QName("http://soaloggerservice.web.logging.soa.stockmann/", "addLogEntryValuesResponse");
    private final static QName _AddLogInterfaceEntry_QNAME = new QName("http://soaloggerservice.web.logging.soa.stockmann/", "addLogInterfaceEntry");
    private final static QName _AddLogInterfaceEntryResponse_QNAME = new QName("http://soaloggerservice.web.logging.soa.stockmann/", "addLogInterfaceEntryResponse");
    private final static QName _Exception_QNAME = new QName("http://soaloggerservice.web.logging.soa.stockmann/", "Exception");
    private final static QName _GetInterfaceFields_QNAME = new QName("http://soaloggerservice.web.logging.soa.stockmann/", "getInterfaceFields");
    private final static QName _AddLogMessage_QNAME = new QName("http://soaloggerservice.web.logging.soa.stockmann/", "addLogMessage");
    private final static QName _GetInterfaceFieldsResponse_QNAME = new QName("http://soaloggerservice.web.logging.soa.stockmann/", "getInterfaceFieldsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.stockmann.ws.wrapper.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link AddLogInterfaceEntryResponse }
     * 
     */
    public AddLogInterfaceEntryResponse createAddLogInterfaceEntryResponse() {
        return new AddLogInterfaceEntryResponse();
    }

    /**
     * Create an instance of {@link AddLogInterfaceEntry }
     * 
     */
    public AddLogInterfaceEntry createAddLogInterfaceEntry() {
        return new AddLogInterfaceEntry();
    }

    /**
     * Create an instance of {@link AddLogEntryValuesResponse }
     * 
     */
    public AddLogEntryValuesResponse createAddLogEntryValuesResponse() {
        return new AddLogEntryValuesResponse();
    }

    /**
     * Create an instance of {@link GetInterfaceFieldsResponse }
     * 
     */
    public GetInterfaceFieldsResponse createGetInterfaceFieldsResponse() {
        return new GetInterfaceFieldsResponse();
    }

    /**
     * Create an instance of {@link AddLogMessage }
     * 
     */
    public AddLogMessage createAddLogMessage() {
        return new AddLogMessage();
    }

    /**
     * Create an instance of {@link GetInterfaceFields }
     * 
     */
    public GetInterfaceFields createGetInterfaceFields() {
        return new GetInterfaceFields();
    }

    /**
     * Create an instance of {@link AddLogEntryValues }
     * 
     */
    public AddLogEntryValues createAddLogEntryValues() {
        return new AddLogEntryValues();
    }

    /**
     * Create an instance of {@link AddLogMessageResponse }
     * 
     */
    public AddLogMessageResponse createAddLogMessageResponse() {
        return new AddLogMessageResponse();
    }

    /**
     * Create an instance of {@link LogMsgTo }
     * 
     */
    public LogMsgTo createLogMsgTo() {
        return new LogMsgTo();
    }

    /**
     * Create an instance of {@link LogFieldTo }
     * 
     */
    public LogFieldTo createLogFieldTo() {
        return new LogFieldTo();
    }

    /**
     * Create an instance of {@link LogEntryTo }
     * 
     */
    public LogEntryTo createLogEntryTo() {
        return new LogEntryTo();
    }

    /**
     * Create an instance of {@link EntryValueTo }
     * 
     */
    public EntryValueTo createEntryValueTo() {
        return new EntryValueTo();
    }

    /**
     * Create an instance of {@link LogInterfaceTo }
     * 
     */
    public LogInterfaceTo createLogInterfaceTo() {
        return new LogInterfaceTo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddLogEntryValues }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaloggerservice.web.logging.soa.stockmann/", name = "addLogEntryValues")
    public JAXBElement<AddLogEntryValues> createAddLogEntryValues(AddLogEntryValues value) {
        return new JAXBElement<AddLogEntryValues>(_AddLogEntryValues_QNAME, AddLogEntryValues.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddLogMessageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaloggerservice.web.logging.soa.stockmann/", name = "addLogMessageResponse")
    public JAXBElement<AddLogMessageResponse> createAddLogMessageResponse(AddLogMessageResponse value) {
        return new JAXBElement<AddLogMessageResponse>(_AddLogMessageResponse_QNAME, AddLogMessageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddLogEntryValuesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaloggerservice.web.logging.soa.stockmann/", name = "addLogEntryValuesResponse")
    public JAXBElement<AddLogEntryValuesResponse> createAddLogEntryValuesResponse(AddLogEntryValuesResponse value) {
        return new JAXBElement<AddLogEntryValuesResponse>(_AddLogEntryValuesResponse_QNAME, AddLogEntryValuesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddLogInterfaceEntry }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaloggerservice.web.logging.soa.stockmann/", name = "addLogInterfaceEntry")
    public JAXBElement<AddLogInterfaceEntry> createAddLogInterfaceEntry(AddLogInterfaceEntry value) {
        return new JAXBElement<AddLogInterfaceEntry>(_AddLogInterfaceEntry_QNAME, AddLogInterfaceEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddLogInterfaceEntryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaloggerservice.web.logging.soa.stockmann/", name = "addLogInterfaceEntryResponse")
    public JAXBElement<AddLogInterfaceEntryResponse> createAddLogInterfaceEntryResponse(AddLogInterfaceEntryResponse value) {
        return new JAXBElement<AddLogInterfaceEntryResponse>(_AddLogInterfaceEntryResponse_QNAME, AddLogInterfaceEntryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaloggerservice.web.logging.soa.stockmann/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInterfaceFields }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaloggerservice.web.logging.soa.stockmann/", name = "getInterfaceFields")
    public JAXBElement<GetInterfaceFields> createGetInterfaceFields(GetInterfaceFields value) {
        return new JAXBElement<GetInterfaceFields>(_GetInterfaceFields_QNAME, GetInterfaceFields.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddLogMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaloggerservice.web.logging.soa.stockmann/", name = "addLogMessage")
    public JAXBElement<AddLogMessage> createAddLogMessage(AddLogMessage value) {
        return new JAXBElement<AddLogMessage>(_AddLogMessage_QNAME, AddLogMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInterfaceFieldsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaloggerservice.web.logging.soa.stockmann/", name = "getInterfaceFieldsResponse")
    public JAXBElement<GetInterfaceFieldsResponse> createGetInterfaceFieldsResponse(GetInterfaceFieldsResponse value) {
        return new JAXBElement<GetInterfaceFieldsResponse>(_GetInterfaceFieldsResponse_QNAME, GetInterfaceFieldsResponse.class, null, value);
    }

}
