package com.techm.trackingtool.util;

import java.util.List;

import com.techm.trackingtool.admin.bean.TicketInfo;
public class SortObjects {
	private String slCompareField = "";
    private String slSortType = "";
    public SortObjects(String slCompareField, String slSortType) {
		super();
		this.slCompareField = slCompareField;
		this.slSortType = slSortType;
	}
	public int compare(Object obj1, Object obj2) {
	        int result = 0;
	        try {
	        	TicketInfo o1 = (TicketInfo) obj1;
	        	TicketInfo o2 = (TicketInfo) obj2;
	                if (slCompareField.equals("casetype")
							|| slCompareField.equals("region")
	                	|| slCompareField.equals("individual")
						|| slCompareField.equals("status")
							|| slCompareField.equals("application")
							|| slCompareField.equals("ttno")
							|| slCompareField.equals("severity")
							|| slCompareField.equals("assigneddate")
							|| slCompareField.equals("createddate")
							|| slCompareField.equals("resolveddate")
					) { 
	                    String level1 = "";
	                    String level2 = "";
	                    if (slCompareField.equals("casetype")) {
	                    	level1 = o1.getIncidentID().toUpperCase();
	                    	level2 = o2.getIncidentID().toUpperCase();
	                    } else if (slCompareField.equals("region")) {
	                        if(o1.getModule()==null){
	                        level1 = "AAAA";
	                    	}
	                        else{
	                        	level1=o1.getModule().toUpperCase();
	                        	}
	                        if(o2.getModule()==null){
		                        level2 = "AAAA";
		                    	}
		                        else{
		                        	level2=o2.getModule().toUpperCase();
		                        	}
	                        if(level1==null||level1.equals("")){
	                        	System.out.println("null encountered");
	                        	level1="Z";
	                        	}
	                        if(level2==null||level2.equals("")){System.out.println("null encountered");
	                        level2="Z";}
	                    } 
	                    else if (slCompareField.equals("status")) {
	                        level1 = o1.getStatus().toUpperCase();
	                        level2 = o2.getStatus().toUpperCase();
	                    } 
	                    
	                    else if (slCompareField.equals("severity")) {
	                        level1 = o1.getPriority().toUpperCase();
	                        level2 = o2.getPriority().toUpperCase();
	                    } 
	                    /*else if (slCompareField.equals("ageing")) {
	                        level1 = o1.getAge().toUpperCase();
	                        level2 = o2.getAge().toUpperCase();
	                    } 
	                    */else if (slCompareField.equals("application")) {
	                    	if(o1.getModule()==null){
		                        level1 = "AAAA";
		                    	}
	                    	else
	                    		level1 = o1.getModule().toUpperCase();
	                    	
	                    	if(o2.getModule()==null){
		                        level2 = "AAAA";
		                    	}
	                    	else	                    	
	                        level2 = o2.getModule().toUpperCase();
	                    	
	                    } 
	                    else if (slCompareField.equals("individual")) {
	                    	if(o1.getAssigneeFullname()==null){
		                        level1 = "AAAA";
		                    }
	                    	else{
		                       	level1=o1.getAssigneeFullname().toUpperCase();
		                       	}
		                    if(o2.getAssigneeFullname()==null){
			                    level2 = "AAAA";
			                   	}
			                else{
			                   	level2=o2.getAssigneeFullname().toUpperCase();
			                   	}
		                    if(level1==null||level1.equals("")){
		                      	level1="Z";
		                    	}
		                    if(level2==null||level2.equals("")){
		                    level2="Z";
		                    	}  
	                    } 
	                    else if (slCompareField.equals("ttno")) {
	                        level1 = o1.getIncidentID().toUpperCase();
	                        level2 = o2.getIncidentID().toUpperCase();
	                    }
	                    if (level1 == null) {
	                        level1 = "";
	                    }
	                    if (level2 == null) {
	                        level2 = "";
	                    }
	                    if (level1 != null && level2 != null) {
	                        level1 = level1.trim();
	                        level2 = level2.trim();
	                        if (slSortType.equals("ascending")) {
	                            result = level1.compareTo(level2);
	                        } else {
	                           result = level2.compareTo(level1);
	                        }
	                    }
	                }
	           /* if(slCompareField.equals("ageing"))
	            {
	            	Double level1 = new Double(Double.parseDouble(o1.getAge()));
	            	Double level2 = Double.parseDouble(o2.getAge());
	            	if (slSortType.equals("ascending")) {
                        result = level1.compareTo(level2);
                    } else {
                       result = level2.compareTo(level1);
                    }
	            }*/
	        }
	        catch (Exception e) {
	        	System.out.println(""+e.getMessage());
	        	e.printStackTrace();
	        }
	        return result;
	    }
	public List getSortedPrograms(List aListToBeSorted,String aSortField,String aSortType) throws  Exception{
        SortObjects searchObj = null;
        int result = 0;
        int size = 0;
        TicketInfo obj1 = null;
        TicketInfo obj2 = null;
        TicketInfo tempObj = null;
        try{
        if(aListToBeSorted != null && aListToBeSorted.size() > 1){
        	searchObj = new SortObjects(aSortField,aSortType);
        	size = aListToBeSorted.size();
        	for(int i = 0; i < size -1 ; i++){
        		for(int j = 0; j < size -1 ; j++){
        			obj1 = (TicketInfo)aListToBeSorted.get(j);
        			obj2 = (TicketInfo)aListToBeSorted.get(j+1);
        			result = searchObj.compare(obj1,obj2);
        			if(result < 0){
        				tempObj = obj2;
        				obj2 = obj1;
        				obj1 = tempObj;
        				aListToBeSorted.set(j,obj1);
        				aListToBeSorted.set(j+1,obj2);
        			}
        		}
        	}
        }
       } catch (Exception e) {
    	   e.printStackTrace();
       }
        return aListToBeSorted;

}
}
