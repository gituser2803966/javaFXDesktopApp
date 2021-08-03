package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import db.utilities.*;

public class BusModel {
    //jobcode,
    // numRouter,
    // num,
    // routeName,
    // category,
    // contract
    private String jobcode;
    private String numRouter;
    private String num;
    private String routeName;
    private String category;
    private Integer contract;

    public BusModel() {

    }

    public BusModel(String jobcode, String numRouter, String num, String routeName, String category, Integer contract) {
        this.jobcode = jobcode;
        this.numRouter = numRouter;
        this.num = num;
        this.routeName = routeName;
        this.category = category;
        this.contract = contract;
    }

    //\\ get
    public String getJobcode() {
        return jobcode;
    }

    public String getNumRouter() {
        return numRouter;
    }

    public String getNum() {
        return num;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getCategory() {
        return category;
    }

    public Integer getContract() {
        return contract;
    }

    //\\ set
    public void setJobcode(String jobcode) {
        this.jobcode = jobcode;
    }

    public void setNumRouter(String numRouter) {
        this.numRouter = numRouter;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setContract(Integer contract) {
        this.contract = contract;
    }


    public ArrayList<BusModel> GetBusList() {
        ArrayList<BusModel> busArrayList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            String selectQuery = "select * from Bus";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = preparedStatement.executeQuery();
            BusModel busModel;
            while (rs.next()) {
                busModel = new BusModel(rs.getString("jobcode"),
                        rs.getString("numRouter"),
                        rs.getString("num"),
                        rs.getString("routeName"),
                        rs.getString("category"),
                        rs.getInt("contract")
                );
                busArrayList.add(busModel);
            }


        } catch (Exception ex) {
            System.out.println("error: " + ex);
            ex.printStackTrace();
        }

        return busArrayList;
    }

}
