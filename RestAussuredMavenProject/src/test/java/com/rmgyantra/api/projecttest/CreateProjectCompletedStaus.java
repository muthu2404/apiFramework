package com.rmgyantra.api.projecttest;

import static io.restassured.RestAssured.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.codehaus.jackson.map.deser.ValueInstantiators.Base;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


import com.mysql.jdbc.Driver;
import com.rmgyantra.api.pojoClassLib.Project;
import com.rmgyantra.genericlib.BaseClass;
import com.rmgyantra.genericlib.DataBaseUtilities;
import com.rmgyantra.genericlib.IEndPoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class CreateProjectCompletedStaus extends BaseClass{
	
	

	@Test
	public void createProjectTest() throws Throwable {
		String projectStatus = "Completed";
		String actAPIProjectNAme = "act";
        Project pObj = new Project(actAPIProjectNAme, "aug", "deepak", projectStatus, 12);
		
   	 
      Response resp =  given()
                        .contentType(ContentType.JSON)
                        .body(pObj)
                      .when()
                      .post(IEndPoints.addSinglePRoject);
			   resp.then()
				       .assertThat().statusCode(201)
				       .and()
				       .assertThat().contentType(ContentType.JSON)
				       .and()
				         .log().all();
       String scuMg = resp.jsonPath().get("msg");
       Assert.assertEquals(scuMg, "Successfully Added");
       
       
       //Connect to dataDase
       String captureStatus = DataBaseUtilities.executeQueryAndGetData("select *from project", 5, projectStatus);
       Assert.assertEquals(captureStatus, projectStatus);
       
       //Connect GUI , get the PRojectNme & verify
       
	}

}













