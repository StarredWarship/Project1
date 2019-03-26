package com.viit.uaha;


public class DbHandler {

    private String BMI;
    private String height;
    private String weight;
    private String DocumentId;


    public DbHandler() {
        //public no-arg constructor needed
    }

    public DbHandler(String DocumentId, String BMI) {
        this.DocumentId=DocumentId;
        this.BMI = BMI;
    }


    public String getDocumentId(){
        return DocumentId;
    }

    public String getBMI() {
        return BMI;
    }

    public void setHeight(String height){
        this.height=height;
    }
    public void setWeight(String weight){
        this.weight=weight;
    }
    public void setBMI(String BMI){
        this.BMI=BMI;
    }
    public void setDocumentId(String DocumentId){
        this.DocumentId=DocumentId;
    }

    public String getHeight(){
        return height;
    }

    public String getWeight(){
        return weight;
    }

}