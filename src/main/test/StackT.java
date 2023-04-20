import java.lang.reflect.Array;

public class StackT<T> {

    private int maxSize;
    private T[] items;
    private int top;

    public StackT(int maxSize, Class<T> clazz){
        this.maxSize = maxSize;
        this.items = this.createArray(clazz);
        this.top = -1;
    }

    public boolean isFull(){
        return this.top == this.maxSize-1;
    }

    public boolean isNull(){
        return this.top <= -1;
    }

    public boolean push(T value){
        if(this.isFull()){
            return false;
        }
        this.items[++this.top] = value;
        return true;
    }

    public T pop(){
        if(this.isNull()){
            throw new RuntimeException("当前栈中无数据");
        }
        T value = this.items[top];
        --top;
        return value;
    }

    private T[] createArray(Class<T> clazz){
        T[] array =(T[]) Array.newInstance(clazz, this.maxSize);
        return array;
    }

}

