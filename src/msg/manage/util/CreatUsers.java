package msg.manage.util;

import msg.manage.modal.Role;
import msg.manage.modal.Status;
import msg.manage.modal.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.sql.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Amysue on 2016/3/10.
 */
public class CreatUsers {
    private static String path     = System.getProperty("user.dir") + "/src/msg/manage/util/";
    private static String harryXML = path + "harry.xml";

    public static void main(String[] args) {
        CreatUsers cu = new CreatUsers();
        cu.insertUser();
    }

    public void insertUser() {
        Document          doc  = getDoc();
        Connection        conn = getConn();
        PreparedStatement ps   = null;
        ResultSet         rs   = null;
        Random            ran  = new Random();

        Element root = doc.getRootElement();
        try {
            conn.setAutoCommit(false);
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element el = (Element) i.next();
                User    u  = new User();
                u.setPassword("123amy");
                u.setUsername(el.element("username").getTextTrim());
                u.setNickname(el.element("nickname").getTextTrim());
                int num = ran.nextInt(8);
                if (num < 7) {
                    u.setRole(Role.NORMAL);
                } else {
                    u.setRole(Role.ADMIN);
                }
                num = ran.nextInt(20);
                if (num < 19) {
                    u.setStatus(Status.INUSE);
                } else {
                    u.setStatus(Status.OFFUSE);
                }
                String sql = "insert into t_user values(NULL, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, u.getUsername());
                ps.setString(2, u.getPassword());
                ps.setString(3, u.getNickname());
                ps.setInt(4, u.getRole().getCode());
                ps.setInt(5, u.getStatus().getCode());
                ps.executeUpdate();
            }
            conn.commit();
//            ps = conn.prepareStatement("select * from t_user");
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(4));
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void createXml() {
        Document       doc  = DocumentHelper.createDocument();
        Element        root = doc.addElement("Harry-Potter-Character");
        BufferedReader br   = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "harryNames")));
            String str;
            while ((str = br.readLine()) != null) {
                String[] values = str.split(":");
                Element  user   = root.addElement("user");
                user.addElement("username").addText(values[0]);
                user.addElement("nickname").addText(values[1]);
            }
            XMLWriter writer = new XMLWriter(new FileWriter(harryXML), OutputFormat.createPrettyPrint());
            writer.write(doc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Document getDoc() {
        Document  doc = null;
        SAXReader sax = new SAXReader();
        try {
            doc = sax.read(new File(harryXML));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }

    private Connection getConn() {
        Properties      props = new Properties();
        FileInputStream fis   = null;
        Connection      conn  = null;
        try {
            fis = new FileInputStream(path + "db.properties");
            props.load(fis);
            Class.forName(props.getProperty("DB_DRIVER_CLASS"));
            conn = DriverManager.getConnection(props.getProperty("DB_URL"),
                                               props.getProperty("DB_USERNAME"),
                                               props.getProperty("DB_PASSWORD"));

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    private void getNames() {
        BufferedReader br = null;
        PrintWriter    pw = null;
        System.out.println(System.getProperty("user.dir"));
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "names")));
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path + "harryNames")));
            String  str;
            String  pattern1 = "(^[\\u4e00-\\u9fa5\\-]+)\\s+\\(?[\\W]*([\\w\\s]+\\w)\\s?\\)?";
            String  pattern2 = "(^[\\w\\s]+\\w)\\s?.*?([\\u4e00-\\u9fa5]+)$";
            Pattern r1       = Pattern.compile(pattern1);
            Pattern r2       = Pattern.compile(pattern2);
            Matcher matcher;
            while ((str = br.readLine()) != null) {
                matcher = r1.matcher(str);
                if (matcher.find()) {
                    String m1 = matcher.group(1);
                    String m2 = matcher.group(2);
                    System.out.println(m2 + "*-*" + m1);
                    pw.println(m2 + ":" + m1);

                } else {
                    matcher = r2.matcher(str);
                    if (matcher.find()) {
                        String m1 = matcher.group(1);
                        String m2 = matcher.group(2);
                        System.out.println(m1 + "*-*" + m2);
                        pw.println(m1 + ":" + m2);
                    }
                }

            }
            pw.flush();
        } catch (IOException e) {
            System.err.println("=====================================");
            e.printStackTrace();
            System.err.println("=====================================");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }


    }


}
