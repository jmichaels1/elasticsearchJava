package Entity;

/**
 * Created by Michael
 */
public class Sesion {

    private String sesionID;
    private String date;
    private String asignatura;
    private String contenido;
    private String descripcion;

    /**
     * Método Constructor
     * @param sesionID
     * @param date
     * @param asignatura
     * @param contenido
     */
    public Sesion(String sesionID, String date, String asignatura, String contenido,
                  String descripcion) {
        this.sesionID = sesionID;
        this.date = date;
        this.asignatura = asignatura;
        this.contenido = contenido;
    }

    /*** Getters and Setters ***/

    public String getSesionID() {
        return sesionID;
    }

    public void setSesionID(String sesionID) {
        this.sesionID = sesionID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sesion sesion = (Sesion) o;

        return sesionID != null ? sesionID.equals(sesion.sesionID) : sesion.sesionID == null;
    }

    @Override
    public int hashCode() {
        return sesionID != null ? sesionID.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Sesion{" +
                "sesionID='" + sesionID + '\'' +
                ", date='" + date + '\'' +
                ", asignatura='" + asignatura + '\'' +
                ", contenido='" + contenido + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
