import java.util.*;
import java.io.*;
import java.util.regex.*;

interface input{
	String traverse();
	String traverse2();
	int get_length();
	input rem_imply();
	input res_neg();
	void set_length(int c);
	input dis();
	HashMap<String,String> stan_var(HashMap<String,String> hm);
	input apply_stan(HashMap<String,String> hm);
	boolean find_match(input i2);
	input unify(input i2,HashMap<String,String> hmu);
	public String[] getvar_values();
	public void setvar_new(HashMap<String,String> hmu);
	public input inverse();
	public String get_all_pred();
	public String get_all_var_cnst();
	public input get_second_part(input t);
	public input join_parts(input p2);


}

class pred implements input{
	String id;
	String[] var;
	int loc;
	static int c=0;
	public  pred(String s,String v,int i){
		id=s;
		if(v!=null)
			var=v.split(",");
		else
			var=null;
		loc=i;

	}
	public pred(pred p){
		id=p.id;
		var=p.var;

	}
	
	public String traverse(){
		//System.out.println("Pred:"+this.id+","+this.loc);
		return this.id.trim();
	}
	public String traverse2(){
		//System.out.println("Pred:"+this.var[0]);
		String[] v=this.var;
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<v.length;i++){
			sb.append(v[i].trim()+",");

		}
		String str=sb.toString();
		str=str.substring(0,str.length()-1);
		//System.out.println("traverse2="+str);
		return this.id+"("+str+")";
	}
	public int get_length(){
		return this.loc;
	}
	public input rem_imply(){
		return this;
	}

	public input res_neg(){
		return this;
	}
	public void set_length(int c){
		this.loc=c;
	}
	public input dis(){
		return this;
	}
	public String getvar(){
		StringBuilder sb=new StringBuilder();
		for(String s: this.var){
			sb.append(s.trim()+"-");

		}
		return sb.toString().substring(0,sb.toString().length()-1);
	}
	public String[] getvar_values(){
		return this.var;
	}
	public void setvar_new(HashMap<String,String> hmu){
		String pres=this.getvar();
		String[] oldval=this.getvar_values();
		for(int i=0;i<oldval.length;i++){
			if(hmu.containsKey(oldval[i])){
				if(!pres.contains(hmu.get(oldval[i])))
				pres=pres.replace(oldval[i],hmu.get(oldval[i]));

			}
			

		}
		this.var=pres.split("-");

	}

	
	public HashMap<String,String> stan_var(HashMap<String,String> hm){
		String[] v=this.var;
		
		
		
		for(int i=0;i<v.length;i++){
			if(v[i].length()==1){
				//System.out.println(v[i]);
				
				if(!hm.containsKey(v[i])){
					//System.out.println("new key");
					c++;
					hm.put(v[i].trim(),"v"+c);
				}
			}

		}
		/*for(int i=0;i<v.length;i++){
			if(v[i].length()==1){
				v[i]=hm.get(v[i]);
			}
		}*/
		/*for(int i=0;i<v.length;i++){
			System.out.println(">>>>>.."+hm.get(v[i]));
		}*/

		
		return hm;


	}
	public input apply_stan(HashMap<String,String> hm){
		String[] v=this.var;
		String str=this.id;
		//System.out.println("prev="+str);
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<v.length;i++){
			//
				str=str.replace(v[i].trim(),"");
			if(hm.get(v[i].trim())!=null)
				v[i]=hm.get(v[i].trim());
			sb.append(v[i].trim()+",");
			
		}
		str=str.replace("(","");
		str=str.replace(")","");
		str=str.replace(",","");
		String s=sb.toString();
		//System.out.println("after"+s);
		return new pred(str,s.substring(0,s.length()-1),0);


	}
	public boolean find_match(input i2){
		//System.out.println("curr:"+this.id+"match:"+i2.traverse());
		if(i2.traverse().equals(this.id)){
					//System.out.println(">>>>>>>>>.....curr:"+this.id+"match:"+i2.traverse());


			return true;

		}
	return false;
	}

	public input unify(input i2,HashMap<String,String> hmu){
		if(i2.traverse().equals(this.id))
			return null;
		return this;
	}

	public input inverse(){
		//System.out.println("pred->neg");
		return new neg(this);
	}
	public String get_all_pred(){
		//System.out.println(this.id);
		return this.id.trim()+"-";
	}

	public String get_all_var_cnst(){
		String s="";
		for(int i=0;i<this.var.length;i++){
			s=s+this.var[i].trim()+",";

		}
		//System.out.println(s);
		return s+"-";
	}
	public input get_second_part(input t){
			if(t.traverse2().equals(this.traverse2()))
				return null;
			else
				return this;
		}
	public input join_parts(input p2){
		if(p2==null)
			return this;
		else
			return new compound(p2,'|',this,0);
	}

	

}
class neg implements input{
	     input p;
	     int loc;
	     public neg(input s){
	     	p=s;
	     }
	     public neg(neg n){
	     	p= new pred((pred) n.p);
	     }

	     public String traverse(){
			//System.out.println("Neg :"+this.p.traverse());
			String str=this.p.traverse();
			return "(~"+str+")";
			}
		public String traverse2(){
			//System.out.println("Neg :"+this.p.traverse());
			String str=this.p.traverse2();
			//System.out.println("str=="+str);
			return "(~"+str+")";
		}
		

		public int get_length(){
			return this.p.get_length();
		}
		
		public input rem_imply(){
			return this;
			
		}
		public String gettext(){
			return this.p.traverse();
		} 
		public String getstrvar(){
				pred temp=(pred) this.p;
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<temp.var.length;i++){
					sb.append(temp.var[i]+",");

				}
				String s=sb.toString();
				return s.substring(0,s.length()-1);

		}
		public input res_neg(){

			if (this.p instanceof neg){
				//System.out.println(this.p.gettext());
				String str=this.p.traverse();
				str=str.substring(2,str.length()-1);
				return new pred(str,null,0);

			}
			if(this.p instanceof compound){
				compound temp=(compound) this.p;
				input part1=temp.getpart1().res_neg();
				input part2=temp.getpart2().res_neg();
				char op=temp.getop();
				if(op=='|'){
					return new compound(new neg(part1),'&',new neg(part2),0);
				}
				if(op=='&'){
					return new compound(new neg(part1),'|',new neg(part2),0);
				}

			}

		return this;
		}
		public void set_length(int c){
			this.p.set_length(c+1);
		}
		public input dis(){
			return this;
		}

		public HashMap<String,String> stan_var(HashMap<String,String> hm){
			HashMap<String,String> hx=this.p.stan_var(hm);
			return hx;
			
		}
		public input apply_stan(HashMap<String,String> hm){
			input p1=this.p.apply_stan(hm);
			return new neg(p1);
			
		}
		public boolean find_match(input i2){
			boolean b=false;
			if(i2 instanceof neg){
				 neg temp=(neg) i2;
				 b=this.p.find_match(temp.p);
				

			}
				

			

			return b;
		
		}
		public input unify(input i2,HashMap<String,String> hmu){
			if(i2.traverse().equals(this.traverse()))
				return null;

			return this;
		}

		public String[] getvar_values(){
			return this.p.getvar_values();
		}

		public void setvar_new(HashMap<String,String> hmu){
			this.p.setvar_new(hmu);
		}
		public input inverse(){
			//System.out.println(this.traverse2());
			return new pred((pred)this.p);
		}
		public String get_all_pred(){
			neg temp=(neg) this;
		return "(~"+temp.gettext()+")"+"-";
		}

		public String get_all_var_cnst(){
			return this.p.get_all_var_cnst();
		}

		public input get_second_part(input t){
			if(t.traverse2().equals(this.traverse2()))
				return null;
			else
				return this;
		}
		public input join_parts(input p2){
		if(p2==null)
			return this;
		else
			return new compound(p2,'|',this,0);
		}

}

class compound implements input{
	input p1;
	char op;
	input p2;
	int loc;
	public compound(input s1,char c,input s2,int i){
		p1=s1;
		op=c;
		p2=s2;
		loc=i;
	}
	public compound(compound c1){
		if(c1.p1 instanceof neg)
			p1=new neg((neg)c1.p1);
		else if(c1.p1 instanceof pred)
			p1=new pred((pred)c1.p1);
		else
			p1=new compound((compound) c1.p1);
		if(c1.p2 instanceof neg)
			p2=new neg((neg)c1.p2);
		else if(c1.p2 instanceof pred)
			p2=new pred((pred)c1.p2);
		else
			p2=new compound((compound) c1.p2);
		op=c1.op;
	}
	public String traverse(){
		//System.out.print("(");
		//System.out.print(this.p1.traverse());
		//System.out.print(" "+this.op+" ");
		//System.out.print(this.p2.traverse()+")");
		//System.out.println("one"+this.p1);
		//System.out.println("two"+this.p2);
		String part1=this.p1.traverse();
		String part2=this.p2.traverse();

		return "("+part1+" "+this.op+" "+part2+")";
	}
	public String traverse2(){
		//System.out.print("(");
		//System.out.print(this.p1.traverse());
		//System.out.print(" "+this.op+" ");
		//System.out.print(this.p2.traverse()+")");
		//System.out.println("one"+this.p1);
		//System.out.println("two"+this.p2);

		String part1=this.p1.traverse2();
		String part2=this.p2.traverse2();

		return "("+part1+" "+this.op+" "+part2+")";
	}
	public int get_length(){
		return this.loc;
		}

	public input rem_imply(){
		//System.out.println("Right now in:"+this.traverse());
		input part1=this.p1.rem_imply();
		//System.out.println("Part1:"+part1.traverse());
		input part2=this.p2.rem_imply();
		//System.out.println("Part2:"+part2.traverse());

		//System.out.println("op="+this.op+"..");
		if(this.op=='>'){
			//System.out.println(part1.traverse());
			//System.out.println(part2.traverse());
			return new compound(new neg(part1),'|',part2,0);
			}
		else
			return new compound(part1,this.op,part2,0);

	}
	public input getpart1(){
		return this.p1;

		}
	public input getpart2(){
		return this.p2;
		
		}
	public char getop(){
		return this.op;
		
		}

	public input res_neg(){
		input part1=this.p1.res_neg();
		input part2=this.p2.res_neg();

		return new compound(part1,this.op,part2,0);



	}
	public void set_length(int c){
		this.loc=c;
	}

	public input dis(){
		input part1=this.p1.dis();
		input part2=this.p2.dis();

		if( ( (part1 instanceof pred )||(part1 instanceof neg) ) & ((part2 instanceof pred) || (part2 instanceof neg)) )
			return this;

		if(part1 instanceof compound){
			compound first=(compound) part1;
			if(this.op=='|' & first.getop()=='&'){

				return new compound(new compound(first.getpart1(),'|',this.getpart2(),0),'&',new compound(first.getpart2(),'|',this.getpart2(),0),0);

			}

		}
		else if(part2 instanceof compound){
			compound second=(compound) part2;
			if(this.op=='|' & second.getop()=='&'){

				return new compound(new compound(this.getpart1(),'|',second.getpart1(),0),'&',new compound(this.getpart1(),'|',second.getpart2(),0),0);

			}

		}
		return new compound(part1,this.op,part2,0);

	}

	public HashMap<String,String> stan_var(HashMap<String,String> hm){
		HashMap<String,String> h1=this.p1.stan_var(hm);
		HashMap<String,String> h2=this.p2.stan_var(h1);
		return h2;
		
	}
	public input apply_stan(HashMap<String,String> hm){
		input part1=this.p1.apply_stan(hm);
		input part2=this.p2.apply_stan(hm);

		return new compound(part1,this.op,part2,0);

		
	}
	public boolean find_match(input i2){
		if(i2 instanceof compound){
			compound temp=(compound) i2;
			boolean part1=this.p1.find_match(temp.p1);
			boolean part2=this.p2.find_match(temp.p2);
			boolean part3=this.p1.find_match(temp.p1);
			boolean part4=this.p2.find_match(temp.p2);
			if(part1 || part2 || part3 || part4 ){
				return true;
			}


		}
		else{
			boolean part1=this.p1.find_match(i2);
			boolean part2=this.p2.find_match(i2);
			if(part1 || part2){
				return true;
			}
		}


	return false;	
	}

	public input unify(input i2,HashMap<String,String> hmu){


		
		
		
			input part1=this.p1.unify(i2,hmu);
			input part2=this.p2.unify(i2,hmu);
			//System.out.println("part1"+part1);
			if(part1==null){
				//System.out.println("here");
				input old1= this.p1;
				input new1= i2;
				String[] oldval=old1.getvar_values();
				String[] newval=new1.getvar_values();
				

				if (this.p2 instanceof neg){
					neg t=(neg) this.p2;
					neg new_t=new neg(t);
					new_t.setvar_new(hmu);
					return new_t;

				}
				else if(this.p2 instanceof pred){
					pred t=(pred) this.p2;
					pred new_t=new pred(t);
					new_t.setvar_new(hmu);
					return new_t;

				}
				else if(this.p2 instanceof compound){
					compound t=(compound) this.p2;
					compound new_t=new compound(t);
					new_t.setvar_new(hmu);
					return new_t;


				}
				return part2;
				

			}
				
			if(part2==null){
				input old1=  this.p2;
				input new1= i2;
				String[] oldval=old1.getvar_values();
				String[] newval=new1.getvar_values();
				

				
				if (this.p1 instanceof neg){
					neg t=(neg) this.p1;
					neg new_t=new neg(t);
					new_t.setvar_new(hmu);
					return new_t;

				}
				else if(this.p1 instanceof pred){
					pred t=(pred) this.p1;
					pred new_t=new pred(t);
					new_t.setvar_new(hmu);
					return new_t;

				}
				else if(this.p1 instanceof compound){
					compound t=(compound) this.p1;
					compound new_t=new compound(t);
					new_t.setvar_new(hmu);
					return new_t;
				}

				return part1;
				

				
					

			}

				
			return new compound(part1,'|',part2,0);
		
		
		
				
		
	}
	public String[] getvar_values(){
		return null;

	}
	public void setvar_new(HashMap<String,String> hmu){
		this.p1.setvar_new(hmu);
		this.p2.setvar_new(hmu);

	}
	public input inverse(){
		input part1=this.p1.inverse();
		input part2=this.p2.inverse();
		return new compound(part1,'|',part2,0);
	}
	public String get_all_pred(){
		String part1=this.p1.get_all_pred();
		String part2=this.p2.get_all_pred();

		return part1+part2;
		
	}

	public String get_all_var_cnst(){
		String part1=this.p1.get_all_var_cnst();
		String part2=this.p2.get_all_var_cnst();

		return part1+part2;

	}
	public input get_second_part(input t){
		//System.out.println("serch for second part----->"+t.traverse2());
		input part1=this.p1.get_second_part(t);
		input part2=this.p2.get_second_part(t);

		if(part1==null)
			return part2;
		else if(part2== null)
			return part1;
		else
			return new compound(part1,'|',part2,0);

	}
	public input join_parts(input p2){
		if(p2==null)
			return this;
		else
			return new compound(p2,'|',this,0);
	}

}





class homework{
	static Set<Integer> coll=new HashSet<Integer>();
	static int counter=0;
	public static void main(String[] args) throws IOException{
		String[] parts=new String[1];
		//parts[0]="((~((~((~((~A(x)) & (~B(x))   )   ) & C(x))) & D(x))) & (~F(x)))";
		//parts[0]="(((Ameri(x) => (~Hjhfjh(x))) & ((~Cjhfgjw(x)) | (~Dshvdj(x,y,z,w)))) => F(x))";
		//parts[0]="(((Amar(x,y,z) | (~Bucky(x))) | Chetan(x,y,z)) => (~(Erick(x) & (~Fauna(y,he)))))";
		//parts[0]="(((~((~A(x)) & (~((~C(x)) | D(x)))))) & (~(F(x) & E(y))))";
		//parts[0]="((A(x,y) | B(x)) => (C(x) & D(x)))";

		//parts[0]="(((D(x,y) | (~F(x,y))) | C(x) ) => (~H(y)))";
		//parts[0]="((((A(x) & (~B(x))) | (~C(x))) | D(x)) & F(x))";
		//parts[0]="(A(x) | (B(x) & C(x)))";
		//for(int k=6;k<7;k++){
		StringBuilder output=new StringBuilder();
		//File f=new File("./test-cases/input"+k+".txt");
		File f=new File("input.txt");
		Scanner in=new Scanner(f);
		String[] kb=null;
		String[] queries=null;
		while(in.hasNext()){
			int n=Integer.parseInt(in.nextLine());
			queries=new String[n];
			for(int i=0;i<n;i++){
				queries[i]=in.nextLine();
				queries[i]=preprocess(queries[i]);
				//System.out.println(queries[i]);

			}
			int m=Integer.parseInt(in.nextLine());
			//System.out.println("KB=");
			kb=new String[m];
			for(int i=0;i<m;i++){
				kb[i]=in.nextLine();
				//System.out.println(kb[i]);

			}
		}
		input[] kb_cnf=new input[kb.length];
		int size=0;
		for(String s: kb){
			s=s.replace("=>",">");
			s=preprocess(s);
			//System.out.println("s="+s);
			coll=generate_map(s);
		//System.out.println(coll);
		//System.out.println(check_neg("((~            A(x))",0));
		//System.out.println(s.length());
		input i=parse(s,0);
		//System.out.println("s="+s);
		//System.out.println("a="+i.traverse());
		
		/*for(int f=0;f<s.length();f++)
			System.out.print(s.charAt(f)+","+f+" ");*/

		input i2=convert_cnf(i);

		
		kb_cnf[size]=i2;
		
		//System.out.println(kb_cnf[size].traverse());
		size++;
		}
		
		
		
		
		/*System.out.println("kb=");
		for(input t:hs2){
			System.out.println(t.traverse2());
		}
		for(String q:queries){
			input qu=parse(q,0);
			hs2.add(qu);
		}*/
		int chk=0;
		//System.out.println("qwe="+queries[0]+"-");
		
		


		for(String q:queries){
			HashSet<input> hs=new HashSet<input>();
		for(int i=0;i<kb_cnf.length;i++){
			String str=kb_cnf[i].traverse();
			//System.out.println(str);
			
			String[] kbs=str.split("&");
			for(String t:kbs){
				t=t.trim();
				
				
				//System.out.println("t="+t);
				input temp=parse("("+t,0);
				//System.out.println("after parse:"+temp.traverse());
				hs.add(temp);
			}

		}

		HashSet<input> hs2=new HashSet<input>();
		for(input temp:hs){

			//System.out.println("in set "+temp.traverse());
			HashMap<String,String> hm=new HashMap<String,String>();
			hm=temp.stan_var(hm);
			input new_temp=temp.apply_stan(hm);
			//System.out.println(""+new_temp.traverse2());
			hs2.add(new_temp);
		}
		Pattern p= Pattern.compile("~?(.*)\\((.*)\\)");
		Matcher m=p.matcher(q);
		m.find();
		//System.out.println("1:"+m.group(1).replace("(","").replace("~","")+"2:"+m.group(2).replace(")",""));
		input qu=new pred(m.group(1).replace("(","").replace("~",""),m.group(2).replace(")",""),0); 
		
		if(!q.contains("~")){
			qu=new neg(qu);
			}
		

		//System.out.println("querie:"+qu.traverse2());
		//System.out.println("add to the kb:"+q);
		//hs2.add(qu);

		
		HashSet<String> hs3=new HashSet<String>();
		for(input t:hs2){
			hs3.add(t.traverse2());
		}

		HashMap<String,List<input>> hm=new HashMap<String,List<input>>();

		for(input t:hs2){
			String all_pred=t.get_all_pred();
			String all_vcns=t.get_all_var_cnst();
			//System.out.println(all_pred);
			//System.out.println("var/cnts:"+all_vcns);
			String[] predi=all_pred.split("-");
			//System.out.println(predi.length);
			for(int i=0;i<predi.length;i++){
				String tmp=t.traverse2();
				//System.out.println("pred of i:"+predi[i]+"/"+"temp:"+tmp);
				if(tmp.contains(predi[i].replace(")","").replace("(",""))){
					if(hm.get(predi[i])==null){
						List<input> li=new ArrayList<input>();
						li.add(t);
						hm.put(predi[i].trim(),li);

					}
					else{
						List<input> li=hm.get(predi[i]);
						li.add(t);
						hm.put(predi[i].trim(),li);
					}
					
				}

			}
			


		}
		//System.out.println(hm);
		chk++;
		long start=System.currentTimeMillis();
		counter=0;
		boolean r=resolution_2(hs3,qu,hm);
		long end=System.currentTimeMillis();
		long time_f=(end-start)/1000;
		//System.out.println("Runnig Time:"+time_f);

		if(r)
			output.append("TRUE\n");
		else
			output.append("FALSE\n");
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>"+chk+"res="+r);

		}
		
		
		//PrintWriter out=new PrintWriter("./all_out/output_"+k+".txt");
		PrintWriter out =new PrintWriter("output.txt");
		out.print(output);
		out.close();	
		

		//}


			



		


	}
	
	public static input parse(String s,int i){
		
		
		input val=null;
		if((s.charAt(i)=='(') &( !coll.contains(i))){
			
			val=parse_para(s,i);
			//System.out.println("at last");
			return val;
			
		}
		
		

		
		if((s.charAt(i)>='A') & (s.charAt(i) <='z')){
			val=parse_pred(s,i);
			//System.out.println("here>>"+val.traverse());
			

			return val;


		}
		if(s.charAt(i)==' '){
			 //System.out.println("here>>>>>>");
			 val=parse(s,i+1);
			 return val;
		}
		if(s.charAt(i)==')'){
			return val;
		}

		if(coll.contains(i)){
			//System.out.println("indi negative");
			val=new neg(parse_pred_neg(s,i+1));
			//System.out.println(val.traverse());
			return val;

		}


		if(s.charAt(i)=='~'){
			
			//if(check_neg(s,i)==1){
				//System.out.println("whole negative");
				//val=parse_para_neg(s,i);
				val=parse(s,i+1);
				val=new neg(val);
				//System.out.println("back");
				//System.out.println(val.traverse());
				return val;
			//}
			/*else{
			System.out.println("indi negative");
			val=new neg(parse_pred_neg(s,i));
			System.out.println(val.traverse());
			return val;
			}*/
			
			
			

		}
		
		return null;

	}

	public static input parse_para(String s,int i){
		//System.out.println("part------one");
		input partone=parse(s,i+1);
		//System.out.println("one"+partone);
		//System.out.println("part------two");
		int j=partone.get_length();
		//System.out.println("j="+j);
		j++;
		if (j>=s.length()){
				return partone;
			}
		while(s.charAt(j)==' '){
			j++;
		}
		

		
		int k=j;
		//System.out.println(s.charAt(k+1));
		//System.out.println("j="+j);
		while((s.charAt(j)!='&') & (s.charAt(j)!='|') & (s.charAt(j)!='>')){
			//System.out.println(s.charAt(j));
			
			
			j++;
			if (j==s.length()){
				return partone;
			}
			if(s.charAt(j)==')'){
				//System.out.println(j);
				partone.set_length(j);
				return partone;
			}
			
			
		}
		//System.out.println("char j="+j);
		char op=s.charAt(j);
		
		
		//System.out.println("op="+op);
		//System.out.println("part------three");
		

		input parttwo=parse(s,j+1);
		/*System.out.println("part------four");
		System.out.println("one"+partone);
		System.out.println("two"+parttwo);*/
		
		return new compound(partone,op,parttwo,parttwo.get_length());
		


	}
	

	public static input parse_pred(String s,int i){
		StringBuilder sb=new StringBuilder();
		while((s.charAt(i)!='(') ){
			sb.append(String.valueOf(s.charAt(i)));
			//System.out.println("s1="+s.charAt(i));
			i++;
		}
		//sb.append("(");
		StringBuilder var=new StringBuilder();
		while(s.charAt(i)!=')'){
			sb.append(String.valueOf(s.charAt(i)));
			//System.out.println("s2="+s.charAt(i));
			if(s.charAt(i)!='(')
				var.append(String.valueOf(s.charAt(i)));
			i++;
		}
		sb.append(")");
		//System.out.println(i); 
		
		
		return new pred(sb.toString(),var.toString(),i);
	}
	public static input parse_pred_neg(String s,int i){
		StringBuilder sb=new StringBuilder();
		i++;
		while((s.charAt(i)!='(') ){
			sb.append(String.valueOf(s.charAt(i)));
			//System.out.println(s.charAt(i));
			i++;
		}
		//sb.append("(");
		StringBuilder var=new StringBuilder();
		while(s.charAt(i)!=')'){
			sb.append(String.valueOf(s.charAt(i)));
			//System.out.println(s.charAt(i));
			if(s.charAt(i)!='(')
				var.append(String.valueOf(s.charAt(i)));
			i++;
		}
		sb.append(")");
		//System.out.println(i); 
		
		
		return new pred(sb.toString(),var.toString(),i+2);
	}

 	public static Set<Integer> generate_map(String s){
 		Set<Integer> st=new HashSet<Integer>();
 		Pattern p = Pattern.compile("\\(~ *[A-Z]");
    	Matcher m = p.matcher(s);
    	while(m.find()){
    		st.add(m.start(0));

    	}
    	return st;
 	}


 	
 	public static input convert_cnf(input i){

 		//Remove Imply
 		input after_imply= i.rem_imply();
 		//System.out.println("after_imply="+after_imply.traverse());
 		//Remove Negative
 		input temp=after_imply;
 		input result=after_imply.res_neg();
 		int j=1;
 		while(j>0){
 			if(temp.traverse().equals(result.traverse()))
 				break;
 			temp=result;
 			result=result.res_neg();
 			j++;
 			//System.out.println("result "+j+": "+result.traverse());

 		}
 		input after_neg=result;
 		input temp2=after_neg;
 		input result2=after_neg.dis();
 		j=1;
 		while(j>0){
 			if(temp2.traverse().equals(result2.traverse()))
 				break;
 			temp2=result2;
 			result2=result2.dis();
 			j++;
 			//System.out.println("result_dis "+j+": "+result2.traverse());

 		}
 			
 		
 		
 		

 		return result2;

 	}

 	

 	public static boolean resolution_2(HashSet<String> hs2,input q,HashMap<String,List<input>> hm){
 		counter++;
 		//System.out.println("counter>>>>>>>>>>>>>>>>>>>>>>>>>>>"+counter);

 		if(counter>1000)
 			return false;
 		boolean result=false;
 		int check2=0;
 		input search=q.inverse();
 		//System.out.println("search:"+search.traverse2());
 		//System.out.println("orignal value:"+q.traverse2());
 		String[] predicates=search.get_all_pred().split("-");
 		String[] var_cnst=search.get_all_var_cnst().split("-");
 		//System.out.println("predicates length:"+predicates.length);
 		int len=predicates.length;
 		
 		for(int i=0;i<predicates.length;i++){
 			//System.out.println("pred:="+hm.containsKey(predicates[i].trim())+predicates[i]+"/");
 			 predicates[i]=predicates[i].trim();
 			 if(hm.containsKey(predicates[i])){
 			 	List<input> l=hm.get(predicates[i]);
 			 	//System.out.println("var_cnst=>>>"+var_cnst[i]);
 			 	String p2=predicates[i].replace(")","").replace("(","");
 			 	input temp=null;
 			 		if(p2.contains("~")){
 			 		p2=p2.replace("~","");
 			 		input x=new pred(p2,var_cnst[i],0);
 			 		temp=new neg(x);


 			 		}
 			 		else{
 			 		temp=new pred(p2,var_cnst[i],0);
 			 		}
 			 	
 			 	
 			 		//System.out.println("l=="+l.size());
 			 		for(input o: l){
 			 		int prev_size=hs2.size();
 			 		//System.out.println("next iter:"+o.traverse2()+"temp:"+temp.traverse2());
 			 		HashMap<String,String> hmu=subsit_map(null,o,search);
 			 		
 			 		
 			 		if(hmu==null & len<2)
 			 			result=false;
 			 			//System.out.println("hmu="+hmu);
 			 			if(hmu!=null){
 			 			input res=o.unify(temp,hmu);
 			 			//System.out.println("res="+res.traverse2());
 			 			if(res==null & len==1)
 			 				return true;
 			 	
 			 			if(len>1){
 			 			input second_part=q.get_second_part(temp.inverse());
 			 		
 			 			if(second_part!=null){
 			 			//System.out.println("second_part:"+second_part.traverse2()+"*********");
 			 			res=second_part.join_parts(res);
 			 			//System.out.println("RES in second_part:"+res);
 			 			input res3=null;
 			 			if(res instanceof compound)
 			 				 res3=new compound((compound)res);
 			 			else if(res instanceof neg)
 			 				 res3=new neg((neg) res);
 			 			else if(res instanceof pred)
 			 				 res3=new pred((pred) res);

 			 			String[] oldval=res3.getvar_values();
 			 			
 			 			
 			 			res3.setvar_new(hmu);
 			 			//System.out.println("second_part_res:"+res3.traverse2()+"**************");
 			 			
 			 			res=res3;
 			 			
 			 			}

 			 				


 			 			}
 			 			
 			 		
 			 			if(res!=null){
 			 			//System.out.println("after resolution:"+res.traverse2());
 			 			hs2.add(res.traverse2());
 			 			if(prev_size==hs2.size()){
 			 				//System.out.println("size not growing");
 			 				return false;

 			 			}
							
						else
							hm=update_map(hm,res);
						result=resolution_2(hs2,res,hm);
						
						hm=remove_from_hm(hm,res);
						if(result==true)
							return true;
						}

							}
 						result=false;
 						} 
 				}		



 		

		

 		}
 		//System.out.println("returning back");
 		return result;
 	}
 	public static HashSet<input> copy_all(HashSet<input> hs){

 		HashSet<input> r=new HashSet<input>();
 		for(input i:hs){
 			r.add(i);
 		}
 		return r;

 	}

 	public static HashMap<String,List<input>> update_map(HashMap<String,List<input>> hm,input t){
 			String all_pred=t.get_all_pred();
			String all_vcns=t.get_all_var_cnst();
			//System.out.println(all_pred);
			//System.out.println("var/cnts:"+all_vcns);
			String[] predi=all_pred.split("-");
			//System.out.println(predi.length);
			for(int i=0;i<predi.length;i++){
				String tmp=t.traverse2();
				if(tmp.contains(predi[i])){
					if(hm.get(predi[i])==null){
						List<input> li=new ArrayList<input>();
						li.add(t);
						hm.put(predi[i],li);

					}
					else{
						List<input> li=hm.get(predi[i]);
						li.add(t);
						hm.put(predi[i],li);
					}
					
				}

			}
		return hm;

 	}
 	public static HashMap<String,List<input>> remove_from_hm(HashMap<String,List<input>> hm,input r){
 		Set<String> st=hm.keySet();
 		//System.out.println("removing from hm:"+r.traverse2()+"obj:"+r);
 		for(String s:st){
 			List<input> li=hm.get(s);
 			li.remove(r);
 			hm.put(s,li);
 		}

 		return hm;

 	}

 	public static HashMap<String,String> subsit_map(HashMap<String,String> hm,input e1,input e2){
 		HashMap<String,String> hm1;
 		HashMap<String,String> hm2;
 		
 		
 		if(e1 instanceof compound){
 			compound e11=(compound) e1;

 			hm1=subsit_map(hm,e11.getpart1(),e2);
 			if(hm1!=null)
 				return hm1;
 			hm2=subsit_map(hm,e11.getpart2(),e2);
 			if(hm2!=null)
 				return hm2;


 		}
 		else if(e2 instanceof compound){
 			compound e22=(compound) e2;
 			hm1=subsit_map(hm,e1,e22.getpart1());
 			if(hm1!=null)
 				return hm1;
 			hm2=subsit_map(hm,e1,e22.getpart2());
 			if(hm2!=null)
 				return hm2;

 		}
 		//System.out.println("e1="+e1.traverse());
 		//System.out.println("e2="+e2.traverse());
 		//String e1_value=e1.traverse().replace("(","").replace(")","").replace("~","");
 		//String e2_value=e2.traverse().replace("(","").replace(")","").replace("~","");
 		if(e1.traverse().equals(e2.traverse())){
 			
 			hm=new HashMap<String,String>();
 			String s1=e1.get_all_var_cnst();
 			String s2=e2.get_all_var_cnst();
 			//System.out.println("s1="+s1+"s2="+s2);
 			String[] s1a=s1.replace("-","").split(",");
 			String[] s2a=s2.replace("-","").split(",");
 			if(s1a.length==s2a.length){
 				for(int i=0;i<s1a.length;i++){
 					Pattern p=Pattern.compile("[v]\\d\\d?");
 			 		Matcher m1=p.matcher(s1a[i]);
 			 		Matcher m2=p.matcher(s2a[i]);
 			 		if(m1.find(0) & !m2.find(0)){
 			 			hm.put(s1a[i],s2a[i]);
 			 		}
 			 		else if(m2.find(0) & !m1.find(0)){
 			 			hm.put(s2a[i],s1a[i]);
 			 		}
 			 		else if(m1.find(0) & m2.find(0)){
 			 			hm.put(s2a[i],s1a[i]);

 			 		}
 			 		else if(s1a[i].equals(s2a[i])){
 			 			//System.out.println("equal constants");
 			 			hm.put(s2a[i],s1a[i]);

 			 		}
 			 		else
 			 			return null;

 				}
 			}
 			if(hm.size()==0){
 				//System.out.println("not unifiable");
 				return null;
 			}
 			for(String s:hm.keySet()){
 				//System.out.println("key:"+s+"value:"+hm.get(s));
 			}
 			return hm;

 		}



 		
 			

 	return null;
 	}

 	public static boolean does_it_contains(input a,input c){
 		
 		if(a instanceof compound){
 			compound t=(compound) a;
 			boolean a1=does_it_contains(t.getpart1(),c);
 			boolean a2=does_it_contains(t.getpart2(),c);
 			return a1||a2;
 		}
 		if(a.traverse().equals(c.traverse())){
 			return true;
 		}
 		else
 			return false;
 		
 	}

 	public static String preprocess(String s){
 		
 		s=s.replace(" ","*");
    
	    StringBuilder pre=new StringBuilder();
	    for(int d=0;d<s.length();d++){
	      pre.append(s.charAt(d));
	      if(s.charAt(d)=='&'||s.charAt(d)=='|'||s.charAt(d)=='>'){
	          
	          pre.setCharAt(d-1,'#');
	          pre.append(s.charAt(d));
	          
	          pre.setCharAt(d+1,'#');
	          d=d+1;
	        
	      }
	      
	      
	    }
	    return pre.toString().replace("*","").replace("#"," ");
 	}

}
