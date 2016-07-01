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
import com.jornada.server.classes.password.BCrypt;
import com.jornada.server.database.ConnectionManager;
//import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.TipoUsuario;
//import com.jornada.shared.classes.UnidadeEscola;
import com.jornada.shared.classes.Usuario;

public class UsuarioServer{

	public static String DB_INSERT = 
			"INSERT INTO usuario " +
			"(" +
			"id_unidade_escola," +
			"primeiro_nome," +
			"sobre_nome," +
			"cpf," +
			"data_nascimento," +
			"id_tipo_usuario," +
			"email," +
			"telefone_celular," +
			"telefone_residencial," +
			"telefone_comercial," +
			"login," +
			"senha," +
			"endereco," +
			"numero_residencia," +
			"bairro," +
			"cidade," +
			"unidade_federativa," +
			"cep," +
			"data_matricula," +
			"rg," +
			"sexo," +
			"empresa_onde_trabalha," +
			"cargo," +
			"resp_academico," +
			"resp_financeiro," +
			"registro_matricula," +
			"tipo_pais," +
			"situacao_responsaveis," +
			"situacao_responsaveis_outros," +
			"registro_aluno, " + 
			"primeiro_login," +
			"observacao," +
			"id_tipo_status_usuario, " +
			"cidade_nascimento, " +
			"uf_nascimento, " +
			"pais_nascimento, " +
			"registro_docente " +
			") " +
			"VALUES " +
			"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static String DB_UPDATE = 
			"UPDATE usuario set " +
			"id_unidade_escola=?, " +
			"primeiro_nome=?, " +
			"sobre_nome=?, " +
			"cpf=?, " +
			"data_nascimento=?, " +
			"id_tipo_usuario=?, " +
			"email=?, " +
			"telefone_celular=?, " +
			"telefone_residencial=?, " +
			"telefone_comercial=?, " +
			"login=?, " +			
			"endereco=?, " +
			"numero_residencia=?, " +
			"bairro=?, " +
			"cidade=?, " +
			"unidade_federativa=?, " +
			"cep=?, " +
			"data_matricula=?, " +
			"rg=?, " +
			"sexo=?, " +
			"empresa_onde_trabalha=?, " +
			"cargo=?, " +
			"resp_academico=?, " +
			"resp_financeiro=?, " +
			"registro_matricula=?, " +
			"tipo_pais=?, " +
			"situacao_responsaveis=?, " +
			"situacao_responsaveis_outros=?, " +
			"registro_aluno=?, " +
			"observacao=?, " +
			"id_tipo_status_usuario=?, " +
            "cidade_nascimento=?, " +
	        "uf_nascimento=?, " +
	        "pais_nascimento=?, " +
	        "registro_docente=? " +
			"where id_usuario=?";
	
	public static final String DB_UPDATE_IDIOMA = "UPDATE usuario set id_idioma=? where id_usuario=?";
	public static final String DB_UPDATE_SENHA = "UPDATE usuario set senha=?, primeiro_login=? where id_usuario=?";
	public static final String DB_SELECT_ILIKE = "SELECT * FROM usuario where (primeiro_nome ilike ?) order by primeiro_nome, sobre_nome asc";
	
	@Deprecated
	public static final String DB_SELECT_DB_FIELD_ILIKE = "select * from usuario, tipo_usuario, unidade_escola where usuario.id_tipo_usuario = tipo_usuario.id_tipo_usuario and unidade_escola.id_unidade_escola=usuario.id_unidade_escola and (<change> ilike ?)  order by primeiro_nome, sobre_nome asc";
	public static final String DB_SELECT_ILIKE_TIPO_USUARIO = "SELECT * FROM usuario where id_tipo_usuario = ? and (primeiro_nome ilike ? or sobre_nome ilike ?) order by primeiro_nome, sobre_nome asc";
	@Deprecated
	public static final String DB_SELECT_USUARIO_PELO_TIPO_USUARIO_OLD = "SELECT * FROM usuario where id_tipo_usuario = ? order by primeiro_nome, sobre_nome asc";
	public static final String DB_SELECT_USUARIO_PELO_TIPO_USUARIO_UNIDADE = "SELECT * FROM usuario where id_tipo_usuario = ? and id_unidade_escola = ? ordder by primeiro_nome, sobre_nome asc";
	public static final String DB_SELECT_ALL = "SELECT * FROM usuario order by primeiro_nome asc;";
	public static final String DB_SELECT_USUARIO_ID = "SELECT * FROM usuario where id_usuario=?;";
//	public static final String DB_SELECT_USUARIO_ID = "select * from usuario, tipo_usuario where (id_usuario = ?) and usuario.id_tipo_usuario = tipo_usuario.id_tipo_usuario order by primeiro_nome, sobre_nome asc";
	public static final String DB_SELECT_USUARIO_LOGIN = "SELECT * FROM usuario where login=? ;";
	public static final String DB_DELETE = "delete from usuario where id_usuario=?";
	public static final String DB_SELECT_ALL_TIPO_USUARIOS = "SELECT * FROM tipo_usuario where is_visible=true order by nome_tipo_usuario asc;";
	public static final String DB_SELECT_TIPO_USUARIOS_POR_NOME = "SELECT * FROM tipo_usuario where nome_tipo_usuario=? ;";
	public static final String DB_SELECT_TIPO_USUARIOS_POR_ID = "SELECT * FROM tipo_usuario where id_tipo_usuario=? ;";
	public static final String DB_SELECT_ILIKE_FILTRADO_POR_CURSO = 
			"select * from usuario where id_usuario in "+
			"( select id_usuario from rel_curso_usuario where id_curso=? ) "+
			"and (primeiro_nome ilike ? or sobre_nome ilike ?) "+
			"order by primeiro_nome, sobre_nome";		
	
	@Deprecated
	public static final String DB_SELECT_FILTRADO_POR_CURSO_OLD = 
			"select * from usuario where id_usuario in "+
			"( select id_usuario from rel_curso_usuario where id_curso=? ) "+
			"order by primeiro_nome, sobre_nome";	
	
	public static final String DB_SELECT_ALUNO_FILTRADO_POR_CURSO_AMBIENTE_PAI =
	"select * from usuario where id_usuario in "+ 
	"( "+
	"	select id_usuario from rel_curso_usuario where id_curso=? and id_usuario in "+
	"	( "+
	"		select id_usuario_aluno from rel_pai_aluno where id_usuario_pais=? "+
	"	) "+
	") order by primeiro_nome, sobre_nome ";
	
	public static final String DB_SELECT_USUARIO_PELO_TIPO_USUARIO_AMBIENTE_PAI = 
			"SELECT * FROM usuario where id_tipo_usuario = ? and id_usuario in "+
			"( "+
			"	select id_usuario_aluno from rel_pai_aluno where id_usuario_pais=? "+
			") "+
			"order by primeiro_nome, sobre_nome asc ";
	
    public static final String DB_SELECT_ALUNO_PELO_PAI_CURSO = 
            "select * from usuario where id_usuario in( "+
            "        select id_usuario from rel_curso_usuario where id_usuario in "+
            "        ( "+
            "            select id_usuario_aluno from rel_pai_aluno where id_usuario_pais=? "+ 
            "        ) and id_curso=? "+
            ");";
	
	public static final String DB_SELECT_FILTRADO_POR_OCORRENCIA = 
			"select * from usuario where id_usuario in "+
			"( select id_usuario from rel_usuario_ocorrencia where id_ocorrencia = ? ) "+
			"order by primeiro_nome, sobre_nome";		
	
	public static final String DB_DELETE_REL_PAI_ALUNO = "delete from rel_pai_aluno where id_usuario_aluno=?";
	public static final String DB_INSERT_REL_PAI_ALUNO = "INSERT INTO rel_pai_aluno (id_usuario_pais, id_usuario_aluno) VALUES (?,?)";
	
    private static final String DB_SELECT_PAIS_POR_CURSO = 
            "select * from usuario where id_usuario in( "+
            "        select id_usuario_pais from rel_pai_aluno where id_usuario_aluno in "+
            "        ( "+
            "            select id_usuario from rel_curso_usuario where id_curso=? group by id_usuario order by id_usuario asc "+
            "        )  group by id_usuario_pais order by id_usuario_pais asc "+
            "    ) and (primeiro_nome ilike ? or sobre_nome ilike ? )  <change> "+
            "    order by primeiro_nome, sobre_nome asc ";	
    
//    @Deprecated
//    private static final String DB_SELECT_PAIS_POR_CURSO_TODOS_OLD = 
//            "select * from usuario where id_usuario in( "+
//            "        select id_usuario_pais from rel_pai_aluno where id_usuario_aluno in "+
//            "        ( "+
//            "            select id_usuario from rel_curso_usuario where id_curso=? group by id_usuario order by id_usuario asc "+
//            "        )  group by id_usuario_pais order by id_usuario_pais asc "+
//            "    ) "+
//            "    order by primeiro_nome, sobre_nome asc ";     
//    @Deprecated
//    private static final String DB_SELECT_PROFESSOR_POR_CURSO_TODOS_OLD = 
//            "select * from usuario where id_usuario in( "+
//            "        select id_usuario from disciplina where id_periodo in "+
//            "            ( "+
//            "            select id_periodo from periodo where id_curso in "+
//            "            ( "+
//            "                select id_curso from curso where id_curso=? "+
//            "            ) "+
//            "        ) "+
//            "    )";
    
    private static final String DB_SELECT_TODOS_PAIS = 
            "select * from usuario where (primeiro_nome ilike ? or sobre_nome ilike ? ) " +
            " and id_tipo_usuario=" + TipoUsuario.PAIS +
            " <change> "+
            "    order by primeiro_nome, sobre_nome asc ";      
	
	
	public static final String DB_SELECT_ALL_PAIS_DO_ALUNO = 
			"select * from usuario where id_usuario in "+
			"(  "+
			"	select id_usuario_pais from rel_pai_aluno where id_usuario_aluno=? "+ 
			")  "+
			"order by primeiro_nome, sobre_nome asc ";	
	

	public static final String DB_SELECT_TODOS_USUARIOS = ""+
	        "select * from "+
	        "usuario as u "+
	        "left join idioma as i on u.id_idioma = i.id_idioma "+
	        "left join tipo_usuario as tu on u.id_tipo_usuario = tu.id_tipo_usuario "+
	        "left join unidade_escola as ue on u.id_unidade_escola = ue.id_unidade_escola "+
	        "left join tipo_status_usuario as tsu on u.id_tipo_status_usuario = tsu.id_tipo_status_usuario ";
	
	public static final String DB_SELECT_USUARIOS_NEW = DB_SELECT_TODOS_USUARIOS +
	        " where (<change> ilike ?)  order by primeiro_nome, sobre_nome asc ";

	public static final String DB_SELECT_USUARIO_PELO_TIPO_USUARIO = DB_SELECT_TODOS_USUARIOS +
	        " where u.id_tipo_usuario = ? order by primeiro_nome, sobre_nome asc";	

    public static final String DB_SELECT_FILTRADO_POR_CURSO = DB_SELECT_TODOS_USUARIOS +
	            "where id_usuario in ( select id_usuario from rel_curso_usuario where id_curso=? ) "+
	            "order by primeiro_nome, sobre_nome";       
    
    private static final String DB_SELECT_PAIS_POR_CURSO_TODOS = DB_SELECT_TODOS_USUARIOS +
            "where id_usuario in( "+
            "        select id_usuario_pais from rel_pai_aluno where id_usuario_aluno in "+
            "        ( "+
            "            select id_usuario from rel_curso_usuario where id_curso=? group by id_usuario order by id_usuario asc "+
            "        )  group by id_usuario_pais order by id_usuario_pais asc "+
            "    ) "+
            "    order by primeiro_nome, sobre_nome asc ";     

    private static final String DB_SELECT_PROFESSOR_POR_CURSO_TODOS = DB_SELECT_TODOS_USUARIOS +
            "where id_usuario in( "+
            "        select id_usuario from disciplina where id_periodo in "+
            "            ( "+
            "            select id_periodo from periodo where id_curso in "+
            "            ( "+
            "                select id_curso from curso where id_curso=? "+
            "            ) "+
            "        ) "+
            "    )";
    
//    private static final String DB_SELECT_PROFESSOR_DISCIPLINA_TODOS = 
//            "select u.id_usuario, u.primeiro_nome, u.sobre_nome, u.registro_docente, "
//            + "c.id_curso, c.nome_curso, "
//            + "d.id_periodo, p.nome_periodo , "
//            + "d.id_disciplina, d.nome_disciplina "
//            + "from usuario u "
//            + "left join disciplina d on u.id_usuario=d.id_usuario "
//            + "left join periodo p on d.id_periodo = p.id_periodo "
//            + "left join curso c on p.id_curso = c.id_curso "
//            + "where u.id_tipo_usuario = 2 and u.id_tipo_status_usuario = 6 "
//            + "order by u.primeiro_nome, u.sobre_nome asc ";
	
	public UsuarioServer(){
		
	}
	
	public static String AdicionarUsuario(Usuario usuario) {

		String success = "false";

		Connection connection = ConnectionManager.getConnection();

		try {


			
			String senhaHashed = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());

			int count = 0;
			PreparedStatement insert = connection.prepareStatement(UsuarioServer.DB_INSERT);
			insert.setString(++count, usuario.getPrimeiroNome());
			insert.setString(++count, usuario.getSobreNome());

		
			insert.setInt(++count, usuario.getIdTipoUsuario());

			insert.setString(++count, usuario.getLogin());
			insert.setString(++count, senhaHashed);
		
			insert.setBoolean(++count, true);
			insert.setString(++count, usuario.getObservacao());
			insert.setInt(++count, usuario.getIdTipoStatusUsuario());


			int numberUpdate = insert.executeUpdate();

			if (numberUpdate == 1) {
				success = "true";
			}

		} catch (SQLException sqlex) {
			success = sqlex.getMessage();
			System.err.println(success);
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return success;
	}
	
	public static String updateUsuarioRow(Usuario usuario){
		String success="false";

		Connection connection = ConnectionManager.getConnection();

		try {

			
			int count = 0;
			PreparedStatement update = connection.prepareStatement(UsuarioServer.DB_UPDATE);
			update.setString(++count, usuario.getPrimeiroNome());
			update.setString(++count, usuario.getSobreNome());
			update.setInt(++count, usuario.getIdTipoUsuario());
			update.setString(++count, usuario.getLogin());
			update.setString(++count, usuario.getObservacao());
			update.setInt(++count, usuario.getIdTipoStatusUsuario());	

						
			update.setInt(++count, usuario.getIdUsuario());

			int numberUpdate = update.executeUpdate();


			if (numberUpdate == 1) {
				success = "true";
			}


		} catch (SQLException sqlex) {			
			success = sqlex.getMessage();
			System.err.println(success);
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		return success;
	}	
		
	public static boolean atualizarIdioma(int idUsuario, int idIdioma){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();
			
			int count = 0;
			PreparedStatement update = connection.prepareStatement(DB_UPDATE_IDIOMA);
			update.setInt(++count, idIdioma);
			update.setInt(++count, idUsuario);

			int numberUpdate = update.executeUpdate();


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
		
	public static boolean atualizarSenha(int idUsuario, String senha, boolean forcarPrimeiroLogin){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();
			
			String senhaHashed = BCrypt.hashpw(senha, BCrypt.gensalt());
			
			int count = 0;
			PreparedStatement update = connection.prepareStatement(DB_UPDATE_SENHA);
			update.setString(++count, senhaHashed);
			update.setBoolean(++count, forcarPrimeiroLogin);
			update.setInt(++count, idUsuario);

			int numberUpdate = update.executeUpdate();


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
		
	public static boolean deleteUsuarioRow(int id_usuario){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement deleteCurso = connection.prepareStatement(UsuarioServer.DB_DELETE);
			deleteCurso.setInt(++count, id_usuario);

			int numberUpdate = deleteCurso.executeUpdate();


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
	
	public static String gerarExcelUsuario(){
		String strLong="";

		XSSFWorkbook wb = new XSSFWorkbook();
		
		Font font = wb.createFont();
        XSSFCellStyle style = wb.createCellStyle();
        
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("sans-serif");
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
		
		///Creating Tabs
		XSSFSheet sheetAdministrador = wb.createSheet("Administrador");
		XSSFSheet sheetAlunos = wb.createSheet("Alunos");
		XSSFSheet sheetCoordenador = wb.createSheet("Coordenador");
		XSSFSheet sheetPais = wb.createSheet("Pais");
		XSSFSheet sheetProfessor = wb.createSheet("Professor");
		XSSFSheet sheetPaisAlunos = wb.createSheet("Associação Pais e Alunos");
		
        gerarExcelAdministrador(sheetAdministrador, font, style);
        gerarExcelAluno(sheetAlunos, font, style);
        gerarExcelCoordenador(sheetCoordenador, font, style);
        gerarExcelPais(sheetPais, font, style);
        gerarExcelProfessor(sheetProfessor, font, style);
        gerarExcelPaisAlunos(sheetPaisAlunos, font, style);
        
		try {
			Date data = new Date();
			strLong += "GerarExcelUsuarios_" + Long.toString(data.getTime())+ ".xlsx";
			FileOutputStream out = new FileOutputStream(ConfigJornada.getProperty("config.download")+ ConfigJornada.getProperty("config.download.excel") + strLong);
			wb.write(out);
			out.close();
			out.flush();
		} catch (Exception ex) {
			System.out.print("Error Excel:" + ex.getMessage());
		}
        
        return ConfigJornada.getProperty("config.download.excel")+strLong;
		
	}
	
	public static void gerarExcelAdministrador(Sheet sheet, Font font, XSSFCellStyle style ){
				
		ArrayList<Usuario> listUsuarios = UsuarioServer.getUsuariosPorTipoUsuario(TipoUsuario.ADMINISTRADOR);
		
		Row row = sheet.createRow((short) 0);		
		
		int intColumn=0;
		row.createCell((short) intColumn++).setCellValue("Tipo Usuário");
		row.createCell((short) intColumn++).setCellValue("Unidade");
        row.createCell((short) intColumn++).setCellValue("Primeiro Nome");
        row.createCell((short) intColumn++).setCellValue("Sobre Nome");
        row.createCell((short) intColumn++).setCellValue("Usuário");
        row.createCell((short) intColumn++).setCellValue("Email");        
        row.createCell((short) intColumn++).setCellValue("Data Nascimento");
        row.createCell((short) intColumn++).setCellValue("Sexo");
        row.createCell((short) intColumn++).setCellValue("Endereço");
        row.createCell((short) intColumn++).setCellValue("Num Res");
        row.createCell((short) intColumn++).setCellValue("Bairro");
        row.createCell((short) intColumn++).setCellValue("Cidade");
        row.createCell((short) intColumn++).setCellValue("UF");
        row.createCell((short) intColumn++).setCellValue("Cep");
        row.createCell((short) intColumn++).setCellValue("Tel Celular");
        row.createCell((short) intColumn++).setCellValue("Tel Res");
        row.createCell((short) intColumn++).setCellValue("Tel Com");
        row.createCell((short) intColumn++).setCellValue("CPF");
        row.createCell((short) intColumn++).setCellValue("RG");
        row.createCell((short) intColumn++).setCellValue("Status");


		for (int i = 0; i < intColumn; i++) {
			row.getCell((short) i).setCellStyle(style);
		}
        
        
        for(int i=0;i<listUsuarios.size();i++){
        	Usuario usuario = listUsuarios.get(i);
        	row = sheet.createRow((short) i+1);
        	
        	intColumn=0;
       	

            row.createCell((short) intColumn++).setCellValue(usuario.getPrimeiroNome());
            row.createCell((short) intColumn++).setCellValue(usuario.getSobreNome());
            row.createCell((short) intColumn++).setCellValue(usuario.getLogin());
      
        }        
        
		for (int i = 0; i < intColumn; i++) {
			sheet.autoSizeColumn(i,true);
		}

	}
	
	public static void gerarExcelAluno(Sheet sheet, Font font, XSSFCellStyle style ){

		ArrayList<Usuario> listUsuarios = UsuarioServer.getUsuariosPorTipoUsuario(TipoUsuario.ALUNO);
		
		Row row = sheet.createRow((short) 0);		
		
		int intColumn=0;
		row.createCell((short) intColumn++).setCellValue("Tipo Usuário");
		row.createCell((short) intColumn++).setCellValue("Unidade");
		row.createCell((short) intColumn++).setCellValue("Matrícula");
		row.createCell((short) intColumn++).setCellValue("Data Matric");
		row.createCell((short) intColumn++).setCellValue("Registro Aluno");
        row.createCell((short) intColumn++).setCellValue("Primeiro Nome");
        row.createCell((short) intColumn++).setCellValue("Sobre Nome");
        row.createCell((short) intColumn++).setCellValue("Usuário");
        row.createCell((short) intColumn++).setCellValue("Email");        
        row.createCell((short) intColumn++).setCellValue("Data Nascimento");
        row.createCell((short) intColumn++).setCellValue("Sexo");
        row.createCell((short) intColumn++).setCellValue("Endereço");
        row.createCell((short) intColumn++).setCellValue("Num Res");
        row.createCell((short) intColumn++).setCellValue("Bairro");
        row.createCell((short) intColumn++).setCellValue("Cidade");
        row.createCell((short) intColumn++).setCellValue("UF");
        row.createCell((short) intColumn++).setCellValue("Cep");
        row.createCell((short) intColumn++).setCellValue("Tel Celular");
        row.createCell((short) intColumn++).setCellValue("Tel Res");
        row.createCell((short) intColumn++).setCellValue("Tel Com");
        row.createCell((short) intColumn++).setCellValue("CPF");
        row.createCell((short) intColumn++).setCellValue("RG");
        row.createCell((short) intColumn++).setCellValue("Situação do Pais");
        row.createCell((short) intColumn++).setCellValue("Situação do Pais : Outros");
        row.createCell((short) intColumn++).setCellValue("Curso");
        row.createCell((short) intColumn++).setCellValue("Status");

        
		for (int i = 0; i < intColumn; i++) {
			row.getCell((short) i).setCellStyle(style);
		}
		
        for (int i = 0; i < listUsuarios.size(); i++) {

            Usuario usuario = listUsuarios.get(i);

//            ArrayList<Object> listCurso = null;//CursoServer.getCursosPorAlunoAmbienteAluno(usuario, true);
//            String strCursos="";
//            for (int j = 0; j < listCurso.size(); j++) {
//                if(j==0){
//                    strCursos=listCurso.get(j).getNome();
//                }else{
//                    strCursos = strCursos+", "+listCurso.get(j).getNome();
//                }
//            }

            row = sheet.createRow((short) i + 1);

            intColumn = 0;


            row.createCell((short) intColumn++).setCellValue(usuario.getPrimeiroNome());
            row.createCell((short) intColumn++).setCellValue(usuario.getSobreNome());
            row.createCell((short) intColumn++).setCellValue(usuario.getLogin());
       
            row.createCell((short) intColumn++).setCellValue("");



        }     
        
		for (int i = 0; i < intColumn; i++) {
			sheet.autoSizeColumn(i,true);
		}

	}
	
	public static void gerarExcelCoordenador(Sheet sheet, Font font, XSSFCellStyle style ){
		
		ArrayList<Usuario> listUsuarios = UsuarioServer.getUsuariosPorTipoUsuario(TipoUsuario.COORDENADOR);
		
		Row row = sheet.createRow((short) 0);		
		
		int intColumn=0;
		row.createCell((short) intColumn++).setCellValue("Tipo Usuário");
		row.createCell((short) intColumn++).setCellValue("Unidade");
        row.createCell((short) intColumn++).setCellValue("Primeiro Nome");
        row.createCell((short) intColumn++).setCellValue("Sobre Nome");
        row.createCell((short) intColumn++).setCellValue("Usuário");
        row.createCell((short) intColumn++).setCellValue("Email");        
        row.createCell((short) intColumn++).setCellValue("Data Nascimento");
        row.createCell((short) intColumn++).setCellValue("Sexo");
        row.createCell((short) intColumn++).setCellValue("Endereço");
        row.createCell((short) intColumn++).setCellValue("Num Res");
        row.createCell((short) intColumn++).setCellValue("Bairro");
        row.createCell((short) intColumn++).setCellValue("Cidade");
        row.createCell((short) intColumn++).setCellValue("UF");
        row.createCell((short) intColumn++).setCellValue("Cep");
        row.createCell((short) intColumn++).setCellValue("Tel Celular");
        row.createCell((short) intColumn++).setCellValue("Tel Res");
        row.createCell((short) intColumn++).setCellValue("Tel Com");
        row.createCell((short) intColumn++).setCellValue("CPF");
        row.createCell((short) intColumn++).setCellValue("RG");
        row.createCell((short) intColumn++).setCellValue("Status");

        
		for (int i = 0; i < intColumn; i++) {
			row.getCell((short) i).setCellStyle(style);
		}
        
        for(int i=0;i<listUsuarios.size();i++){
        	Usuario usuario = listUsuarios.get(i);
        	row = sheet.createRow((short) i+1);
        	
        	intColumn=0;
       	

            row.createCell((short) intColumn++).setCellValue(usuario.getPrimeiroNome());
            row.createCell((short) intColumn++).setCellValue(usuario.getSobreNome());
            row.createCell((short) intColumn++).setCellValue(usuario.getLogin());

        }        
        
		for (int i = 0; i < intColumn; i++) {
			sheet.autoSizeColumn(i,true);
		}

	}

	public static void gerarExcelProfessor(Sheet sheet, Font font, XSSFCellStyle style ){
		
		ArrayList<Usuario> listUsuarios = UsuarioServer.getUsuariosPorTipoUsuario(TipoUsuario.SALAO);
		
		Row row = sheet.createRow((short) 0);		
		
		int intColumn=0;
		row.createCell((short) intColumn++).setCellValue("Tipo Usuário");
		row.createCell((short) intColumn++).setCellValue("Unidade");
        row.createCell((short) intColumn++).setCellValue("Primeiro Nome");
        row.createCell((short) intColumn++).setCellValue("Sobre Nome");
        row.createCell((short) intColumn++).setCellValue("Usuário");
        row.createCell((short) intColumn++).setCellValue("Email");        
        row.createCell((short) intColumn++).setCellValue("Data Nascimento");
        row.createCell((short) intColumn++).setCellValue("Sexo");
        row.createCell((short) intColumn++).setCellValue("Endereço");
        row.createCell((short) intColumn++).setCellValue("Num Res");
        row.createCell((short) intColumn++).setCellValue("Bairro");
        row.createCell((short) intColumn++).setCellValue("Cidade");
        row.createCell((short) intColumn++).setCellValue("UF");
        row.createCell((short) intColumn++).setCellValue("Cep");
        row.createCell((short) intColumn++).setCellValue("Tel Celular");
        row.createCell((short) intColumn++).setCellValue("Tel Res");
        row.createCell((short) intColumn++).setCellValue("Tel Com");
        row.createCell((short) intColumn++).setCellValue("CPF");
        row.createCell((short) intColumn++).setCellValue("RG");
        row.createCell((short) intColumn++).setCellValue("Status");

        
		for (int i = 0; i < intColumn; i++) {
			row.getCell((short) i).setCellStyle(style);
		}
        
        for(int i=0;i<listUsuarios.size();i++){
        	Usuario usuario = listUsuarios.get(i);
        	row = sheet.createRow((short) i+1);
        	
        	intColumn=0;
       	

            row.createCell((short) intColumn++).setCellValue(usuario.getPrimeiroNome());
            row.createCell((short) intColumn++).setCellValue(usuario.getSobreNome());
            row.createCell((short) intColumn++).setCellValue(usuario.getLogin());

        }        
        
		for (int i = 0; i < intColumn; i++) {
			sheet.autoSizeColumn(i,true);
		}

	}
	
	public static void gerarExcelPais(Sheet sheet, Font font, XSSFCellStyle style ){
		
		ArrayList<Usuario> listUsuarios = UsuarioServer.getUsuariosPorTipoUsuario(TipoUsuario.PAIS);
		
		Row row = sheet.createRow((short) 0);		
		
		int intColumn=0;
		row.createCell((short) intColumn++).setCellValue("Tipo Usuário");
		row.createCell((short) intColumn++).setCellValue("Unidade");
        row.createCell((short) intColumn++).setCellValue("Primeiro Nome");
        row.createCell((short) intColumn++).setCellValue("Sobre Nome");
        row.createCell((short) intColumn++).setCellValue("Usuário");
        row.createCell((short) intColumn++).setCellValue("Email");        
        row.createCell((short) intColumn++).setCellValue("Data Nascimento");        
        row.createCell((short) intColumn++).setCellValue("Sexo");
        row.createCell((short) intColumn++).setCellValue("Tipo Pai");
        row.createCell((short) intColumn++).setCellValue("Endereço");
        row.createCell((short) intColumn++).setCellValue("Num Res");
        row.createCell((short) intColumn++).setCellValue("Bairro");
        row.createCell((short) intColumn++).setCellValue("Cidade");
        row.createCell((short) intColumn++).setCellValue("UF");
        row.createCell((short) intColumn++).setCellValue("Cep");
        row.createCell((short) intColumn++).setCellValue("Tel Celular");
        row.createCell((short) intColumn++).setCellValue("Tel Res");
        row.createCell((short) intColumn++).setCellValue("Tel Com");
        row.createCell((short) intColumn++).setCellValue("CPF");
        row.createCell((short) intColumn++).setCellValue("RG");
        row.createCell((short) intColumn++).setCellValue("Empresa");
        row.createCell((short) intColumn++).setCellValue("Cargo");
        row.createCell((short) intColumn++).setCellValue("Resp Acadêmico");
        row.createCell((short) intColumn++).setCellValue("Resp Financeiro");
        row.createCell((short) intColumn++).setCellValue("Status");
        
		for (int i = 0; i < intColumn; i++) {
			row.getCell((short) i).setCellStyle(style);
		}
        
        for(int i=0;i<listUsuarios.size();i++){
        	Usuario usuario = listUsuarios.get(i);
        	row = sheet.createRow((short) i+1);
        	
        	intColumn=0;
       	

            row.createCell((short) intColumn++).setCellValue(usuario.getPrimeiroNome());
            row.createCell((short) intColumn++).setCellValue(usuario.getSobreNome());
            row.createCell((short) intColumn++).setCellValue(usuario.getLogin());

        }        
        
		for (int i = 0; i < intColumn; i++) {
			sheet.autoSizeColumn(i,true);
		}

	}

	public static void gerarExcelPaisAlunos(Sheet sheet, Font font, XSSFCellStyle style ){
		
		ArrayList<Usuario> listAlunos = UsuarioServer.getUsuariosPorTipoUsuario(TipoUsuario.ALUNO);

		
		Row row = sheet.createRow((short) 0);		
		
		int intColumn=0;
		row.createCell((short) intColumn++).setCellValue("Aluno");
		row.createCell((short) intColumn++).setCellValue("Pais");
		row.createCell((short) intColumn++).setCellValue("Email Pais");
		row.createCell((short) intColumn++).setCellValue("Unidade");
		row.createCell((short) intColumn++).setCellValue("Endereço");
		row.createCell((short) intColumn++).setCellValue("Num Res");
		row.createCell((short) intColumn++).setCellValue("Bairro");
		row.createCell((short) intColumn++).setCellValue("Cidade");
		row.createCell((short) intColumn++).setCellValue("UF");
		row.createCell((short) intColumn++).setCellValue("CEP");
		row.createCell((short) intColumn++).setCellValue("Tel Celular");
		row.createCell((short) intColumn++).setCellValue("Tel Res");
		row.createCell((short) intColumn++).setCellValue("Tel Com");
		row.createCell((short) intColumn++).setCellValue("CPF");
		row.createCell((short) intColumn++).setCellValue("RG");
		row.createCell((short) intColumn++).setCellValue("Curso");
        
		for (int i = 0; i < intColumn; i++) {
			row.getCell((short) i).setCellStyle(style);
		}
        
		int countRow=1;
        for(int i=0;i<listAlunos.size();i++){
        	Usuario aluno = listAlunos.get(i);        	 
        	
//        	 ArrayList<Object> listCurso = null;//CursoServer.getCursosPorAlunoAmbienteAluno(aluno, true);
             String strCursos="";
//             for (int j = 0; j < listCurso.size(); j++) {
//                 if(j==0){
////                     strCursos=listCurso.get(j).getNome();
//                 }else{
//                     strCursos = strCursos+", "+listCurso.get(j).getNome();
//                 }
//             }
        	
        	ArrayList<Usuario> arrayPais = UsuarioServer.getTodosOsPaisDoAluno(aluno.getIdUsuario());
        	for(int cv=0;cv<arrayPais.size();cv++){
        		Usuario pai = arrayPais.get(cv);
        		intColumn=0;
        		row = sheet.createRow((short) countRow++);        		
        		row.createCell((short) intColumn++).setCellValue(aluno.getPrimeiroNome() + " "+ aluno.getSobreNome());
        		row.createCell((short) intColumn++).setCellValue(pai.getPrimeiroNome() + " "+ pai.getSobreNome());

        		row.createCell((short) intColumn++).setCellValue(strCursos);
//        		row.createCell((short) intColumn++).setCellValue(pai.getPrimeiroNome());
        		
        	}
       	
        }        
        
		for (int i = 0; i < intColumn; i++) {
			sheet.autoSizeColumn(i,true);
		}

	}

	
//   public static ArrayList<Ob> importarUsuariosUsandoExcel(String strFileName){
//		Workbook wb;
//
////	    String strMessage;
//	    
//	    ArrayList<UsuarioErroImportar> arrayUsuarioError = new ArrayList<UsuarioErroImportar>();
//	    
//	    int SHEET_ALUNO = 0;
//	    int SHEET_COORDENADOR = 1;
//	    int SHEET_PAIS = 2;
//	    int SHEET_PROFESSOR = 3;
//
//	    
//		try {
//
//			wb = new XSSFWorkbook(strFileName);
//			
////			arrayUsuarioError.addAll(importarAlunos(wb.getSheetAt(SHEET_ALUNO)));
////			arrayUsuarioError.addAll(importarCoordenador(wb.getSheetAt(SHEET_COORDENADOR)));
////			arrayUsuarioError.addAll(importarPais(wb.getSheetAt(SHEET_PAIS)));
////			arrayUsuarioError.addAll(importarProfessor(wb.getSheetAt(SHEET_PROFESSOR)));
//
//			
//		} catch (Exception ex) {
//			System.out.println("Error Read Excel:"+ex.getMessage());
//			arrayUsuarioError=null;
////			strMessage="erro";
//		}
//		
//		return arrayUsuarioError;
//	}
	
//	private static String normalizeCellType(Cell cell) {
//		
//		 switch (cell.getCellType()) {
//		 case Cell.CELL_TYPE_STRING: return cell.getStringCellValue(); 
//		 case Cell.CELL_TYPE_NUMERIC:
//			 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//			 java.util.Date d =  cell.getDateCellValue();
//			 String strDate = df.format(d);
//			 System.out.println("date is :- "+ strDate );
//			 return strDate;
//		 }
//		return "";
//	}
	
	
//	public static ArrayList<UsuarioErroImportar> importarAlunos(Sheet sheet){
//		
//		 ArrayList<UsuarioErroImportar> arrayUsuarioError = new ArrayList<UsuarioErroImportar>();
//		 
//	    Row row;
//	    
//	    System.out.println("Aluno=====================");
//		System.out.println("FirstRowNum:"+sheet.getFirstRowNum());		
//		System.out.println("PhysicalNumberOfRows:"+sheet.getPhysicalNumberOfRows());
//		
//		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
//			
//			String strStatusAddUsuario= "true";
//			String strCamposCorretos = "";
//			
//			row = sheet.getRow(i);
//			
//			
//
//			
//			int cv=0;
//			Cell Registro_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Data_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			String strDataMatricula = normalizeCellType(row.getCell(cv++, Row.CREATE_NULL_AS_BLANK));
//			
//			Cell Registro_Aluno = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Primeiro_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Sobre_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Email = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Usuario = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Senha = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Data_Nascimento = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			String strDataNascimento = normalizeCellType(row.getCell(cv++, Row.CREATE_NULL_AS_BLANK));
//			Cell Sexo = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Endereco = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Num_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Bairro = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Cidade = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Estado = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Cep = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Celular = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Comercial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell CPF = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell RG = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Situacao_dos_Pais = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Situacao_dos_Pais_Outros = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);	
//			Cell cellUnidadeEscola = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			
//			
//			Registro_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
////			Data_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
//			Registro_Aluno.setCellType(Cell.CELL_TYPE_STRING);	
//			Primeiro_Nome.setCellType(Cell.CELL_TYPE_STRING);	
//			Sobre_Nome.setCellType(Cell.CELL_TYPE_STRING);	
//			Email.setCellType(Cell.CELL_TYPE_STRING);	
//			Usuario.setCellType(Cell.CELL_TYPE_STRING);	
//			Senha.setCellType(Cell.CELL_TYPE_STRING);	
////			Data_Nascimento.setCellType(Cell.CELL_TYPE_STRING);	
//			Sexo.setCellType(Cell.CELL_TYPE_STRING);	
//			Endereco.setCellType(Cell.CELL_TYPE_STRING);	
//			Num_Residencial.setCellType(Cell.CELL_TYPE_STRING);	
//			Bairro.setCellType(Cell.CELL_TYPE_STRING);
//			Cidade.setCellType(Cell.CELL_TYPE_STRING);
//			Estado.setCellType(Cell.CELL_TYPE_STRING);
//			Cep.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Celular.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Residencial.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Comercial.setCellType(Cell.CELL_TYPE_STRING);
//			CPF.setCellType(Cell.CELL_TYPE_STRING);
//			RG.setCellType(Cell.CELL_TYPE_STRING);
//			Situacao_dos_Pais.setCellType(Cell.CELL_TYPE_STRING);
//			Situacao_dos_Pais_Outros.setCellType(Cell.CELL_TYPE_STRING);
//			cellUnidadeEscola.setCellType(Cell.CELL_TYPE_STRING);
//			
//			Usuario usuario = new Usuario();
//			
//			String senha="";				
//			if((Senha==null)|| (Senha.getStringCellValue().isEmpty())){
//				senha = ConfigJornada.getProperty("config.senha.inicial");
//			}
//			String senhaHashed = BCrypt.hashpw(senha, BCrypt.gensalt());
//			
//			
//			if(Primeiro_Nome.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Primeiro Nome � obrigat�rio || ";
//			}
//			if(Sobre_Nome.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Sobre Nome � obrigat�rio || ";
//			}
////			if(Email.getStringCellValue().isEmpty()){
////				strCamposCorretos+="Campo Email � obrigat�rio || ";
////			}
//			if(Usuario.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Usuario � obrigat�rio || ";
//			}			
//						
//			Date dataMatricula = MpUtilServer.convertStringToDate(strDataMatricula,"dd/MM/yyyy");
//			String strValidateDataMatricula = MpUtilServer.isThisDateValid(strDataMatricula, "dd/MM/yyyy");
//            if (strDataMatricula == null || strDataMatricula.isEmpty()) {
//                dataMatricula = null;
//            } else {
//                if (!strValidateDataMatricula.equals("true")) {
//                    strCamposCorretos += "Data Matricula " + strValidateDataMatricula + " || ";
//                }
//            }
//			
//			Date dataNascimento = MpUtilServer.convertStringToDate(strDataNascimento,"dd/MM/yyyy");
//			String strValidateDataNascimento = MpUtilServer.isThisDateValid(strDataNascimento, "dd/MM/yyyy");
//            if (strDataNascimento == null || strDataNascimento.isEmpty()) {
//                dataNascimento = null;
//            } else {
//                if (!strValidateDataNascimento.equals("true")) {
//                    strCamposCorretos += "Data Matricula " + strValidateDataNascimento + " || ";
//                }
//            }
//
//			TipoUsuario tipoUsuario = UsuarioServer.getTipoUsuarioPorId(TipoUsuario.ALUNO); 
////			UnidadeEscola unidEscola = UnidadeEscolaServer.getUnidadeEscola(cellUnidadeEscola.getStringCellValue());
//			
//			usuario.setIdTipoUsuario(tipoUsuario.getIdTipoUsuario());
//
//			usuario.setPrimeiroNome( (Primeiro_Nome==null) ? "" : Primeiro_Nome.getStringCellValue() );	
//			usuario.setSobreNome( (Sobre_Nome==null) ? "" : Sobre_Nome.getStringCellValue() );	
//
//			usuario.setLogin( (Usuario==null) ? "" : Usuario.getStringCellValue() );	
//			usuario.setSenha(senhaHashed);	
//	
////			arrayUsuario.add(usuario);
//			
//
//			if (strCamposCorretos.isEmpty()) {
//				strStatusAddUsuario = UsuarioServer.AdicionarUsuario(usuario);
//			}else{
//				strStatusAddUsuario = strCamposCorretos;
//			}
//			
//			if(!strStatusAddUsuario.equals("true")){				
//				
//				UsuarioErroImportar usuarioErroImportar = new UsuarioErroImportar();
//				
//				usuarioErroImportar.setIdTipoUsuario(usuario.getIdTipoUsuario());
//				usuarioErroImportar.setPrimeiroNome(usuario.getPrimeiroNome());
//				usuarioErroImportar.setSobreNome(usuario.getSobreNome());
//
//				usuarioErroImportar.setLogin(usuario.getLogin());
//				usuarioErroImportar.setErroImportar(strStatusAddUsuario);
//				if( usuario.getPrimeiroNome().isEmpty()
//					&&usuario.getSobreNome().isEmpty()
//
//					&&usuario.getLogin().isEmpty())
//				{
//					//there is no information to populate
//				}else{
//					arrayUsuarioError.add(usuarioErroImportar);								
//				}
//
//			}
//
//		}
//		
//		return arrayUsuarioError;
//
//	}
	
//	public static ArrayList<UsuarioErroImportar> importarCoordenador(Sheet sheet){
//	    
//
//		
//		ArrayList<UsuarioErroImportar> arrayUsuarioError = new ArrayList<UsuarioErroImportar>();
//		 
//	    Row row;
//		
//	    System.out.println("Coordenador=====================");
//		System.out.println("FirstRowNum:"+sheet.getFirstRowNum());		
//		System.out.println("PhysicalNumberOfRows:"+sheet.getPhysicalNumberOfRows());
//		
//		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
//			
//			String strStatusAddUsuario= "true";
//			String strCamposCorretos = "";
//			
//			row = sheet.getRow(i);
//			int cv=0;
////			Cell Registro_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Data_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Registro_Aluno = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Primeiro_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Sobre_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Email = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Usuario = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Senha = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Data_Nascimento = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			String strDataNascimento = normalizeCellType(row.getCell(cv++, Row.CREATE_NULL_AS_BLANK));
//			Cell Sexo = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Endereco = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Num_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Bairro = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Cidade = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Estado = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Cep = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Celular = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Comercial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell CPF = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell RG = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell cellUnidadeEscola = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Situacao_dos_Pais = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Situacao_dos_Pais_Outros = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);			
//			
//			
////			Registro_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
////			Data_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
////			Registro_Aluno.setCellType(Cell.CELL_TYPE_STRING);	
//			Primeiro_Nome.setCellType(Cell.CELL_TYPE_STRING);	
//			Sobre_Nome.setCellType(Cell.CELL_TYPE_STRING);	
//			Email.setCellType(Cell.CELL_TYPE_STRING);	
//			Usuario.setCellType(Cell.CELL_TYPE_STRING);	
//			Senha.setCellType(Cell.CELL_TYPE_STRING);	
////			Data_Nascimento.setCellType(Cell.CELL_TYPE_STRING);	
//			Sexo.setCellType(Cell.CELL_TYPE_STRING);	
//			Endereco.setCellType(Cell.CELL_TYPE_STRING);	
//			Num_Residencial.setCellType(Cell.CELL_TYPE_STRING);	
//			Bairro.setCellType(Cell.CELL_TYPE_STRING);
//			Cidade.setCellType(Cell.CELL_TYPE_STRING);
//			Estado.setCellType(Cell.CELL_TYPE_STRING);
//			Cep.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Celular.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Residencial.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Comercial.setCellType(Cell.CELL_TYPE_STRING);
//			CPF.setCellType(Cell.CELL_TYPE_STRING);
//			RG.setCellType(Cell.CELL_TYPE_STRING);
//			cellUnidadeEscola.setCellType(Cell.CELL_TYPE_STRING);
////			Situacao_dos_Pais.setCellType(Cell.CELL_TYPE_STRING);
////			Situacao_dos_Pais_Outros.setCellType(Cell.CELL_TYPE_STRING);
//			
//			Usuario usuario = new Usuario();
//			
//			String senha="";				
//			if((Senha==null)|| (Senha.getStringCellValue().isEmpty())){
//				senha = ConfigJornada.getProperty("config.senha.inicial");
//			}
//			String senhaHashed = BCrypt.hashpw(senha, BCrypt.gensalt());
//			
//			
//			if(Primeiro_Nome.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Primeiro Nome � obrigat�rio || ";
//			}
//			if(Sobre_Nome.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Sobre Nome � obrigat�rio || ";
//			}
////			if(Email.getStringCellValue().isEmpty()){
////				strCamposCorretos+="Campo Email � obrigat�rio || ";
////			}
//			if(Usuario.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Usuario � obrigat�rio || ";
//			}			
//			
//
//
////			String strDataMatricula = Data_Matricula.getStringCellValue();
////			Date dataMatricula = MpUtilServer.convertStringToDate(strDataMatricula,"dd/MM/yyyy");
////			String strValidateDataMatricula = MpUtilServer.isThisDateValid(strDataMatricula, "dd/MM/yyyy");
////			if(!strValidateDataMatricula.equals("true")){
////				strCamposCorretos+="Data Matricula "+strValidateDataMatricula +" || ";
////			}
//			
//			
//			
//			Date dataNascimento = MpUtilServer.convertStringToDate(strDataNascimento,"dd/MM/yyyy");
//			String strValidateDataNascimento = MpUtilServer.isThisDateValid(strDataNascimento, "dd/MM/yyyy");
//            if (strDataNascimento == null || strDataNascimento.isEmpty()) {
//                dataNascimento = null;
//            } else {
//                if (!strValidateDataNascimento.equals("true")) {
//                    strCamposCorretos += "Data Matricula " + strValidateDataNascimento + " || ";
//                }
//            }
//			
//			TipoUsuario tipoUsuario = UsuarioServer.getTipoUsuarioPorId(TipoUsuario.COORDENADOR); 
////			UnidadeEscola unidEscola = UnidadeEscolaServer.getUnidadeEscola(cellUnidadeEscola.getStringCellValue());
//			
//			usuario.setIdTipoUsuario(tipoUsuario.getIdTipoUsuario());
//			usuario.setPrimeiroNome( (Primeiro_Nome==null) ? "" : Primeiro_Nome.getStringCellValue() );	
//			usuario.setSobreNome( (Sobre_Nome==null) ? "" : Sobre_Nome.getStringCellValue() );	
//
//			usuario.setLogin( (Usuario==null) ? "" : Usuario.getStringCellValue() );	
//			usuario.setSenha(senhaHashed);	
//
//			if (strCamposCorretos.isEmpty()) {
//				strStatusAddUsuario = UsuarioServer.AdicionarUsuario(usuario);
//			}else{
//				strStatusAddUsuario = strCamposCorretos;
//			}
//			
//			if(!strStatusAddUsuario.equals("true")){				
//				
//				UsuarioErroImportar usuarioErroImportar = new UsuarioErroImportar();
//				
//				usuarioErroImportar.setIdTipoUsuario(usuario.getIdTipoUsuario());
//
//				usuarioErroImportar.setPrimeiroNome(usuario.getPrimeiroNome());
//				usuarioErroImportar.setSobreNome(usuario.getSobreNome());
//
//				usuarioErroImportar.setLogin(usuario.getLogin());
//				usuarioErroImportar.setErroImportar(strStatusAddUsuario);
//				
//				if(usuario.getPrimeiroNome().isEmpty()&& 
//				   usuario.getSobreNome().isEmpty() && 
//				   usuario.getLogin().isEmpty())
//				{
//				 //there is no information to populate								
//				}else{
//					arrayUsuarioError.add(usuarioErroImportar);								
//				}
//			}
//
//		}
//		
//		return arrayUsuarioError;
//
//	}

//	public static ArrayList<UsuarioErroImportar> importarPais(Sheet sheet){
//	    
//
//		
//		ArrayList<UsuarioErroImportar> arrayUsuarioError = new ArrayList<UsuarioErroImportar>();
//		 
//	    Row row;
//		
//	    System.out.println("Pais=====================");
//		System.out.println("FirstRowNum:"+sheet.getFirstRowNum());		
//		System.out.println("PhysicalNumberOfRows:"+sheet.getPhysicalNumberOfRows());
//		
//		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
//			
//			String strStatusAddUsuario= "true";
//			String strCamposCorretos = "";
//			
//			row = sheet.getRow(i);
//			int cv=0;
////			Cell Registro_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Data_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Registro_Aluno = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Primeiro_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Sobre_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Email = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Usuario = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Senha = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Data_Nascimento = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			String strDataNascimento = normalizeCellType(row.getCell(cv++, Row.CREATE_NULL_AS_BLANK));
//			Cell Sexo = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell TipoPais = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Endereco = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Num_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Bairro = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Cidade = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Estado = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Cep = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Celular = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Comercial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell CPF = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell RG = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Empresa = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Cargo = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell RespAcademico = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell RespFinanceiro = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell cellUnidadeEscola = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Situacao_dos_Pais = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Situacao_dos_Pais_Outros = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);			
//			
//			
////			Registro_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
////			Data_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
////			Registro_Aluno.setCellType(Cell.CELL_TYPE_STRING);	
//			Primeiro_Nome.setCellType(Cell.CELL_TYPE_STRING);	
//			Sobre_Nome.setCellType(Cell.CELL_TYPE_STRING);	
//			Email.setCellType(Cell.CELL_TYPE_STRING);	
//			Usuario.setCellType(Cell.CELL_TYPE_STRING);	
//			Senha.setCellType(Cell.CELL_TYPE_STRING);	
////			Data_Nascimento.setCellType(Cell.CELL_TYPE_STRING);	
//			Sexo.setCellType(Cell.CELL_TYPE_STRING);	
//			TipoPais.setCellType(Cell.CELL_TYPE_STRING);
//			Endereco.setCellType(Cell.CELL_TYPE_STRING);	
//			Num_Residencial.setCellType(Cell.CELL_TYPE_STRING);	
//			Bairro.setCellType(Cell.CELL_TYPE_STRING);
//			Cidade.setCellType(Cell.CELL_TYPE_STRING);
//			Estado.setCellType(Cell.CELL_TYPE_STRING);
//			Cep.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Celular.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Residencial.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Comercial.setCellType(Cell.CELL_TYPE_STRING);
//			CPF.setCellType(Cell.CELL_TYPE_STRING);
//			RG.setCellType(Cell.CELL_TYPE_STRING);
//			Empresa.setCellType(Cell.CELL_TYPE_STRING);
//			Cargo.setCellType(Cell.CELL_TYPE_STRING);
//			RespAcademico.setCellType(Cell.CELL_TYPE_STRING);
//			RespFinanceiro.setCellType(Cell.CELL_TYPE_STRING);
//			cellUnidadeEscola.setCellType(Cell.CELL_TYPE_STRING);
//			
////			Situacao_dos_Pais.setCellType(Cell.CELL_TYPE_STRING);
////			Situacao_dos_Pais_Outros.setCellType(Cell.CELL_TYPE_STRING);
//			
//			Usuario usuario = new Usuario();
//			
//			String senha="";				
//			if((Senha==null)|| (Senha.getStringCellValue().isEmpty())){
//				senha = ConfigJornada.getProperty("config.senha.inicial");
//			}
//			String senhaHashed = BCrypt.hashpw(senha, BCrypt.gensalt());
//			
//			
//			if(Primeiro_Nome.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Primeiro Nome � obrigat�rio || ";
//			}
//			if(Sobre_Nome.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Sobre Nome � obrigat�rio || ";
//			}
////			if(Email.getStringCellValue().isEmpty()){
////				strCamposCorretos+="Campo Email � obrigat�rio || ";
////			}
//			if(Usuario.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Usuario � obrigat�rio || ";
//			}
//			
//
//			boolean isRespAcademico=false;
//			if(RespAcademico.getStringCellValue().equals("Sim")){
//				isRespAcademico=true;
//			}
//
//			boolean isRespFinanceiro=false;
//			if(RespFinanceiro.getStringCellValue().equals("Sim")){
//				isRespFinanceiro=true;
//			}
//
////			String strDataMatricula = Data_Matricula.getStringCellValue();
////			Date dataMatricula = MpUtilServer.convertStringToDate(strDataMatricula,"dd/MM/yyyy");
////			String strValidateDataMatricula = MpUtilServer.isThisDateValid(strDataMatricula, "dd/MM/yyyy");
////			if(!strValidateDataMatricula.equals("true")){
////				strCamposCorretos+="Data Matricula "+strValidateDataMatricula +" || ";
////			}
//			
//			
//			Date dataNascimento = MpUtilServer.convertStringToDate(strDataNascimento,"dd/MM/yyyy");
//			String strValidateDataNascimento = MpUtilServer.isThisDateValid(strDataNascimento, "dd/MM/yyyy");
//            if (strDataNascimento == null || strDataNascimento.isEmpty()) {
//                dataNascimento = null;
//            } else {
//                if (!strValidateDataNascimento.equals("true")) {
//                    strCamposCorretos += "Data Matricula " + strValidateDataNascimento + " || ";
//                }
//            }
//            
//			TipoUsuario tipoUsuario = UsuarioServer.getTipoUsuarioPorId(TipoUsuario.PAIS); 
////			UnidadeEscola unidEscola = UnidadeEscolaServer.getUnidadeEscola(cellUnidadeEscola.getStringCellValue());
//			
//			usuario.setIdTipoUsuario(tipoUsuario.getIdTipoUsuario());
//
//
//			usuario.setPrimeiroNome( (Primeiro_Nome==null) ? "" : Primeiro_Nome.getStringCellValue() );	
//			usuario.setSobreNome( (Sobre_Nome==null) ? "" : Sobre_Nome.getStringCellValue() );	
//
//			usuario.setLogin( (Usuario==null) ? "" : Usuario.getStringCellValue() );	
//			usuario.setSenha(senhaHashed);	
//
//			
////			usuario.setSituacaoResponsaveis( (Situacao_dos_Pais==null) ? "" : Situacao_dos_Pais.getStringCellValue() );
////			usuario.setSituacaoResponsaveisOutros( (Situacao_dos_Pais_Outros==null) ? "" : Situacao_dos_Pais_Outros.getStringCellValue() );			
//			
//			if (strCamposCorretos.isEmpty()) {
//				strStatusAddUsuario = UsuarioServer.AdicionarUsuario(usuario);
//			}else{
//				strStatusAddUsuario = strCamposCorretos;
//			}
//			
//			if(!strStatusAddUsuario.equals("true")){				
//				
//				UsuarioErroImportar usuarioErroImportar = new UsuarioErroImportar();
//				
//				usuarioErroImportar.setIdTipoUsuario(usuario.getIdTipoUsuario());
//
//				usuarioErroImportar.setPrimeiroNome(usuario.getPrimeiroNome());
//				usuarioErroImportar.setSobreNome(usuario.getSobreNome());
//
//				usuarioErroImportar.setLogin(usuario.getLogin());
//				usuarioErroImportar.setErroImportar(strStatusAddUsuario);
//				
//				if( usuario.getPrimeiroNome().isEmpty()
//						&&usuario.getSobreNome().isEmpty()
//						&&usuario.getLogin().isEmpty())
//					{
//						//there is no information to populate
//					}else{
//						arrayUsuarioError.add(usuarioErroImportar);								
//					}
//				
//
//			}
//
//		}
//		
//		return arrayUsuarioError;
//
//	}

//	public static ArrayList<UsuarioErroImportar> importarProfessor(Sheet sheet){
//	    
//		
//		
//		ArrayList<UsuarioErroImportar> arrayUsuarioError = new ArrayList<UsuarioErroImportar>();
//		 
//	    Row row;
//		
//	    System.out.println("Professor=====================");
//	    System.out.println("FirstRowNum:"+sheet.getFirstRowNum());		
//		System.out.println("PhysicalNumberOfRows:"+sheet.getPhysicalNumberOfRows());
//		
//		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
//			
//			String strStatusAddUsuario= "true";
//			String strCamposCorretos = "";
//			
//			row = sheet.getRow(i);
//			int cv=0;
////			Cell Registro_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Data_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Registro_Aluno = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Primeiro_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Sobre_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Email = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Usuario = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Senha = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Data_Nascimento = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			String strDataNascimento = normalizeCellType(row.getCell(cv++, Row.CREATE_NULL_AS_BLANK));
//			Cell Sexo = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Endereco = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Num_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Bairro = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Cidade = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Estado = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Cep = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Celular = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Tel_Comercial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell CPF = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell RG = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell cellUnidadeEscola = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Situacao_dos_Pais = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
////			Cell Situacao_dos_Pais_Outros = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);			
//			
//			
////			Registro_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
////			Data_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
////			Registro_Aluno.setCellType(Cell.CELL_TYPE_STRING);	
//			Primeiro_Nome.setCellType(Cell.CELL_TYPE_STRING);	
//			Sobre_Nome.setCellType(Cell.CELL_TYPE_STRING);	
//			Email.setCellType(Cell.CELL_TYPE_STRING);	
//			Usuario.setCellType(Cell.CELL_TYPE_STRING);	
//			Senha.setCellType(Cell.CELL_TYPE_STRING);	
////			Data_Nascimento.setCellType(Cell.CELL_TYPE_STRING);	
//			Sexo.setCellType(Cell.CELL_TYPE_STRING);	
//			Endereco.setCellType(Cell.CELL_TYPE_STRING);	
//			Num_Residencial.setCellType(Cell.CELL_TYPE_STRING);	
//			Bairro.setCellType(Cell.CELL_TYPE_STRING);
//			Cidade.setCellType(Cell.CELL_TYPE_STRING);
//			Estado.setCellType(Cell.CELL_TYPE_STRING);
//			Cep.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Celular.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Residencial.setCellType(Cell.CELL_TYPE_STRING);
//			Tel_Comercial.setCellType(Cell.CELL_TYPE_STRING);
//			CPF.setCellType(Cell.CELL_TYPE_STRING);
//			RG.setCellType(Cell.CELL_TYPE_STRING);
//			cellUnidadeEscola.setCellType(Cell.CELL_TYPE_STRING);
////			Situacao_dos_Pais.setCellType(Cell.CELL_TYPE_STRING);
////			Situacao_dos_Pais_Outros.setCellType(Cell.CELL_TYPE_STRING);
//			
//			Usuario usuario = new Usuario();
//			
//			String senha="";				
//			if((Senha==null)|| (Senha.getStringCellValue().isEmpty())){
//				senha = ConfigJornada.getProperty("config.senha.inicial");
//			}
//			String senhaHashed = BCrypt.hashpw(senha, BCrypt.gensalt());
//			
//			
//			if(Primeiro_Nome.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Primeiro Nome � obrigat�rio || ";
//			}
//			if(Sobre_Nome.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Sobre Nome � obrigat�rio || ";
//			}
////			if(Email.getStringCellValue().isEmpty()){
////				strCamposCorretos+="Campo Email � obrigat�rio || ";
////			}
//			if(Usuario.getStringCellValue().isEmpty()){
//				strCamposCorretos+="Campo Usuario � obrigat�rio || ";
//			}			
//			
//
//
////			String strDataMatricula = Data_Matricula.getStringCellValue();
////			Date dataMatricula = MpUtilServer.convertStringToDate(strDataMatricula,"dd/MM/yyyy");
////			String strValidateDataMatricula = MpUtilServer.isThisDateValid(strDataMatricula, "dd/MM/yyyy");
////			if(!strValidateDataMatricula.equals("true")){
////				strCamposCorretos+="Data Matricula "+strValidateDataMatricula +" || ";
////			}
//            
//            Date dataNascimento = MpUtilServer.convertStringToDate(strDataNascimento, "dd/MM/yyyy");
//            String strValidateDataNascimento = MpUtilServer.isThisDateValid(strDataNascimento, "dd/MM/yyyy");
//            if (strDataNascimento == null || strDataNascimento.isEmpty()) {
//                dataNascimento = null;
//            } else {
//                if (!strValidateDataNascimento.equals("true")) {
//                    strCamposCorretos += "Data Matricula " + strValidateDataNascimento + " || ";
//                }
//            }
//			
//			TipoUsuario tipoUsuario = UsuarioServer.getTipoUsuarioPorId(TipoUsuario.PROFESSOR);
////			UnidadeEscola unidEscola = UnidadeEscolaServer.getUnidadeEscola(cellUnidadeEscola.getStringCellValue());
//			
//			usuario.setIdTipoUsuario(tipoUsuario.getIdTipoUsuario());
//			usuario.setPrimeiroNome( (Primeiro_Nome==null) ? "" : Primeiro_Nome.getStringCellValue() );	
//			usuario.setSobreNome( (Sobre_Nome==null) ? "" : Sobre_Nome.getStringCellValue() );	
//
//			usuario.setLogin( (Usuario==null) ? "" : Usuario.getStringCellValue() );	
//			usuario.setSenha(senhaHashed);	
//
//			if (strCamposCorretos.isEmpty()) {
//				strStatusAddUsuario = UsuarioServer.AdicionarUsuario(usuario);
//			}else{
//				strStatusAddUsuario = strCamposCorretos;
//			}
//			
//			if(!strStatusAddUsuario.equals("true")){				
//				
//				UsuarioErroImportar usuarioErroImportar = new UsuarioErroImportar();
//				
//				usuarioErroImportar.setIdTipoUsuario(usuario.getIdTipoUsuario());
//
//				usuarioErroImportar.setPrimeiroNome(usuario.getPrimeiroNome());
//				usuarioErroImportar.setSobreNome(usuario.getSobreNome());
//
//				usuarioErroImportar.setLogin(usuario.getLogin());
//				usuarioErroImportar.setErroImportar(strStatusAddUsuario);
//				
//				if( usuario.getPrimeiroNome().isEmpty()
//						&&usuario.getSobreNome().isEmpty()
//						&&usuario.getLogin().isEmpty())
//					{
//						//there is no information to populate
//					}else{
//						arrayUsuarioError.add(usuarioErroImportar);								
//					}
//
//			}
//
//		}
//		
//		return arrayUsuarioError;
//
//	}

	
	public static ArrayList<Usuario> getTodosUsuarios() {
	    ArrayList<Usuario> data = new ArrayList<Usuario>();
//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();

        try 
        {

//          dataBase.createConnection();            
//          Connection connection = dataBase.getConnection();

            PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_TODOS_USUARIOS);
            
            
            data = getUserParametersNew(ps.executeQuery());

        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return data;
	}
	
	public static ArrayList<Usuario> getUsuarios() {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ALL);
			
			
			data = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	

	public static ArrayList<Usuario> getUsuarios(String strFilter) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ILIKE);
			
			int count=0;
			ps.setString(++count, strFilter);
			
			data = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	
	   public static ArrayList<Usuario> getUsuariosFieldLike(String strDBField, String strFilter) {

	        ArrayList<Usuario> data = new ArrayList<Usuario>();
//	      JornadaDataBase dataBase = new JornadaDataBase();
	        Connection connection = ConnectionManager.getConnection();

	        try 
	        {
	            
	            String strQuery = UsuarioServer.DB_SELECT_USUARIOS_NEW;
	            strQuery = strQuery.replace("<change>", strDBField);

//	          dataBase.createConnection();            
//	          Connection connection = dataBase.getConnection();
	            PreparedStatement ps = connection.prepareStatement(strQuery);
	            
	            int count=0;
//	          ps.setString(++count, strDBField);
	            ps.setString(++count, strFilter);
	            
	            data = getUserParametersNew(ps.executeQuery());

	        } catch (SQLException sqlex) {
	            data=null;
	            System.err.println(sqlex.getMessage());
	        } finally {
//	          dataBase.close();
	            ConnectionManager.closeConnection(connection);
	        }

	        return data;

	    }
	
	@Deprecated
	public static ArrayList<Usuario> getUsuarios(String strDBField, String strFilter) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{
			
			String strQuery = UsuarioServer.DB_SELECT_DB_FIELD_ILIKE;
			strQuery = strQuery.replace("<change>", strDBField);

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(strQuery);
			
			int count=0;
//			ps.setString(++count, strDBField);
			ps.setString(++count, strFilter);
			
			data = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}		
	
	
	
	
	public static Usuario getUsuarioPeloLogin(String strLogin) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
		
//		JornadaDataBase dataBase = new JornadaDataBase();
		System.out.println("getUsuarioPeloLogin ...");
		Connection connection = ConnectionManager.getConnection();
		System.out.println("Connection connection = ConnectionManager.getConnection();");
		try 
		{
	
			
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_LOGIN);
			System.out.println("PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_LOGIN);");
			
			int count=0;
			ps.setString(++count, strLogin);
			System.out.println("ps.setString(++count, strLogin);");
			
			data = getUserParameters(ps.executeQuery());
			System.out.println("data = getUserParameters(ps.executeQuery());");

		} catch (Exception sqlex) {
			//data=null;
		    System.out.println("Exception sqlex:"+sqlex.getMessage());
			return null;
		} finally {
			ConnectionManager.closeConnection(connection);
//			dataBase.close();
		}
		System.out.println("return data.get(0)");
		return data.get(0);

	}		
	
	public static Usuario getUsuarioPeloId(int idUsuario) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_ID);
			
			int count=0;
			ps.setInt(++count, idUsuario);
			
			data = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {			
			System.err.println(sqlex.getMessage());
			return null;
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		if(data.size()>0){
			return data.get(0);
		}
		else
		{
			return null;
		}


	}		
	
	public static ArrayList<Usuario> getAlunosPorCurso(int idCurso, String strFiltroUsuario) {
		
		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
			
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ILIKE_FILTRADO_POR_CURSO);
			
			int count=0;
			ps.setInt(++count, idCurso);
			ps.setString(++count, strFiltroUsuario);
			ps.setString(++count, strFiltroUsuario);
			
			data = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}		
		
	public static ArrayList<Usuario> getAlunosPorCurso(int idCurso) {
		
		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
			
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_FILTRADO_POR_CURSO);
			
			int count=0;
			ps.setInt(++count, idCurso);
			
			data = getUserParametersNew(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}		
	
	public static ArrayList<Usuario> getUsuariosPorCursoAmbientePai(Usuario usuarioPai, int idCurso) {
		
		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
			
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ALUNO_FILTRADO_POR_CURSO_AMBIENTE_PAI);
			
			int count=0;
			ps.setInt(++count, idCurso);
			ps.setInt(++count, usuarioPai.getIdUsuario());
			
			data = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}		
	
	public static ArrayList<Usuario> getUsuariosPorOcorrencia(int idOcorrencia) {
		
		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
			
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_FILTRADO_POR_OCORRENCIA);
			
			int count=0;
			ps.setInt(++count, idOcorrencia);
			
			data = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}				
	
	public static ArrayList<Usuario> getUsuariosPorTipoUsuario(int id_tipo_usuario, String strFilter) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ILIKE_TIPO_USUARIO);
			
			int count=0;
			ps.setInt(++count, id_tipo_usuario);
			ps.setString(++count, strFilter);
			ps.setString(++count, strFilter);			
			
			data = getUserParameters(ps.executeQuery());
			
		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	
	public static ArrayList<Usuario> getUsuariosPorTipoUsuario(int id_tipo_usuario) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_PELO_TIPO_USUARIO);
			
			int count=0;
			ps.setInt(++count, id_tipo_usuario);
				
			
			data = getUserParametersNew(ps.executeQuery());
			
		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	
    public static ArrayList<Usuario> getUsuariosPorTipoUsuario(int idTipoUsuario, int idUnidadeEscola) {

        ArrayList<Usuario> data = new ArrayList<Usuario>();
//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();
        try 
        {

//          dataBase.createConnection();            
//          Connection connection = dataBase.getConnection();
            PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_PELO_TIPO_USUARIO_UNIDADE);
            
            int count=0;
            ps.setInt(++count, idTipoUsuario);
            ps.setInt(++count, idUnidadeEscola);
                
            
            data = getUserParameters(ps.executeQuery());
            
        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }   	
	
    public static ArrayList<Usuario> getFilhoDoPaiAmbientePais(int idUsuarioPai) {

        ArrayList<Usuario> data = new ArrayList<Usuario>();
//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();
        try 
        {

//          dataBase.createConnection();            
//          Connection connection = dataBase.getConnection();
            PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_PELO_TIPO_USUARIO_AMBIENTE_PAI);
            
            int count=0;
            ps.setInt(++count, TipoUsuario.ALUNO);
            ps.setInt(++count, idUsuarioPai);
                
            
            data = getUserParameters(ps.executeQuery());
            
        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }   	
	
	
	public static ArrayList<Usuario> getFilhoDoPaiAmbientePais(Usuario usuarioPai) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_PELO_TIPO_USUARIO_AMBIENTE_PAI);
			
			int count=0;
			ps.setInt(++count, TipoUsuario.ALUNO);
			ps.setInt(++count, usuarioPai.getIdUsuario());
				
			
			data = getUserParameters(ps.executeQuery());
			
		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	
    public static ArrayList<Usuario> getFilhoDoPaiPorCurso(Usuario usuarioPai, int idCurso) {

        ArrayList<Usuario> data = new ArrayList<Usuario>();
//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();
        try 
        {

//          dataBase.createConnection();            
//          Connection connection = dataBase.getConnection();
            PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ALUNO_PELO_PAI_CURSO);
            
            int count=0;
            ps.setInt(++count, usuarioPai.getIdUsuario());
            ps.setInt(++count, idCurso);
                
            
            data = getUserParameters(ps.executeQuery());
            
        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return data;

    }   	
	
	public static ArrayList<TipoUsuario> getTipoUsuarios() {

		ArrayList<TipoUsuario> data = new ArrayList<TipoUsuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ALL_TIPO_USUARIOS);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				TipoUsuario current = new TipoUsuario();

				current.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
				current.setNomeTipoUsuario(rs.getString("nome_tipo_usuario"));

				data.add(current);
			}

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	
	public static TipoUsuario getTipoUsuario(String nomeTipoUsuario) {

		TipoUsuario current=null;

		Connection connection = ConnectionManager.getConnection();
		try 
		{

			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_TIPO_USUARIOS_POR_NOME);

			ps.setString(1, nomeTipoUsuario);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				 current = new TipoUsuario();

				current.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
				current.setNomeTipoUsuario(rs.getString("nome_tipo_usuario"));

			}

		} catch (SQLException sqlex) {
			current=null;
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(connection);
		}

		return current;

	}	
	
	public static TipoUsuario getTipoUsuarioPorId(int idTipoUsuario) {

		TipoUsuario current=null;

		Connection connection = ConnectionManager.getConnection();
		try 
		{

			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_TIPO_USUARIOS_POR_ID);

			ps.setInt(1, idTipoUsuario);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				 current = new TipoUsuario();

				current.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
				current.setNomeTipoUsuario(rs.getString("nome_tipo_usuario"));

			}

		} catch (SQLException sqlex) {
			current=null;
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(connection);
		}

		return current;

	}
		
	public static ArrayList<Usuario> getUserParameters(ResultSet rs){

		ArrayList<Usuario> data = new ArrayList<Usuario>();
		
		try{
		
		while (rs.next()) 
		{
			Usuario usuario = new Usuario();			
			usuario.setIdUsuario(rs.getInt("id_usuario"));

			usuario.setPrimeiroNome(rs.getString("primeiro_nome"));
			usuario.setSobreNome(rs.getString("sobre_nome"));

			usuario.setLogin(rs.getString("login"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
			usuario.setPrimeiroLogin(rs.getBoolean("primeiro_login"));

			usuario.setObservacao(rs.getString("observacao"));

		
			data.add(usuario);
		}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}
		
		return data;

	}
	
	public static ArrayList<Usuario> getUserParametersNew(ResultSet rs){

        ArrayList<Usuario> data = new ArrayList<Usuario>();
        
        try{
        
        while (rs.next()) 
        {
            Usuario usuario = new Usuario();            
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setPrimeiroNome(rs.getString("primeiro_nome"));
            usuario.setSobreNome(rs.getString("sobre_nome"));
            usuario.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
            usuario.setLogin(rs.getString("login"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setPrimeiroLogin(rs.getBoolean("primeiro_login"));
            usuario.setPrimeiroLogin(rs.getBoolean("primeiro_login"));
            usuario.setObservacao(rs.getString("observacao"));



            data.add(usuario);
        }
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        
        return data;

    }
	
	public static boolean associarPaisAoAluno(int id_aluno, ArrayList<String> list_id_pais){

		boolean success = false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			deleteAssociacaoPaisAoAluno(connection, id_aluno);
			insertAssociacaoPaisAoAluno(connection, id_aluno, list_id_pais);
			success=true;

		} catch (Exception ex) {
			success = false;
			System.err.println(ex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return success;
	}		
	
	private static int deleteAssociacaoPaisAoAluno(Connection conn, int id_aluno){
		
		int count = 0;
		int deleted = 0;

		try {
			
			Connection connection = conn;

			PreparedStatement psDelete = connection.prepareStatement(UsuarioServer.DB_DELETE_REL_PAI_ALUNO);
			psDelete.setInt(++count, id_aluno);
			deleted = psDelete.executeUpdate();

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			
		}
		
		return deleted;
		
	}	
	
	private static int insertAssociacaoPaisAoAluno(Connection conn, int id_aluno, ArrayList<String> list_id_pais){
		
		int count = 0;
		int intAddedItems = 0;

		try {
			
			Connection connection = conn;
			
			//"INSERT INTO rel_pai_aluno (id_usuario_pais, id_usuario_aluno) VALUES (?,?)";	

			for(int i=0;i<list_id_pais.size();i++){
				count=0;
				int id_usuario_pais = Integer.parseInt(list_id_pais.get(i));
				PreparedStatement psInsert = connection.prepareStatement(UsuarioServer.DB_INSERT_REL_PAI_ALUNO);
				psInsert.setInt(++count, id_usuario_pais);				
				psInsert.setInt(++count, id_aluno);	
				intAddedItems=psInsert.executeUpdate();					

			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
		}
		
		return intAddedItems;
		
	}		
		
	public static ArrayList<Usuario> getTodosOsPaisDoAluno(int id_aluno) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ALL_PAIS_DO_ALUNO);
			
			int count=0;
			ps.setInt(++count, id_aluno);

			data = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			data=null;
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}
	

	public static ArrayList<Usuario> getTodosPais(String strFilterResp, String strFilterName){
	    ArrayList<Usuario> data = new ArrayList<Usuario>();
//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();
        try 
        {
//          dataBase.createConnection();            
//          Connection connection = dataBase.getConnection();
            
            String strQuery = DB_SELECT_TODOS_PAIS;
            strQuery = strQuery.replace("<change>", strFilterResp);
            
            PreparedStatement ps = connection.prepareStatement(strQuery);
            
            int count=0;

            ps.setString(++count, "%"+strFilterName+"%");
            ps.setString(++count, "%"+strFilterName+"%");

            data = getUserParameters(ps.executeQuery());

        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return data;	    
	    
	}
	
    public static ArrayList<Usuario> getPaisPorCurso(int idCurso, String strFilterResp, String strFilterName){
        ArrayList<Usuario> data = new ArrayList<Usuario>();
//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();
        try 
        {
//          dataBase.createConnection();            
//          Connection connection = dataBase.getConnection();
            
            String strQuery = DB_SELECT_PAIS_POR_CURSO;
            strQuery = strQuery.replace("<change>", strFilterResp);
            
            PreparedStatement ps = connection.prepareStatement(strQuery);
            
            int count=0;
            ps.setInt(++count, idCurso);
            ps.setString(++count, "%"+strFilterName+"%");
            ps.setString(++count, "%"+strFilterName+"%");

            data = getUserParameters(ps.executeQuery());

        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return data;        
        
    }
    public static ArrayList<Usuario> getPaisPorCurso(int idCurso){
        ArrayList<Usuario> data = new ArrayList<Usuario>();
//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();
        try 
        {
//          dataBase.createConnection();            
//          Connection connection = dataBase.getConnection();
            
            String strQuery = DB_SELECT_PAIS_POR_CURSO_TODOS;
//            strQuery = strQuery.replace("<change>", strFilterResp);
            
            PreparedStatement ps = connection.prepareStatement(strQuery);
            
            int count=0;
            ps.setInt(++count, idCurso);


            data = getUserParametersNew(ps.executeQuery());

        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return data;        
        
    }    
    
    public static ArrayList<Usuario> getProfessorPorCurso(int idCurso){
        ArrayList<Usuario> data = new ArrayList<Usuario>();
//      JornadaDataBase dataBase = new JornadaDataBase();
        Connection connection = ConnectionManager.getConnection();
        try 
        {
//          dataBase.createConnection();            
//          Connection connection = dataBase.getConnection();
            
            String strQuery = DB_SELECT_PROFESSOR_POR_CURSO_TODOS;
//            strQuery = strQuery.replace("<change>", strFilterResp);
            
            PreparedStatement ps = connection.prepareStatement(strQuery);
            
            int count=0;
            ps.setInt(++count, idCurso);


            data = getUserParametersNew(ps.executeQuery());

        } catch (SQLException sqlex) {
            data=null;
            System.err.println(sqlex.getMessage());
        } finally {
//          dataBase.close();
            ConnectionManager.closeConnection(connection);
        }

        return data;        
        
    }        
    
    
//    public static ArrayList<UsuarioNomeID> convertToUsuarioNomeId(ArrayList<Usuario> listUsuario) {
//        ArrayList<UsuarioNomeID> listUsuarioNomeId = new ArrayList<UsuarioNomeID>();
//
//        for (int i = 0; i < listUsuario.size(); i++) {
//            Usuario user = listUsuario.get(i);
//            UsuarioNomeID userNomeId = new UsuarioNomeID();
//            userNomeId.setNomeUsuario(user.getPrimeiroNome() + " " + user.getSobreNome());
//            userNomeId.setIdUsuario(user.getIdUsuario());
//            listUsuarioNomeId.add(userNomeId);
//        }
//        return listUsuarioNomeId;
//    }
    
//    public static ArrayList<UsuarioNomeID> convertToUsuarioNomeIdParaEmail(ArrayList<Usuario> listUsuario) {
//        ArrayList<UsuarioNomeID> listUsuarioNomeId = new ArrayList<UsuarioNomeID>();
//
//        for (int i = 0; i < listUsuario.size(); i++) {
//            Usuario user = listUsuario.get(i);
//            UsuarioNomeID userNomeId = new UsuarioNomeID();
//                       userNomeId.setIdUsuario(user.getIdUsuario());
//            listUsuarioNomeId.add(userNomeId);
//        }
//        return listUsuarioNomeId;
//    }    
    
//    public static ArrayList<ProfessorDisciplinaRelatorio> getProfessoresDisciplinas(){
//        ArrayList<ProfessorDisciplinaRelatorio> data = new ArrayList<ProfessorDisciplinaRelatorio>();
////      JornadaDataBase dataBase = new JornadaDataBase();
//        Connection connection = ConnectionManager.getConnection();
//        try 
//        {
////          dataBase.createConnection();            
////          Connection connection = dataBase.getConnection();
//            
//            String strQuery = DB_SELECT_PROFESSOR_DISCIPLINA_TODOS;
////            strQuery = strQuery.replace("<change>", strFilterResp);
//            
//            PreparedStatement ps = connection.prepareStatement(strQuery);
//            
////            int count=0;
////            ps.setInt(++count, idCurso);
////            data = getUserParametersNew(ps.executeQuery());
//  
//            ResultSet rs = ps.executeQuery();
//            
//            while (rs.next()) 
//            {
//                ProfessorDisciplinaRelatorio pdRel = new ProfessorDisciplinaRelatorio();            
//                pdRel.setIdUsuario(rs.getInt("id_usuario"));
//                pdRel.setIdCurso(rs.getInt("id_curso")); 
//                pdRel.setIdPeriodo(rs.getInt("id_periodo"));
//                pdRel.setIdDisciplina(rs.getInt("id_disciplina"));                
//                pdRel.setPrimeiroNome(rs.getString("primeiro_nome"));
//                pdRel.setSobreNome(rs.getString("sobre_nome"));
//                pdRel.setNomeCurso(rs.getString("nome_curso"));
//                pdRel.setNomePeriodo(rs.getString("nome_periodo"));
//                pdRel.setNomeDisciplina(rs.getString("nome_disciplina"));
//                pdRel.setRegistroDocente(rs.getString("registro_docente"));
//                data.add(pdRel);
//            }
//
//        } catch (SQLException sqlex) {
//            data=null;
//            System.err.println(sqlex.getMessage());
//        } finally {
////          dataBase.close();
//            ConnectionManager.closeConnection(connection);
//        }
//
//        return data;        
//    }
    
    
    public static String getExcelProfessoresDisciplinas(){
        String strLong="";

        XSSFWorkbook wb = new XSSFWorkbook();
        
        Font font = wb.createFont();
        XSSFCellStyle style = wb.createCellStyle();
        
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("sans-serif");
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        
        ///Creating Tabs
//        XSSFSheet sheetAdministrador = wb.createSheet("Professor vs Disciplina");

        
//        gerarExcelTabProfessorDisciplina(sheetAdministrador, font, style);

        
        try {
            Date data = new Date();
            strLong += "GerarExcelProfessorDisciplina_" + Long.toString(data.getTime())+ ".xlsx";
            FileOutputStream out = new FileOutputStream(ConfigJornada.getProperty("config.download")+ ConfigJornada.getProperty("config.download.excel") + strLong);
            wb.write(out);
            out.close();
            out.flush();
        } catch (Exception ex) {
            System.out.print("Error Excel:" + ex.getMessage());
        }
        
        return ConfigJornada.getProperty("config.download.excel")+strLong;
        
    }
    
    
//    public static void gerarExcelTabProfessorDisciplina(Sheet sheet, Font font, XSSFCellStyle style ){
//        
//        ArrayList<ProfessorDisciplinaRelatorio> listPDRel = UsuarioServer.getProfessoresDisciplinas();
//        
//        Row row = sheet.createRow((short) 0);       
//        
//        int intColumn=0;
//        row.createCell((short) intColumn++).setCellValue("Registro Docente");
//        row.createCell((short) intColumn++).setCellValue("Primeiro Nome");
//        row.createCell((short) intColumn++).setCellValue("Sobre Nome");
//        row.createCell((short) intColumn++).setCellValue("Curso");
//        row.createCell((short) intColumn++).setCellValue("Período");
//        row.createCell((short) intColumn++).setCellValue("Disciplina");
//
//        for (int i = 0; i < intColumn; i++) {
//            row.getCell((short) i).setCellStyle(style);
//        }
//        
//        
//        for(int i=0;i<listPDRel.size();i++){
//            ProfessorDisciplinaRelatorio pdRel = listPDRel.get(i);
//            row = sheet.createRow((short) i+1);
//            
//            intColumn=0;
//        
//            row.createCell((short) intColumn++).setCellValue(pdRel.getRegistroDocente());
//            row.createCell((short) intColumn++).setCellValue(pdRel.getPrimeiroNome());
//            row.createCell((short) intColumn++).setCellValue(pdRel.getSobreNome());
//            row.createCell((short) intColumn++).setCellValue(pdRel.getNomeCurso());
//            row.createCell((short) intColumn++).setCellValue(pdRel.getNomePeriodo());
//            row.createCell((short) intColumn++).setCellValue(pdRel.getNomeDisciplina());
//        }        
//        
//        for (int i = 0; i < intColumn; i++) {
//            sheet.autoSizeColumn(i,true);
//        }
//
//    }
	

	

}

