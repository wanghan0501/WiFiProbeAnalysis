package edu.cs.scu.entity;

/**
 * author: maicius
 * date: 2018/12/11
 * description:
 */
public class Cpws {
    private String file_name;
    private String ajjbqk;
    private boolean success;
    private String ysf;
    @Override
    public String toString() {
        return "cpws{" +
                "file_name='" + file_name + "\'" +
                ", ajjbqk='" + ajjbqk + "\'" +
                ", ysf='" + ysf + "\'" +
                ", success='" + success + "\'}";
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getAjjbqk() {
        return ajjbqk;
    }

    public void setAjjbqk(String ajjbqk) {
        this.ajjbqk = ajjbqk;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getYsf() {
        return ysf;
    }

    public void setYsf(String ysf) {
        this.ysf = ysf;
    }
}
