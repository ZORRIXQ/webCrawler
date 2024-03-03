package com.zorrix.database;

import com.zorrix.SiteId;

import java.sql.*;

//SQL logic class
public class SQLRequestManager {
    private Connection connection;

    public void insertData(SiteId siteId) throws SQLException, ClassNotFoundException {
        connection = ConnectionService.connect();

        //inserting url query
        String insertUrlQuery = "INSERT INTO urls(url) VALUES (?) ";
        PreparedStatement statement = connection.prepareStatement(insertUrlQuery);
        statement.setString(1, siteId.getUrl());
        statement.execute();
        statement.close();

        //inserting each key word linked with another table's url
        for (String word : siteId.getKeyWords()){
            insertUrlQuery = "INSERT INTO key_words(word, url_id) VALUES (?, ?)";
            PreparedStatement statement2 = connection.prepareStatement(insertUrlQuery);

            statement2.setString(1, word);

            Statement urlIndexStatement = connection.createStatement();
            //getting current word's url
            ResultSet urlsId = urlIndexStatement.executeQuery("SELECT id FROM urls where url = '" + siteId.getUrl() + "'");

            if (urlsId.next()) {
                statement2.setInt(2, urlsId.getInt("id"));
                statement2.executeUpdate();
            } else
                System.out.println("not found same urls id");
        }

        connection.close();
    }
}
