package com.techm.trackingtool.util.csvReader.opencsv.bean;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;

import com.techm.trackingtool.admin.dao.CRInfoDAO;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.csvReader.opencsv.CSVReader;

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

public class HeaderColumnNameMappingStrategy implements MappingStrategy {
	
	private static final LindeToolLogManager lindeLogMgr = new LindeToolLogManager(HeaderColumnNameMappingStrategy.class.getName());
	
    protected String[] header;
    protected PropertyDescriptor[] descriptors;
    protected Class type;

    public void captureHeader(CSVReader reader) throws IOException {
        header = reader.readNext();
    }

    public PropertyDescriptor findDescriptor(int col) throws IntrospectionException {
        String columnName = getColumnName(col);
        lindeLogMgr.logMessage("INFO", "findDescriptor ::::: columnName"+columnName);
        return (null != columnName && columnName.trim().length()>0) ? findDescriptor(columnName) : null;
    }

    protected String getColumnName(int col) {
        return (null != header && col < header.length) ? header[col] : null;
    }
    
    protected PropertyDescriptor findDescriptor(String name) throws IntrospectionException {
    	lindeLogMgr.logMessage("INFO", "findDescriptor :::"+name);
    	lindeLogMgr.logMessage("INFO", "getType :::"+getType());
    	try{
        if (null == descriptors) descriptors = loadDescriptors(getType()); //lazy load descriptors
        
        lindeLogMgr.logMessage("INFO", "descriptors :::"+descriptors.length);
        for (int i = 0; i < descriptors.length; i++) {
            PropertyDescriptor desc = descriptors[i];
            lindeLogMgr.logMessage("INFO", "PropertyDescriptor  :::desc "+desc.getName());
            if (matches(name, desc)) return desc; // TODO: (Kyle) do null/blank check
        }
    	}
    	catch(Exception ex)
    	{
    		lindeLogMgr.logStackTrace("ERROR", "exiugyhgdhgdiuhgi", ex);
    	}
    	 lindeLogMgr.logMessage("INFO", "PropertyDescriptor  :::returning ");
        return null;
    }
    
    protected boolean matches(String name, PropertyDescriptor desc) {
       // return desc.getName().equals(name); 
    	
    	lindeLogMgr.logMessage("DEBUG", "name is ::: "+name);
    	lindeLogMgr.logMessage("DEBUG", "PropertyDescriptor desc is ::: "+desc);
    	String[] nameStr = name.split(" ");
    	StringBuilder nameStrBuilder = new StringBuilder();
    	lindeLogMgr.logMessage("DEBUG", "Length of the coulmn name is  ::: "+nameStr.length);
    	if(nameStr.length>1)
    	for (String string : nameStr) {
    		nameStrBuilder.append(string);
		}
    	else
    		nameStrBuilder.append(nameStr[0]);
    	
    	
    	 return nameStrBuilder.toString().equalsIgnoreCase(desc.getName());
    }
    
    protected PropertyDescriptor[] loadDescriptors(Class cls) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(cls);
        lindeLogMgr.logMessage("INFO", "beanInfo :::"+beanInfo.getBeanDescriptor().getDisplayName());
        
        return beanInfo.getPropertyDescriptors();
    }
    
    public Object createBean() throws InstantiationException, IllegalAccessException {
        return type.newInstance();
    }
    
    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }
}
