import java.sql.*;

/**
 * Created by zhangzheming on 2014/4/2.
 */
public class pgSQLTest {

    public static void main(String[] args){


            try {
                Class.forName("org.postgresql.Driver").newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        try {
            String url ="jdbc:postgresql://172.18.108.101:5432/ebusi_channel" ;
            String user="postgres";
            String password="postgres";
            Connection conn= DriverManager.getConnection(url, user, password);
            Statement stmt = null;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "select * from channel_rule";
            ResultSet rs= null;
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
