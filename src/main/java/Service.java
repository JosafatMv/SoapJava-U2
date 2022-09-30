import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;
import java.util.Random;

@WebService(name = "Service", targetNamespace = "utez")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Service {

    Random random = new Random();
    int randomNumber = random.nextInt(2);

    @WebMethod(operationName = "responseMessage")
    public String responseMessage(@WebParam(name = "message") String message){
        return "El mensaje recibido fue "+ message;
    }

    @WebMethod(operationName = "randomNumber")
    public String randomNumber(@WebParam(name = "number") int number){

        if (randomNumber != number){
            return "Numeros diferentes";
        }

        return "Adivinaste el número";
    }

    @WebMethod(operationName = "consonants")
    public String consonants(@WebParam(name = "message") String message){
        message = message.replaceAll("a","");
        message = message.replaceAll("e","");
        message = message.replaceAll("i","");
        message = message.replaceAll("o","");
        message = message.replaceAll("u","");
        return message;
    }

    @WebMethod(operationName = "getRfc")
    public String getRfc(@WebParam(name = "name") String name, @WebParam(name = "apellidoP") String apellidoP, @WebParam(name = "apellidoM") String apellidoM, @WebParam(name = "dateBirth") String dateBirth){
        String rfcName = "" + apellidoP.charAt(0) + apellidoP.charAt(1) + apellidoM.charAt(0) + name.charAt(0);
        String datePart = dateBirth.substring(2, 4) + dateBirth.substring(5, 7) + dateBirth.substring(8, 10);

        String alphanumerics = "abcdefghijklmnopqrstuvwxyz1234567890";
        String clave = "";
        for (int i = 0; i < 3; i++) {
            int randomNumber = (int) (Math.random() * (alphanumerics.length()) - 1);
            clave += alphanumerics.charAt(randomNumber);
        }
        String rfc = (rfcName + datePart + clave + "").toUpperCase();
        return rfc;
    }

    public static void main(String[] args) {
        System.out.println("Inicializando server...");
        Endpoint.publish("http://localhost:8081/Service", new Service());
        System.out.println("Waiting requests...");
    }

}
