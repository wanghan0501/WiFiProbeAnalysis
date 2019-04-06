package edu.cs.scu.entity;

/**
 * author: maicius
 * date: 2019/2/25
 * description:
 */
public class CpwsSim {
    private String file_name;
    private Float node_sim;
    private Float struc_sim;

    @Override
    public String toString() {

        return "CpwsSim{" +
                "file_name=\'" + file_name + "\'" +
                "node_sim=\'" + node_sim + "\'" +
                "struc_sim=\'" + struc_sim + "\'" +
                "}";
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public Float getNode_sim() {
        return node_sim;
    }

    public void setNode_sim(Float node_sim) {
        this.node_sim = node_sim;
    }

    public Float getStruc_sim() {
        return struc_sim;
    }

    public void setStruc_sim(Float struc_sim) {
        this.struc_sim = struc_sim;
    }
}
