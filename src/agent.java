import java.util.ArrayList;
import java.util.Map;

public class agent {
    public static ArrayList<int[]> stan = new ArrayList<>();
    public static  ArrayList<String> Actions(int[] position) {
        System.out.println("action");
        ArrayList<String> can = new ArrayList<>();
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if(i!=0 || j!=0){
                    try{
                    if (Math.abs(Enviroment.enviroment[position[0]][position[1]] - Enviroment.enviroment[position[0] + i][position[1]+ j]) <= 2) {
                        can.add(Main.canDo.get(i+" "+j));
                    }
                }catch (Exception r){

                    }
                }
        return can;
    }
    static int Price(String move, int[] position, int[] finish) {
        int[] s = new int[]{Main.doCan.get(move)[0], Main.doCan.get(move)[1]};
        s[0]+=position[0];
        s[1]+=position[1];

        int mass = Math.abs(Enviroment.enviroment[s[0]][s[1]]-Enviroment.enviroment[position[0]][position[1]]);
        System.out.println("Price1: "+mass);
        for(int i=0;i<stan.size();i++){
            if(stan.get(i)[0]==s[0]&&stan.get(i)[1]==s[1]){
                mass++;
                mass+=5;
            }
        }
        System.out.println("Price2: "+mass);
        int f = Math.abs(finish[0]-s[0]);
        int p = Math.abs(finish[0]-position[0]);
        int ss = s[0]+s[1];
        mass+=(f-p)-(Math.abs(finish[1]-position[1])-Math.abs(finish[1]-s[1]));
return mass;
    }
    public static String findBest(ArrayList<String> canDo, int[] position, int[] finish){
        int number = 0;
        int price = 100;
        for(int i=0;i< canDo.size();i++){
            int p = Price(canDo.get(i),position,finish);
            if(p<price){
                number = i;
                price = p;
            }
            System.out.println("Price3: "+p+" "+Main.doCan.get(canDo.get(i))[0]+" "+Main.doCan.get(canDo.get(i))[1]);
        }
        try{
        return canDo.get(number);
        }catch (Exception t) {
            return "null";
        }
    }
}
