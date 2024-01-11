package tf.tfischer.musicbooks.interpreter.objects;


import java.util.LinkedList;

public class CharStack {
    private LinkedList<Character> stack = new LinkedList<>();

    public void addString(String str){
        for(char c : str.toCharArray()){
            stack.addLast(c);
        }
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    public Character peek(){
        return stack.peek();
    }
}
