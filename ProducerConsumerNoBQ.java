package threading;

public class ProducerConsumerNoBQ {

    public static void main(String[] args) {

        MyOwnQueue queue = new MyOwnQueue();

        new Thread(new ProducerConsumerNoBQ.Producer(queue)).start();
        new Thread(new ProducerConsumerNoBQ.Consumer(queue)).start();
    }

    static class MyOwnQueue{

        int data;
        private volatile boolean isValueAdded = false;

        public synchronized void add(int data){

            this.data = data;
            isValueAdded = true;
            notifyAll();
        }

        public synchronized  int remove(){


            if(!isValueAdded){
                try {
                    wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notifyAll();
            isValueAdded=false;
            return data;

        }

    }



    static class Producer implements Runnable{

        private MyOwnQueue queue;

        Producer(MyOwnQueue queue){

            this.queue = queue;
        }

        public void run(){

            for (int i = 1; i <=20 ; i++) {

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

        private MyOwnQueue queue;

        Consumer(MyOwnQueue queue){
            this.queue = queue;
        }

        public void run(){

            while(true){

                try {

                    int i = queue.remove();
                    if(i==20){
                        break;
                    }
                    System.out.println("Consumes :-"+ i);
                }
                catch (RuntimeException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
