package com.jornada.client.content.config;

import com.google.gwt.i18n.client.Messages;

/**
 *
 * @author xwm468
 */
//public class ConfigClient
public interface ConfigClient extends Messages
{
//	
//	String hierarquiaShowCursoEmail();
//	String hierarquiaShowCursoDataNascimento();
//	String hierarquiaShowCursoTelCelular();
//	String hierarquiaShowCursoTelResidencial();
//	
//	String usuarioShowUnidadeEscola();
	
	String lotacaoMaxima();    
	String lotacaoQuaseMaxima();

    String quaseLimiteSalaoExternoCoberto();
    String limiteSalaoExternoCoberto();

    String quaseLimiteSalaoExternoAberto();
    String limiteSalaoExternoAberto();

    String quaseLimiteSalaoChurrasqueira();
    String limiteSalaoChurrasqueira();

    String quaseLimiteSalaoInterno();
    String limiteSalaoInterno();

    
}

