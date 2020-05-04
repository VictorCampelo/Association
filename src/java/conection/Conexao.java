package conection;


import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	private static Connection[] conexao = new Connection[10];
	private static boolean conectou = false;
	private static int pos = 0;
	private Conexao(){
		
	}
	public static Connection getConexao(){
		if (pos == 10){
			pos = 0;
		}
		if (!conectou) {
			try {
  			  Class.forName("com.mysql.jdbc.Driver");
			  for (int i = 0; i < 10; i++){
				conexao[i] = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/associacao?useSSL=false", "root", "mudvayne123");
			  }
			} catch (Exception e) {
				System.out.println("conectou?");
			  System.exit(1);
			}
			conectou = true;	
		}
		return conexao[pos++];
	}
}