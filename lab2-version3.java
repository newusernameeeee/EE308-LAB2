import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class lab2-version3
{
	public static void main(String[] args) throws IOException
	{
		String keywords[] = {"auto","break","case","char","const","continue","default","do",
				             "double","else","enum","extern","float","for","goto","if","int",
				             "long","register","return","short","signed","sizeof","static",
				             "struct","switch","typedef","union","unsigned","void","volatile","while"};
		int number[] = new int[keywords.length];
		FileInputStream iStream = new FileInputStream("C:\\c.txt");
		BufferedReader BR = new BufferedReader(new InputStreamReader(iStream));
		String str = null;
		int total = 0;
		int double_index = 0;
		for(int i = 0;i < keywords.length;i++) {
			if(keywords[i] == "double") {
				double_index = i;
			}
		}
		boolean flag = false;
		boolean inswitch = false;
		Vector v = new Vector();
		int num_switch = 0;
		int num_case = 0;
		int num_if_else = 0;
		int num_if_elseif_else = 0;
		while((str = BR.readLine()) != null)
		{
			if(flag)
			{
				if(str.contains("*/")) 
				{
					int index = 0;
					index = str.indexOf("*/");
					str = str.substring(index+1);
					flag = false;
				}
				else
				{
					break;
				}
			}
			
			if(str.contains("//"))
			{
				int index1 = 0;
				index1 = str.indexOf("//");
				str = str.substring(0, index1);
			}
			else if(str.contains("/*"))
			{
				flag = true;
				int index2 = 0;
				index2 = str.indexOf("/*");
				str = str.substring(0, index2);
			}
			else if(str.contains("\""))
			{
				int index3 = 0;
				int index4 = 0;
				index3 = str.indexOf("\"");
				String str1 = str.substring(0, index3);
				str = str.substring(index3+1);
				index4 = str.indexOf("\"");
				String str2 = str.substring(index4+1);
				str = str1+str2;
			}
			else
			{}
			for(int i = 0;i < keywords.length;i++)
			{
				if(str.contains(keywords[i])) {
					number[i]++;
					total++;
				}
			}
			if(str.contains("switch"))
			{
				num_switch++;
				inswitch = true;
			}
			if(str.contains("case")) 
			{
				num_case++;
			}
			if(str.contains("default"))
			{
				v.addElement(num_case);
				num_case = 0;
				inswitch = false;
			}	
		}
		total = total - number[double_index];	
		iStream.close();
		BR.close();
		System.out.println("total num: "+total);
		System.out.println("switch num: "+num_switch);
		System.out.print("case num: "+v.toString());
	}
}