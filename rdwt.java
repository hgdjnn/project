package exp1;

import java.io.*;
import java.lang.Math.*;

class Node{
	public Node(){
		ability=10000;
		priority=0;
	}
	public Node(Node temp){
		ability=temp.ability;
		cost=temp.cost;
		priority=temp.priority;
		num=temp.num;
		sym=temp.sym;
	}
	double ability;
	double cost;
	int priority;
	int num;
	char sym;
}

public class rdwt {
	public static void main(String[] ags){
		long pre=System.currentTimeMillis();
		File file=new File("src/exp1/SERVICE.txt");
		File REQ=new File("src/exp1/REQ.txt");
		File PRO=new File("src/exp1/PROCESS.txt");
		File RES=new File("src/exp1/RESULT.txt");
		try{
			if(!RES.exists()){//判断文件是否真正存在,如果不存在,创建一个
				RES.createNewFile();
        	}
			FileReader read=new FileReader(file);
			BufferedReader readstr=new BufferedReader(read);
			String data = readstr.readLine();
			String[] num=null;
			double[][] ability=new double[14][500];
			double[][] cost=new double[14][500];
			Node[][] node1=new Node[14][11];
			Node temp=new Node();
			int i=0,ii=0,iii=0;
			for(i=0;i<14;i++)
				for(ii=0;ii<11;ii++)
					node1[i][ii]= new Node();
			i=0;
			ii=0;
			while(data!=null) //读入SERVICE.txt中的内容
			{
				num=data.split(" ");
				ability[i][ii] = Double.parseDouble(num[2]);
				cost[i][ii] = Double.parseDouble(num[4]);
				data = readstr.readLine();
				ii++;
				if(ii==500)
				{
					i++;
					ii=0;
				}	
			}
			for(i=0;i<14;i++)    //初始化对象数组
				for(ii=0;ii<11;ii++)
				{
					double nnn=(double)(90+ii)/100;
					node1[i][ii].cost=10000;
					node1[i][ii].ability=nnn;
					node1[i][ii].sym=(char)(65+i);
					/*System.out.println(node1[i][ii].ability+"   "+node1[i][ii].cost);*/
				}
			for(i=0;i<14;i++)  //第一次剪枝
			{
				for(ii=0;ii<500;ii++)
				{
					for(iii=0;iii<11;iii++)
					{
						double gg=iii+90;
						double ggg=gg/100;
						if(ability[i][ii]==ggg)
						{
							if(cost[i][ii]<node1[i][iii].cost) 
							{
								node1[i][iii].cost=cost[i][ii];
								node1[i][iii].num=ii+1;
							}
							break;
						}
					}
				}
			} 	
			
			
		
			
			
			
			FileReader re = new FileReader(REQ);
			FileReader pr = new FileReader(PRO);
			char rearray[]=new char[11];
			char prarray[]=new char[1000];
			Node[][] node2= new Node[200][12];
			int times=0, mark=0, n=0, nodenumber=-1,find=0;
			int q, qq, OK=0, priority=0, lengthpr;
			int[] symbol=new int[14];
			double TP, TR, TP1=0, TR1=1, TR2=1, TP2=0;
			FileWriter result=new FileWriter(RES);
			while(mark!=1)
			{
				i=0;
				do //读入REQ.txt内容
				{
					n=re.read();
					if(n!=10)
					{
						rearray[i]=(char) n;
						i++;
					}
					if(n==-1) mark=1;
				}while(n!=13&&n!=-1);
				TP=(double)(rearray[3]-48)/10;
				TR=(int)(rearray[5]-48)*10;
				
				
				for(i=0;i<14;i++)    //重新排序node1对象数组
					for(ii=0;ii<11;ii++)
						for(iii=ii+1;iii<11;iii++)
						{
							if(node1[i][ii].priority>node1[i][iii].priority) 
							{
								temp=new Node(node1[i][ii]);
								node1[i][ii]=new Node(node1[i][iii]);
								node1[i][iii]=new Node(temp);
							}
						}
				
				i=0;
				do //读入PROCESS.txt内容
				{
					n=pr.read();
					prarray[i]=(char) n;
					i++;
					if(n==-1) mark=1;
				}while(n!=13&&n!=-1);
				lengthpr=i;
				nodenumber=-1;
				i=lengthpr;
				for(q=0;q<i-2;q++)
				{
					if(prarray[q]>64&&prarray[q]<91)
						++nodenumber;                  //计算PROCESS节点数目
				}
				
				
				for(i=0;i<14;i++)
					for(ii=0;ii<11;ii++)
						node1[i][ii].priority=0;   //初始化数组优先级
					
					
				for(i=0;i<14;i++)
					for(ii=0;ii<11;ii++)
					{
						
						for(iii=ii+1;iii<11;iii++)
						if(node1[i][ii].ability>node1[i][iii].ability) 
						{
							temp=new Node(node1[i][ii]);
							node1[i][ii]=new Node(node1[i][iii]);
							node1[i][iii]=new Node(temp);          //根据优先级重新排序数组
						}
					}
			
				
				for(i=0;i<14;i++)
				{
					for(ii=0;ii<11;ii++)
					{
						for(iii=ii+1;iii<11;iii++)
						{
							if(pow(0.9,nodenumber)*(node1[i][iii].ability-node1[i][ii].ability)>(node1[i][iii].cost-node1[i][ii].cost)/100)
							{
								if(node1[i][iii].ability!=0.1&&node1[i][ii].ability!=0.1)
								{
									++node1[i][ii].priority;   //根据PROCESS决定优先级
								}
							}
							else
							{
								if(node1[i][iii].ability-node1[i][ii].ability<(node1[i][iii].cost-node1[i][ii].cost)/100)
								{
									if(node1[i][iii].ability!=0.1&&node1[i][ii].ability!=0.1)
									{
										++node1[i][iii].priority;
									}
								}
							}
						}
					}
				}
				
				
				for(i=0;i<14;i++)
					for(ii=0;ii<11;ii++)
						for(iii=ii+1;iii<11;iii++)
						{
							if(node1[i][ii].priority>node1[i][iii].priority) 
							{
								temp=new Node(node1[i][ii]);
								node1[i][ii]=new Node(node1[i][iii]);    //根据优先级重新排序
								node1[i][iii]=new Node(temp);
							}
						}
				
				
				
				
				
				                                   
				for(q=0;q<14;q++)
					symbol[q]=0;
				nodenumber=-1;
				i=lengthpr;
				for(q=0;q<i-2;q++)
				{
					
					if(prarray[q]>64&&prarray[q]<91&&symbol[prarray[q]-65]==0)
					{
						++nodenumber;
						symbol[prarray[q]-65]=1;
						for(qq=0;qq<11;qq++)
						{
							node2[20*times+nodenumber][qq]=new Node(node1[prarray[q]-65][qq]);    //对象内容传递给另一个数组方便搜索
						}
					}
				}
				
				
				TP1=0;
				TR1=1;
				for(q=0;q<nodenumber+1;q++)
					symbol[q]=0;
				while(OK!=1)   //最终的搜索过程
				{	
					for(i=0;i<nodenumber+1;i++)
					{
						ii=0;
						while(node2[20*times+i][ii].priority<=priority)
						{
							TP2=1;
							TR2=0;
							for(q=0;q<nodenumber+1;q++)
							{
								if(q!=ii)
								{
									TP2*=node2[20*times+q][symbol[q]].ability;   //计算替换后的TR，TP
									TR2+=node2[20*times+q][symbol[q]].cost;
								}
								else
								{
									TP2*=node2[20*times+q][q].ability;
									TR2+=node2[20*times+q][q].cost;
								}
							}
							if(TP1<TP||TR1>TR)  //更新TR，TP
							{
								if(TP2>TP1)
								{
									TP1=TP2;
									TR1=TR2;
									symbol[i]=ii;
									find=1;
								}
							}
							else
							{
								if(TR2<TR&&TP2>TP&&TP2-TR2/100>TP1-TR1/100)
								{
									TR1=TR2;
									TP1=TP2;
									symbol[i]=ii;
									find=1;
								}
							}
							ii++;
						}
					}
					if(find==0)
					{
						if(TR1<TR&&TP1>TP)  
						{
							OK=1;
							System.out.println(TP1-TR1/100);   //输出最终的结果
						}	
						else 
						{
							++priority;
						}
					}
					find=0;
				}
				TP1 = DecimalFormat(TP1,3);
				for(i=0;i<lengthpr-1;i++)
				{
					if(prarray[i]>64&&prarray[i]<91)
						for(ii=0;ii<nodenumber;ii++)
						{
							if(node2[20*times+ii][symbol[ii]].sym==prarray[i])   //输出到RESULI.txt中
								result.write(node2[20*times+ii][symbol[ii]].sym+"-"+node2[20*times+ii][symbol[ii]].num);
						}
					else
						result.write(prarray[i]);
						
				}
				result.write("\r\n");
				result.write("Reliability="+DecimalFormat(TP1,3)+",Cost="+DecimalFormat(TR1,3)+",Q="+DecimalFormat((TP1-TR1/100),3));
				result.write("\r\n");
				++times;
				OK=0;
				
			}
			System.out.println("end");
			long post=System.currentTimeMillis();
			result.write("starttime："+pre+"\r\n");
			result.write("endtime："+post+"\r\n");
			post=post-pre;
			System.out.println(post);
			result.write("runtime："+post);
			result.close();
		}catch (FileNotFoundException e) {   
            e.printStackTrace();   
        } catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static double pow(double d, int i) {   //自己定义的指数函数
		int iiii=0;
		for(iiii=0;iiii<1;iiii++)
			d*=d;
		return d;
	}
	
	private static double DecimalFormat(double m, int i){  //自己定义的保留小数位数操作
		double mm;
		int ii,n=1;
		for(ii=0;ii<i;ii++) n*=10;
		ii=(int)(m*n);
		return (double)ii/n;
	}

}
