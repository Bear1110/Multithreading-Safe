
public class Client implements Runnable{
    char[][] mybuffer = new char[1000][1000];
    ICC imc;// get the unique object of ICC
    
    public Client(){
        imc = ICC.getInstence();        
    }
    
    public void run() { // override Thread's run()
        for (;;) {
            int fno = imc.capture();  
            imc.memcopy(mybuffer, fno);
            // assert all elements in mybuffer[x][y] are the same
            // to prove that your synchronization is correct.
            char c = mybuffer[0][0];
            for (int x=0;x<1000;x++) {
                 for (int y=0;y<1000;y++) {
                   assert mybuffer[x][y] == c;
                   //System.out.println(mybuffer[x][y]);
                 }
            }
        }
    }
    
}
