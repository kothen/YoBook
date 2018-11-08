package com.haomai.ywj.yobook.bean;

/**
 * Created by Administrator on 2018/10/30.
 */

public class FileBean {
    String fileName;
    String filePath;

    public FileBean( String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }
    public String getFilePath(){
        return  filePath;
    }
}
