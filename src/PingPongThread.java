public class PingPongThread extends Thread
{
   // Fields
   String text;  
   int timeout;  
   
   public PingPongThread(String text, int timeout) // Constructor
   {
      this.text = text;
      this.timeout = timeout;
   }

   // Thread.run()
   public void run()
   {
      for (int i = 0; i < 100; i++) {
         System.out.print(" " + text + " ");
         try  { 
            Thread.sleep(timeout);
         } catch (InterruptedException exc){}
      }
   }

   public static void main(String[] args)
   {
       (new PingPongThread("ping", 300)).start();
       (new PingPongThread("PONG", 300)).start();
   }
}