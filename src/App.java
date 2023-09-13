public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        for(int i=1;i<3;i++){
            ThreadThing myThing = new ThreadThing();
            myThing.start();
        }     
    }
}
