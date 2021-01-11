import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Admin {
    public  static int Uid;
    public static int Hid;
    public static int Sid;
    public static int Cid;
    public static void admin(JFrame prFrame,Connection connection) throws Exception{
        mainWin(connection);
    }
    public static void  mainWin(Connection connection){
        JFrame frame = new JFrame();
        frame.setTitle("管理员登录");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(350,200);
        frame.setLocation(600,300);
        JLabel adminName = new JLabel("用户名");
        JTextField adminNameText = new JTextField(30);
        JLabel adminPsd = new JLabel("密码");
        JPasswordField adminPsdText = new JPasswordField(30);
        JButton logIn = new JButton("登录");
        JButton quitButton = new JButton("退出");
        Container container = frame.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(adminName);
        container.add(adminNameText);
        container.add(adminPsd);
        container.add(adminPsdText);
        container.add(logIn);
        container.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = String.format("select * from Adminstrator where Aname = '%s' and Apassword = '%s'",adminNameText.getText(),new String(adminPsdText.getPassword()));
                try {
                    Statement statement = connection.createStatement();
                    ResultSet textres = statement.executeQuery(sql);
                    if (textres.next()){
                        frame.setVisible(false);
                        chooseWin(frame,connection);
                    }
                    else {
                        adminNameText.setText("用户名或密码有误");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
    public static void chooseWin(JFrame frame,Connection connection){
        JFrame choose = new JFrame();
        choose.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        choose.setVisible(true);
        choose.setSize(600,300);
        choose.setLocation(600,300);
        JLabel title = new JLabel("选择操作对象");
        JButton userButton = new JButton("用户");
        JButton hotelButton = new JButton("酒店");
        JButton ticketButton = new JButton("车票");
        JButton senseButton = new JButton("景点");
        JButton backButton = new JButton("返回");
        Container container = choose.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(title);
        container.add(userButton);
        container.add(hotelButton);
        container.add(ticketButton);
        container.add(senseButton);
        container.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                choose.setVisible(false);
            }
        });
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.setVisible(false);
                try {
                    userWin(choose,connection);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        hotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.setVisible(false);
                try {
                    hotelWin(choose,connection);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        senseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.setVisible(false);
                try {
                    senseWin(choose,connection);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        ticketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.setVisible(false);
                try {
                    TicketWin(choose,connection);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
    public static void userWin(JFrame frame, Connection connection) throws Exception{
        JFrame user = new JFrame();
        user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        user.setVisible(true);
        user.setSize(600,600);
        user.setLocation(600,300);
        JLabel username = new JLabel("用户名");
        JTextField usenameText = new JTextField(50);
        JButton query = new JButton("查询");
        JButton next = new JButton("下一条");
        JButton back = new JButton("返回");
        JButton delete = new JButton("删除");
        JTextArea textArea = new JTextArea(30,30);
        Container container = user.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.add(username);
        container.add(usenameText);
        container.setLayout(new FlowLayout());
        container.add(query);
        container.add(next);
        container.add(back);
        container.add(delete);
        container.add(textArea);
        Statement statement = connection.createStatement();
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setVisible(false);
                frame.setVisible(true);
            }
        });
        query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("select * from userinfo where Uname = '%s'",usenameText.getText());
                    ResultSet res = statement.executeQuery(sql);
                    if(res.next()){
                        try {
                            Uid = res.getInt("Uid");
                            String result = "用户名" + " " + res.getString("Uname") + " " + "用户密码" + " " + res.getString("Upassword")
                                    +" " + "用户性别" + " " + res.getString("Usex") + " " + "注册日期" + " " + res.getString("Utime") +
                                    "\n";
                            textArea.setText(result);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                    else {
                        textArea.setText("暂无用户");
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
                    String sql = String.format("select * from userinfo where Uname = '%s'",usenameText.getText());
                    ResultSet res = statement.executeQuery(sql);
                    res.next();
                    if(res.next()){
                        Uid = res.getInt("Uid");
                        String result = "用户名" + " " + res.getString("Uname") + " " + "用户密码" + " " + res.getString("Upassword")
                                +" " + "用户性别" + " " + res.getString("Usex") + " " + "注册日期" + " " + res.getString("Utime") +
                                "\n";
                        textArea.setText(result);
                    }
                    else {
                        textArea.setText("没有更多用户了");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("delete from userinfo where Uid = %d",Uid);
                    statement.execute(sql);
                    textArea.setText("用户已删除");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
    public static void hotelWin(JFrame frame,Connection connection) throws Exception{
        JFrame hotel = new JFrame();
        hotel.setVisible(true);
        hotel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hotel.setSize(600,600);
        hotel.setLocation(600,300);
        JLabel hotelName = new JLabel("酒店名");
        JTextField hotelNameText = new JTextField(50);
        JButton query = new JButton("查询");
        JButton next = new JButton("下一条");
        JButton add = new JButton("添加");
        JButton delete = new JButton("删除");
        JButton back = new JButton("返回");
        JTextArea textArea = new JTextArea(30,50);
        Container container = hotel.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(hotelName);
        container.add(hotelNameText);
        container.add(query);
        container.add(next);
        container.add(add);
        container.add(delete);
        container.add(back);
        container.add(textArea);
        Statement statement = connection.createStatement();
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                hotel.setVisible(false);
            }
        });
        query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("select * from Hotel where Hname = '%s'",hotelNameText.getText());
                    ResultSet res = statement.executeQuery(sql);
                    if(res.next()){
                        Hid = res.getInt("Hid");
                        String result = "id"+ " " + res.getString("Hid") + " " + "Hname" + " " + res.getString("Hname")
                                + " " + "评分" + " " + res.getString("Hgrade") + " " + "最近车站" + " " + res.getString("Hnearst");
                        textArea.setText(result);
                    }
                    else {
                        textArea.setText("未查询到记录");
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
                    String sql = String.format("select * from Hotel where Hname = '%s'",hotelNameText.getText());
                    ResultSet res = statement.executeQuery(sql);
                    res.next();
                    if(res.next()){
                        Hid = res.getInt("Hid");
                        String result = "id"+ " " + res.getString("Hid") + " " + "Hname" + " " + res.getString("Hname")
                                + " " + "评分" + " " + res.getString("Hgrade") + " " + "最近车站" + " " + res.getString("Hnearst");
                        textArea.setText(result);
                    }
                    else {
                        textArea.setText("未查询到记录");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("delete from Hotel where Hid = %d",Hid);
                    statement.execute(sql);
                    textArea.setText("数据已删除");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hotel.setVisible(false);
                addHotelWin(hotel,connection);
            }
        });
    }
    public static void addHotelWin(JFrame frame,Connection connection){
        int u0 = 55;
        JFrame addHotel = new JFrame();
        addHotel.setVisible(true);
        addHotel.setSize(600,600);
        addHotel.setLocation(600,300);
        addHotel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton add = new JButton("添加");
        JButton back = new JButton("返回");
        JLabel nameLabel = new JLabel("酒店名称");
        JTextField nameText = new JTextField(u0);
        JLabel gradeLabel = new JLabel("评分");
        JTextField gradeText = new JTextField(u0);
        JLabel timeLabel = new JLabel("入住时间");
        JTextField timeText = new JTextField(u0);
        JCheckBox allowPet = new JCheckBox("是否允许携带宠物");
        JCheckBox meal = new JCheckBox("是否提供午饭");
        JCheckBox mall = new JCheckBox("是否靠近商场");
        JLabel nearStation = new JLabel("最近汽车站");
        JTextField stationText = new JTextField(u0);
        JLabel distance = new JLabel("距最近车站距离");
        JTextField distanceText = new JTextField(u0);
        JLabel tid = new JLabel("酒店标签");
        JTextField tidText = new JTextField(u0);
        JLabel hotelType = new JLabel("酒店类型");
        JTextField typeText = new JTextField(u0);
        Container container = addHotel.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(nameLabel);
        container.add(nameText);
        container.add(gradeLabel);
        container.add(gradeText);
        container.add(timeLabel);
        container.add(timeText);
        container.add(nearStation);
        container.add(stationText);
        container.add(distance);
        container.add(distanceText);
        container.add(hotelType);
        container.add(typeText);
        container.add(tid);
        container.add(tidText);
        container.add(allowPet);
        container.add(meal);
        container.add(mall);
        container.add(add);
        container.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHotel.setVisible(false);
                frame.setVisible(true);
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalCount;
                int tagCount = 0;
                String Hmeal = "";
                String Hpet = "";
                String Hmall = "";
                try {
                    Statement statement = connection.createStatement();
                    ResultSet res1 = statement.executeQuery("select count(*) as totalCount from Hotel");
                    if(res1.next()){
                        totalCount = res1.getInt("totalCount");
                    }
                    ResultSet res2 = statement.executeQuery("select count(*) as tagCount from Hoteltag");
                    if(res2.next()){
                        tagCount = res2.getInt("tagCount");
                    }
                    String sql = String.format("insert into Hoteltag (Thid,TagH,TtypeH) values(%d,'%s','%s')",tagCount+1,tidText.getText(),typeText.getText());
                    statement.execute(sql);
                    if(meal.isSelected()){
                        Hmeal = "Y";
                    }
                    else {
                        Hmeal = "N";
                    }
                    if(mall.isSelected()){
                        Hmall = "Y";
                    }
                    else {
                        Hmall = "N";
                    }
                    if(allowPet.isSelected()){
                        Hpet = "Y";
                    }
                    else {
                        Hpet = "N";
                    }
                    String sql1 = String.format("insert into Hotel(Hid,Hname,Hgrade,Hintime,Hpet,Hmeal,Hmall,Hnearst,Hdistance,Htid) " +
                            "values(%d,'%s','%s','%s','%s','%s','%s','%s',%f,%d)",tagCount + 1,
                            nameText.getText(),gradeText.getText(),timeText.getText(),Hpet,Hmeal,Hmall,stationText.getText(),
                            Float.parseFloat(distanceText.getText()),tagCount + 1);
                    statement.execute(sql1);
                    add.setText("插入成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }
    public static void senseWin(JFrame frame,Connection connection) throws Exception{
        JFrame sense = new JFrame();
        sense.setVisible(true);
        sense.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sense.setSize(600,600);
        sense.setLocation(600,300);
        JLabel senceName = new JLabel("景点名称");
        JTextField senceNameText = new JTextField(55);
        JButton queryButton = new JButton("查询");
        JButton nextButton = new JButton("下一条");
        JButton backButton = new JButton("返回");
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JTextArea textArea = new JTextArea(30,50);
        Container container = sense.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(senceName);
        container.add(senceNameText);
        container.add(queryButton);
        container.add(nextButton);
        container.add(addButton);
        container.add(deleteButton);
        container.add(backButton);
        container.add(textArea);
        Statement statement = connection.createStatement();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                sense.setVisible(false);
            }
        });
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("select * from Scenicspot where Sname = '%s'",senceNameText.getText());
                    ResultSet res = statement.executeQuery(sql);
                    if(res.next()){
                        Sid = res.getInt("Sid");
                        String result = "景点名称" + " " + res.getString("Sname") + " " + "开放时间" + " " + res.getString("Sopentime")
                                + " " + "关闭时间" + " " + res.getString("Sclosetime") + " " + "排名" + " " + res.getString("Srank")
                                + " " + "距最近酒店距离" + " " + res.getString("Sdistance") + "\n";
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
                try {
                    String sql = String.format("select * from Scenicspot where Sname = '%s'",senceNameText.getText());
                    ResultSet res = statement.executeQuery(sql);
                    res.next();
                    if(res.next()){
                        Sid = res.getInt("Sid");
                        String result = "景点名称" + " " + res.getString("Sname") + " " + "开放时间" + " " + res.getString("Sopentime")
                                + " " + "关闭时间" + " " + res.getString("Sclosetime") + " " + "排名" + " " + res.getString("Srank")
                                + " " + "距最近酒店距离" + " " + res.getString("Sdistance") + "\n";
                        textArea.setText(result);
                    }
                    else {
                        textArea.setText("没有更多记录了");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("delete from Scenicspot where Sid = %d",Sid);
                    statement.execute(sql);
                    textArea.setText("删除成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sense.setVisible(false);
                addSenceWin(sense,connection);
            }
        });
    }
    public static void addSenceWin(JFrame frame,Connection connection){
        int length = 55;
        JFrame addSence = new JFrame();
        addSence.setVisible(true);
        addSence.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addSence.setSize(600,600);
        addSence.setTitle("添加景点");
        addSence.setLocation(600,300);
        JButton addButton = new JButton("添加");
        JButton backButton = new JButton("返回");
        JLabel name = new JLabel("景点名称");
        JTextField nameText = new JTextField(length);
        JLabel openTime = new JLabel("开门时间");
        JTextField openTimeText = new JTextField(length);
        JLabel closeTime = new JLabel("关门时间");
        JTextField closeTimeText = new JTextField(length);
        JLabel rank = new JLabel("排名");
        JTextField rankText = new JTextField(length);
        JLabel hotel = new JLabel("最近酒店编号");
        JTextField hotelText = new JTextField(length);
        JLabel distance = new JLabel("据酒店距离");
        JTextField distanceText = new JTextField(length);
        JLabel tag = new JLabel("标签编号");
        JTextField tagText = new JTextField(length);
        JLabel cost = new JLabel("景点价格编号");
        JTextField costText = new JTextField(length);
        Container container = addSence.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(name);
        container.add(nameText);
        container.add(openTime);
        container.add(openTimeText);
        container.add(closeTime);
        container.add(closeTimeText);
        container.add(rank);
        container.add(rankText);
        container.add(hotel);
        container.add(hotelText);
        container.add(distance);
        container.add(distanceText);
        container.add(tag);
        container.add(tagText);
        container.add(cost);
        container.add(costText);
        container.add(addButton);
        container.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSence.setVisible(false);
                frame.setVisible(true);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int senceCount = 0;
                    String sql = "select count(*) as senceCount from Scenicspot";
                    Statement statement = connection.createStatement();
                    ResultSet res = statement.executeQuery(sql);
                    if(res.next()){
                        senceCount = res.getInt("senceCount");
                    }
                    String sql1 = String.format("insert into Scenicspot (Sid,Sname,Sopentime,Sclosetime,Srank,SHid,Sdistance,Stid," +
                            "Spid) values(%d,'%s','%s','%s',%d,%d,%f,%d,%d)",senceCount + 1,nameText.getText(),openTimeText.getText(),
                            closeTimeText.getText(),Integer.parseInt(rankText.getText()),Integer.parseInt(hotelText.getText()),
                            Float.parseFloat(distanceText.getText()),Integer.parseInt(tagText.getText()),Integer.parseInt(costText.getText()));
                    statement.execute(sql1);
                    addButton.setText("插入成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
    public static void TicketWin(JFrame frame,Connection connection) throws Exception{
        int length = 55;
        JFrame ticket = new JFrame();
        ticket.setVisible(true);
        ticket.setSize(600,600);
        ticket.setLocation(600,300);
        ticket.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ticket.setTitle("车票查询");
        JLabel departure = new JLabel("出发地");
        JTextField departureText = new JTextField(length);
        JLabel destination = new JLabel("目的地");
        JTextField destinationText = new JTextField(length);
        JButton queryButton = new JButton("查询");
        JButton nextButton = new JButton("下一条");
        JButton deleteButton = new JButton("删除");
        JButton addButton = new JButton("添加");
        JButton backButton = new JButton("返回");
        JTextArea textArea = new JTextArea(30,50);
        Container container = ticket.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(departure);
        container.add(departureText);
        container.add(destination);
        container.add(destinationText);
        container.add(queryButton);
        container.add(nextButton);
        container.add(deleteButton);
        container.add(addButton);
        container.add(backButton);
        container.add(textArea);
        Statement statement = connection.createStatement();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                ticket.setVisible(false);
            }
        });
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("select * from coachinfo where Cistation = '%s' and Cdstation = '%s'",
                            departureText.getText(),destinationText.getText());
                    ResultSet res = statement.executeQuery(sql);
                    if(res.next()){
                        Cid = res.getInt("Cid");
                        String result = "车票id" + " " + res.getString("Cid") + " " + "发车时间" + " " + res.getString("Cdtime")
                                + " " + "剩余座位数" + " " + res.getString("Crseatnum") + " " + "发车站" + " " + res.getString("Cistation")
                                + " " + "终点站" + res.getString("Cdstation") + " " + "价格标签" + " " + res.getString("Cpid") + "\n";
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
                try {
                    String sql = String.format("select * from coachinfo where Cistation = '%s' and Cdstation = '%s'",
                            departureText.getText(),destinationText.getText());
                    ResultSet res = statement.executeQuery(sql);
                    res.next();
                    if(res.next()){
                        Cid = res.getInt("Cid");
                        String result = "车票id" + " " + res.getString("Cid") + " " + "发车时间" + " " + res.getString("Cdtime")
                                + " " + "剩余座位数" + " " + res.getString("Crseatnum") + " " + "发车站" + " " + res.getString("Cistation")
                                + " " + "终点站" + res.getString("Cdstation") + " " + "价格标签" + " " + res.getString("Cpid") + "\n";
                        textArea.setText(result);
                    }
                    else {
                        textArea.setText("没有更多的数据了");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("delete from coachinfo where Cid = %d",Cid);
                    statement.execute(sql);
                    textArea.setText("数据已删除");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticket.setVisible(false);
                addTicketWin(ticket,connection);
            }
        });
    }
    public static void addTicketWin(JFrame frame,Connection connection){
        int length = 55;
        JFrame addTicket = new JFrame();
        addTicket.setVisible(true);
        addTicket.setTitle("添加车票信息");
        addTicket.setSize(600,600);
        addTicket.setLocation(600,300);
        addTicket.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel time = new JLabel("发车时间");
        JTextField timeText = new JTextField(length);
        JLabel seatCount = new JLabel("剩余座位数");
        JTextField seatText = new JTextField(length);
        JLabel departure = new JLabel("发车站");
        JTextField departureText = new JTextField(length);
        JLabel destination = new JLabel("终点站");
        JTextField destinationText = new JTextField(length);
        JLabel cost = new JLabel("价格编号");
        JTextField costText = new JTextField(length);
        JButton addButton = new JButton("添加");
        JButton backButton = new JButton("返回");
        Container container = addTicket.getContentPane();
        ((JPanel)container).setOpaque(false);
        container.setLayout(new FlowLayout());
        container.add(time);
        container.add(timeText);
        container.add(seatCount);
        container.add(seatText);
        container.add(departure);
        container.add(departureText);
        container.add(destination);
        container.add(destinationText);
        container.add(cost);
        container.add(costText);
        container.add(addButton);
        container.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                addTicket.setVisible(false);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int ticketCount = 0;
                    Statement statement = connection.createStatement();
                    String sql = "select count(*) as ticketCount from coachinfo";
                    ResultSet res = statement.executeQuery(sql);
                    if(res.next()){
                        ticketCount = res.getInt("ticketCount");
                    }
                    String sql2 = String.format("insert into coachinfo (Cid,Cdtime,Crseatnum,Cistation,Cdstation,Cpid) " +
                            "values(%d,'%s',%d,'%s','%s',%d)",ticketCount + 2,timeText.getText(),Integer.parseInt(seatText.getText()),departureText.getText(),destinationText.getText(),Integer.parseInt(costText.getText()));
                    statement.execute(sql2);
                    addButton.setText("插入成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
