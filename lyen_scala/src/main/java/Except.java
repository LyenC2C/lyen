import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyen on 16-9-13.
 */
public class Except {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, -1, 5, -8, -10, 7};
        int flag = 0;
        List<Integer> list = new ArrayList<Integer>();
        for (int a : arr) {
            if(a < 0){
                flag += 1;
            }
            if(a>0 || flag <2){
                list.add(a);
            }
        }
        for(int as: list){
            System.out.print(as+ ",");
        }
    }
}
