class TicketReservationSystem {
    private int availableSeats;

    public TicketReservationSystem(int totalSeats) {
        this.availableSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    //synchronized to avoid data racing issue
    // public synchronized void bookTickets(int numberOfTickets) {
    //     if (availableSeats >= numberOfTickets) {

    //         try {
    //             Thread.sleep(500); // Using delay to make data race issue bigger
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //         availableSeats -= numberOfTickets;
    //         System.out.println(Thread.currentThread().getName() + " booked " + numberOfTickets + " tickets.");
    //     } else {
    //         System.out.println(Thread.currentThread().getName() + " couldn't book tickets. Not enough available seats.");
    //     }
    // }

    public void bookTickets(int numberOfTickets) {
        boolean canBook = false;

        synchronized (this) {
            if (availableSeats >= numberOfTickets) {
                availableSeats -= numberOfTickets;
                canBook = true;
            }
        }

    if (canBook) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // availableSeats -= numberOfTickets;
            System.out.println(Thread.currentThread().getName() + " booked " + numberOfTickets + " tickets.");
        } else {
            System.out.println(Thread.currentThread().getName() + " couldn't book tickets. Not enough available seats.");
        }
    }
}

public class task1 {
    public static void main(String[] args) {
        TicketReservationSystem reservationSystem = new TicketReservationSystem(15);

        Runnable bookingTask = () -> {
            for (int i = 0; i < 5; i++) {
                reservationSystem.bookTickets(2);
            }
        };

        Thread thread1 = new Thread(bookingTask, "User 1");
        Thread thread2 = new Thread(bookingTask, "User 2");

        thread1.start();
        thread2.start();
    }
}
