package model;

/**
 * Created by Michael
 */
public class Master {

    private String masterID;
    private String master;
    private String descripcion;

    /**
     * Método constructor 1
     * @param masterID
     * @param master
     * @param descripcion
     */
    public Master(String masterID, String master, String descripcion) {
        this.masterID = masterID;
        this.master = master;
        this.descripcion = descripcion;
    }

    public Master(String masterID, String master) {
        this.masterID = masterID;
        this.master = master;
    }

    /**
     * Método constructor 2
     * @return
     */
    public String getMasterID() {
        return masterID;
    }

    public void setMasterID(String masterID) {
        this.masterID = masterID;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
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

        Master master = (Master) o;

        return masterID != null ? masterID.equals(master.masterID) : master.masterID == null;
    }

    @Override
    public int hashCode() {
        return masterID != null ? masterID.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Master{" +
                "masterID='" + masterID + '\'' +
                ", master='" + master + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
