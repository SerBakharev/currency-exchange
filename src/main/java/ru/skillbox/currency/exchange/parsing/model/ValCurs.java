package ru.skillbox.currency.exchange.parsing.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "ValCurs")
@XmlType(propOrder = {"valuteList"})
public class ValCurs {

    private List<Valute> valuteList;

    public ValCurs() {
    }

    @XmlElement(name = "Valute") // Сопоставляется с каждым элементом списка
    public List<Valute> getValuteList() {
        return valuteList;
    }

    public void setValuteList(List<Valute> valuteList) {
        this.valuteList = valuteList;
    }



}
