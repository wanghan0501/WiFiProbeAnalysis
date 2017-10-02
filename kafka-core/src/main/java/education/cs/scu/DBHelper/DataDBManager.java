package education.cs.scu.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataDBManager extends DBHelper {
    private ResultSet rs = null;
    
	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	
	public DataDBManager(Connection dbConnection) {
		super(dbConnection);
	}
	
	//根据username, password进行查询
	//登陆查询
	public ResultSet executeQuery() throws SQLException{
		String logSql = "        SELECT *\n" +
				"        FROM user_visit a\n" +
				"        WHERE a.time in (\n" +
				"        SELECT max(b.time)\n" +
				"        FROM user_visit b\n" +
				"        )";
		//System.out.println("query...");
        preparedStatement = dbConnection.prepareStatement(logSql);
        setRs(preparedStatement.executeQuery());
		return getRs();
	}
}
