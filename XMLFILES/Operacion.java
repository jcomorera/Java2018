package actividad01;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author joanc
 */
@XmlRootElement
@XmlType(propOrder = {"numcuenta", "propietario", "tipo", "cantidad", "saldo"})
public class Operacion {

    public Operacion() {
    }

    @XmlAttribute
    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    @XmlElement
    public String getNumcuenta() {
        return numcuenta;
    }

    public void setNumcuenta(String numcuenta) {
        this.numcuenta = numcuenta;
    }

    @XmlElement
    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    @XmlElement
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlElement
    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @XmlElement
    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    private String fechahora = null;
    private String numcuenta = null;
    private String propietario = null;
    private String tipo = null;
    private String cantidad = null;
    private String saldo = null;

    //Constructor de la clase operacion con parametros.
    public Operacion(String fechahora, String numcuenta, String propietario,
            String tipo, String cantidad, String saldo) {
        this.fechahora = fechahora;
        this.numcuenta = numcuenta;
        this.propietario = propietario;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.saldo = saldo;
    }
}
