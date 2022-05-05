import javax.imageio.ImageIO;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.python.util.PythonInterpreter;

public class Main extends JFrame {
    public static BufferedImage map;
    static int[] position = {19,0};
    static int[] finishPos = {19,0};
    Main(){
        super("Карта");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTextField start = new JTextField();
        JTextField finish = new JTextField();
        start.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
             String[] pos =  start.getText().split(" ");
             position[0]=Integer.parseInt(pos[0]);
             position[1]=Integer.parseInt(pos[1]);
             map.setRGB(position[0],position[1],0x000000);
            }
        });
        finish.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] pos =  finish.getText().split(" ");
                finishPos[0]=Integer.parseInt(pos[0]);
                finishPos[1]=Integer.parseInt(pos[1]);
                map.setRGB(finishPos[0],finishPos[1],0x000000);
                System.out.println(finishPos[0]+" "+finishPos[1]);
            }
        });
        start.setToolTipText("Начало маршрута");
        finish.setToolTipText("Конец маршрута");
        File f = new File("Picture3.png");
        try {
            map = ImageIO.read(f);
        }catch (Exception e){

        }
        JPanel contents = new JPanel(){
            Graphics2D g2;
            protected void paintComponent(Graphics g ){
                super.paintComponent(g);
                g2=(Graphics2D)g;
                g2.drawImage(map,10,35,this);

            }

        };
        JButton jButton = new JButton("Действие");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Enviroment.write(position,finishPos);

               /*while(position[0]!=finishPos[0]||position[1]!=finishPos[1]){
                ArrayList<String> can = agent.Actions(position);
                String move = agent.findBest(can,position,finishPos);
                int[] moves = doCan.get(move);
                position[0]+=moves[0];
                position[1]+=moves[1];
                System.out.println(position[0]+","+position[1]);
                map.setRGB(position[0],position[1],0xFF0000);
                agent.stan.add(new int[]{position[0],position[1]});
                }*/
                try{
                    StringWriter writer = new StringWriter();
                    ScriptEngineManager manager = new ScriptEngineManager();
                    ScriptContext context = new SimpleScriptContext();
                    context.setWriter(writer);
                    ScriptEngine engine = manager.getEngineByName("python");
                    engine.eval(new FileReader("agent.py"), context);
                }catch (Exception u){
                    System.out.println(u.getLocalizedMessage());
                }
                try(BufferedReader bufferreader = new BufferedReader(new FileReader("text2.txt"))){
                    String str = bufferreader.readLine();
                    System.out.println("str");
                    String[] pos = str.split(" ");
                    for(int i=0;i<pos.length;i+=2){
                        System.out.println(pos[i]+" "+pos[i+1]);
                        map.setRGB(Integer.parseInt(pos[i]),Integer.parseInt(pos[i+1]),0xFF0000);
                    }
                }catch (Exception k){
                    System.out.println(k.getMessage());
                };
                update(getGraphics());
           }
        });
        contents.setLayout(null);
        jButton.setBounds(10,345,300,15);
        start.setBounds(10,10,145, 20);
        finish.setBounds(165,10,145, 20);
        contents.add(finish);
        contents.add(start);
        contents.add(jButton);
        setContentPane(contents);
        // Определяем размер окна и выводим его на экран
        setResizable(false);
        setSize(335, 400);
        setVisible(true);
    }
    static Map<String,String> canDo = new HashMap<>();
    static Map<String,int[]> doCan = new HashMap<>();

    public static void main(String[] args){
        agent.stan.add(new int[]{position[0],position[1]});
        canDo.put("-1 0","Up");
        canDo.put("1 0","Down");
        canDo.put("0 1","Right");
        canDo.put("0 -1","Left");
        canDo.put("-1 1","Up-Right");
        canDo.put("1 1","Down-Right");
        canDo.put("-1 -1","Up-Left");
        canDo.put("1 -1","Down-Left");
        canDo.put("0 0","null");
        doCan.put("Up",new int[]{-1,0});
        doCan.put("Down",new int[]{1,0});
        doCan.put("Right",new int[]{0,1});
        doCan.put("Left",new int[]{0,-1});
        doCan.put("Up-Right",new int[]{-1,1});
        doCan.put("Down-Right",new int[]{1,1});
        doCan.put("Up-Left",new int[]{-1,-1});
        doCan.put("Down-Left",new int[]{1,-1});
        doCan.put("null",new int[]{0,0});
        setMap();
       new Main();
    }
    static void setMap(){
       Enviroment.enviroment = new int[300][300];
        File f = new File("Picture2.png");
        try {
            map = ImageIO.read(f);
        }catch (Exception e){

        }
        for(int i=0;i<300;i++)
            for(int j=0;j<300;j++)
            {
                int number = map.getRGB(i,j);
                int r = (number & 0x00ff00) >> 8;
                Enviroment.enviroment[i][j] = r;
            }
    }
}
