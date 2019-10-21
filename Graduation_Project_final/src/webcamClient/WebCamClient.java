
package webcamClient;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;



/**
 *
 * @author imran
 */
public class WebCamClient {
    public static DatagramSocket ds;

    public WebCamClient(String IP,int port) throws Exception {
        ds = new DatagramSocket();
        //"172.30.1.50",6782
        byte[] init = new byte[62000];
        init = "givedata".getBytes();
        
        //InetAddress addr = InetAddress.getLocalHost();
        InetAddress addr = InetAddress.getByName(IP);

        DatagramPacket dp = new DatagramPacket(init,init.length,addr,4321);
        
        ds.send(dp);
        
        DatagramPacket rcv = new DatagramPacket(init, init.length);
        
        ds.receive(rcv);
        System.out.println(new String(rcv.getData()));
        
        System.out.println(ds.getPort());
        Vidshow vd = new Vidshow();
        new Thread(vd).start();
        
        String modifiedSentence;

        InetAddress inetAddress = InetAddress.getLocalHost();
        //.getByName(String hostname); "CL11"
        System.out.println(inetAddress);

        Socket clientSocket = new Socket(addr, port);
        DataOutputStream outToServer =
                new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outToServer.writeBytes("Thanks man\n");

 

        
        clientSocket.close();
    }
}

class Vidshow extends JFrame implements Runnable  {

    public static JPanel jp = new JPanel(new GridLayout(2,1));
    public static JPanel half = new JPanel(new GridLayout(3,1));
    JLabel jl = new JLabel();
    public static JTextArea ta,tb;
    
    byte[] rcvbyte = new byte[62000];
    
    DatagramPacket dp = new DatagramPacket(rcvbyte, rcvbyte.length);
    BufferedImage bf;
    ImageIcon imc;
    
    
    public Vidshow() throws Exception {
        //sc = mysoc;
        //sc.setTcpNoDelay(true);
        setSize(640, 960);
        setTitle("Client Show");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setLayout(new BorderLayout());
        setVisible(true);
        jp.add(jl);
        jp.add(half);
        add(jp);
        
        
        JScrollPane jpane = new JScrollPane();
        jpane.setSize(300, 200);
        ta = new JTextArea();
        tb = new JTextArea();
        
        jpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jpane.add(ta);
        jpane.setViewportView(ta);
        half.add(jpane);
        half.add(tb);
        ta.setText("Begins\n");
        
        
    }

    @Override
    public void run() {

        try {
            System.out.println("got in");
            do {
                System.out.println("doing");
                System.out.println(WebCamClient.ds.getPort());
                
                WebCamClient.ds.receive(dp);
                System.out.println("received");
                ByteArrayInputStream bais = new ByteArrayInputStream(rcvbyte);
                
                bf = ImageIO.read(bais);

                if (bf != null) {
                    //jf.setVisible(true);
                    imc = new ImageIcon(bf);
                    jl.setIcon(imc);
                    //jp.add(jl);
                    //jf.add(jp);
                    Thread.sleep(15);
                }
                revalidate();
                repaint();
                

            } while (true);

        } catch (Exception e) {
            System.out.println("couldnt do it");
        }
    }
}



