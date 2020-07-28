package br.unicamp.ft.l201039_l201253;

import java.util.Date;

public class ToDo {

    private String atividade;
    private String categoria;
    private Date notificar;

    public ToDo(String atividade, String categoria, Date notificar)
    {
        this.atividade = atividade;
        this.categoria = categoria;
        this.notificar = notificar;
    }

    public String getAtividade()
    {
        return atividade;
    }

    public String getCategoria()
    {
        return categoria;
    }

    public Date getNotificar()
    {
        return notificar;
    }

}
