import java.io.*;
import java.util.*;
import java.lang.Math;
class state{
	//int[][] bs1;
	char[][] bs2;
	int score;
	String pos;
	String move;
	state(){

	}

	state(char[][] players,int sc,String p,String m){
		//bs1=values;
		bs2=new char[players.length][players.length];
		copy2darray(bs2,players);
		score=sc;
		pos=p;
		move=m;
	}
	static void copy2darray(char[][] a,char[][] b){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a.length;j++){
				a[i][j]=new Character(b[i][j]);


			}
		}



	}
	state(state cpy){
		//this.bs1=cpy.bs1;
		this.bs2=new char[cpy.bs2.length][cpy.bs2.length];
		copy2darray(this.bs2,cpy.bs2);
		this.move=cpy.move;
	}
}



public class homework{
	static int[][] values;
	static int cnt=0;
	public static void main(String[] args) throws IOException{
	//System.out.println("MinMax");
	long startTime = System.currentTimeMillis();
	//for(int fi=1;fi<11;fi++){
	//File f =new File("./TestCasesHW2/Test"+Integer.toString(fi)+"/input.txt");
	File f=new File("input.txt");
	Scanner in=new Scanner(f);
	while(in.hasNextLine()){
		int n=Integer.parseInt(in.nextLine().trim());

		String algo=in.nextLine().trim();
		char player=in.nextLine().charAt(0);
		char oppo;

		if(player=='X')
			oppo='O';
		else
			oppo='X';
		int depth=Integer.parseInt(in.nextLine().trim());
		//System.out.println(n);
		int j;
		values=new int[n][n];
		char[][] players=new char[n][n];
		for(int i=0;i<n;i++){
			String[] parts=in.nextLine().split(" ");
			//System.out.println();
			for( j=0;j<n;j++){
			
			values[i][j]=Integer.parseInt(parts[j]);
			//System.out.print(values[i][j]+" ");
			
			

			}


			

			}
		for(int i=0;i<n;i++){
			String parts=in.nextLine();
			//System.out.println();
			for( j=0;j<n;j++){
			
			//System.out.print(parts);
			players[i][j]=(parts.charAt(j));
			//System.out.print(players[i][j]);
			
			



			}
			

			}

		state s=new state(players,getscore(players,player,n,oppo,depth),"","");
		//System.out.println(s.score);
		if(algo.equals("MINIMAX"))
			minmax_decision(s,player,oppo,depth);
		if(algo.equals("ALPHABETA"))
			alphabeta_search(s,player,oppo,depth);
		//long endTime   = System.currentTimeMillis();
		//long totalTime = endTime - startTime;
		//System.out.println(totalTime/1000.00);

			}
		/*File f1= new File("output.txt");
		Scanner in1=new Scanner(f1);
		
		File f2= new File("./TestCasesHW2/Test"+Integer.toString(fi)+"/output.txt");
		Scanner in2=new Scanner(f2);
		int check=0;
		while(in1.hasNextLine()&& in2.hasNextLine()){
		String s1=in1.nextLine();
		String s2= in2.nextLine();
		//System.out.println("s1="+s1+" s2="+s2);
		if(!s1.equals(s2)){
			System.out.println("Test case failed "+s1+" - "+s2+"--------------------------------"+fi);
			
			check=1;
			break;
			}
		}
	if(check==0)
	System.out.println("test case passed"+Integer.toString(fi)+"input.txt");
	if(check==1)
		break;
		}*/
	}
	

	static void minmax_decision(state s, char player,char oppo,int depth) throws IOException{
		
		
		LinkedList<state> ls=getstates(s.bs2,player,s.bs2.length,oppo,player,depth);
		ListIterator<state> li=ls.listIterator();
		int v=Integer.MIN_VALUE;
		state intial=new state();
		intial.score=v;
		state max;
		max=intial;
		state final_state=new state();
		state ret_state;
		//int count=0;
		while(li.hasNext()){
			state temp=li.next();
			//System.out.println("("+temp.pos+")"+"Score:"+temp.score);
			ret_state=Min_V(temp,depth,player,oppo);
			//System.out.println(ret_state.move);
			if(ret_state.score>v){
				v=ret_state.score;
				final_state=temp;

			}

			




			/*max=getMaxFinal(max,final_state,temp,Min_V(temp,2,player,oppo));
			System.out.println();
			System.out.println("temp----/"+temp.score);
			printstate(final_state.bs2);
			System.out.println();
			System.out.println("Max----/"+max.score);
			printstate(max.bs2);*/

			
			//count++;
			
		}
		//System.out.println("Max:"+getMax(ls).score+" Move:"+getMax(ls).move+" Pos:"+getMax(ls).pos);
		//System.out.println("Min:"+getMin(ls).score+" Move:"+getMin(ls).move+" Pos:"+getMin(ls).pos);
		//System.out.println("Result:"+"Move,Pos:"+max.move+"  "+max.pos);
		//printstate(max.bs2);
		//System.out.println(final_state.pos);
		//printstate(final_state.bs2);
		//System.out.println("count="+count);
		output_print(final_state);
		


	}
	static state Min_V(state s,int depth,char player,char oppo){
		if (depth==1)
			return s;
		int v=Integer.MAX_VALUE;
		state intial=new state();
		intial.score=v;
		state min;
		min=intial;
		LinkedList<state> ls=getstates(s.bs2,oppo,s.bs2.length,player,player,depth);
		if(ls.size()==0)
			return s;
		ListIterator<state> li=ls.listIterator();
		while(li.hasNext()){
			state temp=li.next();
			//System.out.println("MIN->Sub: ("+temp.pos+")"+"Score:"+temp.score+" Move:"+temp.move);
			min=getMin(min,Max_V(temp,depth-1,player,oppo));
			//System.out.println("State:-------------------");
			//printstate(s.bs2);
		}
		return min;

	}
	static state Max_V(state s,int depth,char player,char oppo){
		if (depth==1)
			return s;
		int v=Integer.MIN_VALUE;
		state intial=new state();
		intial.score=v;
		state max;
		max=intial;
		LinkedList<state> ls=getstates(s.bs2,player,s.bs2.length,oppo,player,depth);
		if(ls.size()==0)
			return s;
		ListIterator<state> li=ls.listIterator();
		
		while(li.hasNext()){
			state temp=li.next();
			max=getMax(max,Min_V(temp,depth-1,player,oppo));
			//System.out.println("State:-------------------");
			//printstate(s.bs2);
			//System.out.println("Max->Sub: ("+temp.pos+")"+"Score:"+temp.score+" Move:"+temp.move);
		}
		return max;

	}

	static void alphabeta_search(state s, char player,char oppo,int depth) throws IOException{
		
		state final_state=Max_AB(s,depth,player,oppo,Integer.MIN_VALUE,Integer.MAX_VALUE);
		//System.out.println(final_state.score);
		//printstate(final_state.bs2);
		/*LinkedList<state> ls=getstates(s.bs1,s.bs2,player,s.bs1.length,oppo,player);
		ListIterator<state> li=ls.listIterator();
		while(li.hasNext()){
			state temp=li.next();
			System.out.println(temp.score);
			if(temp.score==v)
				printstate(temp.bs2);

		}*/
		output_print(final_state);
		


	}


	static state Max_AB(state s,int depth,char player,char oppo,int alpha,int beta){
		if(depth==0)
			return s;
		int v=Integer.MIN_VALUE;
		int ev=Integer.MIN_VALUE;
		
		LinkedList<state> ls=getstates(s.bs2,player,s.bs2.length,oppo,player,depth);
		if(ls.size()==0)
			return s;
		ListIterator<state> li=ls.listIterator();
		state ret_state=new state();
		while(li.hasNext()){
			state temp=li.next();
			v=Math.max(v,Min_AB(temp,depth-1,player,oppo,alpha,beta).score);
			if(v!=ev){
				temp.score=v;
				ret_state=temp;
				ev=v;

			}
			
			
			if(v>beta)
				return ret_state;
			alpha=Math.max(alpha,v);

		}
		return ret_state;



	}
	static state Min_AB(state s,int depth,char player,char oppo,int alpha,int beta){
		if(depth==0)
			return s;
		int v=Integer.MAX_VALUE;
		int ev=Integer.MAX_VALUE;
		
		
		
		LinkedList<state> ls=getstates(s.bs2,oppo,s.bs2.length,player,player,depth);
		if(ls.size()==0)
			return s;
		state ret_state=new state();
		ListIterator<state> li=ls.listIterator();
		while(li.hasNext()){
			state temp=li.next();
			v=Math.min(v,Max_AB(temp,depth-1,player,oppo,alpha,beta).score);
			if(v!=ev){
				temp.score=v;
				ret_state=temp;
				ev=v;

			}
			if(v<alpha)
				return ret_state;
			beta=Math.min(beta,v);

		}
		return ret_state;



	}



	static int getscore(char[][] players,char player,int n,char oppo, int depth){
		
		
		int pscore=0;
		int oscore=0;

		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(players[i][j]==player)
					pscore=pscore+values[i][j];
				if(players[i][j]==oppo)
					oscore=oscore+values[i][j];

			}
		}
		//System.out.println("In get score"+pscore+","+oscore);
		return pscore-oscore;

		}

	static state getMax(state s1,state s2){
		if(s1.score<s2.score)
			return s2;
		else
			return s1;
		

	}
	static state getMin(state s1,state s2){
		if(s1.score>s2.score)
			return s2;
		else
			return s1;
		
		
	}
	static state getMaxFinal(state s1,state final_state,state temp,state s2){
		if(s1.score<s2.score){
			final_state=new state(temp);
			//printstate(final_state.bs2);
			return s2;
		}
		else
			return s1;



	}







	//Stake + Raid to get states
	static LinkedList<state> getstates(char[][] players,char player,int n,char oppo ,char org_player,int depth){
		//Stake Moves
		LinkedList<state> ls=new LinkedList<state>();
		state s;
		//System.out.println("player:"+player+"org_player:"+org_player);
		char[][] tempplayers=players;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(players[i][j]=='.'){
					tempplayers[i][j]=player;
					if(org_player==player)
						s=new state(tempplayers,getscore(tempplayers,player,n,oppo,depth),Integer.toString(i)+","+Integer.toString(j),"S");
					else
						s=new state(tempplayers,-1*(getscore(tempplayers,player,n,oppo,depth)),Integer.toString(i)+","+Integer.toString(j),"S");
					tempplayers[i][j]='.';

					ls.add(s);
					

				}


			}
		}
		//Raid Move
		
		/*for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				//System.out.println("Raid Move:"+tempplayers[i][j]+","+player);
				if(tempplayers[i][j]==player){

					//System.out.println("here");
					LinkedList<String> rls=getraidpositions(players,n,i,j,oppo);
					ListIterator<String> li=rls.listIterator();
					/*while(li.hasNext()){
						System.out.print(li.next()+"--");
						System.out.println();
					}

					ls=performraid(ls,rls,values,players,player,oppo,org_player);
				}
			}
		}*/
		LinkedList<String> rls=new LinkedList<String>();
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(players[i][j]=='.' &&checkvalid_raid2(players,n,i,j,player)&&checkvalid_raid2(players,n,i,j,oppo)){
				
				String str=Integer.toString(i)+","+Integer.toString(j);
				rls.add(str);
				}




			}
		}
		/*ListIterator<String> li=rls.listIterator();
					while(li.hasNext()){
						System.out.print(li.next()+"--");
						System.out.println();
					}*/
		
		ls=performraid(ls,rls,players,player,oppo,org_player,depth);
		return ls;
	} 
	//RAID Functions
	static LinkedList<String> getraidpositions(char[][] players,int n,int i,int j,char oppo){
		LinkedList<String> ls= new LinkedList<String>();
		int a,b;
		a=i-1;
		b=j;
		String str=Integer.toString(a)+","+Integer.toString(b);
		//System.out.println(str);
		if(a>-1){
			if(players[a][b]=='.' && checkvalid_raid(players,n,a,b,oppo))
			ls.add(str);
		}
		a=i;
		b=j-1;
		str=Integer.toString(a)+","+Integer.toString(b);
		//System.out.println(str);
		if(b>-1){
			if(players[a][b]=='.'  && checkvalid_raid(players,n,a,b,oppo))
			ls.add(str);
		}
		a=i+1;
		b=j;
		str=Integer.toString(a)+","+Integer.toString(b);
		//System.out.println(str);
		if(a<n){
			if(players[a][b]=='.'  && checkvalid_raid(players,n,a,b,oppo))
			ls.add(str);
		}
		a=i;
		b=j+1;
		str=Integer.toString(a)+","+Integer.toString(b);
		//System.out.println(str);
		if(b<n) {
			if(players[a][b]=='.'  && checkvalid_raid(players,n,a,b,oppo))
			ls.add(str);
		}
		return ls;
		

	}
	static LinkedList<state> performraid(LinkedList<state> ls,LinkedList<String> rls,char[][] players,char player,char oppo,char org_player,int depth){
		
		ListIterator<String> li=rls.listIterator();
		int r1,r2;
		String[] parts;
		char[][] temp=players;
		state s,s_updated;
					while(li.hasNext()){

						parts=li.next().split(",");
						r1=Integer.parseInt(parts[0]);
						r2=Integer.parseInt(parts[1]);
						//System.out.println("before:");
						//printstate(players);
						temp[r1][r2]=player;
						s=new state(temp,getscore(temp,player,values.length,oppo,depth),Integer.toString(r1)+","+Integer.toString(r2),"R");
 						//System.out.println("pos"+s.pos+" score"+s.score);
					 	s_updated=occupy_nearby(s,player,oppo,depth);
					 	if(org_player!=player)
					 		s_updated.score=-1*(s_updated.score);
					 	ls.add(s_updated);
					 	//System.out.println("after:");
					 	//printstate(players);
					 	

					 	temp[r1][r2]='.';
					 	//System.out.println("after change:");

					 	//printstate(players);
					 	//System.out.println("state players");
					 	//printstate(s_updated.bs2);

					}
		return ls;

	}
	static state occupy_nearby(state s,char player,char oppo,int depth){
		String[] points=s.pos.split(",");
		int i=Integer.parseInt(points[0]);
		int j=Integer.parseInt(points[1]);
		int a,b;
		a=i-1;
		b=j;
		if(a>-1){
			if(s.bs2[a][b]==oppo){
				s.bs2[a][b]=player;
				s.score=getscore(s.bs2,player,s.bs2.length,oppo,depth);

			}
		}
		a=i;
		b=j-1;
		if(b>-1){
			if(s.bs2[a][b]==oppo){
				s.bs2[a][b]=player;
				s.score=getscore(s.bs2,player,s.bs2.length,oppo,depth);

			}
		}
		a=i+1;
		b=j;
		if(a<s.bs2.length){
			if(s.bs2[a][b]==oppo){
				s.bs2[a][b]=player;
				s.score=getscore(s.bs2,player,s.bs2.length,oppo,depth);

			}
		}
		a=i;
		b=j+1;
		if(b<s.bs2.length){
			if(s.bs2[a][b]==oppo){
				s.bs2[a][b]=player;
				s.score=getscore(s.bs2,player,s.bs2.length,oppo,depth);

			}
		}
		return s;

	}
	static boolean checkvalid_raid(char[][] players,int n,int i,int j,char oppo){
		int a,b;
		a=i-1;
		b=j;
		
		if(a>-1){
			if(players[a][b]==oppo)
				return true;
			
		}
		a=i;
		b=j-1;
		
		//System.out.println(str);
		if(b>-1){
			if(players[a][b]==oppo)
				return true;
		}
		a=i+1;
		b=j;
		//System.out.println(str);
		if(a<n){
			if(players[a][b]==oppo)
				return true;
		}
		a=i;
		b=j+1;
		//System.out.println(str);
		if(b<n) {
			if(players[a][b]==oppo)
				return true;
		}
		return false;
	}
	static boolean checkvalid_raid2(char[][] players,int n,int i,int j,char player){
		int a,b;
		a=i;
		b=j-1;
		
		//System.out.println(str);
		if(b>-1){
			if(players[a][b]==player)
				return true;
		}
		
		a=i;
		b=j+1;
		//System.out.println(str);
		if(b<n) {
			if(players[a][b]==player)
				return true;
		}

		a=i-1;
		b=j;

		if(a>-1){
			if(players[a][b]==player)
				return true;
			
		}
		a=i+1;
		b=j;
		//System.out.println(str);
		if(a<n){
			if(players[a][b]==player)
				return true;
		}
		
		return false;
	}



	static void printstate(char[][] players){
		for(int i=0;i<players.length;i++){
			System.out.println();
			for(int j=0;j<players.length;j++){
				System.out.print(players[i][j]);


			}
		}

	}
	static void output_print(state final_state) throws IOException{
		StringBuilder output=new StringBuilder();
		String[] parts=final_state.pos.split(",");
		int x=Integer.parseInt(parts[0]); 
		int y=Integer.parseInt(parts[1]);
		y=y+65;
		x=x+1;
		//System.out.print((char)y+""+x+" ");
		output.append((char)y+""+x+" "); 
		if(final_state.move=="S"){
			//System.out.print("Stake");
			output.append("Stake");
		}
		if(final_state.move=="R"){
			//System.out.print("Raid");
			output.append("Raid");
		}
		//printstate(final_state.bs2);
		for(int i=0;i<final_state.bs2.length;i++){
			output.append("\n");
			for(int j=0;j<final_state.bs2.length;j++){
				//System.out.print(final_state.bs2[i][j]);
				output.append(final_state.bs2[i][j]);


			}
		}
		PrintWriter out=new PrintWriter("output.txt");
		out.print(output);
		out.close();
		//System.out.println();


	}

	
}