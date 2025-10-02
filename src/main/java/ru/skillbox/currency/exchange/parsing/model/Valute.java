package ru.skillbox.currency.exchange.parsing.model;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"numCode", "charCode", "nominal", "name", "value", "vUnitRate"})
public class Valute {


    private String id;
    private String numCode;
    private String charCode;
    private int nominal;
    private String name;
    private String value;
    private double vUnitRate;

    public Valute() {
    }
    @XmlAttribute(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlElement(name = "NumCode")
    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }
    @XmlElement(name = "CharCode")
    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }
    @XmlElement(name = "Nominal")
    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }
    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlElement(name = "Value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    @XmlElement(name = "VunitRate")
    public double getvUnitRate() {
        return vUnitRate;
    }

    public void setvUnitRate(double vUnitRate) {
        this.vUnitRate = vUnitRate;
    }
}
