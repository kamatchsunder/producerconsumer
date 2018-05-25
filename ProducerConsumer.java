package threading;

import java.util.LinkedList;
import java.util.Queue;


public class ProducerConsumer {

    public static void main(String[] args) {
        Queue queue = new LinkedList();

        Producer p = new Producer(queue);

        Consumer c = new Consumer(queue);

        new Thread(p).start();
        new Thread(c).start();

    }

    static class Producer implements Runnable{

        private Queue queue;

        Producer(Queue queue){

            this.queue = queue;
        }

        public void run(){

            int i=0;
            while(true){

                synchronized (queue){

                    if(i==20){
                        break;
                    }
                    if(queue.size() >0){
                        try {
                            queue.wait();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    queue.add(i);
                    System.out.println("Produces  :"+i);
                    queue.notifyAll();
                    i++;


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

            int i=0;
            while(true){

                synchronized (queue){

                    if(queue.isEmpty()){
                        try {
                            queue.wait();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    Integer number = (Integer)queue.poll();

                    System.out.println("Consumes :"+number);

                    queue.notifyAll();
                    if(number==20){
                        break;
                    }

                }
            }
        }
    }
}

