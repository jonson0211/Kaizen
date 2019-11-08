/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//**package kaizen;

import java.sql.ResultSet;

/**
 *
 * @author jonso
 */

    public class TaskDataAccessor {
    //Query
    
    ResultSet rs = taskStatement.executeQuery("SELECT TITLE, CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY FROM TASKS");
    
    String title = rs.getString("TITLE");
    String categoryName = rs.getString("CATEGORYNAME");
    String description = rs.getString("DESCRIPTION");
    String doDate = rs.getString("DO_DATE");
    String dueDate = rs.getString("DUE_DATE");
    String priority = rs.getString("PRIORITY");
    
    Task task = new Task(title, categoryName, description, priority, doDate, dueDate);
