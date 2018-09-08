import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("toto");
        list.add("tata");
        list.forEach( l -> System.out.println(l));
        list.retainAll(Arrays.asList("toto"));
        list.forEach( l -> System.out.println(l));
    }

}
