package pdp;

import mpi.MPI;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        DSM dsm = new DSM();
        System.out.println("Start "+me+" of "+size);
        if (me == 0) {
            Thread thread = new Thread(new Subscriber(dsm));

            thread.start();

            dsm.subscribeTo("a");
            dsm.subscribeTo("b");
            dsm.subscribeTo("c");
            dsm.checkAndReplace("a",0,20);
            dsm.checkAndReplace("b",1,40);
            dsm.checkAndReplace("c",2, 60);
            dsm.close();

            thread.join();

        } else if (me == 1) {
            Thread thread = new Thread(new Subscriber(dsm));

            thread.start();

            dsm.subscribeTo("a");
            dsm.subscribeTo("c");

            thread.join();
        } else if (me == 2) {
            Thread thread = new Thread(new Subscriber(dsm));

            thread.start();
//
            dsm.subscribeTo("b");
            dsm.checkAndReplace("b", 2, 50);
//
            thread.join();
        }
        MPI.Finalize();
    }
}
