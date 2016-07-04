package com.jornada.server.classes;

import com.jornada.ConfigJornada;

public class SaloesServer {
    
    
    public static final String NOME_SALAO_INTERNO = ConfigJornada.getProperty("config.nome.salao.interno");
    public static final int LIMITE_SALAO_INTERNO = Integer.parseInt(ConfigJornada.getProperty("config.limite.salao.interno"));

    public static final String NOME_SALAO_EXTERNO_COBERTO = ConfigJornada.getProperty("config.nome.salao.externo.coberto");
    public static final int LIMITE_SALAO_EXTERNO_COBERTO = Integer.parseInt(ConfigJornada.getProperty("config.limite.salao.externo.coberto"));

    public static final String NOME_SALAO_EXTERNO_ABERTO = ConfigJornada.getProperty("config.nome.salao.externo.aberto");
    public static final int LIMITE_SALAO_EXTERNO_ABERTO = Integer.parseInt(ConfigJornada.getProperty("config.limite.salao.externo.aberto"));

    public static final String NOME_SALAO_CHURRASQUEIRA = ConfigJornada.getProperty("config.nome.salao.churrasqueira");
    public static final int LIMITE_SALAO_CHURRASQUEIRA = Integer.parseInt(ConfigJornada.getProperty("config.limite.salao.churrasqueira"));

}
