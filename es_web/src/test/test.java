import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by zhangzheming on 2014/4/2.
 */
public class test {

    public static void main(String[] args){
        String driver = "com.mysql.jdbc.Driver";

        // URL指向要访问的数据库名scutcs

        String url = "jdbc:mysql://172.18.108.102:3306/esanalysis";

        // MySQL配置时的用户名

        String user = "root";

        // Java连接MySQL配置时的密码

        String password = "bigdata_mysql";

        try {
            // 加载驱动程序
            Class.forName(driver);
            // 连续数据库
            Connection conn = DriverManager.getConnection(url, user, password);
            if (!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            // statement用来执行SQL语句
            Statement statement = conn.createStatement();
            // 要执行的SQL语句
            String sql = "select * from ebusi_daily_report";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                System.out.println(rs.getString("email"));
            }

            rs.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
