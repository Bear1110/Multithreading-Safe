
public class ICC {
    private static ICC ICC;
    
    public static ICC getInstence(){
        if(ICC == null)
           ICC = new ICC();
        return ICC;
    }
    boolean busy ;
    int currentframe = 0 ;
    HWframebuffer[] hwframe = new HWframebuffer[2];
    boolean[] framestatus = new boolean[2]; // “true” means the image data in HWframebuf is new
    char c = 'A' ;  // character c will be rotated from ‘A’ to ‘Z’ always
    
    public ICC(){
        for(int i = 0 ; i < hwframe.length ; i++)
            hwframe[i] = new HWframebuffer();
    }

    public boolean isbusy(){
        return busy;
    }
    
    public synchronized int capture() {
        int w = currentframe;
        
        try {
            while(framestatus[w])
                wait();             
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        busy = true ;
        hwframe[w].write(c);
        
        try {
            Thread.sleep(100);// put a reasonable delay for your own debugging
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
        framestatus[w] = true ;
        currentframe = 1 - currentframe;
        busy = false ;
        if ( c=='Z') c = 'A' ;
        else c++ ;
        return w ;
    }
    

    public synchronized void memcopy(char[][] userbuffer, int fno) {
        // let’s simulate the memory copy 
        for (int x=0; x<1000 ; x++) {
          for (int y=0;y<1000; y++)
             userbuffer[x][y] = hwframe[fno].getpixel(x,y) ;
        }
        framestatus[fno] = false;
        notify();
   }
    
   class HWframebuffer {
       char hwdata[][] = new char[1000][1000];
       public void write(char c) {
            // let’s simulate writing data into hardware framebuffer 
            int i,j ;
            for ( i = 0 ; i < 1000 ;i++) {
              for ( j = 0; j < 1000 ;j++)
                 hwdata[i][j]= c ;
            }
       }
       public char getpixel(int x, int y) { return hwdata[x][y] ; }
   }    
}
