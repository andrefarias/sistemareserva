package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

public class AvaliacaoServer {

    public static String DB_INSERT_AVALIACAO = "INSERT INTO avaliacao( " + "rest_moquem, ambiente, atendimento, qualidade,  " + "espaco_kids, cozinha, recomendaria_rest, como_conheceu_rest,  " + "voltaria_rest, cidade, data, email, telefone, obs, atendente) " + "VALUES (?, ?, ?, ?,  " + "?, ?, ?, ?,  " + "?, ?, ?, ?, ?, ?,?); ";
    public static String DB_UPDATE_RESERVA = "UPDATE reserva " + "SET nome_reserva=?, numero_adultos=?, numero_criancas=?, " + "telefone=?, cidade=?, data=?, horario=?, turno=?, observacao=?, chegou=?, salao=?, mesa=? " + "WHERE id_reserva=?;";

    public static String DB_SELECT_GRAFICO_COLUNA_CIDADE = "select count(*) as counter, cidade from avaliacao where data>=? and data<=? group by cidade order by counter desc";
//    public static String DB_SELECT_GRAFICO_LINHA_CIDADE = "select count(*) as counter, cidade, data from avaliacao where data>='2017-07-09' and data<='2017-07-09' group by cidade, data order by data asc, counter desc";
    
    
//    public static String DB_SELECT_GRAFICO_CIDADE = "select cidade  from avaliacao where data>='2017-06-09' and data<='2017-07-09' group by cidade order by cidade asc";
//    public static String DB_SELECT_GRAFICO_DATA = "select data  from avaliacao where data>='2017-06-09' and data<='2017-07-09' group by data order by data asc";


    public static String DB_SELECT_GRAFICO_COMO_CONHECEU_REST="select como_conheceu_rest, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by como_conheceu_rest order by counter desc";
    public static String DB_SELECT_GRAFICO_RECOMENDARIA_REST="select recomendaria_rest, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by recomendaria_rest order by counter desc";
    public static String DB_SELECT_GRAFICO_VOLTARIA_REST="select voltaria_rest, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by voltaria_rest order by counter desc";
    
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_REST_MOQUEM="select rest_moquem, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by rest_moquem order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_AMBIENTE="select ambiente, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by ambiente order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ATENDIMENTO="select atendimento, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by atendimento order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_QUALIDADE="select qualidade, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by qualidade order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ESPACOKIDS="select espaco_kids, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by espaco_kids order by counter desc";
    public static String DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_COZINHA="select cozinha, count(id_avaliacao)as counter from avaliacao where data>=? and data<=? group by cozinha order by counter desc";
    
    
    
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
            
            psInsert.setString(++param, (object.getCidade()=="")?"Não Informado":object.getCidade());
            psInsert.setDate(++param, new java.sql.Date(object.getData().getTime()));
            psInsert.setString(++param, object.getEmail());
            psInsert.setString(++param, object.getTelefone());
            psInsert.setString(++param, object.getSugestao());
            psInsert.setString(++param, (object.getAtendente()=="")?"Não Informado":object.getAtendente());

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
    
 
}
