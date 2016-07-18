package com.techm.trackingtool.util.csvReader.opencsv.bean;

/**
 Copyright 2007 Kyle Miller.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.VivaldiInfo;
import com.techm.trackingtool.admin.dao.CRInfoDAO;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.csvReader.opencsv.CSVReader;

public class CsvToBean {

	private static final LindeToolLogManager lindeLogMgr = new LindeToolLogManager(CsvToBean.class.getName());
	
    public CsvToBean() {
    }
 
    public List parse(Class obj, Reader reader,String []columns) {
        try {
        	ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
  	      	CSVReader csv = new CSVReader(reader);
  	        strat.setType(obj);
            strat.captureHeader(csv);
            String[] line;
            //System.out.println("columns "+columns[0]+columns[1]+columns[2]+columns[3]+columns[4]+columns[5]+columns[6]+columns[7]+columns[8]+columns[9]+columns[10]);
            List list = new ArrayList();
            if(null != (line = csv.readNext()))
            {
            	strat.setColumnMapping(columns);
            	
	            while(null != (line = csv.readNext())) {
	            	Object obj1 = processLine(strat, line);
	            	
            		list.add(obj1);
            		TicketInfo add=(TicketInfo)obj1;
            		//System.out.println("CSV To: "+add.getStatus()+" "+add.getDescription()+" "+add.getIndividual());
            		// TODO: (Kyle) null check object
	            }
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing CSV!", e);
        }
    }
    
    public List vivaldiparse(Class obj, Reader reader,String []columns) {
        try {
        	ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
  	      	CSVReader csv = new CSVReader(reader);
  	        strat.setType(obj);
            strat.captureHeader(csv);
            String[] line;
            
            List list = new ArrayList();
            if(null != (line = csv.readNext()))
            {
            	strat.setColumnMapping(columns);
            	
	            while(null != (line = csv.readNext())) {
	            	Object obj1 = processLine(strat, line);
	            	
            		list.add((VivaldiInfo)obj1);
            		VivaldiInfo add=(VivaldiInfo)obj1;
            		//System.out.println("CSV To: "+add.getStatus()+" "+add.getDescription()+" "+add.getIndividual());
            		// TODO: (Kyle) null check object
	            }
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing CSV!", e);
        }
    }
    
    public List crdataparse(Class obj, Reader reader,String[] columns) {
        try {
        	ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
  	      	CSVReader csv = new CSVReader(reader);
  	        strat.setType(obj);
            strat.captureHeader(csv);
            String[] line;
            
            List list = new ArrayList();
            if(null != (line = csv.readNext()))
            {
            	strat.setColumnMapping(columns);
            	
	            while(null != (line = csv.readNext())) {
	            	Object obj1 = processLine(strat, line);
	            	
            		list.add((CRinfo)obj1);
            		CRinfo add=(CRinfo)obj1;
	            }
            }
            return list;
        } catch (Exception e) {
        	System.out.println("Exception Message is ::::"+e.getMessage());
            throw new RuntimeException("Error parsing CSV!", e);
        }
    }

    protected Object processLine(MappingStrategy mapper, String[] line) throws IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        Object bean = mapper.createBean();
        try{
        for(int col = 0; col < line.length; col++) {
            String value = line[col];
            lindeLogMgr.logMessage("DEBUG","processLine processLine   "+value);
            PropertyDescriptor prop = mapper.findDescriptor(col);
            if (null != prop) {
                Object obj = convertValue(value, prop);
                lindeLogMgr.logMessage("DEBUG","objobjobjobjobjobjobj   "+obj.toString());
                prop.getWriteMethod().invoke(bean, new Object[] {obj});
            }
        }
        }
        catch(Exception ex)
    	{
    		lindeLogMgr.logStackTrace("ERROR", "Exception in processLine() ::  ", ex);
    	}
        return bean;
    }

    
    protected Object convertValue(String value, PropertyDescriptor prop) throws InstantiationException, IllegalAccessException {
    	PropertyEditor editor = getPropertyEditor(prop);
        Object obj = value;
        java.sql.Date sqlDate =null;
        SimpleDateFormat sdf = null;
        lindeLogMgr.logMessage("DEBUG","value of field is "+value.toString());
    	try
    	{
    		lindeLogMgr.logMessage("DEBUG","convertValue PropertyDescriptor prop   "+prop.getPropertyType()+"  sdfsf "+prop.getName());
    		lindeLogMgr.logMessage("DEBUG","convertValue value is  "+value.toString());
    		String propertyType= prop.getPropertyType().toString();
    		
    		if(value.split("/").length>1) 
    		{
    			lindeLogMgr.logMessage("DEBUG","convertValue value in if condition  "+value);
    			String[] yearStrValue = value.split("/");
        		
        		//String yearValue=yearStrValue[2];
        		
        		String[] yearValue=yearStrValue[2].split(" ");
    			
        		lindeLogMgr.logMessage("DEBUG","yearValue is ::"+yearValue.length+" yearValue[2] :: "+yearValue[0].length());
    		if(yearValue[0].length() == 4)
    		{
    			 sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    			 lindeLogMgr.logMessage("INFO","if");
    		}
    		else
    		{
    			sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    			lindeLogMgr.logMessage("INFO","else ");
    		}
    		
    			Date date = sdf.parse(value);
    			sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    			value=sdf.format(date);
    			//sqlDate =  new java.sql.Date(date.getTime());
    		}
    				
        if (null != editor) {
     		 editor.setAsText(value);
            obj = editor.getValue();
        }
    	}
    	catch(Exception ex)
    	{
    		lindeLogMgr.logStackTrace("ERROR", "Exception in convertValue() ::  ", ex);
    	}
        return obj;
    }

    /*
     * Attempt to find custom property editor on descriptor first, else try the propery editor manager.
     */
    protected PropertyEditor getPropertyEditor(PropertyDescriptor desc) throws InstantiationException, IllegalAccessException {
        Class cls = desc.getPropertyEditorClass();
        if (null != cls) return (PropertyEditor) cls.newInstance();
        return PropertyEditorManager.findEditor(desc.getPropertyType());
    }

}
