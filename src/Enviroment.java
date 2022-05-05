import java.io.FileWriter;
import java.io.IOException;

public class Enviroment {
    public static int[][] enviroment;
    public static void write(int[] s, int[] f){
        try(FileWriter writer = new FileWriter("text.txt", false))
        {
            writer.write(s[0]+" "+s[1]);
            writer.append('\n');
            writer.write(f[0]+" "+f[1]);
            writer.append('\n');
            for(int i=0;i<300;i++){
                for(int j=0;j<300;j++){
                    writer.write(enviroment[i][j]+" ");
                }
                writer.append('\n');
            }
            writer.flush();
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
