package stockmann.com.soalogger.ws.client;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 101221.1153.15811)

@WebServiceClient(wsdlLocation="http://hiphiphurax:8080/SoaloggerService/SoaloggerServiceService?WSDL",
  targetNamespace="http://soaloggerservice.web.logging.soa.stockmann/",
  name="SoaloggerServiceService")
public class SoaloggerServiceService
  extends Service
{
  private static URL wsdlLocationURL;

  private static Logger logger;
  static
  {
    try
    {
      logger = Logger.getLogger("stockmann.com.soalogger.ws.client.SoaloggerServiceService");
      URL baseUrl = SoaloggerServiceService.class.getResource(".");
      if (baseUrl == null)
      {
        wsdlLocationURL =
            SoaloggerServiceService.class.getResource("http://hiphiphurax:8080/SoaloggerService/SoaloggerServiceService?WSDL");
        if (wsdlLocationURL == null)
        {
          baseUrl = new File(".").toURL();
          wsdlLocationURL =
              new URL(baseUrl, "http://hiphiphurax:8080/SoaloggerService/SoaloggerServiceService?WSDL");
        }
      }
      else
      {
                if (!baseUrl.getPath().endsWith("/")) {
         baseUrl = new URL(baseUrl, baseUrl.getPath() + "/");
}
                wsdlLocationURL =
            new URL(baseUrl, "http://hiphiphurax:8080/SoaloggerService/SoaloggerServiceService?WSDL");
      }
    }
    catch (MalformedURLException e)
    {
      logger.log(Level.ALL,
          "Failed to create wsdlLocationURL using http://hiphiphurax:8080/SoaloggerService/SoaloggerServiceService?WSDL",
          e);
    }
  }

  public SoaloggerServiceService()
  {
    super(wsdlLocationURL,
          new QName("http://soaloggerservice.web.logging.soa.stockmann/",
                    "SoaloggerServiceService"));
  }

  public SoaloggerServiceService(URL wsdlLocation, QName serviceName)
  {
    super(wsdlLocation, serviceName);
  }

  @WebEndpoint(name="SoaloggerServicePort")
  public stockmann.com.soalogger.ws.client.SoaloggerService getSoaloggerServicePort()
  {
    return (stockmann.com.soalogger.ws.client.SoaloggerService) super.getPort(new QName("http://soaloggerservice.web.logging.soa.stockmann/",
                                                                                        "SoaloggerServicePort"),
                                                                              stockmann.com.soalogger.ws.client.SoaloggerService.class);
  }

  @WebEndpoint(name="SoaloggerServicePort")
  public stockmann.com.soalogger.ws.client.SoaloggerService getSoaloggerServicePort(WebServiceFeature... features)
  {
    return (stockmann.com.soalogger.ws.client.SoaloggerService) super.getPort(new QName("http://soaloggerservice.web.logging.soa.stockmann/",
                                                                                        "SoaloggerServicePort"),
                                                                              stockmann.com.soalogger.ws.client.SoaloggerService.class,
                                                                              features);
  }
}
