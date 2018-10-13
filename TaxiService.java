import java.util.*;
import java.io.*;
class Myset<T>
{
	MyLinkedList<T> l1;
	void addElement(T element)
	{
		MyLinkedList<T> ptr;
		for(ptr=l1;ptr!=null && !ptr.data.equals(element) ;ptr=ptr.next){}
		if(ptr==null)
		{ 
			l1=new MyLinkedList(element,l1);
		}
	}
	public Boolean mem(T o)
	{
		MyLinkedList<T> ptr;
		for(ptr=l1;ptr!=null && !ptr.data.equals(o);ptr=ptr.next){}
		if (ptr!=null) 
		{
			return true;	
		}
		else
			return false;
	}
}
class graph
{
	HashMap<String,Integer> map=new HashMap<String,Integer>();
	HashMap<Integer,String> net=new HashMap<Integer,String>();
	int[][] arr=new int[100][100];
	int[][] path=new int[100][100];
	int [][] dist=new int[100][100];
	Myset<taxi> tax=new Myset<taxi>();
	int i=1;
	String[] str=new String[100];
	graph()
	{
		for(int x=0;x<100;x++)
		{
			for(int y=0;y<100;y++)
			{
				if(x==y)
					arr[x][y]=0;
				else
					arr[x][y]=100000;
			}
		}
	}
	void addmatrix(String a,String b,int t)
	{
		Iterator < String > it= map. keySet (). iterator ();
		int flags=0,flagd=0;
		while (it. hasNext ())
		{
			String abc=it.next();
			if(abc.equals(a))
			{
				flags=1;
			}
			if(abc.equals(b))
			{
				flagd=1;
			}
		}
		if(flags==0)
		{
			map.put(a,i);
			net.put(i-1,a);
			str[i-1]=net.get(i-1);
			i++;
		}
		if(flagd==0)
		{
			map.put(b,i);
			net.put(i-1,b);
			str[i-1]=net.get(i-1);
			i++;
		}
		//System.out.println(str[i-2]);
		String[] alt=new String[i-1];
		for(int j=0;j<i-1;j++)
		{
			//System.out.println(str[j]);
			alt[j]=str[j];
		}
		arr[map.get(a)-1][map.get(b)-1]=t;
		arr[map.get(b)-1][map.get(a)-1]=t;
		Arrays.sort(alt);
		for(int j=0;j<i-1;j++)
		{
			str[j]=alt[j];
		}
	}
	void shortest()
	{
		
		for(int x=0;x<i-1;x++)
		{
			for(int j=0;j<i-1;j++)
			{
				dist[x][j]=arr[x][j];
			}
		}
		for(int x=0;x<i-1;x++)
		{
			for(int j=0;j<i-1;j++)
			{
				if (x==j || dist[x][j]==100000) 
					path[x][j]=-1;
				else
					path[x][j]=x;
			}
		}
		for(int k=0;k<i-1;k++)
		{
			for(int x=0;x<i-1;x++)
			{
				for(int j=0;j<i-1;j++) 
				{
					int alt=dist[x][k]+dist[k][j];
					if(alt<dist[x][j]) 
					{
						dist[x][j]=alt;
						path[x][j]=path[k][j];
					}
				}
			}
		}
	}
	void prin()
	{
		for(int x=0;x<i-1;x++)
		{
			/*for(int y=0;y<i-1;y++)
			{
				System.out.print(dist[x][y]+" ");
			}
			System.out.println();*/
			System.out.println(str[x]);
		}
	}
	ArrayList<String> findpath(int a,int b)
	{
		ArrayList<String> answer=new ArrayList<String>();
		answer.add(net.get(b));
		int x=a,y=b;
		if(path[x][y]!=-1)
		{
			while(path[x][y]!=a)
			{
				answer.add(net.get(path[x][y]));
				y=path[x][y];
			}
			answer.add(net.get(a));
		}
		return answer;

	}
	void addtaxi(taxi a)
	{
		tax.addElement(a);
	}
	void update(MyLinkedList<taxi> list, int ti)
	{
		while(ti>=0)
		{	//System.out.println(ti);
			if(list.data.q.peek()==null)
			{//path of empty taxi
				int j;
				for(j=0;j<i-1;j++)
				{
					if(str[j].equals(list.data.currentlocation))
						break;
				}
				int newid=(j+1)%(i-1);
				System.out.println("At time "+list.data.taxifree+", "+list.data.name+" chose a new destination vertex "+ str[newid]); 
				list.data.position=map.get(list.data.currentlocation)-1;
				list.data.taxifree+=dist[list.data.position][map.get(str[newid])-1];
				//System.out.println(list.data.taxifree);
				int s=ti-dist[list.data.position][map.get(str[newid])-1];
				if(s>=0)
				{
					ti=s;
					list.data.currentlocation=str[newid];
					list.data.finallocation=str[newid];
					list.data.position=map.get(str[newid])-1;
					list.data.inter=0;
					//list.data.avail+=dist[list.data.position][map.get(str[newid])];
				}
				else
				{
					ArrayList<String> array=findpath(list.data.position,map.get(str[newid])-1);
					int l;
					l=array.size()-2;
					while(l>=0)
					{
						node n=new node();
						n.src=array.get(l+1);
						n.dest=array.get(l);
						n.interm=0;
						list.data.q.offer(n);
						l--;
					}
					while(ti>=0)
					{
						node x=new node();
						x=list.data.q.peek();
						if(dist[map.get(x.src)-1][map.get(x.dest)-1]-x.interm>ti)
						{
							x.interm+=ti;
							ti=-1;
							list.data.currentlocation=x.src;
							list.data.finallocation=x.dest;
							list.data.inter=x.interm;
						}
						else
						{
							//System.out.println(x.src);
							list.data.position=map.get(x.src)-1;
							ti=ti-(dist[map.get(x.src)-1][map.get(x.dest)-1]-x.interm);
							list.data.q.poll();
						}
					}
					ti=s;
				}
			}
			else
			{
				node x=new node();
				x=list.data.q.peek();
				if(dist[map.get(x.src)-1][map.get(x.dest)-1]-x.interm>ti)
				{
					x.interm+=ti;
					//System.out.println(ti);
					ti=-1;
					list.data.currentlocation=x.src;
					//list.data.position=map.get(list.data.currentlocation);
					list.data.finallocation=x.dest;
					list.data.inter=x.interm;
				}
				else
				{
					ti=ti-(dist[map.get(x.src)-1][map.get(x.dest)-1]-x.interm);
					list.data.q.poll();
					if(list.data.q.peek()==null)
					{
						list.data.inter=0;
						list.data.currentlocation=x.dest;
						list.data.position=map.get(x.dest)-1;
						list.data.finallocation=x.dest;
					}
				}
			}
		}
	}
}

class MyLinkedList<T>
{
	T data;
	MyLinkedList<T> next;
	MyLinkedList ()
	{
		next=null;
	} 
	MyLinkedList(T element,MyLinkedList<T> n)
	{
		data=element;
		next=n;
	}
}
class taxi
{
	String name;
	String currentlocation;
	String finallocation;
	int position;
	int avail;//time of availability
	int inter;//in between two vertices distance
	int taxifree;
	Queue<node> qu=new LinkedList<node>();//stores the path of taxi
	Queue<node> q=new LinkedList<node>();
	int timejourney;
	Boolean kaam;
	taxi(String str,String str2,int pos)
	{
		name=str2;
		currentlocation=str;
		finallocation=str;
		position=pos;
		avail = 0;
		inter=0;
		timejourney=0;
		kaam=false;
		taxifree=0;
	}
}
class node
{
	String src;
	String dest;
	int interm;
}
public class TaxiService
{
	graph grap=new graph();
	public TaxiService() 
	{
		grap=new graph();
	}
	public void performAction(String actionMessage) 
	{
		System.out.println("action to be performed: " + actionMessage);
		System.out.println();
		String[] str = actionMessage.split("\\s+"); 
		if(str[0].equals("edge"))
		{
			String src=str[1];
			String dst=str[2];
			int t=Integer.parseInt(str[3]);
			grap.addmatrix(str[1],str[2],t);
		}
		else if(str[0].equals("taxi"))
		{
			String taxiName=str[1];
			String taxiPosition=str[2];
			int position=grap.map.get(str[2])-1;
			taxi cab=new taxi(taxiPosition,taxiName,position);
			grap.addtaxi(cab);
		}
		else if(str[0].equals("customer"))
		{
			String src1=str[1];
			String dst1=str[2];
			int t1=Integer.parseInt(str[3]);
			String src2=str[4];
			String dst2=str[5];
			int t2=Integer.parseInt(str[6]);
			int t=Integer.parseInt(str[7]);
			Iterator < Integer > it= grap.net. keySet (). iterator ();
			int flags=0,flagd=0,flaga=0,flagb=0;
			while (it. hasNext ())
			{
				Integer abc=it.next();
				if(grap.net.get(abc).equals(src1) )
				{
					flags=1;
				}
				if(grap.net.get(abc).equals(src2) )
					flaga=1;
				if(grap.net.get(abc).equals(dst1))
				{
					flagd=1;
				}
				if(grap.net.get(abc).equals(dst2))
					flagb=1;
			}
			if(flags==1 && flagd==1 && flaga==1 && flagb==1)
			{
				int poss1=grap.map.get(str[1])-1;
				int posd1=grap.map.get(str[2])-1;
				int poss2=grap.map.get(str[4])-1;
				int posd2=grap.map.get(str[5])-1;
				MyLinkedList<taxi> list=grap.tax.l1;
				grap.shortest();
				//ti=available for movement between queries
				for(list=grap.tax.l1;list!=null;list=list.next)
				{int ti=t-list.data.avail;
					if(list.data.kaam==false)
					{
						//System.out.println(ti);
						//System.out.println(list.data.avail+"fhsdfs");
						if(list.data.inter==0 || list.data.q.peek()!=null)
						{
							grap.update(list,ti);
						}
						else
						{
							int op1,op2;
							op1=list.data.inter;
							op2=grap.dist[list.data.position][grap.map.get(list.data.finallocation)]-list.data.inter;
							//System.out.println("DFfs"+op1+"  dad"+op2);
							if(op1>op2)
							{
								if(ti<op2)
								{
									list.data.inter=(list.data.inter+ti);
								}
								else
								{
									ti=ti-op2;
									list.data.currentlocation=list.data.finallocation;
									list.data.taxifree=t-ti;
									list.data.position=grap.map.get(list.data.currentlocation)-1;
									grap.update(list,ti);
								}
							}
							else if(op1<op2)
							{
								if(ti<op1)
								{
									list.data.inter=(list.data.inter-ti);
								}
								else
								{
									ti=ti-op1;
									list.data.finallocation=list.data.currentlocation;
									list.data.taxifree=t-ti;
									grap.update(list,ti);
								}
							}
							else
							{
								int temp1=0,temp2=0;
								for(int j=0;j<grap.i-1;j++)
								{
									if(grap.str[j].equals(list.data.currentlocation))
										temp1=j;
									if(grap.str[j].equals(list.data.finallocation))
										temp2=j;
								}
								if(temp1<temp2)
								{
									if(ti<op1)
									{
										list.data.finallocation=list.data.currentlocation;
										list.data.inter=(list.data.inter-ti);
									}
									else
									{
										ti=ti-op1;
										list.data.finallocation=list.data.currentlocation;
										list.data.taxifree=t-ti;
										list.data.inter=0;
										grap.update(list,ti);
									}
								}
								else
								{
									if(ti<op1)
									{
										list.data.inter+=ti;
									}
									else
									{
										ti=ti-op2;
										list.data.currentlocation=list.data.finallocation;
										list.data.inter=0;
										list.data.position=grap.map.get(list.data.currentlocation)-1;
										grap.update(list,ti);
									}
								}
							}
						}	
						list.data.avail=t;
					}
					else // when taxi has a customer
					{
						//System.out.println(list.data.avail+"-----------------------------");
						//System.out.println(list.data.timejourney);
						//System.out.println(list.data.avail+list.data.timejourney+"Fg");
						if(list.data.avail+list.data.timejourney>t)
						{
							//decrement 
							//if(list.data.timejourney+list.data.avail==t)
							//	list.data.timejourney=0;
							//else
							list.data.timejourney=(list.data.timejourney+list.data.avail-t);
							//System.out.println(list.data.timejourney+"[[[[[[[[[[[[[[[[[[[[[[[");
							while(ti>0)
							{
								node n=new node();
								n=list.data.qu.peek();
								int sd=0;
								sd=ti-(grap.dist[grap.map.get(n.src)-1][grap.map.get(n.dest)-1]-n.interm);
								if(sd<0)
								{
									n.interm=n.interm+ti;
									ti=sd;
									
								}
								else
								{
									ti=sd;
									list.data.qu.poll();
								}
							}
						}
						else
						{
							list.data.kaam=false;
							//System.out.println(ti+"qqqqqqqqqqqqqqqqqqqqq");
							//System.out.println(list.data.timejourney+"fffffffffffffffff");
							ti=ti-list.data.timejourney;
							list.data.taxifree=list.data.avail+list.data.timejourney;
							//System.out.println(ti);
							list.data.timejourney=0;
							while(list.data.qu.peek()!=null)
							{
								node n=list.data.qu.peek();
								list.data.currentlocation=n.src;
								list.data.finallocation=n.dest;
								list.data.position=grap.map.get(n.src)-1;
								list.data.inter=n.interm;
								//System.out.println(n.interm);
								list.data.qu.poll();
							}
							//int ti=t-list.data.avail;
							//assuming customer dropped
							//assuming all taxi details updated .i.e. currentlocation, position, final position
							if(list.data.inter==0)
							{
								grap.update(list,ti);
							}
							else
							{
								int op1,op2;
								op1=list.data.inter;
								op2=grap.dist[list.data.position][grap.map.get(list.data.finallocation)]-list.data.inter;
								if(op1>op2)
								{
									if(ti<op2)
									{
										list.data.inter=(list.data.inter+ti);
									}
									else
									{
										ti=ti-op2;
										list.data.currentlocation=list.data.finallocation;
										list.data.position=grap.map.get(list.data.currentlocation)-1;
										list.data.taxifree=t-ti;
										grap.update(list,ti);
									}
								}
								else if(op1<op2)
								{
									if(ti<op1)
									{
										list.data.inter=(list.data.inter-ti);
									}
									else
									{
										ti=ti-op1;
										list.data.finallocation=list.data.currentlocation;
										list.data.taxifree=t-ti;
										grap.update(list,ti);
									}
								}
								else
								{
									int temp1=0,temp2=0;
									for(int j=0;j<grap.i-1;j++)
									{
										if(grap.str[j].equals(list.data.currentlocation))
											temp1=j;
										if(grap.str[j].equals(list.data.finallocation))
											temp2=j;
									}
									if(temp1<temp2)
									{
										if(ti<op1)
										{
											list.data.finallocation=list.data.currentlocation;
										}
										else
										{
											ti=ti-op1;
											list.data.finallocation=list.data.currentlocation;
											list.data.taxifree=t-ti;
											grap.update(list,ti);
										}
									}
									else
									{
										if(ti<op1)
										{
											list.data.inter+=ti;
										}
										else
										{
											ti=ti-op2;
											list.data.currentlocation=list.data.finallocation;
											list.data.position=grap.map.get(list.data.currentlocation)-1;
											list.data.taxifree=t-ti;
											grap.update(list,ti);
										}
									}
								}
							}
						}
						list.data.avail=t;
					}
				}//////////-----------------check list.data.avail-----------------
				int min=100000;
				System.out.println("Available taxis:");
				String answ=new String();
				MyLinkedList<taxi> ans=grap.tax.l1;
				int k=0;
				int[] distance=new int[4];
				for(list=grap.tax.l1;list!=null;list=list.next)
				{
					System.out.print("Path of "+ list.data.name+": ");
					distance[0]=list.data.timejourney+list.data.inter+grap.dist[grap.map.get(list.data.currentlocation)-1][poss1]+t1;
					distance[1]=list.data.timejourney+list.data.inter+grap.dist[grap.map.get(list.data.currentlocation)-1][posd1]+(grap.arr[poss1][posd1]-t1);
					distance[2]=list.data.timejourney+grap.arr[grap.map.get(list.data.currentlocation)-1][grap.map.get(list.data.finallocation)-1]-list.data.inter+grap.dist[grap.map.get(list.data.finallocation)-1][posd1]+grap.arr[poss1][posd1]-t1;
					distance[3]=list.data.timejourney+grap.arr[grap.map.get(list.data.currentlocation)-1][grap.map.get(list.data.finallocation)-1]-list.data.inter+grap.dist[grap.map.get(list.data.finallocation)-1][poss1]+t1;
					/*if(list.data.currentlocation.equals(str[1]))
					{
						distance[0]=list.data.inter+t1;
						distance[1]=distance[0];
					}
					if(list.data.finallocation.equals(str[2]))
					{
						System.out.println(list.data.inter);
						distance[2]=grap.dist[grap.map.get(list.data.currentlocation)-1][grap.map.get(list.data.finallocation)-1]-list.data.inter;
						distance[3]=distance[2];
					}*/
					//System.out.println(distance[0]+" "+ distance[1]+" "+distance[2]+" "+distance[3]);
					int sid=0;
					if(distance[0]<=distance[1] && distance[0]<=distance[2] && distance[0]<=distance[3])
					{
						ArrayList<String> array = grap.findpath(list.data.position,poss1);
						int l=array.size()-1;
						while(l>=0)
						{
							System.out.print(array.get(l)+", ");
							l--;
						}
						if(min>=distance[0])
						{
							min=distance[0];
							answ=list.data.name;
							k=0;
						}	sid=distance[0];

					}
					else if(distance[1]<=distance[0] && distance[1]<=distance[2] && distance[1]<=distance[3])
					{
						ArrayList<String> array = grap.findpath(list.data.position,posd1);
						int l=array.size()-1;
						while(l>=0)
						{
							System.out.print(array.get(l)+", ");
							l--;
						}
						if(min>=distance[1])
						{
							min=distance[1];
							answ=list.data.name;
							k=1;
						}	sid=distance[1];
					}
					else if(distance[2]<=distance[0] && distance[2]<=distance[1] && distance[2]<=distance[3])
					{
						ArrayList<String> array = grap.findpath(grap.map.get(list.data.finallocation)-1,posd1);
						int l=array.size()-1;
						while(l>=0)
						{
							System.out.print(array.get(l)+", ");
							l--;
						}
						if(min>=distance[2])
						{
							min=distance[2];
							answ=list.data.name;
							k=2;
						}	sid=distance[2];
					}
					else if(distance[3]<=distance[0] && distance[3]<=distance[1] && distance[3]<=distance[2])
					{
						//System.out.println("dsdgs");
						ArrayList<String> array = grap.findpath(grap.map.get(list.data.finallocation)-1,poss1);
						int l=array.size()-1;
						while(l>=0)
						{
							System.out.print(array.get(l)+", ");
							l--;
						}
						if(min>=distance[3])
						{
							min=distance[3];
							answ=list.data.name;
							k=3;
						}	sid=distance[3];
					}
					System.out.println(" time taken is "+sid+" units");
				}
				if(k==0)
				{
					node n=new node();
					for(;ans!=null;ans=ans.next)
					{
						if(ans.data.name.equals(answ))
						{
							n.src=ans.data.finallocation;
							n.dest=ans.data.currentlocation;
							n.interm=grap.dist[ans.data.position][grap.map.get(ans.data.finallocation)-1]-ans.data.inter;
							ans.data.qu.offer(n);
							ans.data.kaam=true;
							break;
						}
					}
					ArrayList<String> array = grap.findpath(poss1,ans.data.position);
					//ans.data.finallocation=str[3];
					int l=0;
					if(array.size()>1)
					{
						while(l<array.size()-1)
						{ 
							node x=new node();
							x.src=array.get(l);
							x.dest=array.get(l+1);
							x.interm=0;
							ans.data.qu.offer(x);
							l++;
						}
					}
					node x=new node();
					x.src=str[1];
					x.dest=str[2];
					x.interm=t1;
					ans.data.qu.offer(x);
				}
				else if(k==1)
				{
					node n=new node();
					for(;ans!=null;ans=ans.next)
					{
						if(ans.data.name.equals(answ))
						{
							n.src=ans.data.finallocation;
							n.dest=ans.data.currentlocation;
							n.interm=grap.dist[ans.data.position][grap.map.get(ans.data.finallocation)-1]-ans.data.inter;
							ans.data.qu.offer(n);
							ans.data.kaam=true;
							break;
						}
					}
					ArrayList<String> array = grap.findpath(posd1,ans.data.position);
					int l=0;
					if(array.size()>1)
					{
						while(l<array.size()-1)
						{ 
							node x=new node();
							x.src=array.get(l);
							x.dest=array.get(l+1);
							x.interm=0;
							ans.data.qu.offer(x);
							l++;
						}
					}
					node x=new node();
					x.src=str[2];
					x.dest=str[1];
					x.interm=grap.dist[poss1][posd1]-t1;
					ans.data.qu.offer(x);
				}
				else if(k==2)
				{
					node n=new node();
					for(;ans!=null;ans=ans.next)
					{
						if(ans.data.name.equals(answ))
						{
							n.src=ans.data.currentlocation;
							n.dest=ans.data.finallocation;
							n.interm=ans.data.inter;
							ans.data.qu.offer(n);
							ans.data.kaam=true;
							break;
						}
					}
					ArrayList<String> array = grap.findpath(poss1,grap.map.get(ans.data.finallocation)-1);
					int l=0;
					if(array.size()>1)
					{
						while(l<array.size()-1)
						{ 
							node x=new node();
							x.src=array.get(l);
							x.dest=array.get(l+1);
							x.interm=0;
							ans.data.qu.offer(x);
							l++;
						}
					}
					node x=new node();
					x.src=str[2];
					x.dest=str[1];
					x.interm=grap.dist[poss1][posd1]-t1;
					ans.data.qu.offer(x);
						//ans.data.avail+=grap.dist[poss1][posd1]-t1;
						//ans.data.finallocation=str[2];
				}
				else if(k==3)
				{
					node n=new node();
					for(;ans!=null;ans=ans.next)
					{
						if(ans.data.name.equals(answ))
						{
							n.src=ans.data.currentlocation;
							n.dest=ans.data.finallocation;
							n.interm=ans.data.inter;
							ans.data.qu.offer(n);
							ans.data.kaam=true;
							break;
						}
					}
					ArrayList<String> array = grap.findpath(posd1,grap.map.get(ans.data.finallocation)-1);
					int l=0;
					if(array.size()>1)
					{
						while(l<array.size()-1)
						{ 
							node x=new node();
							x.src=array.get(l);
							x.dest=array.get(l+1);
							x.interm=0;
							ans.data.qu.offer(x);
							l++;
						}
					}
					node x=new node();
					x.src=str[1];
					x.dest=str[2];
					x.interm=t1;
					ans.data.qu.offer(x);			
				}
				ans.data.timejourney=min;
				while(ans.data.q.peek()!=null)
				{
					ans.data.q.poll();
				}
				ans.data.kaam=true;
				int[] dista=new int[4];
				dista[0]=t1+grap.dist[poss1][poss2]+t2;
				dista[1]=t1+grap.dist[poss1][posd2]+(grap.arr[poss2][posd2]-t2);
				dista[2]=grap.arr[poss1][posd1]-t1+grap.dist[posd1][posd2]+(grap.arr[poss2][posd2]-t2);
				dista[3]=grap.arr[poss1][posd1]-t1+grap.dist[posd1][poss2]+t2;
				//System.out.println(dista[0]+" "+ dista[1]+" "+dista[2]+" "+dista[3]);
				if(dista[0]<=dista[1] && dista[0]<=dista[2] && dista[0]<=dista[3])
				{
					node n=new node();
					n.src=str[2];
					n.dest=str[1];
					n.interm=grap.dist[posd1][poss1]-t1;
					ans.data.qu.offer(n);
					System.out.println("** Choose "+ answ+" to service the customer request ***");
					System.out.print("Path of customer: ");
					ArrayList<String> array = grap.findpath(poss1,poss2);
					int l=array.size()-1;
					while(l>=0)
					{
						System.out.print(array.get(l)+", ");
						l--;
					}
					System.out.println("time taken is "+ dista[0]+ " units");
					System.out.println();
					array = grap.findpath(poss2,poss1);
					l=0;
					if(array.size()>1)
					{
						while(l<array.size()-1)
						{ 
							node x=new node();
							x.src=array.get(l);
							x.dest=array.get(l+1);
							x.interm=0;
							ans.data.qu.offer(x);
							l++;
						}
					}
					node x=new node();
					x.src=str[4];
					x.dest=str[5];
					x.interm=t2;
					ans.data.qu.offer(x);
					ans.data.currentlocation=str[4];
					ans.data.finallocation=str[5];
					ans.data.inter=t2;
					ans.data.timejourney+=dista[0];
				}
				else if(dista[1]<=dista[0] && dista[1]<=dista[2] && dista[1]<=dista[3])
				{
					node n=new node();
					n.src=str[2];
					n.dest=str[1];
					n.interm=t1;
					ans.data.qu.offer(n);
					ArrayList<String> array = grap.findpath(poss1,posd2);
					System.out.println("** Choose "+ answ+" to service the customer request ***");
					System.out.print("Path of customer: ");
					int l=array.size()-1;
					while(l>=0)
					{
						System.out.print(array.get(l)+", ");
						l--;
					}
					System.out.println("time taken is "+ dista[1]+ " units");
					System.out.println();	
					array = grap.findpath(posd2,poss1);
					l=0;
					if(array.size()>1)
					{
						while(l<array.size()-1)
						{ 
							node x=new node();
							x.src=array.get(l);
							x.dest=array.get(l+1);
							x.interm=0;
							ans.data.qu.offer(x);
							l++;
						}
					}
					node x=new node();
					x.src=str[5];
					x.dest=str[4];
					x.interm=grap.dist[poss2][posd2]-t2;
					ans.data.qu.offer(x);
					ans.data.finallocation=str[4];
					ans.data.currentlocation=str[5];
					ans.data.inter=grap.dist[poss2][posd2]-t2;
					ans.data.timejourney+=dista[1];
				}
				else if(dista[2]<=dista[0] && dista[2]<=dista[1] && dista[2]<=dista[3])
				{
					node n=new node();
					n.src=str[1];
					n.dest=str[2];
					n.interm=grap.dist[poss1][posd1]-t1;
					ans.data.qu.offer(n);
					ArrayList<String> array = grap.findpath(posd1,posd2);
					System.out.println("** Choose "+ answ+" to service the customer request ***");
					System.out.print("Path of customer: ");
					int l=array.size()-1;
					while(l>=0)
					{
						System.out.print(array.get(l)+", ");
						l--;
					}
					System.out.println("time taken is "+ dista[2]+ " units");
					System.out.println();
					array = grap.findpath(posd2,posd1);
					l=0;
					if(array.size()>1)
					{
						while(l<array.size()-1)
						{ 
							node x=new node();
							x.src=array.get(l);
							x.dest=array.get(l+1);
							x.interm=0;
							ans.data.qu.offer(x);
							l++;
						}
					}
					node x=new node();
					x.src=str[5];
					x.dest=str[4];
					x.interm=grap.dist[poss2][posd2]-t2;
					ans.data.qu.offer(x);
					ans.data.finallocation=str[4];
					ans.data.currentlocation=str[5];
					ans.data.inter=grap.dist[poss2][posd2]-t2;
					ans.data.timejourney+=dista[2];
				}
				else if(dista[3]<=dista[0] && dista[3]<=dista[1] && dista[3]<=dista[2])
				{
					node n=new node();
					n.src=str[1];
					n.dest=str[2];
					n.interm=grap.dist[poss1][posd1]-t1;
					ans.data.qu.offer(n);
					ArrayList<String> array = grap.findpath(posd1,poss1);
					System.out.println("** Choose "+ answ+" to service the customer request ***");
					System.out.print("Path of customer: ");
					int l=array.size()-1;
					while(l>=0)
					{
						System.out.print(array.get(l)+", ");
						l--;
					}
					System.out.println("time taken is "+ dista[3]+ " units");
					System.out.println();
					array = grap.findpath(poss1,posd1);
					l=0;
					if(array.size()>1)
					{
						while(l<array.size()-1)
						{ 
							node x=new node();
							x.src=array.get(l);
							x.dest=array.get(l+1);
							x.interm=0;
							ans.data.qu.offer(x);
							l++;
						}
					}
					node x=new node();
					x.src=str[4];
					x.dest=str[5];
					x.interm=t2;
					ans.data.qu.offer(x);
					ans.data.currentlocation=str[4];
					ans.data.finallocation=str[5];
					ans.data.inter=t2;
					ans.data.timejourney+=dista[3];
				}
			}
			else
			{
				if(flags==0 || flaga==0)
					System.out.println("source doesn't exist");
				else
					System.out.println("destination doesn't exist");
			}

		}
		else if(str[0].equals("printTaxiPosition"))
		{
			int t=Integer.parseInt(str[1]);
			MyLinkedList<taxi> list=grap.tax.l1;
			grap.shortest();
			//ti=available for movement between queries
			for(list=grap.tax.l1;list!=null;list=list.next)
			{
				int ti=t-list.data.avail;
				if(list.data.kaam==false)
				{
					//System.out.println(ti);
					//System.out.println(list.data.avail+"fhsdfs");
					if(list.data.inter==0 || list.data.q.peek()!=null)
					{
						grap.update(list,ti);
					}
					else
					{
						int op1,op2;
						op1=list.data.inter;
						op2=grap.dist[list.data.position][grap.map.get(list.data.finallocation)]-list.data.inter;
						//System.out.println("DFfs"+op1+"  dad"+op2);
						if(op1>op2)
						{
							if(ti<op2)
							{
								list.data.inter=(list.data.inter+ti);
							}
							else
							{
								ti=ti-op2;
								list.data.currentlocation=list.data.finallocation;
								list.data.taxifree=t-ti;
								list.data.position=grap.map.get(list.data.currentlocation)-1;
								grap.update(list,ti);
							}
						}
						else if(op1<op2)
						{
							if(ti<op1)
							{
								list.data.inter=(list.data.inter-ti);
							}
							else
							{
								ti=ti-op1;
								list.data.finallocation=list.data.currentlocation;
								list.data.taxifree=t-ti;
								grap.update(list,ti);
							}
						}
						else
						{
							int temp1=0,temp2=0;
							for(int j=0;j<grap.i-1;j++)
							{
								if(grap.str[j].equals(list.data.currentlocation))
									temp1=j;
								if(grap.str[j].equals(list.data.finallocation))
									temp2=j;
							}
							if(temp1<temp2)
							{
								if(ti<op1)
								{
									list.data.finallocation=list.data.currentlocation;
									list.data.inter=(list.data.inter-ti);
								}
								else
								{
									ti=ti-op1;
									list.data.finallocation=list.data.currentlocation;
									list.data.taxifree=t-ti;
									list.data.inter=0;
									grap.update(list,ti);
								}
							}
							else
							{
								if(ti<op1)
								{
									list.data.inter+=ti;
								}
								else
								{
									ti=ti-op2;
									list.data.currentlocation=list.data.finallocation;
									list.data.inter=0;
									list.data.position=grap.map.get(list.data.currentlocation)-1;
									grap.update(list,ti);
								}
							}
						}
					}	
					list.data.avail=t;
				}
				else // when taxi has a customer
				{
					//System.out.println(list.data.avail+"-----------------------------");
					//System.out.println(list.data.timejourney);
					//System.out.println(list.data.avail+list.data.timejourney+"Fg");
					if(list.data.avail+list.data.timejourney>t)
					{
						//decrement 
						//if(list.data.timejourney+list.data.avail==t)
						//	list.data.timejourney=0;
						//else
						list.data.timejourney=(list.data.timejourney+list.data.avail-t);
						//System.out.println("[[[[[[[[[[[[[[[[[[[[[[[");
						while(ti>0)
						{
							node n=new node();
							n=list.data.qu.peek();
							int sd=0;
							
							sd=ti-(grap.dist[grap.map.get(n.src)-1][grap.map.get(n.dest)-1]-n.interm);
							//System.out.println(grap.dist[grap.map.get(n.src)-1][grap.map.get(n.dest)-1]);
							if(sd<0)
							{
								n.interm=n.interm+ti;
								//System.out.println("dsdgdg");
								//list.data.inter=n.interm;
								ti=sd;
								
							}
							else
							{
								ti=sd;

								//System.out.println("FDgdfsgfs");
								//n.interm=n.interm+ti;
								//list.data.inter=n.interm;
								list.data.qu.poll();
							}
						}
					}
					else
					{
						list.data.kaam=false;

							//System.out.println(ti+"qqqqqqqqqqqqqqqqqqqqq");
							//System.out.println(list.data.timejourney+"fffffffffffffffff");
						ti=ti-list.data.timejourney;
						list.data.taxifree=list.data.avail+list.data.timejourney;
						//System.out.println(ti);
						list.data.timejourney=0;
						while(list.data.qu.peek()!=null)
						{
							node n=list.data.qu.peek();
							list.data.currentlocation=n.src;
							list.data.finallocation=n.dest;
							list.data.position=grap.map.get(n.src)-1;
							list.data.inter=n.interm;
							//System.out.println(n.interm);
							list.data.qu.poll();
						}//System.out.println(list.data.inter);
						//System.out.println(ti+"qqqqqqqqqqqqqqqqqqqqq");
						//System.out.println(list.data.currentlocation);
						//int ti=t-list.data.avail;
						//assuming customer dropped
						//assuming all taxi details updated .i.e. currentlocation, position, final position
						if(list.data.inter==0)
						{
							grap.update(list,ti);
						}
						else
						{
							int op1,op2;
							op1=list.data.inter;
							op2=grap.dist[list.data.position][grap.map.get(list.data.finallocation)]-list.data.inter;
							if(op1>op2)
							{
								if(ti<op2)
								{
									list.data.inter=(list.data.inter+ti);
								}
								else
								{
									ti=ti-op2;
									list.data.currentlocation=list.data.finallocation;
									list.data.position=grap.map.get(list.data.currentlocation)-1;
									list.data.taxifree=t-ti;
									grap.update(list,ti);
								}
							}
							else if(op1<op2)
							{
								if(ti<op1)
								{
									list.data.inter=(list.data.inter-ti);
								}
								else
								{
									ti=ti-op1;
									list.data.finallocation=list.data.currentlocation;
									list.data.taxifree=t-ti;
									grap.update(list,ti);
								}
							}
							else
							{
								int temp1=0,temp2=0;
								for(int j=0;j<grap.i-1;j++)
								{
									if(grap.str[j].equals(list.data.currentlocation))
										temp1=j;
									if(grap.str[j].equals(list.data.finallocation))
										temp2=j;
								}
								if(temp1<temp2)
								{
									if(ti<op1)
									{
										list.data.finallocation=list.data.currentlocation;
									}
									else
									{
										ti=ti-op1;
										list.data.finallocation=list.data.currentlocation;
										list.data.taxifree=t-ti;
										grap.update(list,ti);
									}
								}
								else
								{
									if(ti<op1)
									{
										list.data.inter+=ti;
									}
									else
									{
										ti=ti-op2;
										list.data.currentlocation=list.data.finallocation;
										list.data.position=grap.map.get(list.data.currentlocation)-1;
										list.data.taxifree=t-ti;
										grap.update(list,ti);
									}
								}
							}
						}
					}
					list.data.avail=t;
				}
			}	
			for(list=grap.tax.l1;list!=null;list=list.next)
			{
				if(list.data.kaam==false)
				{
					System.out.println(list.data.name+": "+ list.data.currentlocation + " "+ list.data.finallocation+ " "+ list.data.inter);
				}
				else
				{
					node n=list.data.qu.peek();
					if(n!=null)
					{System.out.println(list.data.name+": "+ n.src+" "+n.dest+" "+n.interm);}
					else
					{
						System.out.println(list.data.name+": "+ list.data.currentlocation+" "+list.data.finallocation+" "+list.data.inter);
					}

				}
			}
			System.out.println();
		}
		else if(str[0].equals("print"))
		{
			grap.prin();
		}
		else
		{
			System.out.println("dhokebaaz");
		}
	}
}
