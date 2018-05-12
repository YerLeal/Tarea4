package domain;

import utility.Buffer;

public class SynchronizedBuffer implements Buffer {

    private int buffer = -1;
    private int occupiedBufferCount = 0;
    private boolean paint = false;

    @Override
    public synchronized void set(int value) {
//        String name = Thread.currentThread().getName();
        while (this.occupiedBufferCount == 1) {
            try {
                wait();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        } // while
        this.buffer = value;
        this.paint = true;
        ++this.occupiedBufferCount;
//        displayState(name + " escribe " + this.buffer);
        notify();
    } // set

    @Override
    public synchronized int get() {
        String name = Thread.currentThread().getName();
        while (this.occupiedBufferCount == 0) {
            try {
//                System.out.println(name + " trató de leer.");
//                displayState("Buffer limpio. " + name + " en espera.");
                wait();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        --this.occupiedBufferCount;
//        displayState(name + " lee " + buffer);
        notify();
        return this.buffer;
    } // get
    
    public boolean isPaint(){
        return this.paint;
    }
    
    public void setPaint(boolean paint){
        this.paint = paint;
    }

//    public void displayState(String operation) {
//        StringBuffer outputLine = new StringBuffer(operation);
//        outputLine.setLength(40);
//        outputLine.append(buffer + "\t\t" + occupiedBufferCount);
//        System.out.println(outputLine);
//        System.out.println();
//    } // displayState

} // fin de la clase
