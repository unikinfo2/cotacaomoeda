package com.conversormoedas.cotacao.utils;

import org.springframework.beans.BeanUtils;
import com.conversormoedas.cotacao.model.Transacao;

public class AppUtils {

    public static Transacao entityToDto(Transacao tr) {
        Transacao trDto = new Transacao();
        BeanUtils.copyProperties(tr, trDto);
        return trDto;
    }

    public static Transacao dtoToEntity(Transacao trDto) {
        Transacao tr = new Transacao();
        BeanUtils.copyProperties(trDto, tr);
        return tr;
    }
}