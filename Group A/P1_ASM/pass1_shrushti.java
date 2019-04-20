package grpa1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

public class pass1 {
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		String line;
		HashMap<String,String> IS = new HashMap<>();
		IS.put("STOP", "00");
		IS.put("ADD","01");
		IS.put("SUB","02");
		IS.put("MULT","03");
		IS.put("MOVER","04");
		IS.put("MOVEM","05");
		IS.put("COMP","06");
		IS.put("BC","07");
		IS.put("DIV","08");
		IS.put("READ","09");
		IS.put("PRINT","10");
		HashMap<String,String> AD = new HashMap<>();
		AD.put("START","01");
		AD.put("END","02");
		AD.put("ORIGIN","03");
		AD.put("EQU","04");
		AD.put("LTORG","05");
		HashMap<String,String> DL = new HashMap<>();
		DL.put("DC", "01");
		DL.put("DS", "02");
		HashMap<String,String> REG = new HashMap<>();
		REG.put("AREG", "1");
		REG.put("BREG", "2");
		REG.put("CREG", "3");
		REG.put("DREG", "4");
		HashMap<String,String> CC = new HashMap<>();
		CC.put("LT", "1");
		CC.put("LE", "2");
		CC.put("EQ", "3");
		CC.put("GT", "4");
		CC.put("GE", "5");
		CC.put("ANY", "6");
		int lit[]=new int[100];
		ArrayList<String> symtab = new ArrayList<>(); 
		//String symtab[]=new String[100];
		int symptr=0;
		int litpntr=0;
		try (
		    InputStream fis = new FileInputStream("/home/TE/3413/sposlab/grpa1/src/grpa1/code.txt");
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		) {
		    while ((line = br.readLine()) != null) {
		       String token[]=line.split("\\s*(,|\\s)\\s*");
		       String line2="";
		       for(String a : token)
		       {
		    	   if(IS.get(a)!=null)
		    	   {
		    		   line2=line2+"(IS,"+IS.get(a)+")"+"\t";
		    		   //System.out.println("(IS,"+IS.get(a)+")");
		    	   }
		    	   else if(AD.get(a)!=null)
		    	   {
		    		   line2=line2+"(AD,"+AD.get(a)+")"+"\t";
		    		   
		    		   if(AD.get(a).equals("05"))
		    		   {
		    			   
		    			   for(int j=0;j<litpntr;j++)
		    			   {
		    				   line2=line2+"\n"+"(DL,01) (C,"+lit[j]+")";
		    			   }
		    			   break;
		    		   }
		    		   if(AD.get(a).equals("04"))
		    		   {
		    			   line2="NO IC";
		    			   break;
		    		   }
		    		   
		    	   }
		    	   else if(DL.get(a)!=null)
		    	   {
		    		   line2=line2+"(DL,"+DL.get(a)+")"+"\t";
		    		   //System.out.println("(DL,"+DL.get(a)+")");
		    	   }
		    	   else if(REG.get(a)!=null)
		    	   {
		    		   line2=line2+"("+REG.get(a)+")\t";
		    		  // System.out.println("("+REG.get(a)+")");
		    	   }
		    	   else if(CC.get(a)!=null)
		    	   {
		    		   line2=line2+"("+CC.get(a)+")\t";
		    		  // System.out.println("("+REG.get(a)+")");
		    	   }
		    	   else
		    	   {
		    		   
		    		   if(a.contains("="))
		    		   {
		    			   String litt[]=a.split("'");
		    			   lit[litpntr]=Integer.parseInt(litt[1]);
		    			   line2=line2+"(L,"+(litpntr+1)+")";
		    			   litpntr++;
		    			   
		    		   }
		    		   else
		    		   {
		    			   if(a!=token[0])
		    			   {
			    			  char arr[]=a.toCharArray();
			    			   int num[]=new int[a.length()];
			    			   int flag = 0;
			    			   for(int z=0;z<a.length();z++)
			    			   {
			    				   num[z]=arr[z];
			    				   if(num[z]>=65&&num[z]<=90)
			    				   {
			    					   if(!symtab.contains(a))
			    					   {
			    						   if(a.contains("+"))
			    						   {
			    							   String dis[]=a.split("\\+");
			    							   line2=line2+"(S,"+(symtab.indexOf(dis[0])+1)+")+"+dis[1];
			    							   flag=1;
			    							   break;
			    						   }
			    						   else
			    						   {
					    					   symtab.add(a);
					    					   //symtab[symptr]=a;
					    					   symptr++;
					    					   flag=1;
					    					   line2=line2+"(S,"+symptr+")";
					    					   break;
					    				   }
			    					   }
			    				   }
			    			   }
			    			   if(symtab.contains(a)&&flag==0)
			    			   {
			    				   line2=line2+"(S,"+(symtab.indexOf(a)+1)+")";
			    				   flag=1;
			    			   }
			    			  if(flag==0)
			    			  {
			    			   line2=line2+"(C,"+a+")";
			    			  }
		    			   }
		    			   else
		    			   {
		    				   if(!symtab.contains(a))
		    				   {
		    				   symtab.add(a);
		    				   symptr++;
		    				   }
	
		    			   }
		    		   }
		    		   //System.out.println(a);
		    	   }
		       }
		       System.out.println(line2);
		    } 
		}
		for(int sym=0;sym<litpntr;sym++)
		   {
			   System.out.println(lit[sym]);
		   }
	       
			  System.out.println(symtab);
	}

}
