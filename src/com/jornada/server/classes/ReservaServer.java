package com.jornada.server.classes;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jornada.ConfigJornada;
import com.jornada.server.classes.utility.MpUtilServer;
import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.Clientes;
import com.jornada.shared.classes.Reserva;




public class ReservaServer {
	
	
    public static String DB_INSERT_RESERVA = "INSERT INTO reserva"
            + "(nome_reserva, numero_adultos, numero_criancas, telefone, cidade, "
            + " data, horario, turno, observacao, chegou) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static String DB_UPDATE_RESERVA = "UPDATE reserva "+
    "SET nome_reserva=?, numero_adultos=?, numero_criancas=?, "+ 
    "telefone=?, cidade=?, data=?, horario=?, turno=?, observacao=?, chegou=? "+
    "WHERE id_reserva=?;";
	public static String DB_SELECT_RESERVA = "SELECT * FROM reserva order by data, nome_reserva, horario asc;";
	public static String DB_SELECT_RESERVA_DATE = "SELECT * FROM reserva where data=? and turno=? order by data, nome_reserva, horario asc;";
	public static String DB_DELETE_RESERVA = "delete from reserva where id_reserva=?";	
	
	
	public ReservaServer(){
		
	}

    public static String AdicionarReservaString(Reserva object) {

        String success = "false";

        // JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();

        try {
            // dataBase.createConnection();
            // Connection conn = dataBase.getConnection();

            Date date = new Date();

            if (object.getDataReserva() == null) {
                object.setDataReserva(date);
            }

            //(nome_reserva, numero_adultos, numero_criancas, telefone, cidade, data, hora, turno)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);

            int param = 0;
            PreparedStatement psInsertReserva = connection.prepareStatement(DB_INSERT_RESERVA);
            psInsertReserva.setString(++param, object.getNomeReserva());
            psInsertReserva.setInt(++param, object.getNumeroAdultos());
            psInsertReserva.setInt(++param, object.getNumeroCriancas());
            psInsertReserva.setString(++param, object.getTelefone());
            psInsertReserva.setString(++param, object.getCidade());
            psInsertReserva.setDate(++param, new java.sql.Date(object.getDataReserva().getTime()));
            psInsertReserva.setString(++param, object.getHorario());
            psInsertReserva.setString(++param, object.getTurno());
            psInsertReserva.setString(++param, object.getObservacao());
            psInsertReserva.setString(++param, "Não");
            
            psInsertReserva.executeUpdate();
//            rs.next();

            success = "true";

        } catch (SQLException ex) {
            success = ex.getMessage();
            System.err.println(ex.getMessage());
        } finally {
            // dataBase.close();
            ConnectionManager.closeConnection(connection);

        }

        return success;
    }
			
	public static ArrayList<Reserva> getReservas() {

		ArrayList<Reserva> data = new ArrayList<Reserva>();

		Connection connection = ConnectionManager.getConnection();

		try 
		{

			PreparedStatement psPeriodo = connection.prepareStatement(DB_SELECT_RESERVA);
			
			data = getReservaParameters(psPeriodo.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}
	
    public static ArrayList<Reserva> getReservas(Date dataReservas, String strTurno) {

        ArrayList<Reserva> data = new ArrayList<Reserva>();
        Connection connection = ConnectionManager.getConnection();

        try {

            PreparedStatement ps = connection.prepareStatement(DB_SELECT_RESERVA_DATE);

            int count = 0;
            ps.setDate(++count, new java.sql.Date(dataReservas.getTime()));
            ps.setString(++count, strTurno);

            data = getReservaParameters(ps.executeQuery());

        } catch (SQLException sqlex) {
            data = null;
            System.err.println(sqlex.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }
    
  	public static Clientes getNumeroClientes(Date dataReservas,String strTurno) {

	        Clientes clientes = new Clientes();
	        ArrayList<Reserva> data = new ArrayList<Reserva>();
	        Connection connection = ConnectionManager.getConnection();

	        try 
	        {

	            PreparedStatement ps = connection.prepareStatement(DB_SELECT_RESERVA_DATE);
	            
	          int count=0;
	          ps.setDate(++count, new java.sql.Date(dataReservas.getTime()));  
	          ps.setString(++count, strTurno);  
	          
	          data = getReservaParameters(ps.executeQuery());
	          
	          
	          int totalAdultos = 0;
	          int totalCriancas = 0;
	          int totalClientes=0;
	          for(Reserva reserva: data){	              
	              totalAdultos = totalAdultos + reserva.getNumeroAdultos();
	              totalCriancas = totalCriancas + reserva.getNumeroCriancas();	              
	          }          
	          
	          totalClientes = totalAdultos + totalCriancas;
	          
	          clientes.setNumeroTotalAdultos(totalAdultos);
	          clientes.setNumeroTotalCriancas(totalCriancas);	          
	          clientes.setNumeroTotalClientes(totalClientes);
	            

	        } catch (SQLException sqlex) {
	            clientes=null;
	            System.err.println(sqlex.getMessage());
	        } finally {
//	          dataBase.close();
	            ConnectionManager.closeConnection(connection);
	        }

	        return clientes;

	    }
		
	public static boolean deleteRow(int id_periodo){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement deletePeriodo = connection.prepareStatement(DB_DELETE_RESERVA);
			deletePeriodo.setInt(++count, id_periodo);

			int numberUpdate = deletePeriodo.executeUpdate();


			if (numberUpdate == 1) {
				success = true;
			}


		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		return success;
	}		
	
	public static boolean updateRow(Reserva object){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement updateReserva = connection.prepareStatement(DB_UPDATE_RESERVA);
			updateReserva.setString(++count, object.getNomeReserva().trim());
			updateReserva.setInt(++count, object.getNumeroAdultos());
			updateReserva.setInt(++count, object.getNumeroCriancas());
			updateReserva.setString(++count, object.getTelefone());
			updateReserva.setString(++count, object.getCidade());
			updateReserva.setDate(++count, new java.sql.Date(object.getDataReserva().getTime()));
			updateReserva.setString(++count, object.getHorario());
			updateReserva.setString(++count, object.getTurno());
			updateReserva.setString(++count, object.getObservacao());
			updateReserva.setString(++count, object.getChegou());
			updateReserva.setInt(++count, object.getIdReserva());

			int numberUpdate = updateReserva.executeUpdate();


			if (numberUpdate == 1) {
				success = true;
			}


		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}		

		return success;
	}	
		
	private static ArrayList<Reserva> getReservaParameters(ResultSet rs){

		ArrayList<Reserva> data = new ArrayList<Reserva>();
		
		try{
		
		while (rs.next()) 		{
			Reserva object = new Reserva();			
			object.setIdReserva(rs.getInt("id_reserva"));
			object.setNomeReserva(rs.getString("nome_reserva"));
			object.setNumeroAdultos(rs.getInt("numero_adultos"));
			object.setNumeroCriancas(rs.getInt("numero_criancas"));
			object.setTelefone(rs.getString("telefone"));
			object.setCidade(rs.getString("cidade"));
			object.setDataReserva(rs.getDate("data"));
			object.setHorario(rs.getString("horario"));
			object.setTurno(rs.getString("turno"));
			object.setObservacao(rs.getString("observacao"));
			object.setChegou(rs.getString("chegou"));
			   
			  
			data.add(object);
		}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}		
		return data;
	}


    public static String getReservasExcel(Date dataReserva, String strTurno) {
        String strLong="";

        XSSFWorkbook wb = new XSSFWorkbook();
        
        Font font = wb.createFont();
        XSSFCellStyle style = wb.createCellStyle();
        
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("sans-serif");
        font.setFontHeightInPoints((short)12);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        
        ///Creating Tabs
        XSSFSheet sheetReservas = wb.createSheet("Reserva Restaurante");
        sheetReservas.setFitToPage(true);
        sheetReservas.getPrintSetup().setLandscape(true);

        
        gerarExcelDasReservas(wb, sheetReservas, font, style, dataReserva, strTurno);

        
        try {
            Date data = new Date();
            strLong += "GerarExcelReservas_" + Long.toString(data.getTime())+ ".xlsx";
            FileOutputStream out = new FileOutputStream(ConfigJornada.getProperty("config.download")+ ConfigJornada.getProperty("config.download.excel") + strLong);
            wb.write(out);
            out.close();
            out.flush();
        } catch (Exception ex) {
            System.out.print("Error Excel:" + ex.getMessage());
        }
        
        return ConfigJornada.getProperty("config.download.excel")+strLong;
    }

    
    public static void gerarExcelDasReservas(XSSFWorkbook wb, Sheet sheet, Font font, XSSFCellStyle style, Date dataReserva, String strTurno){
        
        ArrayList<Reserva> listReservas = ReservaServer.getReservas(dataReserva, strTurno);
        
        
        Font fontValue = wb.createFont();
        fontValue.setColor(IndexedColors.BLACK.getIndex());
        fontValue.setFontHeightInPoints((short)12);
        
       XSSFCellStyle styleValue = wb.createCellStyle();
       styleValue.setFont(fontValue);
       styleValue.setAlignment(CellStyle.ALIGN_CENTER);
       styleValue.setFillForegroundColor(IndexedColors.WHITE.getIndex());
       styleValue.setBorderBottom(CellStyle.BORDER_THIN);
       styleValue.setBorderTop(CellStyle.BORDER_THIN);
       styleValue.setBorderLeft(CellStyle.BORDER_THIN);
       styleValue.setBorderRight(CellStyle.BORDER_THIN);
        
        Row row = sheet.createRow((short) 0);       
        
        int intColumn=0;
        row.createCell((short) intColumn++).setCellValue("DataReserva");
        row.createCell((short) intColumn++).setCellValue("Turno");
        row.createCell((short) intColumn++).setCellValue("H.Chegada");
        row.createCell((short) intColumn++).setCellValue("NomeReserva");
        row.createCell((short) intColumn++).setCellValue("Adultos");
        row.createCell((short) intColumn++).setCellValue("Crianças");        
        row.createCell((short) intColumn++).setCellValue("Cidade");
        row.createCell((short) intColumn++).setCellValue("Telefone");
        row.createCell((short) intColumn++).setCellValue("Chegou");
        row.createCell((short) intColumn++).setCellValue("Obs.");

        for (int i = 0; i < intColumn; i++) {
            row.getCell((short) i).setCellStyle(style);
        }        
        
        for(int i=0;i<listReservas.size();i++){
            Reserva reserva = listReservas.get(i);
            row = sheet.createRow((short) i+1);
           
            intColumn=0;
        
            row.createCell((short) intColumn++).setCellValue((reserva.getDataReserva()==null)?"":MpUtilServer.convertDateToString(reserva.getDataReserva()));
            row.createCell((short) intColumn++).setCellValue(reserva.getTurno());
            row.createCell((short) intColumn++).setCellValue(reserva.getHorario());
            row.createCell((short) intColumn++).setCellValue(reserva.getNomeReserva());
            row.createCell((short) intColumn++).setCellValue(reserva.getNumeroAdultos());
            row.createCell((short) intColumn++).setCellValue(reserva.getNumeroCriancas());
            row.createCell((short) intColumn++).setCellValue(reserva.getCidade());      
            row.createCell((short) intColumn++).setCellValue(reserva.getTelefone());
            row.createCell((short) intColumn++).setCellValue(reserva.getChegou());
            row.createCell((short) intColumn++).setCellValue(reserva.getObservacao());
//            intColumn=0;
            for(int count = 0; count<intColumn; count++){
                row.getCell((short) count).setCellStyle(styleValue);    
            }
            
//            row.getCell((short) intColumn++).setCellStyle(styleValue);
//            row.getCell((short) intColumn++).setCellStyle(styleValue);
//            row.getCell((short) intColumn++).setCellStyle(styleValue);
//            row.getCell((short) intColumn++).setCellStyle(styleValue);
//            row.getCell((short) intColumn++).setCellStyle(styleValue);
//            row.getCell((short) intColumn++).setCellStyle(styleValue);
//            row.getCell((short) intColumn++).setCellStyle(styleValue);
//            row.getCell((short) intColumn++).setCellStyle(styleValue);
//            row.getCell((short) intColumn++).setCellStyle(styleValue);
//            
        }        
        
        for (int i = 0; i < intColumn; i++) {
            sheet.autoSizeColumn(i,true);
        }

    }
   
    
    
    
		

}
