import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainWin {
    public static void main(String[] args) throws Exception{
        Connection connection;
        String driveName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=ticketquerysys";
        String userName = "sa";
        String psd = "123";
        Class.forName(driveName);
        connection = DriverManager.getConnection(dbURL,userName,psd);
        JFrame mainFrame  = new JFrame("数据库课程设计");
        mainFrame.setVisible(true);
        mainFrame.setSize(400,300);
        mainFrame.setLocation(600,300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("长途出行信息查询管理系统");
        label.setFont(new Font("",Font.BOLD,30));
        label.setHorizontalAlignment(JLabel.CENTER);
        JButton admin = new JButton("管理员登录");
        JButton user = new JButton("用户登录");
        Container container = mainFrame.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(label);
        container.add(admin);
        container.add(user);
        ImageIcon img = new ImageIcon("https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2927021215,1199075952&fm=26&gp=0.jpg");
        JLabel imageLabel = new JLabel(img);
        mainFrame.getLayeredPane().add(imageLabel,Integer.valueOf(Integer.MIN_VALUE));
        imageLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
        container.add(imageLabel);
        user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                try {
                    Win.userWin(mainFrame, connection);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                try {
                    Admin.admin(mainFrame,connection);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
