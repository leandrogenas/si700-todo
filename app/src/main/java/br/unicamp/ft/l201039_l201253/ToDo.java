package br.unicamp.ft.l201039_l201253;

import java.util.Date;

public class ToDo {

    private String id;
    private String atividade;
    private String categoria;
    private String notificar;

    public ToDo(String id, String atividade, String categoria, String notificar)
    {
        this.id = id;
        this.atividade = atividade;
        this.categoria = categoria;
        this.notificar = notificar;
    }

    public String getId(){ return id; }

    public String getAtividade()
    {
        return atividade;
    }

    public String getCategoria()
    {
        return categoria;
    }

    public String getNotificar()
    {
        return notificar;
    }

}
