package com.example.projectone.Databases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Entity Means Table
@Entity
public class ProjectTable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "ProjectName")
    private String ProjectName;

    @ColumnInfo(name = "Quantity")
    private String Quantity;

    @ColumnInfo(name = "Item")
    private String Item;

    @ColumnInfo(name = "OPlus")
    private String OPlus;

    @ColumnInfo(name = "V")
    private String V;

    @ColumnInfo(name = "VA")
    private String VA;

    @ColumnInfo(name = "A")
    private String A;

    @ColumnInfo(name = "P")
    private String P;

    @ColumnInfo(name = "AT")
    private String AT;

    @ColumnInfo(name = "AF")
    private String AF;

    @ColumnInfo(name = "SNUM")
    private String SNUM;

    @ColumnInfo(name = "SMM")
    private String SMM;

    @ColumnInfo(name = "STYPE")
    private String STYPE;

    @ColumnInfo(name = "GNUM")
    private String GNUM;

    @ColumnInfo(name = "GMM")
    private String GMM;

    @ColumnInfo(name = "GTYPE")
    private String GTYPE;

    @ColumnInfo(name = "MMPlus")
    private String MMPlus;

    @ColumnInfo(name = "CTYPE")
    private String CTYPE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getOPlus() {
        return OPlus;
    }

    public void setOPlus(String OPlus) {
        this.OPlus = OPlus;
    }

    public String getV() {
        return V;
    }

    public void setV(String v) {
        V = v;
    }

    public String getVA() {
        return VA;
    }

    public void setVA(String VA) {
        this.VA = VA;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getP() {
        return P;
    }

    public void setP(String p) {
        P = p;
    }

    public String getAT() {
        return AT;
    }

    public void setAT(String AT) {
        this.AT = AT;
    }

    public String getAF() {
        return AF;
    }

    public void setAF(String AF) {
        this.AF = AF;
    }

    public String getSNUM() {
        return SNUM;
    }

    public void setSNUM(String SNUM) {
        this.SNUM = SNUM;
    }

    public String getSMM() {
        return SMM;
    }

    public void setSMM(String SMM) {
        this.SMM = SMM;
    }

    public String getSTYPE() {
        return STYPE;
    }

    public void setSTYPE(String STYPE) {
        this.STYPE = STYPE;
    }

    public String getGNUM() {
        return GNUM;
    }

    public void setGNUM(String GNUM) {
        this.GNUM = GNUM;
    }

    public String getGMM() {
        return GMM;
    }

    public void setGMM(String GMM) {
        this.GMM = GMM;
    }

    public String getGTYPE() {
        return GTYPE;
    }

    public void setGTYPE(String GTYPE) {
        this.GTYPE = GTYPE;
    }

    public String getMMPlus() {
        return MMPlus;
    }

    public void setMMPlus(String MMPlus) {
        this.MMPlus = MMPlus;
    }

    public String getCTYPE() {
        return CTYPE;
    }

    public void setCTYPE(String CTYPE) {
        this.CTYPE = CTYPE;
    }
}
