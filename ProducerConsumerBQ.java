package threading;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


public class ProducerConsumerBQ {

    public static void main(String[] args) {

        Queue queue = new ArrayBlockingQueue(20);

        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }

    static class Producer implements Runnable{

        private Queue queue;

        Producer(Queue queue){

            this.queue = queue;
        }

        public void run(){

            for (int i = 0; i <20 ; i++) {

                try {
                    Thread.sleep(1000);

                    queue.add(i);
                    System.out.println("Produces :-"+ i);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    static class Consumer implements Runnable{

        private Queue queue;

        Consumer(Queue queue){
            this.queue = queue;
        }

        public void run(){

            while(true){

                try {
                    Thread.sleep(1000);

                    if(queue.isEmpty()){
                        break;
                    }
                    int i = (Integer)queue.poll();
                    System.out.println("Consumes :-"+ i);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
