import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


public class Win {
    public static int newUid;
    public  static  int randomNum;
    public static String username;
    public static String userPassWord;
    public static int Uid;
    public static int Cid;
    public static String hoteltype;
    public static int hotelid;
    public  static String Allhotelname;
    public static void userWin(JFrame prWin,Connection connection) throws Exception{
        // 创建 JFrame 实例
        // Setting the width and height of frame
        JFrame frame = new JFrame("数据库课程设计");
        frame.setSize(350, 200);
        frame.setLocation(600,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        mainWin(frame,panel,connection);

        // 设置界面可见
        frame.setVisible(true);
    }

    private static void mainWin(JFrame frame, JPanel panel,Connection connection) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */

        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("用户");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("密码：");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        // 创建登录按钮
        JButton loginButton = new JButton("登录");
        ActionListener pushloginbutton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    username = userText.getText();
                    userPassWord = new String(passwordText.getPassword());
                    Statement statement = connection.createStatement();
                    ResultSet res = statement.executeQuery("select  * from userinfo");
                    while (res.next()){
                        String name = res.getString("Uname");
                        String psd = res.getString("Upassword");
                        if(name.equals(userText.getText()) && psd.equals(new String(passwordText.getPassword()))){
                            Uid = Integer.parseInt(res.getString("Uid"));
                            frame.setVisible(false);
                            chooseWin(frame,connection);
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        };
        loginButton.addActionListener(pushloginbutton);
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
        JButton register = new JButton("注册");
        register.setBounds(105,100,80,25);
        JButton quitButton = new JButton("退出");
        quitButton.setBounds(200,80,80,25);
        ActionListener pushQuitButton = e -> frame.setVisible(false);
        quitButton.addActionListener(pushQuitButton);
        panel.add(quitButton);
        register.addActionListener(e -> {
            frame.setVisible(false);
            registerWin(frame,connection);
        });
        panel.add(register);
    }



    public static void queryTicket(JFrame frame,Connection connection) throws Exception{
        JFrame queryTicketWin = new JFrame("车票查询");
        queryTicketWin.setSize(600,600);
        queryTicketWin.setLocation(600,300);
        JButton query = new JButton("查询");
        JButton next = new JButton("下一条");
        JButton buy = new JButton("购买");
        JButton back = new JButton("返回");
        JLabel departure = new JLabel("出 发 地");
        JLabel distination = new JLabel("目 的 地");
        JTextField departureText = new JTextField(50);
        JTextField distinactionText = new JTextField(50);
        JTextArea textArea = new JTextArea(20,55);
        queryTicketWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        queryTicketWin.setVisible(true);
        Statement statement = connection.createStatement();
        back.addActionListener(e -> {
            frame.setVisible(true);
            queryTicketWin.setVisible(false);
        });
        query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("select * from 车票信息 where Cistation = '%s' and Cdstation = '%s'",departureText.getText(),distinactionText.getText());
                    ResultSet res = statement.executeQuery(sql);
                    if(res.next()){
                        float discount = Float.parseFloat(res.getString("PdiscountC"));
                        String discountInfo = null;
                        if(discount == 0){
                            discountInfo = "暂无折扣";
                        }
                        else {
                            discountInfo = String.valueOf(discount);
                        }
                        String result = "发车时间" + " " + res.getString("Cdtime") + " " + "剩余座位数" + " " + res.getString("Crseatnum")
                                + " " + "出发站" + " " + res.getString("Cistation") + " " + "终点站" + " " + res.getString("Cdstation")
                                + " " + "票价" + " " + res.getString("PriceC") + " " + "折扣" + " " + discountInfo + "\n";
                        textArea.setText(result);
                    }
                    else {
                        textArea.setText("暂无车票");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("select * from 车票信息 where Cistation = '%s' and Cdstation = '%s'",departureText.getText(),distinactionText.getText());
                    ResultSet res = statement.executeQuery(sql);
                    res.next();
                    if(res.next()){
                        float discount = Float.parseFloat(res.getString("PdiscountC"));
                        String discountInfo = null;
                        if(discount == 0){
                            discountInfo = "暂无折扣";
                        }
                        else {
                            discountInfo = String.valueOf(discount);
                        }
                        String result = "发车时间" + " " + res.getString("Cdtime") + " " + "剩余座位数" + " " + res.getString("Creatnum")
                                + " " + "出发站" + " " + res.getString("Cistation") + " " + "终点站" + " " + res.getString("Cdstation")
                                + " " + "票价" + " " + res.getString("PriceC") + " " + "折扣" + " " + discountInfo + "\n";
                        textArea.setText(result);
                        Cid =Integer.parseInt(res.getString("Cid"));
                    }
                    else {
                        textArea.setText("没有更多车票了");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql1 = String.format("insert into UTB (COuid,COid) values(%d,%d)",Uid,Cid);
                    statement.execute(sql1);
                    buy.setText("购买成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        Container container = queryTicketWin.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(departure);
        container.add(departureText);
        container.add(distination);
        container.add(distinactionText);
        container.add(query);
        container.add(back);
        container.add(textArea);
        container.add(next);
        container.add(buy);
    }
    public static void registerWin(Frame frame,Connection connection){
        JFrame register = new JFrame();
        JPanel panel = new JPanel();
        JLabel userlabel = new JLabel("用户名");
        JLabel userpsd = new JLabel("密码");
        JPasswordField psd = new JPasswordField(35);
        JTextField username = new JTextField(35);
        JLabel email = new JLabel("邮箱");
        JTextField emailtext = new JTextField(35);
        JLabel comfirmedNum = new JLabel("验证码");
        JTextField num = new JTextField(10);
        JButton numButton = new JButton("获取验证码");
        register.setVisible(true);
        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        register.setSize(400,400);
        register.setLocation(600,300);
        Container container = register.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(userlabel);
        container.add(username);
        container.add(userpsd);
        container.add(psd);
        container.add(email);
        container.add(emailtext);
        JButton registerButton = new JButton("注册");
        JButton backButton = new JButton("返回");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement statement = connection.createStatement();
                    String sql = "select * from userinfo";
                    ResultSet temple = statement.executeQuery(sql);
                    int Uid = 1;
                    while(temple.next()){
                        Uid ++;
                    }
                    String newsql = "select count(*) as newUid from dbo.userinfo";
                    ResultSet newtemple = statement.executeQuery(newsql);
                    newtemple.next();
                    newUid = newtemple.getInt("newUid");
                    String templeSQL = String.format("values('%d','%s','%s')",newUid + 2,username.getText(),new String(psd.getPassword()));
                    String insertSQL = "insert into dbo.userinfo (Uid,Uname,Upassword) " + templeSQL;
                    statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    if(Integer.parseInt(num.getText())==randomNum){
                        statement.execute(insertSQL);
                        registerButton.setText("注册成功");
                        connection.commit();
                    }
                    else {
                        numButton.setText("验证码错误");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        numButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomNum = (int)(Math.random() * 10000);
                String numText = String.valueOf(randomNum);
                numButton.setText(numText);
            }
        });
        backButton.addActionListener(e -> {frame.setVisible(true);register.setVisible(false);});
        container.add(comfirmedNum);
        container.add(num);
        container.add(numButton);
        container.add(registerButton);
        container.add(backButton);
    }

    public static void chooseWin(JFrame frame,Connection connection){
        JFrame choose = new JFrame();
        choose.setVisible(true);
        JButton queryticket = new JButton("查询车票");
        JButton queryHotel = new JButton("查询酒店");
        JButton queryScense = new JButton("查询景点");
        JButton queryUserTicket = new JButton("查询已购车票");
        JButton backButton = new JButton("返回");
        choose.setSize(600,200);
        choose.setLocation(600,300);
        backButton.addActionListener(e -> {
            frame.setVisible(true);
            choose.setVisible(false);
        });
        Container container = choose.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(queryticket);
        container.add(queryHotel);
        container.add(queryScense);
        container.add(queryUserTicket);
        container.add(backButton);
        queryticket.addActionListener(e -> {
            choose.setVisible(false);
            try {
                queryTicket(choose,connection);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        queryScense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.setVisible(false);
                queryScenseWin(choose,connection);
            }
        });
        queryHotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.setVisible(false);
                try {
                    queryHotelWin(choose,connection);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        queryUserTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.setVisible(false);
                quryBuyedTicketWin(choose,connection);
            }
        });
    }
    public static void queryScenseWin(JFrame frame,Connection connection){
        JFrame queryScense = new JFrame();
        queryScense.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        queryScense.setVisible(true);
        queryScense.setSize(1200,600);
        queryScense.setLocation(600,300);
        ArrayList<JButton> jButtons = new ArrayList<>();
        jButtons.add(new JButton("查询"));
        jButtons.add(new JButton("返回"));
        jButtons.get(1).addActionListener(e -> {
            queryScense.setVisible(false);
            frame.setVisible(true);
        });
        Container container = queryScense.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        JLabel hotel = new JLabel("酒店名称");
        JTextField hotelname = new JTextField(50);
        container.add(hotel);
        container.add(hotelname);
        for(JButton button : jButtons){
            container.add(button);
        }
        JTextArea textArea = new JTextArea(30,90);
        container.add(textArea);
        jButtons.get(0).addActionListener(e -> {
            String hotelNameText = hotelname.getText();
            String sql = String.format("select * from 景点信息 where Hname = '%s'",hotelNameText);
            try {
                Statement statement = connection.createStatement();
                ResultSet textres = statement.executeQuery(sql);
                if(textres.next()){
                    ResultSet res = statement.executeQuery(sql);
                    while(res.next()){
                        String result = "景点名称" + " " + res.getString("Sname") + "  " + "开业时间" + " " + res.getString("Sopentime")
                                + "  " + "歇业时间" + " " + res.getString("Sclosetime") + "  " + "排名" + " " + res.getString("Srank") + "  " + "据酒店距离" + " " + res.getString("Sdistance") + "  " + "酒店标签" + " " + res.getString("Ttag") + "  " +
                                "景点类型" + " " + res.getString("Ttype") + "  " + "价格" + " " + res.getString("PriceS") + "  " +
                                "折扣" + " " + res.getString("PdiscountS");
                        textArea.append(result);
                    }
                }
                else {
                    textArea.setText("暂无记录");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

    }
    public static void queryHotelWin(JFrame frame,Connection connection) throws Exception{
        JFrame queryHotel = new JFrame();
        queryHotel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        queryHotel.setVisible(true);
        queryHotel.setSize(1200,500);
        queryHotel.setLocation(600,300);
        Container container = queryHotel.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        JLabel hotel = new JLabel("酒店名");
        JTextField hotelName = new JTextField(50);
        JButton queryButton = new JButton("查询");
        JButton nextButton = new JButton("下一条");
        JButton buyButton = new JButton("购买");
        JButton backButton = new JButton("返回");
        JTextArea textArea = new JTextArea(25,90);
        container.add(hotel);
        container.add(hotelName);
        container.add(queryButton);
        container.add(nextButton);
        container.add(buyButton);
        container.add(backButton);
        container.add(textArea);
        Statement statement = connection.createStatement();
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = String.format("select * from 酒店信息 where Hname = '%s'",hotelName.getText());
                try {
                    ResultSet textres = statement.executeQuery(sql);
                    if(textres.next()){
                        ResultSet res = statement.executeQuery(sql);
                        res.next();
                        String result = res.getString("Hname") + "  " + "评分" + res.getString("Hgrade") + "  " + res.getString("Hintime") + "  " + "能否携带宠物" + res.getString("Hpet") +
                                "  " + "是否提供伙食" + res.getString("Hmeal") + "  " + "最近汽车站" + res.getString("Hnearst") + "  "
                                + "距离" + " " + res.getString("Hdistance") + "  " + "是否靠近商场" + " " + res.getString("Hmall")
                                + "  "  + "房间类型" + " " + res.getString("Rtype") + "  " + "价格" + " " + res.getString("Rprice")
                                + "  " + "折扣" + " " + res.getString("Rdiscount") + "  " + "特色" + " " + res.getString("TagH")
                                + "  " + "酒店类型" +  " " + res.getString("TtypeH") + "\n";
                        Allhotelname = res.getString("Hname");
                        textArea.setText(result);
                    }
                    else {
                        textArea.setText("暂无记录");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = String.format("select * from 酒店信息 where Hname = '%s'",hotelName.getText());
                try {
                    ResultSet res = statement.executeQuery(sql);
                    res.next();
                    if(res.next()){
                        String result = res.getString("Hname") + "  " + "评分" + res.getString("Hgrade") + "  " + res.getString("Hintime") + "  " + "能否携带宠物" + res.getString("Hpet") +
                                "  " + "是否提供伙食" + res.getString("Hmeal") + "  " + "最近汽车站" + res.getString("Hnearst") + "  "
                                + "距离" + " " + res.getString("Hdistance") + "  " + "是否靠近商场" + " " + res.getString("Hmall")
                                + "  "  + "房间类型" + " " + res.getString("Rtype") + "  " + "价格" + " " + res.getString("Rprice")
                                + "  " + "折扣" + " " + res.getString("Rdiscount") + "  " + "特色" + " " + res.getString("TagH")
                                + "  " + "酒店类型" +  " " + res.getString("TtypeH") + "\n";
                        textArea.setText(result);
                        hoteltype = res.getString("TtypeH");
                        Allhotelname = res.getString("Hname");
                    }
                    else {
                        textArea.setText("没有更多酒店了");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql1 = String.format("select * from Hotel where Hname = '%s'",Allhotelname);
                    ResultSet res = statement.executeQuery(sql1);
                    res.next();
                    System.out.println(Allhotelname);
                    System.out.println(res.getString("Hgrade"));
                    hotelid = Integer.parseInt(res.getString("Hid"));
                    String sql2 = String.format("insert into URB(HOuid,HOid,URtype) values(%d,%d,'%s')",Uid,hotelid,hoteltype);
                    statement.execute(sql2);
                    buyButton.setText("购买成功");
                    connection.commit();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryHotel.setVisible(false);
                frame.setVisible(true);
            }
        });
    }
    public  static void quryBuyedTicketWin(JFrame frame,Connection connection){
        JFrame quryBuryedTicket = new JFrame();
        quryBuryedTicket.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        quryBuryedTicket.setVisible(true);
        quryBuryedTicket.setSize(600,600);
        quryBuryedTicket.setLocation(600,300);
        JButton queryButton = new JButton("查询");
        JButton backButton = new JButton("返回");
        Container container = quryBuryedTicket.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(queryButton);
        container.add(backButton);
        JTextArea textArea = new JTextArea(30,30);
        container.add(textArea);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quryBuryedTicket.setVisible(false);
                frame.setVisible(true);
            }
        });
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = String.format("select * from 用户购买车票 where Uname = '%s'",username);
                try {
                    Statement statement = connection.createStatement();
                    ResultSet textres = statement.executeQuery(sql);
                    if(textres.next()){
                        ResultSet res = statement.executeQuery(sql);
                        while(res.next()){
                            String [] temple = res.getString("Octime").split(" ");
                            String result = "用户名" + " " + res.getString("Uname") + " " + "发车时间" + " " + res.getString("Cdtime")
                                    + " " + "出发站" + " " + res.getString("Cistation") + " " + "目的地" + " " +
                                    res.getString("Cdstation") + " " + "发车日期" + " " + temple[0] + "\n";
                            textArea.append(result);
                        }
                    }
                    else {
                        textArea.setText("暂无记录");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
