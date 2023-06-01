package com.zhouzhou;
//package sqldemo;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;



import java.awt.event.*;
class function {
    public static class CustomOutputStream extends OutputStream {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            // redirects data to the text area
            textArea.append(String.valueOf((char)b));
            // scrolls the text area to the end of data
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

}
class Demo extends JFrame {
    private static int itemid = 23153;
    private static String purl1 = "https://www.mercari.com/us/category/1/";
    private static String purl2 = "https://www.facebook.com/marketplace/";
    private static String purl3 = "https://offerup.com/";
    private static int[] flag = new int[5];
    com.zhouzhou.function user = new com.zhouzhou.function();
    // 定义组件
    JPanel jp1, jp2, jp3, jp4, jp5, jp6, output, jpPrice, jpU, jpPlatform;
    JLabel jlb1, jlb2, jlb3, jlb4, jlb5, jlb6, jlbPrice, jlbU, jlbPlatform;
    String getCategory = "";
    String getState = "";
    String getItem = "";
    int getMin = 0;
    int getMax = 10000;
    static JTextArea area = null;
    JScrollPane scrollPane;
    JTextField jtf = null;// 文本框
    JTextField jtfMin = null;// 文本框
    JTextField jtfMax = null;// 文本框
    JButton jb = null;
    JButton jb1 = null;
    JButton jb2 = null;
    String input = "";
    int getPrice = 0;
    String getPname = "";
    String getUname = "";
    String getItemId = "";
    public static void main(String[] args) {
        com.zhouzhou.Demo d3 = new com.zhouzhou.Demo();
    }
    public void reset() {
        jtf.setText("");
        jtfMin.setText("");
        jtfMax.setText("");
        flag[0] = 0;
        flag[1] = 0;
        flag[2] = 0;
        flag[3] = 0;
        flag[4] = 0;
    }
    public void resetMin() {
        jtfMin.setText("Not a number");
        flag[2] = 0;
    }
    public void resetMax() {
        jtfMax.setText("Not a number");
        flag[3] = 0;
    }
    public Demo() {
        String[] states = { "Empty", "Alabama", "Alaska", "American Samoa", "Arizona", "Arkansas", "California", "Colorado",
                "Connecticut", "Delaware", "Florida", "Georgiav", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
                "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota",
                "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
                "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
                "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia",
                "Washington", "West Virginia", "Wisconsin", "Wyoming"};
        JComboBox a1 = new JComboBox(states);
        a1.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent event)
            {
                switch (event.getStateChange())
                {
                    case ItemEvent.SELECTED:
                        getState = (String) event.getItem();
                        if(getState.equals("Empty")){
                            break;
                        }
                        flag[0] = 1;
                        break;
                    case ItemEvent.DESELECTED:
                        break;
                }
            }
        });
        add(a1);

        String[] category = { "Empty","Mercari-Woman", "Mercari-Men", "Mercari-Kids", "Mercari-Home", "Mercari-Collectibles", "Mercari-Beauty", "Mercari-Electronics", "Mercari-Outdoors", "Mercari-Handmade", "Mercari-Other",
                "Fb Market-Woman", "Fb Market-Men", "Fb Market-Kids", "Fb Market-Home", "Fb Market-Collectibles", "Fb Market-Beauty", "Fb Market-Electronics", "Fb Market-Outdoors", "Fb Market-Handmade", "Fb Market-Other",
                "OfferUp-Woman", "OfferUp-Men", "OfferUp-Kids", "OfferUp-Home", "OfferUp-Collectibles", "OfferUp-Beauty", "OfferUp-Electronics", "OfferUp-Outdoors", "OfferUp-Handmade", "OfferUp-Other"};
        JComboBox a2 = new JComboBox(category);

        a2.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent event)
            {
                switch (event.getStateChange())
                {
                    case ItemEvent.SELECTED:
                        getCategory = (String) event.getItem();
                        if(getCategory.equals("Empty")){
                            break;
                        }
                        flag[1] = 1;
                        break;
                    case ItemEvent.DESELECTED:
                        //System.out.println("取消选中"+event.getItem());
                        break;
                }
            }
        });
        add(a2);


        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();
        jp6 = new JPanel();
        jpPlatform = new JPanel();
        jpPrice = new JPanel();
        jpU = new JPanel();
        jlb1 = new JLabel("State");
        jlb2 = new JLabel("Category");
        jlb3 = new JLabel("min price");
        jlb4 = new JLabel("Item");
        jlb5 = new JLabel("max price");
        jlbPlatform = new JLabel("Platform name(for add)");
        jlbPrice = new JLabel("Want to sell the price like this(for add)");
        jlbU = new JLabel("Your name please(for delete and add)");
        jlb6 = new JLabel("Item ID (for delete)");

        // 布局管理
        this.setLayout(new GridLayout(7, 1));
        JTextField text1 = new JTextField(10);
        JTextField text2 = new JTextField(10);
        JTextField text3 = new JTextField(10);
        jpPlatform.add(jlbPlatform);
        jpPlatform.add(text1);
        jpPrice.add(jlbPrice);
        jpPrice.add(text2);
        jpU.add(jlbU);
        jpU.add(text3);
        JTextField jtf6 = new JTextField(10);
        jp6.add(jtf6);
        jp6.add(jlb6);
        text1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = "";
                    input = text1.getText();//获取JTextField内容
                    getPname = input;
                    //System.out.println(getMax);//控制台查看获取的文本内容
                }
                catch(Exception ex){
                }
            }
        });
        text2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = "";
                    input = text2.getText();//获取JTextField内容
                    getPrice = Integer.parseInt(input);
                    //System.out.println(getMax);//控制台查看获取的文本内容
                }
                catch(Exception ex){
                }
            }
        });
        text3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = "";
                    input = text3.getText();//获取JTextField内容
                    getUname = input;
                    //System.out.println(getMax);//控制台查看获取的文本内容
                }
                catch(Exception ex){
                }
            }
        });
        jtf6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = "";
                    input = jtf6.getText();//获取JTextField内容
                    getItemId = input;
                    //System.out.println(getMax);//控制台查看获取的文本内容
                }
                catch(Exception ex){
                }
            }
        });
        //state
        jp1.add(jlb1);
        jp1.add(a1);

        //category
        jp2.add(jlb2);
        jp2.add(a2);

        //min price
        jtfMin = new JTextField(10);
        jp3.add(jlb3);
        jp3.add(jtfMin);

        //min price text

        jtfMin.setText("Enter a number");
        //添加监听机制
        jtfMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = "";
                    input = jtfMin.getText();//获取JTextField内容
                    getMin = Integer.parseInt(input);
                    //System.out.println(getMin);//控制台查看获取的文本内容
                    flag[2] = 1;
                }
                catch(Exception ex){
                    jtfMin.setText("Not a number");
                    resetMin();
                }
            }
        });

        jp3.add(jtfMin);

        jp3.setSize(300, 200);
        jp3.setVisible(true);

        //max price
        jtfMax = new JTextField(10);
        jp5.add(jlb5);
        jp5.add(jtfMax);

        //max price text

        jtfMax.setText("Enter a number");
        //添加监听机制
        jtfMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = "";
                    input = jtfMax.getText();//获取JTextField内容
                    getMax = Integer.parseInt(input);
                    //System.out.println(getMax);//控制台查看获取的文本内容
                    flag[3] = 1;
                }
                catch(Exception ex){
                    resetMax();
                }
            }
        });

        jp5.add(jtfMax);

        jp5.setSize(300, 200);
        jp5.setVisible(true);



        // item text
        jtf = new JTextField(10);
        jb = new JButton("Search");
        jb1 = new JButton("add data");
        jb2 = new JButton("delete data");

        //item
        jtf.addActionListener(new   java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = "";
                input = jtf.getText();//获取JTextField内容
                getItem = input;
                //System.out.println(getItem);//控制台查看获取的文本内容
                flag[4] = 1;
            }
        });
        jp4.add(jtf);

        jp4.setSize(300, 200);
        jp4.setVisible(true);

        jpU.add(jb1);
        jpU.add(jb2);

        // 添加
        jp4.add(jlb4);
        jp4.add(jtf);
        jp4.add(jb);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        output = new JPanel();
        area = new JTextArea(5, 40);
        area.setSize(400, 100);

        scrollPane = new JScrollPane(area);
        area.setEditable(false);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        output.add(scrollPane, constraints);
        this.add(jp4, BorderLayout.SOUTH);
        this.add(jp2);
        this.add(jp1);
        this.add(jp3);
        this.add(jp5, BorderLayout.SOUTH);
        this.add(jpPlatform);
        this.add(jpPrice);
        this.add(jpU);
        this.add(jp6);
        this.add(output);
        this.setSize(1500, 800);
        this.setTitle("Happy Trade");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        jb.addActionListener(new   java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(flag[0] == 0 && flag[1] == 0 && flag[2] == 0 && flag[3] == 0 && flag[4] == 0) {
                    System.out.println("Please give some requirements");
                }
                else if(getMin > getMax) {
                    System.out.println("Your min price is higher than max price");
                }
                else{
                	area.setText("");
                    search(getCategory, getItem, getState, getMin, getMax, flag);
                    text1.setText("");
                    text2.setText("");
                    text3.setText("");
                    jtf6.setText("");
                    
                }
                reset();
            }
        });
        //insert
        jb1.addActionListener(new   java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(getMin > getMax) {
                    System.out.println("Your min price is higher than max price");
                }
                else{
                	area.setText("");
                    insert(getCategory, getItem, getState, getPrice, getPname, getUname);
                    text1.setText("");
                    text2.setText("");
                    text3.setText("");
                    jtf6.setText("");
                }
                reset();
            }
        });
        //delete
        jb2.addActionListener(new   java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(getMin > getMax) {
                    System.out.println("Your min price is higher than max price");
                }
                else{
                	area.setText("");
                    delete(getItemId, getUname);
                    text1.setText("");
                    text2.setText("");
                    text3.setText("");
                    jtf6.setText("");
                 
                }
                reset();
            }
        });
    }
    public static void delete(String getItemId, String userName) {
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/secondhandmarket";
        //MySQL配置时的用户名
        String user = "ruiying";
        //MySQL配置时的密码
        String password = "Wry13871557836";

        String userid = userName;
        String itemID = "Item" + String.valueOf(getItemId);
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            //flag: 2,3,4   2 is min, 3 is max, 4 is item, 0 is state, 1 is category

            // no item
            String sql1 = "set foreign_key_checks = 0";
            String sql2 = "delete from User where uname = \'" + userName + "\'";
            String sql3 = "set foreign_key_checks = 1";
            String sql4 = "set foreign_key_checks = 0";
            String sql5 = "delete from Item where iid = \'" + getItemId + "\'";
            String sql6 = "set foreign_key_checks = 1";
            //state

            //3.ResultSet类，用来存放获取的结果集！！
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
            statement.executeUpdate(sql3);
            statement.executeUpdate(sql4);
            statement.executeUpdate(sql5);
            statement.executeUpdate(sql6);
            System.out.println("-----------------");
            System.out.println("The result is below:");
            System.out.println("-----------------");
            //output change
            PrintStream printStream = new PrintStream(new com.zhouzhou.function.CustomOutputStream(area));
            System.setOut(printStream);
            System.setErr(printStream);
            con.close();
        } catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            System.out.println("Deletion Done");
        }
    }
    public static void insert(String category, String item, String state, int price, String pname, String userName) {
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/secondhandmarket";
        //MySQL配置时的用户名
        String user = "ruiying";
        //MySQL配置时的密码
        String password = "Wry13871557836";
        String purl = "";
        String userid = "new user" + String.valueOf(itemid);
        String itemID = "Item" + String.valueOf(itemid);
        String categoryID = "";
        itemid += 1;
        if (pname.equals("Mercari")){
            purl = purl1;
        }
        if (pname.equals("Facebook Market")){
            purl = purl2;
        }
        if (pname.equals("OfferUp")){
            purl = purl3;
        }

        if (category.equals("Mercari-Woman")) {
            categoryID ="MCR01";
        }
        if (category.equals("Mercari-Men")) {
            categoryID ="MCR02";
        }
        if (category.equals("Mercari-Kids")) {
            categoryID ="MCR03";
        }
        if (category.equals("Mercari-Home")) {
            categoryID ="MCR04";
        }
        if (category.equals("Mercari-Collectibles")) {
            categoryID ="MCR05";
        }
        if (category.equals("Mercari-Beauty")) {
            categoryID ="MCR06";
        }
        if (category.equals("Mercari-Electronics")) {
            categoryID ="MCR07";
        }
        if (category.equals("Mercari-Outdoors")) {
            categoryID ="MCR08";
        }
        if (category.equals("Mercari-Handmade")) {
            categoryID ="MCR09";
        }
        if (category.equals("Mercari-Other")) {
            categoryID ="MCR09";
        }
        if (category.equals("Fb Market-Woman")) {
            categoryID ="FBM01";
        }
        if (category.equals("Fb Marketi-Men")) {
            categoryID ="FBM02";
        }
        if (category.equals("Fb Market-Kids")) {
            categoryID ="FBM03";
        }
        if (category.equals("Fb Market-Home")) {
            categoryID ="FBM04";
        }
        if (category.equals("Fb Market-Collectibles")) {
            categoryID ="FBM05";
        }
        if (category.equals("Fb Market-Beauty")) {
            categoryID ="FBM06";
        }
        if (category.equals("Fb Market-Electronics")) {
            categoryID ="FBM07";
        }
        if (category.equals("Fb Market-Outdoors")) {
            categoryID ="FBM08";
        }
        if (category.equals("Fb Market-Handmade")) {
            categoryID ="FBM09";
        }
        if (category.equals("Fb Market-Other")) {
            categoryID ="FBM10";
        }
        if (category.equals("OfferUp-Woman")) {
            categoryID ="OFUP01";
        }
        if (category.equals("OfferUp-Men")) {
            categoryID ="OFUP02";
        }
        if (category.equals("OfferUp-Kids")) {
            categoryID ="OFUP03";
        }
        if (category.equals("OfferUp-Home")) {
            categoryID ="OFUP04";
        }
        if (category.equals("OfferUp-Collectibles")) {
            categoryID ="OFUP05";
        }
        if (category.equals("OfferUp-Beauty")) {
            categoryID ="OFUP06";
        }
        if (category.equals("OfferUp-Electronics")) {
            categoryID ="OFUP07";
        }
        if (category.equals("OfferUp-Outdoors")) {
            categoryID ="OFUP08";
        }
        if (category.equals("OfferUp-Handmade")) {
            categoryID ="OFUP09";
        }
        if (category.equals("OfferUp-Other")) {
            categoryID ="OFUP10";
        }
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            //flag: 2,3,4   2 is min, 3 is max, 4 is item, 0 is state, 1 is category

            // no item
            String sql0 = "set foreign_key_checks = 0";
            //String sql1 = "insert into Platform Values(\'" + pname + "\')";
           // String sql2 = "insert into Category Values(\'" + categoryID + "\', \'" + purl + "\', \'" + category + "\')";
            String sql3 = "insert into User Values(\'" + userid + "\',\'" + userName +"\', \'" + state + "\', \'" + purl + "')";
            String sql4 = "insert into Item Values(\'" + itemID + "\',\'" + item + "\'," + String.valueOf(price) +
                    ",\'" + categoryID + "\',\'" + userid + "\')";
            String sql5 = "set foreign_key_checks = 0";
            //System.out.println(sql4);

            //state

            //3.ResultSet类，用来存放获取的结果集！！
            //statement.executeUpdate(sql1);
            //statement.executeUpdate(sql2);
            statement.executeUpdate(sql0);
            statement.executeUpdate(sql3);
            statement.executeUpdate(sql4);
            statement.executeUpdate(sql5);
            System.out.println("-----------------");
            System.out.println("The result is below:");
            System.out.println("-----------------");
            //output change
            PrintStream printStream = new PrintStream(new com.zhouzhou.function.CustomOutputStream(area));
            System.setOut(printStream);
            System.setErr(printStream);
            con.close();
        } catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            System.out.println("Insertion Done");
        }
    }
    public static void search(String category, String item, String state, int min, int max, int[] flag) {
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/secondhandmarket";
        //MySQL配置时的用户名
        String user = "ruiying";
        //MySQL配置时的密码
        String password = "Wry13871557836";
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            //flag: 2,3,4   2 is min, 3 is max, 4 is item, 0 is state, 1 is category

            // no item
            String sql = "select distinct p.pname, i.iname, i.iprice, u.uname, c.ctitle, u.ulocation from platform p, item i, user u, " +
                    "category c where c.cid = i.cid and i.uid = u.uid and u.purl = p.purl ";
            //state
            if(flag[0] == 1){
                sql = sql + "AND u.ulocation LIKE \'%" + state + "%\' ";
            }
            //category
            if(flag[1] == 1){
                sql = sql + "AND c.ctitle LIKE \'%" + category + "%\' ";
            }
            //minprice
            if(flag[2] == 1){
                sql = sql + "AND i.iprice > " + String.valueOf(min) + " ";
            }
            //maxprice
            if(flag[3] == 1){
                sql = sql + "AND i.iprice < " + String.valueOf(max) + " ";
            }
            //item
            if(flag[4] == 1){
                sql = sql + "AND i.iname LIKE \'%" + item + "%\' ";
            }
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("-----------------");
            System.out.println("The result is below:");
            System.out.println("-----------------");
            String iname = null;
            String iprice = null;
            String uname = null;
            String ctitle = null;
            String ulocation = null;
            String pname = null;
            //output change
            PrintStream printStream = new PrintStream(new com.zhouzhou.function.CustomOutputStream(area));
            System.setOut(printStream);
            System.setErr(printStream);
            while (rs.next()) {
                //获取iname这列数据
                //System.out.println("test");
                iname = rs.getString("iname");
                iprice = rs.getString("iprice");
                uname = rs.getString("uname");
                ctitle = rs.getString("ctitle");
                ulocation = rs.getString("ulocation");
                pname = rs.getString("pname");
                System.out.print("Selling in platform: " + pname + "\n");
                System.out.print("The merchandise: " + iname + "\n");
                System.out.print("The price is: " + iprice + "\n");
                System.out.print("The seller name is: " + uname + "\n");
                System.out.print("The category of this merchandise is: " + ctitle + "\n");
                System.out.print("The seller location is: " + ulocation + "\n");
                System.out.print("\n");
            }

            if(iname == null && iprice == null && uname == null && ctitle == null && ulocation == null){
                System.out.println("No merchandise matched your requirement");
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            System.out.println("Searching Done");
        }
    }
}
