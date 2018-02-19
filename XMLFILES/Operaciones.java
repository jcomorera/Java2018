package actividad01;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joanc
 */
@XmlRootElement(name = "OperacionesBancarias")
public class Operaciones {

    private List<Operacion> operacion;

    @XmlElement(name = "operacion")
    public List<Operacion> getOperacions() {
        return operacion;
    }

    public void setOperacions(List<Operacion> operacions) {
        this.operacion = operacions;
    }

    public Operaciones() {
        this.operacion = new ArrayList<>();
    }

    public Operaciones(List<Operacion> operacions) {
        this.operacion = new ArrayList<>();
        this.operacion = operacions;
    }
}
