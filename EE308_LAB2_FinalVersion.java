import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class EE308_LAB2_FinalVersion
{
	public static void main(String[] args) throws IOException
	{
		String address = "";
		int level = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the path of the code file: ");
		address = sc.nextLine();
		System.out.println("Please enter the completion level you want: ");
		level = sc.nextInt();
		String keywords[] = {"auto","break","case","char","const","continue","default","do",
				             "double","else","enum","extern","float","for","goto","if","int",
				             "long","register","return","short","signed","sizeof","static",
				             "struct","switch","typedef","union","unsigned","void","volatile","while"};
		int number[] = new int[keywords.length];
		FileInputStream iStream = new FileInputStream(address);
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
		boolean haselseif = false;
		Vector v = new Vector();
		Stack<String> s = new Stack<String>();
		String temp = "";
		int num_switch = 0;
		int num_case = 0;
		int num_if_else = 0;
		int num_if_elseif_else = 0;
		while((str = BR.readLine()) != null)
		{
			//Delete the content before the block comments symbol
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
			//Delete the content after the line comment symbol
			if(str.contains("//"))
			{
				int index1 = 0;
				index1 = str.indexOf("//");
				str = str.substring(0, index1);
			}
			//Delete the content after the block comment symbol
			else if(str.contains("/*"))
			{
				flag = true;
				int index2 = 0;
				index2 = str.indexOf("/*");
				str = str.substring(0, index2);
			}
			//Delete strings
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
			//count the total number of keywords
			for(int i = 0;i < keywords.length;i++)
			{
				if(str.contains(keywords[i])) {
					number[i]++;
					total++;
				}
			}
			//count the number of "switch case" structures and the number of "case" corresponding to each group
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
			//count the number of "if else" structures and the number of "if, else if, else" structures
			if(str.contains("else if"))
			{
				s.push("else if");
			}
			else if(str.contains("if"))
			{
				s.push("if");
			}
			else if(str.contains("else"))
			{
				while(true)
				{
					temp = s.pop();
					if(temp == "else if")
					{
						haselseif = true;
					}
					if(temp == "if")
					{
						break;
					}
				}
				if(haselseif) 
				{
					haselseif = false;
					num_if_elseif_else++;
				}
				else
				{
					num_if_else++;
				}
			}
			else
			{}
		}
		//keyword "do" and keyword "double" have the same prefix, counting "double" means counting "do" together
		//so I need to subtract the times which "double" was counted.
		total = total - number[double_index];	
		//close streams
		iStream.close();
		BR.close();
		//output the results according to different completion level
		if(level >= 1)
		{
			System.out.println("total num: "+total);
		}
		if(level >= 2)
		{
			System.out.println("switch num: "+num_switch);
			System.out.println("case num: "+v.toString());
		}
		if(level >= 3)
		{
			System.out.println("if-else num: "+num_if_else);
		}
		if(level >= 4)
		{
			System.out.println("if-elseif-else num: "+num_if_elseif_else);
		}
	}
}