package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;
import com.jornada.shared.classes.pesquisasatisfacao.DadoServicoTrend;

public class AvaliacaoServer {
    
    public static String strAlterarTextoTempo="<alterar-texto-tempo>"; 

    public static String DB_INSERT_AVALIACAO = "INSERT INTO avaliacao( " + "rest_moquem, ambiente, atendimento, qualidade,  " + "espaco_kids, cozinha, recomendaria_rest, como_conheceu_rest,  " + "voltaria_rest, cidade, data, email, telefone, obs, atendente) " + "VALUES (?, ?, ?, ?,  " + "?, ?, ?, ?,  " + "?, ?, ?, ?, ?, ?,?); ";
//    public static String DB_UPDATE_RESERVA = "UPDATE reserva " + "SET nome_reserva=?, numero_adultos=?, numero_criancas=?, " + "telefone=?, cidade=?, data=?, horario=?, turno=?, observacao=?, chegou=?, salao=?, mesa=? " + "WHERE id_reserva=?;";

    public static String DB_SELECT_GRAFICO_COLUNA_CIDADE = "select count(*) as counter, cidade from avaliacao where data>=? and data<=? group by cidade order by counter desc";
    public static String DB_SELECT_GRAFICO_COLUNA_CIDADE_FILTRO_CIDADE = "select count(*) as counter, cidade from avaliacao where cidade=? and data>=? and data<=? group by cidade order by counter desc";
//    public static String DB_SELECT_GRAFICO_LINHA_CIDADE = "select count(*) as counter, cidade, data from avaliacao where data>='2017-07-09' and data<='2017-07-09' group by cidade, data order by data asc, counter desc";
    
    public static String DB_SELECT_GRAFICO_COLUNA_OBS = "select count(*) as counter,rec_grupo_primario, rec_grupo_secundario from avaliacao where data>=? and data<=? and obs!='' group by rec_grupo_primario, rec_grupo_secundario order by counter desc";
    public static String DB_SELECT_GRAFICO_COLUNA_OBS_FILTRO_CIDADE = "select count(*) as counter,rec_grupo_primario, rec_grupo_secundario from avaliacao where cidade=? and data>=? and data<=? and obs!='' group by rec_grupo_primario, rec_grupo_secundario order by counter desc";
    
    public static String DB_SELECT_AVALIACAO_CIDADE = "select cidade from avaliacao group by cidade order by cidade asc;";
//    public static String DB_SELECT_GRAFICO_DATA = "select data  from avaliacao where data>='2017-06-09' and data<='2017-07-09' group by data order by data asc";


    public static String DB_SELECT_GRAFICO_COMO_CONHECEU_REST="select como_conheceu_rest, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by como_conheceu_rest order by counter desc";
    public static String DB_SELECT_GRAFICO_RECOMENDARIA_REST="select recomendaria_rest, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by recomendaria_rest order by counter desc";
    public static String DB_SELECT_GRAFICO_VOLTARIA_REST="select voltaria_rest, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by voltaria_rest order by counter desc";
    
    public static String DB_SELECT_GRAFICO_COMO_CONHECEU_REST_FILTRO_CIDADE="select como_conheceu_rest, count(id_avaliacao)as counter from avaliacao where cidade=? and data>=? and data<=? group by como_conheceu_rest order by counter desc";
    public static String DB_SELECT_GRAFICO_RECOMENDARIA_REST_FILTRO_CIDADE="select recomendaria_rest, count(id_avaliacao)as counter from avaliacao where cidade=? and data>=? and data<=? group by recomendaria_rest order by counter desc";
    public static String DB_SELECT_GRAFICO_VOLTARIA_REST_FILTRO_CIDADE="select voltaria_rest, count(id_avaliacao)as counter from avaliacao where cidade=? and data>=? and data<=? group by voltaria_rest order by counter desc";

    
    
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_REST_MOQUEM="select rest_moquem, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by rest_moquem order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_AMBIENTE="select ambiente, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by ambiente order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ATENDIMENTO="select atendimento, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by atendimento order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_QUALIDADE="select qualidade, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by qualidade order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ESPACOKIDS="select espaco_kids, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by espaco_kids order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_COZINHA="select cozinha, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by cozinha order by counter desc";
    
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_REST_MOQUEM_FILTRO_CIDADE="select rest_moquem, count(id_avaliacao)as counter from avaliacao where cidade=? and  data>=? and data<=? group by rest_moquem order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_AMBIENTE_FILTRO_CIDADE="select ambiente, count(id_avaliacao)as counter from avaliacao where cidade=? and  data>=? and data<=? group by ambiente order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ATENDIMENTO_FILTRO_CIDADE="select atendimento, count(id_avaliacao)as counter from avaliacao where cidade=? and  data>=? and data<=? group by atendimento order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_QUALIDADE_FILTRO_CIDADE="select qualidade, count(id_avaliacao)as counter from avaliacao where cidade=? and  data>=? and data<=? group by qualidade order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ESPACOKIDS_FILTRO_CIDADE="select espaco_kids, count(id_avaliacao)as counter from avaliacao where cidade=? and  data>=? and data<=? group by espaco_kids order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_COZINHA_FILTRO_CIDADE="select cozinha, count(id_avaliacao)as counter from avaliacao where cidade=? and  data>=? and data<=? group by cozinha order by counter desc";    
    
    public static String DB_SELECT_GRAFICO_ATENDENTE = "select atendimento, count(id_avaliacao)as counter from avaliacao where atendente=? and  data>=? and data<=? group by atendimento order by counter desc;";    
    public static String DB_SELECT_GRAFICO_ATENDENTE_FILTRO_CIDADE = "select atendimento, count(id_avaliacao)as counter from avaliacao where cidade=? and atendente=? and  data>=? and data<=? group by atendimento order by counter desc;";
    
    
    public static String STR_SELECT_GRAFICO_SERVICOS_1=""
    + "select timeframe.date, servico.counter from "
    + "        ( "
    + "                select date_trunc(?, data) as date  "
    + "                from avaliacao  "
    + "                where data>=? and data <=? group by date order by date asc "
    + "         ) as timeframe left join  "
    + "         ( "
    + "           select count(id_avaliacao) as counter, date_trunc(?, data) as date  "
    + "           from avaliacao  "
    + "           where ";
    public static String STR_SELECT_GRAFICO_SERVICOS_2=
            "=? and data>=? and data <=? group by date order by date asc "
    + "         ) as servico "
     + "        on timeframe.date=servico.date ";
    

    public static String DB_SELECT_GRAFICO_SERVICOS_REST_MOQUEM_FILTRO_CIDADE="select count(id_avaliacao) as counter, date_trunc(?, data) as date from avaliacao where cidade=? and  rest_moquem=? and data>=? and data<=? group by date order by date asc;";
    public static String DB_SELECT_GRAFICO_SERVICOS_AMBIENTE_FILTRO_CIDADE="select count(id_avaliacao) as counter, date_trunc(?, data) as date from avaliacao where cidade=? and  ambiente=? and data>=? and data<=? group by date order by date asc;";    
    public static String DB_SELECT_GRAFICO_SERVICOS_ATENDIMENTO_FILTRO_CIDADE="select count(id_avaliacao) as counter, date_trunc(?, data) as date from avaliacao where cidade=? and  atendimento=? and data>=? and data<=? group by date order by date asc;";
    public static String DB_SELECT_GRAFICO_SERVICOS_QUALIDADE_FILTRO_CIDADE="select count(id_avaliacao) as counter, date_trunc(?, data) as date from avaliacao where cidade=? and  qualidade=? and data>=? and data<=? group by date order by date asc;";    
    public static String DB_SELECT_GRAFICO_SERVICOS_ESPACOKIDS_FILTRO_CIDADE="select count(id_avaliacao) as counter, date_trunc(?, data) as date from avaliacao where cidade=? and  espaco_kids=? and data>=? and data<=? group by date order by date asc;";
    public static String DB_SELECT_GRAFICO_SERVICOS_COZINHA_FILTRO_CIDADE="select count(id_avaliacao) as counter, date_trunc(?, data) as date from avaliacao where cidade=? and  cozinha=? and data>=? and data<=? group by date order by date asc;";    

    
    public static String DB_SELECT_AVALIACAO_DATA = "select * from avaliacao where data >= ? and data <= ? and obs!='' order by data desc";
    public static String DB_SELECT_AVALIACAO_DATA_FILTER_OBS = "select * from avaliacao where data >= ? and data <= ? and (obs ilike ?)   order by data desc";
    
    
    public static String DB_UPDATE_AVALIACAO = "UPDATE avaliacao   "  
    + " SET rest_moquem=?, ambiente=?, atendimento=?, qualidade=?, " 
    + "     espaco_kids=?, cozinha=?, recomendaria_rest=?, como_conheceu_rest=?, "
    + "     voltaria_rest=?, cidade=?, data=?, email=?, telefone=?, "
    + "    atendente=?, obs=?, rec_grupo_primario=?, rec_grupo_secundario=? "
    + " WHERE id_avaliacao=?; ";
    
    
    public static String DB_DELETE_AVALIACAO = "DELETE FROM avaliacao WHERE id_avaliacao=?;";


    
    public AvaliacaoServer() {

    }

    public static String AdicionarAvaliacao(Avaliacao object) {

        String success = "false";

        Connection connection = ConnectionManager.getConnection();

        try {

            Date date = new Date();

            if (object.getData() == null) {
                object.setData(date);
            }

            int param = 0;
            PreparedStatement psInsert = connection.prepareStatement(DB_INSERT_AVALIACAO);
            psInsert.setString(++param, object.getRestMoquem());
            psInsert.setString(++param, object.getAmbiente());
            psInsert.setString(++param, object.getAtendimento());
            psInsert.setString(++param, object.getQualidade());
            psInsert.setString(++param, object.getEspacoKids());
            psInsert.setString(++param, object.getCozinha());
            psInsert.setString(++param, object.getRecomendariaRest());
            psInsert.setString(++param, object.getComoConheceuRest());
            psInsert.setString(++param, object.getVoltariaRest());
            
            psInsert.setString(++param, (object.getCidade().trim().isEmpty())?"Não Informado":object.getCidade());
            psInsert.setDate(++param, new java.sql.Date(object.getData().getTime()));
            psInsert.setString(++param, object.getEmail());
            psInsert.setString(++param, object.getTelefone());
            psInsert.setString(++param, object.getObs());
            psInsert.setString(++param, (object.getAtendente().trim().isEmpty())?"Não Informado":object.getAtendente());

            psInsert.executeUpdate();

            success = "true";

        } catch (SQLException ex) {
            success = ex.getMessage();
            System.err.println(ex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return success;
    }

    public static ArrayList<String> getGraficoColunaCidade(Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(DB_SELECT_GRAFICO_COLUNA_CIDADE);          
            int count=0;
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strCounter = rs.getString("counter");
                String strCidade = rs.getString("cidade");

                data.add(strCidade + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }
    
    public static ArrayList<String> getGraficoColunaCidade(String strCidade, Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(DB_SELECT_GRAFICO_COLUNA_CIDADE_FILTRO_CIDADE);          
            int count=0;
            ps.setString(++count,strCidade);
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strCounter = rs.getString("counter");
                String strCid = rs.getString("cidade");

                data.add(strCid + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }    
    
    
    public static ArrayList<String> getGraficoColunaObs(Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(DB_SELECT_GRAFICO_COLUNA_OBS);          
            int count=0;
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strCounter = rs.getString("counter");
                String strGrupo = rs.getString("rec_grupo_primario")+":"+rs.getString("rec_grupo_secundario");

                data.add(strGrupo + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }
    
    
    public static ArrayList<String> getGraficoColunaObs(String strCidade, Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(DB_SELECT_GRAFICO_COLUNA_CIDADE_FILTRO_CIDADE);          
            int count=0;
            ps.setString(++count,strCidade);
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strCounter = rs.getString("counter");
                String strGrupo = rs.getString("rec_grupo_primario")+":"+rs.getString("rec_grupo_secundario");

                data.add(strGrupo + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }  
    
    
    
//    public static ArrayList<String> getGraficoCidades(Date dataInicial, Date dataFinal) {
//
//        ArrayList<String> listData = new ArrayList<String>();
//
//        Connection connection = ConnectionManager.getConnection();
//
//        try {
//
//            PreparedStatement ps = connection.prepareStatement(DB_SELECT_GRAFICO_CIDADE);          
//            int count=0;
//            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
//            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
//            
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//
//                String strCidade = rs.getString("cidade");
//
//                listData.add(strCidade);
//            }
//
//        } catch (SQLException sqlex) {
//            listData = null;
//            System.err.println(sqlex.getMessage());
//        } finally {
//            ConnectionManager.closeConnection(connection);
//        }
//
//        return listData;
//
//    }
    
//    public static ArrayList<String> getGraficoDatas(Date dataInicial, Date dataFinal) {
//
//        ArrayList<String> listData = new ArrayList<String>();
//
//        Connection connection = ConnectionManager.getConnection();
//
//        try {
//
//            PreparedStatement ps = connection.prepareStatement(DB_SELECT_GRAFICO_DATA);          
//            int count=0;
//            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
//            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
//            
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//
//                String strData = rs.getDate("data").toString();
//
//                listData.add(strData);
//            }
//
//        } catch (SQLException sqlex) {
//            listData = null;
//            System.err.println(sqlex.getMessage());
//        } finally {
//            ConnectionManager.closeConnection(connection);
//        }
//
//        return listData;
//
//    }
    
//    public static ArrayList<ArrayList<String>> getGraficoCidadeData(String cidades, String datas, Date dataInicial, Date dataFinal) {
//
//        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
//
//        Connection connection = ConnectionManager.getConnection();
//
//        try {
//
//            PreparedStatement ps = connection.prepareStatement(DB_SELECT_GRAFICO_LINHA_CIDADE);          
//            int count=0;
//            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
//            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
//            
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//
//                String strCounter = rs.getString("counter");
//                String strCidade = rs.getString("cidade");
//                String strData =  rs.getDate("data").toLocalDate().toString();
//
////                data.add(strCidade + Avaliacao.SEPARATE_DATA + strCounter + Avaliacao.SEPARATE_DATA + strData);
//            }
//
//        } catch (SQLException sqlex) {
//            data = null;
//            System.err.println(sqlex.getMessage());
//        } finally {
//            ConnectionManager.closeConnection(connection);
//        }
//
//        return data;
//
//    }
//    
    
    public static ArrayList<String> getGraficoTortaSobreRestaurante(String strCampo, String strQuery, Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(strQuery);          
            int count=0;
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strComoConheceuRest = rs.getString(strCampo);
                String strCounter = rs.getString("counter");
                

                data.add(strComoConheceuRest + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }
    
    public static ArrayList<String> getGraficoTortaSobreRestaurante(String strCampo, String strQuery, String strCidade, Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(strQuery);          
            int count=0;
            ps.setString(++count, strCidade);
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strComoConheceuRest = rs.getString(strCampo);
                String strCounter = rs.getString("counter");
                

                data.add(strComoConheceuRest + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }    
    
    public static ArrayList<String> getGraficoPesquisaSatisfacao(String strCampo, String strQuery, Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(strQuery);          
            int count=0;
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strRestMoquem = rs.getString(strCampo);
                String strCounter = rs.getString("counter");
                

                data.add(strRestMoquem + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }
    
    public static ArrayList<String> getGraficoPesquisaSatisfacao(String strCampo, String strQuery, String strCidade, Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(strQuery);          
            int count=0;
            ps.setString(++count, strCidade);
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strRestMoquem = rs.getString(strCampo);
                String strCounter = rs.getString("counter");
                

                data.add(strRestMoquem + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }    
    
    
    public static ArrayList<String> getCidades() {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(DB_SELECT_AVALIACAO_CIDADE);          
             
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strCidade = rs.getString("cidade");

                data.add(strCidade);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }
    
    public static ArrayList<String> getGraficoAtendentes(String strAtendente, Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(DB_SELECT_GRAFICO_ATENDENTE);          
            int count=0;
            ps.setString(++count, strAtendente);
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strCounter = rs.getString("counter");
                String strAtendimento = rs.getString("atendimento");

                data.add(strAtendimento + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }    
    
    public static ArrayList<String> getGraficoAtendentes(String strCidade, String strAtendente, Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(DB_SELECT_GRAFICO_ATENDENTE_FILTRO_CIDADE);          
            int count=0;
            ps.setString(++count, strCidade);
            ps.setString(++count, strAtendente);
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strCounter = rs.getString("counter");
                String strAtendimento = rs.getString("atendimento");

                data.add(strAtendimento + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }        
    
    
    public static ArrayList<DadoServicoTrend> getGraficoServicos(String strColuna, String strNota, String strEscala, Date dataInicial, Date dataFinal) {

        
        ArrayList<DadoServicoTrend> listData = new ArrayList<DadoServicoTrend>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(STR_SELECT_GRAFICO_SERVICOS_1+strColuna+STR_SELECT_GRAFICO_SERVICOS_2);          
            int count=0;
            ps.setString(++count, strEscala);
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            ps.setString(++count, strEscala);
            ps.setString(++count, strNota);
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                DadoServicoTrend dadosServico = new DadoServicoTrend();

                Date strData = rs.getDate("date");
                int strCounter = rs.getInt("counter");
                
                dadosServico.setStrServico(strColuna);
                dadosServico.setStrNota(strNota);
                dadosServico.setData(strData.toString());
                dadosServico.setContador(strCounter);
                
                listData.add(dadosServico);
            }

        } catch (SQLException sqlex) {
            listData = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return listData;

    }    
    
    
    public static ArrayList<String> getGraficoServicos(String strColuna, String strNota, String strEscala, String strCidade, Date dataInicial, Date dataFinal) {

        ArrayList<String> data = new ArrayList<String>();

        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(strNota);          
            int count=0;
            ps.setString(++count, strCidade);
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String strRestMoquem = rs.getString(strColuna);
                String strCounter = rs.getString("counter");
                

                data.add(strRestMoquem + Avaliacao.SEPARATE_DATA + strCounter);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }        
    
    
    public static ArrayList<Avaliacao> getAvaliacoes(Date dataInicial, Date dataFinal, String strObs) {

        ArrayList<Avaliacao> data = new ArrayList<Avaliacao>();

        Connection connection = ConnectionManager.getConnection();

        try {
            
            String StrQuery = "";
            
            if(strObs.isEmpty()){
                StrQuery = DB_SELECT_AVALIACAO_DATA;
            }else{
                StrQuery = DB_SELECT_AVALIACAO_DATA_FILTER_OBS;
            }

            PreparedStatement ps = connection.prepareStatement(StrQuery);          
            int count=0;
            ps.setDate(++count, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(++count, new java.sql.Date(dataFinal.getTime()));
            if(!strObs.isEmpty()){
                ps.setString(++count, "%" + strObs + "%");
            }
            
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                Avaliacao avaliacao = new Avaliacao();

                avaliacao.setIdAvaliacao(rs.getInt("id_avaliacao"));    
                avaliacao.setRestMoquem(rs.getString("rest_moquem"));    
                avaliacao.setAmbiente(rs.getString("ambiente"));           
                avaliacao.setAtendimento(rs.getString("atendimento"));         
                avaliacao.setQualidade(rs.getString("qualidade"));        
                avaliacao.setEspacoKids(rs.getString("espaco_kids"));     
                avaliacao.setCozinha(rs.getString("cozinha"));        
                avaliacao.setRecomendariaRest(rs.getString("recomendaria_rest"));           
                avaliacao.setComoConheceuRest(rs.getString("como_conheceu_rest"));         
                avaliacao.setVoltariaRest(rs.getString("voltaria_rest"));       
                avaliacao.setCidade(rs.getString("cidade"));         
                avaliacao.setData(rs.getDate("data"));        
                avaliacao.setEmail(rs.getString("email"));       
                avaliacao.setTelefone(rs.getString("telefone"));        
                avaliacao.setObs(rs.getString("obs"));
                avaliacao.setAtendente(rs.getString("atendente"));
                avaliacao.setObs(rs.getString("obs"));
                avaliacao.setRecGrupoPrimario(rs.getString("rec_grupo_primario"));
                avaliacao.setRecGrupoSecundario(rs.getString("rec_grupo_secundario"));

                data.add(avaliacao);
            }

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }
    
    public static boolean updateRow(Avaliacao object){
        boolean success=false;

        Connection connection = ConnectionManager.getConnection();

        try {

            
//            UPDATE avaliacao
//            SET rest_moquem=?, ambiente=?, atendimento=?, qualidade=?, 
//                espaco_kids=?, cozinha=?, recomendaria_rest=?, como_conheceu_rest=?, 
//                voltaria_rest=?, cidade=?, data=?, email=?, telefone=?, obs=?, 
//                atendente=?
//          WHERE id_avaliacao=? ;

            int count = 0;
            PreparedStatement updateReserva = connection.prepareStatement(DB_UPDATE_AVALIACAO);
            updateReserva.setString(++count, object.getRestMoquem());
            updateReserva.setString(++count, object.getAmbiente());
            updateReserva.setString(++count, object.getAtendimento());
            updateReserva.setString(++count, object.getQualidade());
            updateReserva.setString(++count, object.getEspacoKids());            
            updateReserva.setString(++count, object.getCozinha());
            updateReserva.setString(++count, object.getRecomendariaRest());
            updateReserva.setString(++count, object.getComoConheceuRest());
            updateReserva.setString(++count, object.getVoltariaRest());
            updateReserva.setString(++count, object.getCidade());
            updateReserva.setDate(++count, new java.sql.Date(object.getData().getTime()));
            updateReserva.setString(++count, object.getEmail());
            updateReserva.setString(++count, object.getTelefone());
//            updateReserva.setString(++count, object.getSugestao());
            updateReserva.setString(++count, object.getAtendente());
            updateReserva.setString(++count, object.getObs());
            updateReserva.setString(++count, object.getRecGrupoPrimario());
            updateReserva.setString(++count, object.getRecGrupoSecundario());
            updateReserva.setInt(++count, object.getIdAvaliacao());
            
            

            int numberUpdate = updateReserva.executeUpdate();


            if (numberUpdate == 1) {
                success = true;
            }


        } catch (SQLException sqlex) {
            success=false;
            System.err.println(sqlex.getMessage());         
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }       

        return success;
    }     
    
    
    public static boolean deleteRow(int id_avaliacao){
        
        boolean success=false;

        Connection connection = ConnectionManager.getConnection();

        try {

            int count = 0;
            PreparedStatement deletePS = connection.prepareStatement(DB_DELETE_AVALIACAO);
            deletePS.setInt(++count, id_avaliacao);

            int numberUpdate = deletePS.executeUpdate();

            if (numberUpdate == 1) {
                success = true;
            }


        } catch (SQLException sqlex) {
            success=false;
            System.err.println(sqlex.getMessage());         
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        
        return success;
    }       
    
 
}
